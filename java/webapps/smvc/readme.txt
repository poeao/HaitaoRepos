1. 使用MAVEN 进行管理JAR包依赖 ,使用jetty 进行测试

Debug模式运行，Run模式下是一样的，
用Debug模式可以在Eclipse中断点运行程序，非常便于调试。
下面我们就让它跑起来吧。运行命令是jetty:run，
Base directory配置是：${workspace_loc:/应用名}，启动调试，
看到如下信息，Jetty就成功启动