#!/bin/sh
#inotify.sh
#Create by Song haitao 
#parameter 
backuphost=192.168.0.135
src=/backup
dst=appfiles
user=rsync_backup
rsync_passwdfile=/etc/rsync.pwd
inotify_home=/usr/local/inotify

#judge
if [ ! -e "$src" ] \
|| [ ! -e "$rsync_passwdfile" ] \
|| [ ! -e "$inotify_home/bin/inotifywait" ] \
|| [ ! -e "/usr/bin/rsync" ];
then 
  echo "Check inotify and rsync files and folder"
  exit 9
fi

$inotify_home/bin/inotifywait -mrq --timefmt '%d/%m/%y %H:%M' --format '%T %w%f' -e close_write,delete,create,attrib $src|\
while read file
  do 
    cd $src && rsync -aruz -R --delete ./ --timeout=100 $user@$backuphost::$dst --password-file=$rsync_passwdfile > /dev/null 2>&1
  done
exit 0
