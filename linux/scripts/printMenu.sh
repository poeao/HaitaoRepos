#!/bin/sh

menu() {
cat << MENU
=================================
        1. [Install lamp]
        2. [Install lnmp]
        3. [exit]
=================================
MENU
echo -n "Pls input the option : "
read -t 10 option
[ -z "$option" ] && {
        echo "Pls input option above."
        exit 2
}

#[ $option -ne 1 -a $option -ne 2 -a $option -ne 3 ]  && echo "Input Error " && exit 1
[ $option != "1" -a $option != "2" -a $option != "3" ]  && echo "Input Error " && exit 1
}

menu2() {
        echo '

        =============================


        =============================
'
}

readOption() {
[ $option -eq 1 ] && {
        echo "Start installing lamp"
        sleep 1
        #/bin/sh /server/scripts/lamp.sh
        echo '#####################'
        echo '# Lamp is installed.#'
        echo '#####################'
        sleep 2
        clear
        exit 0
}
[ $option -eq 2 ] && {
        echo "Start installing lamp"
        exit 0
}

[ $option -eq 3 ] && {
        echo "Exit."
        exit 0
}
}


menu
readOption