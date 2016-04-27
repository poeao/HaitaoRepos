#!/bin/bash
#author lic
#date 1304

DISK_NO="/dev/sda1"
NGINX_DIR="/usr/local/tdoa/nginx/sbin/nginx"
MYSQL_DIR="/usr/local/tdoa/mysql/bin/mysqld_safe"
SERVER1="WEB"
SERVER2="MYSQL"
SERVER1_START="/usr/local/tdoa/nginx/sbin/nginx"
SERVER1_STOP="kill -QUIT `cat /usr/local/tdoa/logs/nginx/nginx.pid`"
SERVER1_RESTART="kill -HUP `cat /usr/local/tdoa/logs/nginx/nginx.pid`"
SERVER2_START="/etc/init.d/mysql start"
SERVER2_STOP="/etc/init.d/mysql stop"
SERVER2_RESTART="/etc/init.d/mysql restart"

#date
DATE=`date +"%y-%m-%d %H:%M:%S"`
#ip
IPADDR=`ifconfig eth0|grep 'inet addr'|sed 's/^.*addr://g' |sed 's/Bcast:.*$//g'`
#hostname
HOSTNAME=`hostname -s`
#user
USER=`whoami`
#disk_check
DISK_SDA=`df -h | grep $DISK_NO | awk '{print $5}'`
#cpu_average_check
cpu_uptime=`cat /proc/loadavg | cut -c1-14`

#process_check
function process_check() {
for dir in $NGINX_DIR $MYSQL_DIR
do
process_count=$(ps -ef | grep "$dir" | grep -v grep | wc -l)
for service in nginx mysql 
do
echo "$dir" |grep -q "$service"
if [ $? -eq 0 ]
then
if [ $process_count -eq 0 ]
then
echo "$service.......................[NOT RUN]"
else
echo "$service.......................[RUNNING]"
continue
fi
fi
done
done
}

declare flag=0
clear
while [ "$flag" -eq 0 ]
do
echo "========================================"
process_check
echo "========================================"
cat << EOF

|-----------System Infomation-----------
| DATE       :$DATE
| HOSTNAME   :$HOSTNAME
| USER       :$USER
| IP         :$IPADDR
| DISK_USED  :$DISK_SDA
| CPU_AVERAGE:$cpu_uptime
----------------------------------------
|****Please Enter Your Choice:[0-5]****|
----------------------------------------
(1) Configure $SERVER1 Service
(2) Configure $SERVER2 Service
(3) Configure NETWORKE Service
(4) Change Passwd
(5) Logs
(0) Quit
EOF
read -p "Please enter your choice[0-5]: " input
case $input in
#web service
1)
clear
while [ "$flag" -eq 0 ]
do
cat << EOF
----------------------------------------
|****Please Enter Your Choice:[0-3]****|
----------------------------------------
(1) Start $SERVER1 Service
(2) Stop $SERVER1 Service
(3) ReStart $SERVER1 Service
(0) Back
EOF
	read -p "Please enter your choice[0-3]: " input1
	case $input1 in 
	1)
		echo -e "\n>>>>>>>>>>>$DATE Start $SERVER1">>/log.txt
		$SERVER1_START 2>>/log.txt
		if [ $? == 0 ];then
			echo "Start $SERVER1......................................................[OK]"
		else 
			echo "Start $SERVER1......................................................[FAILED]"
		fi
		sleep 5
		clear
		;;
	2) 	
		echo -e "\n>>>>>>>>>>>$DATE Stop $SERVER1">>/log.txt
		$SERVER1_STOP 2>>/log.txt
		if [ $? == 0 ];then
			echo "Stop $SERVER1.......................................................[OK]"
		else 
			echo "Stop $SERVER1.......................................................[FAILED]"
		fi
		sleep 5
		clear
		;;
	3)	
		echo -e "\n>>>>>>>>>>>$DATE ReStart $SERVER1">>/log.txt
		/usr/local/tdoa/php/sbin/php-fpm restart 2>>/log.txt
		$SERVER1_RESTART 2>>/log.txt
		if [ $? == 0 ];then
			echo "Restart $SERVER1....................................................[OK]"
		else 
			echo "Restart $SERVER1....................................................[FAILED]"
		fi
		sleep 5
		clear
		;;
	0)	
	clear 
	break
	;;
	*) echo "----------------------------------"
	   echo "|          Warning!!!            |"
	   echo "|   Please Enter Right Choice!   |"
	   echo "----------------------------------"
	for i in `seq -w 10 -1 1`
	  do
	    echo -ne "\b\b$i";
	    sleep 1;
	  done
	clear
	;;
	esac
	done
