@echo off
set filename=safety_%date:~0,4%%date:~5,2%%date:~8,2%-%time:~0,2%%time:~3,2%%time:~6,2%.dmp
exp safety/safety file=%filename%
echo "export dmp file completely!"
pause