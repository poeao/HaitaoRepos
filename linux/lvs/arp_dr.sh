#!/bin/bash
#arp_dr.sh

ifconfig lo:0 200.168.10.10 netmask 255.255.255.255 up
route add -host 200.168.10.10 dev lo:0
echo "1" > /proc/sys/net/ipv4/conf/lo/arp_ignore
echo "2" > /proc/sys/net/ipv4/conf/lo/arp_announce
echo "1" > /proc/sys/net/ipv4/conf/all/arp_ignore
echo "2" > /proc/sys/net/ipv4/conf/tunl0/arp_announce
