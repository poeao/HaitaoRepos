#!/bin/sh
#rsync_client_config.sh
#Created by Song haitao 20131214
#Only password needed in client
echo "rs_bakup" > /etc/rsync.pwd
chmod 600 /etc/rsync.pwd
cat /etc/rsync.pwd
ls -l /etc/rsync.pwd
#install inotify tool
tar zxvf inotify-tools-3.14.tar.gz
cd inotify-tools-3.14
./configure --prefix=/usr/local/inotify-tools-3.14
make && make install
ln -s /usr/local/inotify-tools-3.14 /usr/local/inotify
ls -l /usr/local/|grep inotify
rm -rf ./inotify-tools-3.14
#change inotify prameter in production enviroment
#cd /proc/sys/fs/inotify
#cat "50000000" > /proc/sys/fs/inotify/max_user_watches
#cat "327679" > /proc/sys/fs/inotify/max_queued_events


