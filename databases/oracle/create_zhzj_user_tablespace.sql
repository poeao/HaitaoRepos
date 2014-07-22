create temporary tablespace FILEDATA_TEMP  
tempfile 'E:\app\lenovo\oradata\orcl\FILEDATA_TEMP.dbf' 
size 50m  
autoextend on  
extent management local;  

create temporary tablespace ZHA_DB_TEMP
tempfile 'E:\app\lenovo\oradata\orcl\ZHA_DB_TEMP.dbf' 
size 50m  
autoextend on  
extent management local;  

create tablespace ZHAJ
logging  
datafile 'E:\app\lenovo\oradata\orcl\ZHAJ.dbf' 
size 50m  
autoextend on  
extent management local;  

create tablespace ZHAJ_DB
logging  
datafile 'E:\app\lenovo\oradata\orcl\ZHAJ_DB.dbf' 
size 50m  
autoextend on  
extent management local;  

create tablespace ZHAJ_DB_PAR
logging  
datafile 'E:\app\lenovo\oradata\orcl\ZHAJ_DB_PAR.dbf' 
size 50m  
autoextend on  
extent management local;  

create tablespace ZHAJ_DB_PAR_IDX
logging  
datafile 'E:\app\lenovo\oradata\orcl\ZHAJ_DB_PAR_IDX.dbf' 
size 50m  
autoextend on  
extent management local;  

create tablespace FILEDATA  
logging  
datafile 'E:\app\lenovo\oradata\orcl\FILEDATA.dbf' 
size 50m  
autoextend on  
extent management local;  

create user zhaj_user identified by zhaj_user default tablespace ZHAJ_DB 
temporary tablespace ZHA_DB_TEMP;  

grant connect, resource,dba to zhaj_user;

grant create view to zhaj_user;

create user FILEDATA identified by FILEDATA  
default tablespace FILEDATA  temporary tablespace FILEDATA_TEMP;  
 
grant connect,resource,create view to FILEDATA; 


