#!/bin/bash
#if user anhry not exist then create it
user_count=`cat /etc/passwd|grep anhry|wc -l`
if [ $user_count == 0 ]
then
	useradd anhry
fi
tomcat_tar = "apache-tomcat-6.0.43.tar.gz"
mkdir -p /opt/usr/local/tomcat6/
cp -fp ./$tomcat_tar /opt/usr/local/tomcat6/
#cp ./server.xml /opt/usr/local/tomcat6/
cd /opt/usr/local/tomcat6/
tar zxvf  ./$tomcat_tar
rm -rf ./$tomcat_tar
mv ./apache-tomcat-6.0.43 ./server-8080
#yes|mv ./server.xml ./server-80/conf/server.xml
chown anhry.anhry -R /opt/usr/local/tomcat6/
