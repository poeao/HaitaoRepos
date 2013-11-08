#!/bin/bash
mkdir -fp /opt/usr/local/tomcat6/
cp -fp apache-tomcat-6.0.36.tar.gz /opt/usr/local/tomcat6/
cd /opt/usr/local/tomcat6/
tar zxvf  apache-tomcat-6.0.36.tar.gz
rm -rf apache-tomcat-6.0.36.tar.gz
mv apache-tomcat-6.0.36 server-8080
chown webadmin.webadmin -R /opt/usr/local/tomcat6/
