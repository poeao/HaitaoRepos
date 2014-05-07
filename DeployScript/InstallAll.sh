#!/bin/bash
current_path=`pwd`
echo "++++++++++++++++++++++++"
echo "-------Start deploy JDK------"
echo "++++++++++++++++++++++++"
cd ${current_path}/jdk_installer/
dos2unix ./jdk_installer.sh
chmod +x ./jdk_installer.sh
./jdk_installer.sh
source /etc/profile
echo "JAVA_HOME:"$JAVA_HOME
echo "++++++++++++++++++++++++"
echo "-------JDK Deployed------"
echo "++++++++++++++++++++++++"
sleep 2

echo "++++++++++++++++++++++++++"
echo "-------Start deploy tomcat6------"
echo "++++++++++++++++++++++++++"
cd ${current_path}/tomcat6_installer/
dos2unix ./tomcat_installer.sh
chmod +x ./tomcat_installer.sh
./tomcat_installer.sh
echo "++++++++++++++++++++++++++"
echo "-------tomcat deployed------"
echo "++++++++++++++++++++++++++"
sleep 2

echo "+++++++++++++++++++++++++++++++"
echo "-------Start deploy tomcat service------"
echo "+++++++++++++++++++++++++++++++"
cd ${current_path}/tomcat6_installer
dos2unix ./autostart_tomcat.sh tomcat
chmod +x ./autostart_tomcat.sh
./autostart_tomcat.sh
echo "+++++++++++++++++++++++++++++++"
echo "-------Tomcat service deployed. -----"
echo "+++++++++++++++++++++++++++++++"

sleep 2
