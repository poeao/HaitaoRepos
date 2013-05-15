#!/bin/bash
#lvs_iptune.sh

ifconfig eth0:0 200.168.10.10 netmask 255.255.255.255 up
route add -host 200.168.10.10 dev eth0:0
#clean the transfrom table
ipvsadm -C
#add a new transform table with althorithem  t-tcp  mode - rr  method --s
ipvsadm -At 200.168.10.10:80 -s rr
#-m :nat mode
ipvsadm -at 200.168.10.10:80 -r 200.168.10.2:80 -g
ipvsadm -at 200.168.10.10:80 -r 200.168.10.3:80 -g
ipvsadm -L -n
