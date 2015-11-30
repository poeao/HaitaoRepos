for dbname in `mysql -uroot -proot -e "show databases;"|grep -Evi "database|infor|perfor"`
do
	mysqldump -h localhost -uroot -proot  --events -B $dbname|gzip > /opt/bak/${dbname}_$(date +%F)bak.sql.gz
done
