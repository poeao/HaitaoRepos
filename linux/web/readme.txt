mysql -P3306 -uroot -ppassword # just for installer package .

update use set Host='%' where Host='localhost';
flush privileges;

