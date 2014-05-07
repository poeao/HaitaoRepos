#!/bin/sh

echo "#--------sshConfig change ssh default port, forbidden root login--------------"
/bin/cp /etc/ssh/sshd_config /etc/ssh/sshd_config.`date+"%Y-%m-%d_%H-%M-%S"`
sed -i 's%#Port 22%Port 51206%' /etc/ssh/sshd_config
sed -i 's%#PermitRootLogin yes%PermitRootLogin no%' /etc/ssh/sshd_config
sed -i 's%#PermitEmptyPasswords no%PermitEmptyPasswords no%' /etc/ssh/sshd_config
sed -i 's%#UseDNS yes%UseDNS no%' /etc/ssh/sshd_config
egrep "UseDNS|51206|RootLogin|EmptyPass" /etc/ssh/sshd_config
/etc/init.d/sshd reload
