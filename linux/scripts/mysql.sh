#!/bin/bash
#mysql.sh

nc -w2 localhost 3306
if [ $? -ne 0 ]
then
        echo "Mysql's 3306 port is down, pls check it ASAP."|mail user1@anhry.com -s "Mysql is down"
        #restart mysql
        /usr/local/mysql/bin/mysqld_safe --user=mysql &
fi
