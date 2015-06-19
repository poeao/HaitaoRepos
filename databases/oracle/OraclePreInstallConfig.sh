#!/bin/bash
# OraclePreInstallConfig.sh
# set parameters for oracle 10g installation. 

# revise /etc/sysctl.conf
echo "kernel.shmmni = 4096" >> /etc/sysctl.conf 
# semaphors: semmsl, semmns, semopm, semmni 
echo "kernel.sem = 250 32000 100 128 " >> /etc/sysctl.conf
echo "fs.file-max = 65536 " >> /etc/sysctl.conf
echo "net.ipv4.ip_local_port_range = 1024 65000" >> /etc/sysctl.conf 
echo "net.core.rmem_default=262144 " >> /etc/sysctl.conf
echo "net.core.rmem_max=262144 " >> /etc/sysctl.conf
echo "net.core.wmem_default=262144 " >> /etc/sysctl.conf
echo "net.core.wmem_max=262144 " >> /etc/sysctl.conf

/sbin/sysctl -p

#revise /etc/security/limits.conf
echo "oracle soft    nproc   2047">>/etc/security/limits.conf
echo "oracle hard    nproc   16384">>/etc/security/limits.conf
echo "oracle soft    nofile  1024">>/etc/security/limits.conf
echo "oracle hard    nofile  65536">>/etc/security/limits.conf 

#revise /etc/pam.d/login
echo "session required /lib/security/pam_limits.so">>/etc/pam.d/login
echo "session required pam_limits.so" >> /etc/pam.d/login

/bin/cp /etc/redhat-release  /etc/redhat-release.bak
echo "Redhat-4" > /etc/redhat-release
#after installation , change back
# echo "CentOS release 5.8 (Final)">>/etc/redhat-release

# revise /etc/profile
echo "if [ $USER = "oracle" ]; then">>/etc/profile
echo "  if [ $SHELL = "/bin/ksh" ]; then">>/etc/profile
echo "    ulimit -p 16384">>/etc/profile
echo "    ulimit -n 65536">>/etc/profile
echo "  else">>/etc/profile
echo "    ulimit -u 16384 -n 65536">>/etc/profile
echo "  fi">>/etc/profile
echo "fi">>/etc/profile
source /etc/profile


#add oracle user and group 
/usr/sbin/groupadd oinstall
/usr/sbin/groupadd dba
/usr/sbin/groupadd oper
/usr/sbin/useradd -g oinstall -G dba oracle
echo oracle|/usr/bin/passwd --stdin oracle

mkdir -p /home/oracle/oracle/product/10.2.0/db_1
chown -R oracle.oinstall /home/oracle/oracle

 

