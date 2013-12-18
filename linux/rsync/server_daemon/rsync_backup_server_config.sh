#!/bin/sh
#rsync_backup_server_config.sh
#Used to config the rsync daemon service in backup server
#create rsyncd.conf file
cp ./rsyncd.conf /etc/rsyncd.conf
#create rsync virtual user
echo "rsync_backup:rs_bakup" > /etc/rsync.pwd
#change file privilege
chmod 600 /etc/rsync.pwd 
cat /etc/rsync.pwd
ls -l /etc/rsync.pwd
# set rsync service startup automatically
echo "/usr/bin/rsync --daemon" >> /etc/rc.local
mkdir -p /opt/var/appfiles
chown -R nobody.nobody /opt/var/appfiles/
