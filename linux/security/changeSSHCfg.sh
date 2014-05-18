#!/bin/sh

#Change ssh configuration 


/bin/mv /etc/ssh/sshd_config /etc/ssh/sshd_config.bak
/bin/cp sshd_config /etc/ssh/
/etc/init.d/sshd restart
