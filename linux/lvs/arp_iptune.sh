#!/bin/bash
#arp.sh

ifconfig tunl0 200.168.10.10 netmask 255.255.255.255 up
route add -host 200.168.10.10 dev tunl0
echo "1" > /proc/sys/net/ipv4/conf/tunl0/arp_ignore
echo "2" > /proc/sys/net/ipv4/conf/tunl0/arp_announce
echo "1" > /proc/sys/net/ipv4/conf/all/arp_ignore
echo "2" > /proc/sys/net/ipv4/conf/tunl0/arp_announce
