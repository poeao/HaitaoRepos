#!/bin/sh
MYUSER=root
MYPASS=root
SOCKET=/data/3306/mysql.sock
MYCMD="mysql -u$MYUSER -p$MYPASS -S $SOCKET"
MYDUMP="mysqldump -u$MYUSER -p$MYPASS -S $SOCKET"
for db in `$MYCMD -e "show databases;"|sed '1,2d'|egrep -v "mysql|schema"` 
do 
	#$MYDUMP $db|gzip > /opt/bak/$db_$(data +%F).sql.gz
	$MYDUMP $db|gzip > /opt/bak/${db}_$(date +%F).sql.gz
done
