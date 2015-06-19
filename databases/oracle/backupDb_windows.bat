@echo off
rem ====================================================
rem windows oracle database export script
rem call this script by windows' task plan.
rem ====================================================
rem ==========Please set the following varialble=================
rem 1. BACKUP_DIR   The directory for store the backupdata.
rem 2. ORACLE_USERNAME 
rem 3. ORACLE_PASSORD
rem 4. ORACLE_DB  
rem 5. BACK_OPTION
rem 6. RAR_CMD    The RAR tool's directory.
rem ====================================================

set BACKUP_DIR=F:\DATABASE_BACKUP
set ORACLE_USERNAME=mobile_app
set ORACLE_PASSWORD=mobile_app
set ORACLE_DB=ora11g
set BACK_OPTION=""
set RAR_CMD="C:\Program Files\WinRAR\Rar.exe"
set TODAY=%date:~0,4%%date:~5,2%%date:~8,2%
set BACK_NAME=%ORACLE_USERNAME%_%ORACLE_DB%
set BACK_FULL_NAME=%BACKUP_DIR%\%BACK_NAME%_%TODAY%
 
rem create backup directory 
if not exist %BACKUP_DIR% mkdir %BACKUP_DIR%

echo "===================Backup %ORACLE_USERNAME%'s data===============" >> %BACK_FULL_NAME%_bat.log
echo "Starting Backup ...... " >> %BACK_FULL_NAME%_bat.log
echo Current time is :%DATE% %time% >>%BACK_FULL_NAME%_bat.log

exp %ORACLE_USERNAME%/%ORACLE_PASSWORD%@%ORACLE_DB% grants=Y %BACK_OPTION% file=%BACK_FULL_NAME%.dmp log=%BACK_FULL_NAME%_exp.log 

 
echo "Export db data finished." >> %BACK_FULL_NAME%_bat.log
echo Finish time is : %DATE% %time% >> %BACK_FULL_NAME%_bat.log

echo "zip dmp file and delete the exported dmp file." >> %BACK_FULL_NAME%_bat.log
%RAR_CMD% a -df "%BACK_FULL_NAME%_logic.rar" "%BACK_FULL_NAME%.dmp" 

echo "Backup done" >> %BACK_FULL_NAME%_bat.log

echo "Delete old back files over 60 days. " >> %BACK_FULL_NAME%_bat.log

forfiles /p "%BACKUP_DIR%" /s /m *.* /d -60 /c "cmd /c del @path" > %BACK_FULL_NAME%_deleteOld.txt 2>&1

echo "===================Backup %ORACLE_USERNAME%'s data done.===============" >> %BACK_FULL_NAME%_bat.log
exit  