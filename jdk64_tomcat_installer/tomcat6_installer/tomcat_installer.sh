#!/bin/bash
useradd webadmin
mkdir -p /opt/usr/local/tomcat6/
cp -fp ./apache-tomcat-6.0.36.tar.gz /opt/usr/local/tomcat6/
cp ./server.xml /opt/usr/local/tomcat6/
cd /opt/usr/local/tomcat6/
tar zxvf  ./apache-tomcat-6.0.36.tar.gz
rm -rf ./apache-tomcat-6.0.36.tar.gz
mv ./apache-tomcat-6.0.36 ./server-80
yes|mv ./server.xml ./server-80/conf/server.xml
chown webadmin.webadmin -R /opt/usr/local/tomcat6/
