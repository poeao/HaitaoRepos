#!/bin/bash
cp ./jdk-6u38-linux-i586.bin /usr/local
#dos2unix profile
cp -fp ./profile /etc/profile
cd /usr/local
chmod u+x ./jdk-6u38-linux-i586.bin
sh /usr/local/jdk-6u38-linux-i586.bin
cd /usr/bin
ln -s -f /usr/local/jdk1.6.0_38/jre/bin/java
ln -s -f /usr/local/jdk1.6.0_38/bin/javac
source /etc/profile
rm -rf /usr/local/jdk-6u38-linux-i586.bin