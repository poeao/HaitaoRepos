#!/bin/sh

usage() {
        echo "Usage: $0 URL"
        exit 1
}

check_url() {
        wget -T 5 --spider -t 2 $1 &>/dev/null
        RETVAL=$?
        if [ $RETVAL -eq 0 ] ;then
                echo "$1 is online"
        else
                echo "$1 is not avliable."
        fi

        return $RETVAL
}

main() {
        if [ $# -ne 1 ]; then
                usage
        else
                check_url $1
        fi
}

#main $1
main $*