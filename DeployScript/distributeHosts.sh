#!/bin/sh
#Used to distribute /etc/hosts file to other servrs
. /etc/init.d/functions

#for n in 91 81 82 
#do 
#	scp -P22 hosts oldgirl@192.168.137.$n
#done
if [ $# -ne 1 ] 
then
  echo "Usage: $0 {FILENAME|DIRNAME}"
  exit;
fi

for n in 90 81 82 
do 
	scp -P22 -r $1 oldgirl@192.168.137.$n:~ &>/dev/null
	if [ $? -eq 0 ]
	then
		action "Distribute $1 ok" /bin/true
	else 
		action "Distribute $1 fail" /bin/false
        fi
done

