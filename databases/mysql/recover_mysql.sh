#!/bin/sh

for db in `ls *sql|sed 's#_bak.sql##g'`
do
	mysql -uroot -proot <${db}.sql
done
