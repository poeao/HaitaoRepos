#!/bin/sh
MYUSER=root
MYPASS=root
SOCKET=/data/3306/mysql.sock
MYCMD="mysql -u$MYUSER -p$MYPASS -S $SOCKET"
MYDUMP="mysqldump -u$MYUSER -p$MYPASS -S $SOCKET"
for db in `$MYCMD -e "show databases;"|sed '1,2d'|egrep -v "mysql|schema"` 
do 

	mkdir /opt/bak/${db} -p
	for table in `$MYCMD -e  "show tables from ${db};"|sed '1d'`
	do
	#$MYDUMP $db|gzip > /opt/bak/$db_$(data +%F).sql.gz
	$MYDUMP $db $table|gzip > /opt/bak/$db/${table}_$(date +%F).sql.gz
	done
done