;;
#mysql service
2)
clear
while [ "$flag" -eq 0 ]
do
cat << EOF
----------------------------------------
|****Please Enter Your Choice:[0-3]****|
----------------------------------------
(1) Start $SERVER2 Service
(2) Stop $SERVER2 Service
(3) ReStart $SERVER2 Service
(0) Back
EOF
	read -p "Please enter your Choice[0-3]: " input2
	case $input2 in 
	1)
		echo -e "\n>>>>>>>>>>>$DATE Start $SERVER2">>/log.txt
		$SERVER2_START 2>>/log.txt
		if [ $? == 0 ];then
			echo "Start $SERVER2......................................................[OK]"
		else 
			echo "Start $SERVER2......................................................[FAILED]"
		fi
		sleep 5
		clear
		;;
	2)
		echo -e "\n>>>>>>>>>>>$DATE Stop $SERVER2">>/log.txt
		$SERVER2_STOP 2>>/log.txt
		if [ $? == 0 ];then
			echo "Stop $SERVER2.......................................................[OK]"
		else 
			echo "Stop $SERVER2.......................................................[FAILED]"
		fi
		sleep 5
		clear
		;;
	3)	
		echo -e "\n>>>>>>>>>>>$DATE ReStart $SERVER2">>/log.txt
		$SERVER2_RESTART 2>>/log.txt
		if [ $? == 0 ];then
			echo "Restart $SERVER2....................................................[OK]"
		else 
			echo "Restart $SERVER2....................................................[FAILED]"
		fi
		sleep 5
		clear
		;;
	0)	
	clear 
	break
	;;
	*) echo "----------------------------------"
	   echo "|          Warning!!!            |"
	   echo "|   Please Enter Right Choice!   |"
	   echo "----------------------------------"
	for i in `seq -w 10 -1 1`
	  do
	    echo -ne "\b\b$i";
	    sleep 1;
	  done
	clear
	;;
	esac
	done
;;
#ÍøÂç·þÎñ
3)
clear
while [ "$flag" -eq 0 ]
do
cat << EOF
----------------------------------------
|****Please Enter Your Choice:[0-3]****|
----------------------------------------
(1) ReStart NETWORK Service
(2) Configure NETWORK Service
(0) Back
EOF
	read -p "Please enter your Choice[0-3]: " input3
	case $input3 in
	1)
	  echo -e "\n>>>>>>>>>>>$DATE ReStart Network">>/log.txt
		/etc/init.d/network restart  2>>/log.txt
		if [ $? == 0 ];then
			echo "Restart Network.....................................................[OK]"
		else 
			echo "Restart Network.....................................................[FAILED]"
		fi
		sleep 5
		clear
		;;
	2)  
    clear
		setup
		clear
		;;
	0)	
	clear 
	break
	;;
	*) echo "----------------------------------"
	   echo "|          Warning!!!            |"
	   echo "|   Please Enter Right Choice!   |"
	   echo "----------------------------------"
	for i in `seq -w 10 -1 1`
	  do
	    echo -ne "\b\b$i";
	    sleep 1;
	  done
	clear
	;;
	esac
	done
;;
4)
clear
while [ "$flag" -eq 0 ]
do
cat << EOF
----------------------------------------
|****Please Enter Your Choice:[0-3]****|
----------------------------------------
(1) Change ROOT Passwd
(2) Change Samba Passwd
(0) Back
EOF
	read -p "Please enter your Choice[0-3]: " input5
	case $input5 in
	1)
		echo -e "\n>>>>>>>>>>>$DATE Change System Administertor Root Passwd\n">>/log.txt
		passwd root 2>>/log.txt
		if [ $? == 0 ];then
			echo "Change ROOT Passwd.....................................................[OK]"
		else 
			echo "Change ROOT Passwd.....................................................[FAILED]"
		fi
		sleep 5
		clear
		;;
	2) 
		echo -e "\n>>>>>>>>>>>$DATE Change Samba Passwd">>/log.txt
		smbpasswd -a tdoa 2>>/log.txt
		if [ $? == 0 ];then
			echo "Change Samba Passwd.....................................................[OK]"
		else 
			echo "Change Samba Passwd.....................................................[FAILED]"
		fi
		sleep 5
		clear
		;;
	0)	
	clear 
	break
	;;
	*) echo "----------------------------------"
	   echo "|          Warning!!!            |"
	   echo "|   Please Enter Right Choice!   |"
	   echo "----------------------------------"
	sleep 8
	clear
	;;
esac
done
;;
5)
clear
echo "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"
echo "|"
less /log.txt
echo "|"
echo "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"
;;
0)
clear
exit 0
;;
*) 	echo "----------------------------------"
	echo "|          Warning!!!            |"
	echo "|   Please Enter Right Choice!   |"
	echo "----------------------------------"
	for i in `seq -w 10 -1 1`
	  do
	    echo -ne "\b\b$i";
	    sleep 1;
	  done
	clear
;;
esac
done