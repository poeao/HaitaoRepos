环境：windows 7 + nginx-1.1.5 + memcached-1.2.6-win32-bin + apache-tomcat-7.0.20-windows-x86 + jdk-6u6-windows-i586-p(JDK 1.6，memcached-1.2.6-win32-bin下载地址：http://code.jellycan.com/files/memcached-1.2.6-win32-bin.zip。
需要用到的jar包：
memcached-2.6.jar（http://spymemcached.googlecode.com/files/memcached-2.6.jar）
javolution-5.4.3.1.jar
memcached-session-manager-1.5.1.jar
memcached-session-manager-tc7-1.5.1.jar
msm-javolution-serializer-1.5.1.jar
msm-kryo-serializer-1.5.1.jar
msm-xstream-serializer-1.5.1.jar
以上jar包在http://code.google.com/p/memcached-session-manager/downloads/list中都可以找到。
 
安装Memcached
解压缩memcached-1.2.6-win32-bin.zip，打开cmd，进入memcached-1.2.6-win32-bin目录，运行“memcached.exe –d install”安装memcached的windows服务。
 
         安装niginx
与<<Windows下实现Nginx+Tomcat集群部署方案>>是一样的。
 
安装Tomcat
网上很多资料都是基于Tomcat6的，如果使用Tomcat7需要用到memcached-session-manager-tc7-1.5.1.jar，把上面说到的jar包放在Tomcat7的lib目录下。
         我在测试的时候，设置了两个节点，也就是有两个Tomcat，他们主要的区别在于端口不同，因为我是在一台电脑上进行测试的。
1、server.xml修改
1）<Server port="8005" shutdown="SHUTDOWN">两个Tomcat的port分别为：8005、8006
2）<Connector port="8080" protocol="HTTP/1.1" connectionTimeout="20000" redirectPort="8443" />两个Tomcat的port分别为：8080、8081
3）<Connector port="8009" protocol="AJP/1.3" redirectPort="8443" />两个Tomcat的port分别为：8009、9009
4）<Engine name="Catalina" defaultHost="localhost" jvmRoute="tomcat7-1">两个Tomcat的jvmRoute分别为：tomcat7-1、tomcat7-2
2、context.xml修改
在<Context>标签内加入以下代码：
<Manager className="de.javakaffee.web.msm.MemcachedBackupSessionManager" 
memcachedNodes="n1:localhost:11211" 
requestUriIgnorePattern=".*/.(png|gif|jpg|css|js)$" 
sessionBackupAsync="false" 
sessionBackupTimeout="100"
transcoderFactoryClass="de.javakaffee.web.msm.serializer.javolution.JavolutionTranscoderFactory" 
copyCollectionsForSerialization="false"/>
-------------------------------------------------------------------------------------
这里的memcachedNodes是填写memcached节点,多个节点时可以以空隔分开，
如:  
 n1:localhost:11211 n2:localhost:11212     
/localhost改为安装memcached的服务器的IP 

sessionBackupTimeout的单位为分钟 

/var/www/html改为Tomcat服务器web根目录的路径
**********************************************************************************
CheckedOperatoinTimeoutException:   sessionBackupTimeout="1800000" 
**********************************************************************************
transcoderFactoryClass="de.javakaffee.web.msm.serializer.kryo.KryoTranscoderFactory" 
在tomcat/conf/logging.properties文件中添加de.javakaffee.web.msm.level=FINE，就可以在catalina.out的日志中看到详细的session存取情况
*********************************************************************************************
测试
在两个Tomcat的webapps目录下创建test目录，在test目录下，创建index.jsp文件，文件内容如下：
 
<%@ page contentType="text/html; charset=GBK" %>  
 
<%@ page import="java.util.*" %>  
 
<html><head><title>Cluster Test</title></head>  
 
<body>  
 
<%  
 
  //HttpSession session = request.getSession(true);  
  System.out.println(session.getId());
  out.println("<br> SESSION ID:" + session.getId()+"<br>");
%>
 
</body>  
</html>
我们先启动memcached，打开cmd，进入memcached-1.2.6-win32-bin目录，运行“memcached.exe –p 11211 –d start”；然后启动niginx和两个Tomcat。
打开浏览器，输入http://127.0.0.1/test，我们会看到页面上显示以下内容：
SESSION ID:D91C4F897566168C82A92AF2A36E154B-n1.tomcat7-2
         “D91C4F897566168C82A92AF2A36E154B”为SessionId，“n1”为memcached的节点名称，“tomcat7-2”为目前所访问的web应用服务器的名称



==================Tomcat ProxyPort===================
解决思路2:找到问题原因，修改出错的地方解决
根据上个思路解决了问题以后，一点都没如释重负的感觉，反而各个地方都觉得很空的感觉，因为有好几个疑问没解决，其中包括为啥是80而不是81或者8080没道理？这个Location是不是应该nginx来重写，修改掉那个跳转错的地方是不是比这个思路会更好？

那就先来分析下问题的原因：既然response的Locaiton不对，那么首先想到的就是这个Location是谁构造出来的，了解HTTP协议的人应该都知道，request中的header都是client（浏览器等）构造好发送给服务器的，服务器收到请求以后构造response信息返回给client。那么这样Location这个值肯定就是nginx或者Tomcat给搞出的问题了，这个地方nginx只是一个proxy server，那么response肯定是Tomcat发给nginx的，也就是说我们应该从Tomcat下手来分析这个问题。

首先我就看了下Tomcat的官方文档Proxy Support，这里面对这个介绍如下：

The proxyName and proxyPort attributes can be used when Tomcat is run behind a proxy server. These attributes modify the values returned to web applications that call the request.getServerName() and request.getServerPort() methods, which are often used to construct absolute URLs for redirects. Without configuring these attributes, the values returned would reflect the server name and port on which the connection from the proxy server was received, rather than the server name and port to whom the client directed the original request.

意思就是proxyPort的属性就是用来我这种nginx做前端代理服务器Tomcat在后端处理动态请求的情况的。修改属性的值可以作用于应用的两个方法，主要用于绝对路径和重定向之用。如果不配置的话，可能会不对头。那么既然是这里不对头，我就先把server.xml中我这个http的connector的配置加入了proxyPort="81",重启Tomcat，然后把nginx上步骤的修改注释掉，重启测试。结果基本如所料，完全正常跳转了。

事情到了这个时候，其实问题基本明了了，不过我还是对这个Tomcat为啥默认解析了80端口很是疑惑。我下载了Tomcat的source来看下问题究竟，看了以后用通俗的语言来表述的话就是这样：如果默认不配置proxyPort默认为0，然后在构造response的时候判断如果proxyPort为0那么就不添加端口，不添加端口当然就默认走了80端口，源代码如下：

// FIXME: the code below doesnt belongs to here, 
// this is only have sense 
// in Http11, not in ajp13..
// At this point the Host header has been processed.
// Override if the proxyPort/proxyHost are set 
String proxyName = connector.getProxyName();
int proxyPort = connector.getProxyPort();
if (proxyPort != 0) {
    req.setServerPort(proxyPort);
}
if (proxyName != null) {
    req.serverName().setString(proxyName);
}