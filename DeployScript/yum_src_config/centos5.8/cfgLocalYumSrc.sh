#!/bin/sh

#change the yum source to Local CDROM

/bin/mkdir  /mnt/cdrom
mount -o loop /dev/cdrom /mnt/cdrom
/bin/cp localYum.repo /etc/yum.repos.d/
cd /etc/yum.repos.d/
/bin/mv CentOS-Base.repo CentOS-Base.repo.bak
/bin/mv localYum.repo CentOS-Base.repo
/usr/bin/yum clean all




