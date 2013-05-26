#!/bin/bash
#disk.sh

num=`df |awk 'NR==2{print int($5)}'`
if [ $num -gt 22 ]
then
        echo "dis space is ${num}%, nwo > 22%"|mail user1@anhry.com -s "disk space > %22"
fi