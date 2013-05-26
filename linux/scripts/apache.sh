#!/bin/bash
#apache.sh

nc -w2 191.168.10.1 80
if [ $? -ne 0 ]
then
        echo "Apache's 80 port is down, pls check it ASAP."|mail user1@anhry.com -s "Apache is down"
        #restart apache
        /usr/local/apache2/bin/apachectl restart
fi
