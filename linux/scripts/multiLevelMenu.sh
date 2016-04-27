#!/bin/bash
echo -e "\033[32m========================================\033[0m"
echo -e "\033[32m   The Author : leo.song                \033[0m"
echo -e "\033[32m   Description:lamp/lnmp                \033[0m"
echo -e "\033[32m   QQ:46179140                          \033[0m"
echo -e "\033[32m========================================\033[0m"
menu(){
cat << EOF
----------------------------------------
|****Please Enter Your Choice:[0-3]****|
----------------------------------------
	1.[install lamp]
	2.[install lnmp]
	3.[exit]
EOF
}

menu1(){
cat << EOF
----------------------------------------
|****Please Enter Your Choice:[0-4]****|
----------------------------------------
	1.[install apache]
	2.[install php]
	3.[install mysql]
	4.[back]
EOF
}

menu2(){
cat << EOF
----------------------------------------
|****Please Enter Your Choice:[0-4]****|
----------------------------------------
        1.[install nginx]
        2.[install php]
        3.[install mysql]
        4.[back]
EOF
}

lamp(){
        clear
        while true
        do
        menu1
                read -p "please input a number:" num1
                case "$num1" in
                1)
                        echo -e "\033[32mThe Apache is installing......\033[0m"
                        sleep 3
                ;;
                2)
                        echo -e "\033[32mThe Php is installing......\033[0m"
                        sleep 3
                ;;
                3)
                        echo -e "\033[32mThe Mysql is installing......\033[0m"
                        sleep 3
                ;;
                4)
                        clear
                        break
		;;
		*)
                esac
        done
}

lnmp(){
        clear
        while true
        do
        menu2
                read -p "please input a number:" num2
                case "$num2" in
                1)
                        echo -e "\033[32mThe Nginx is installing......\033[0m"
                        sleep 3
                ;;
                2)
                        echo -e "\033[32mThe Php is installing......\033[0m"
                        sleep 3
                ;;
                3)
                        echo -e "\033[32mThe Mysql is installing......\033[0m"
                        sleep 3
                ;;
                *)
                        clear
                        break
                esac
        done
}

#main 
declare flag=0
while [ "$flag" -eq 0 ]
	do
	menu
		while true
		do
			read -p "pls input the you want:" num
			[ $num != "1" -a $num != "2" -a $num != "3" ] &&{
				echo "pls input a intger number!"
				exit 1
			}
			[ $num -eq 1 ] && {
				lamp
			}
			[ $num -eq 2 ] && {
				lnmp
			}
			[ $num -eq 3 ] && {
				exit
			}
			menu
		done
	##############################################
	[ $num -ge 4  ] && {
		echo -e  "\033[32mplease select 1 or 2 or 3\033[0m"
		menu
		read -p "pls input the you want:" num3
		[ $num3 -eq 1 ] && {
			lamp
		}
		[ $num3 -eq 2 ] && {
			lnmp
		}
		[ $num3 -eq 3 ] && {
		exit
		}
	}
done
