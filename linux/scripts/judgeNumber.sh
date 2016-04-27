#!/bin/sh

#no1 judge args 
[ $# -ne 2 ] && {
        echo "Usage: $0 num1 num2"
        exit 1
}
#no.2 juges if int 
#see if it's a number 
expr $1 + $2 &>/dev/null
[ $? -ne 0 ] && {
        echo "Pls input two numbers."
        exit 2;
}

#3 compare
[ $1 -lt $2 ] && {
        echo "$1 < $2"
        exit 0
}
[ $1 -eq $2 ] && {
        echo "$1 == $2"
        exit 0
}
[ $1 -gt $2 ] && {
        echo "$1 > $2"
        exit 0
}