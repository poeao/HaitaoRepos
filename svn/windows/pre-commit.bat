@echo off
rem SVNǿ��дע�͵�hooks�ű�(Windows)
rem �ļ�����: pre-commit.bat,�ŵ�repository/hooksĿ¼��
setlocal
set SVN_BINDIR="E:\programs\csvn\bin"
set REPOS=%1
set TXN=%2
rem �����־��Ϣ������10���ַ�
%SVN_BINDIR%\svnlook log "%REPOS%" -t "%TXN%" | findstr ".........." > nul
if %errorlevel% gtr 0 goto err
exit 0
:err
echo �ύʱ������дע�ͣ����Ҳ�����10���ַ�! 1>&2
exit 1 