#!/bin/sh

#change the yum source to sohu mirror

cd /etc/yum.repos.d/
/bin/mv CentOS-Base.repo CentOS-Base.repo.bak
wget http://mirrors.sohu.com/help/CentOS-Base-sohu.repo
/bin/mv CentOS-Base-sohu.repo CentOS-Base.repo

