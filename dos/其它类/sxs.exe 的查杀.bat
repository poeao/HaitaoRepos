::echo ��������ÿ���̸�Ŀ¼���Զ�����sxs.exe,autorun.inf�ļ����еĻ���windows\system32������SVOHOST.exe �� sxs.exe ���ļ�����Ϊ�������ԡ��Զ�����ɱ����� 

::��������������������ļ� �����ݣ� 
@echo off 
taskkill /f /im sxs.exe /t 
taskkill /f /im SVOHOST.exe /t 
c: 
attrib sxs.exe -a -h -s  
del /s /q /f sxs.exe  
attrib autorun.inf -a -h -s  
del /s /q /f autorun.inf  
d: 
attrib sxs.exe -a -h -s  
del /s /q /f sxs.exe  
attrib autorun.inf -a -h -s  
del /s /q /f autorun.inf  
e: 
attrib sxs.exe -a -h -s  
del /s /q /f sxs.exe  
attrib autorun.inf -a -h -s  
del /s /q /f autorun.inf  
f: 
attrib sxs.exe -a -h -s  
del /s /q /f sxs.exe  
attrib autorun.inf -a -h -s  
del /s /q /f autorun.inf  
g: 
attrib sxs.exe -a -h -s  
del /s /q /f sxs.exe  
attrib autorun.inf -a -h -s  
del /s /q /f autorun.inf 
h: 
attrib sxs.exe -a -h -s  
del /s /q /f sxs.exe  
attrib autorun.inf -a -h -s  
del /s /q /f autorun.inf 
i: 
attrib sxs.exe -a -h -s  
del /s /q /f sxs.exe  
attrib autorun.inf -a -h -s  
del /s /q /f autorun.inf 
j: 
attrib sxs.exe -a -h -s  
del /s /q /f sxs.exe  
attrib autorun.inf -a -h -s  
del /s /q /f autorun.inf 
k: 
attrib sxs.exe -a -h -s  
del /s /q /f sxs.exe  
attrib autorun.inf -a -h -s  
del /s /q /f autorun.inf 
l: 
attrib sxs.exe -a -h -s  
del /s /q /f sxs.exe  
attrib autorun.inf -a -h -s  
del /s /q /f autorun.inf 
m: 
attrib sxs.exe -a -h -s  
del /s /q /f sxs.exe  
attrib autorun.inf -a -h -s  
del /s /q /f autorun.inf 
n: 
attrib sxs.exe -a -h -s  
del /s /q /f sxs.exe  
attrib autorun.inf -a -h -s  
del /s /q /f autorun.inf 
reg delete HKLM\Software\Microsoft\windows\CurrentVersion\explorer\Advanced\Folder\Hidden\SHOWALL /V CheckedValue /f 
reg add HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\Advanced\Folder\Hidden\SHOWALL /v "CheckedValue" /t "REG_DWORD" /d "1" /f 
REG DELETE HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run /V sxs.exe /f 
REG DELETE HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run /V SVOHOST.exe /f 
exit 
 
 