#!/bin/bash
#####################################
#tomcat自启动配置脚本。
#在tomcat配置文件里的JAVA_HOME与RESIN_HOME要配置正确。
#####################################
cp tomcat /etc/rc.d/init.d/
chmod +x /etc/rc.d/init.d/tomcat
chkconfig --add tomcat
chkconfig --level 35 tomcat on
