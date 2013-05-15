#!/bin/bash
#lvs_nat.sh

#open the route pipe
echo 1 > /proc/sys/net/ipv4/ip_forward
#clean the transfrom table
ipvsadm -C
#add a new transform table with althorithem  t-tcp  mode - rr  method --s
ipvsadm -At 8.8.8.8:80 -s rr
#-m :nat mode
ipvsadm -at 8.8.8.8:80 -r 192.168.10.2:80 -m
ipvsadm -at 8.8.8.8:80 -r 192.168.10.3:80 -m
ipvsadm -L -n
