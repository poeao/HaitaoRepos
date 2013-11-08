#!/bin/bash
current_path=`pwd`
echo "++++++++++++++++++++++++"
echo "-------开始部署JDK------"
echo "++++++++++++++++++++++++"
cd ${current_path}/jdk_installer/
dos2unix jdk_installer.sh
chmod +x jdk_installer.sh
./jdk_installer.sh
source /etc/profile
echo "JAVA_HOME:"$JAVA_HOME
echo "++++++++++++++++++++++++"
echo "-------部署JDK完成------"
echo "++++++++++++++++++++++++"
sleep 2

echo "++++++++++++++++++++++++++"
echo "-------开始部署tomcat6------"
echo "++++++++++++++++++++++++++"
cd ${current_path}/tomcat6_installer/
dos2unix tomcat_installer.sh
chmod +x tomcat_installer.sh
./tomcat_installer.sh
echo "++++++++++++++++++++++++++"
echo "-------部署tomcat完成------"
echo "++++++++++++++++++++++++++"
sleep 2

echo "+++++++++++++++++++++++++++++++"
echo "-------开始配置tomcat自启动服务------"
echo "+++++++++++++++++++++++++++++++"
cd ${current_path}/tomcat6_installer
dos2unix autostart_tomcat.sh tomcat
chmod +x autostart_tomcat.sh
./autostart_tomcat.sh
echo "+++++++++++++++++++++++++++++++"
echo "-------配置Tomcat自启动服务完成------"
echo "+++++++++++++++++++++++++++++++"

sleep 2