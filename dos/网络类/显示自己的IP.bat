@echo off
for /f "tokens=15" %%i in ('ipconfig ^| find /i "IPv4 Address"') do set ip=%%i
echo %ip%
pause