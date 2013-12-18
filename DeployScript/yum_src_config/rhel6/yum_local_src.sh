#!/bin/bash
# yum_src.sh
# configure local yum source.
mkdir -p /mdeia/cdrom
#mount -o loop -t iso9660 isoname.iso  /media/cdrom
mount /dev/cdrom /media/cdrom
cp /etc/yum.repos.d/rhel-source.repo rhel-source.repo.bak
alias cp='cp'
cp -fp rhel-debuginfo.repo /etc/yum.repos.d/rhel-debuginfo.repo
cp /usr/lib/python2.6/site-packages/yum/yumRepo.py /usr/lib/python2.6/site-packages/yum/yumRepo.py.bak
cp -fp yumRepo.py /usr/lib/python2.6/site-packages/yum/yumRepo.py
yum clean all
