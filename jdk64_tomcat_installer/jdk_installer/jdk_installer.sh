#!/bin/bash
/bin/cp ./jdk-6u38-linux-64.bin /usr/local
/usr/bin/dos2unix profile
/bin/cp -fp ./profile /etc/profile
cd /usr/local
chmod u+x ./jdk-6u38-linux-64.bin
/bin/sh /usr/local/jdk-6u38-linux-64.bin
cd /usr/bin
/bin/ln -s -f /usr/local/jdk1.6.0_38/jre/bin/java
/bin/ln -s -f /usr/local/jdk1.6.0_38/bin/javac
source /etc/profile
/bin/rm -rf /usr/local/jdk-6u38-linux-64.bin
