
#!/bin/sh
VIP=192.168.1.77
/etc/rc.d/init.d/functions
case "$1" in
start)
    echo " start tunl port"
    ifconfig lo:0 $VIP netmask 255.255.255.255 broadcast $VIP up
    echo "2">/proc/sys/net/ipv4/conf/all/arp_announce
    echo "1">/proc/sys/net/ipv4/conf/all/arp_ignore
    echo "2">/proc/sys/net/ipv4/conf/lo/arp_announce
    echo "1">/proc/sys/net/ipv4/conf/lo/arp_ignore
    ;;
stop)
    echo " stop tunl port"
    ifconfig lo:0 down
    echo "0">/proc/sys/net/ipv4/conf/all/arp_announce
    echo "0">/proc/sys/net/ipv4/conf/all/arp_ignore
    echo "0">/proc/sys/net/ipv4/conf/lo/arp_announce
    echo "0">/proc/sys/net/ipv4/conf/lo/arp_ignore
    ;;

*)
    echo "Usage: $0 {start|stop}"
    exit 1
esac
