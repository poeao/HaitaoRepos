#!/bin/sh

for dbname in `mysql -uroot -p'root' -e "show databases;"|grep -Evi "database|infor|perfor"`
do 
	mysqldump -uroot -p'root' --events -B ${dbname}|gzip > /opt/bak/${dbname}_bak.sql.gz
done
