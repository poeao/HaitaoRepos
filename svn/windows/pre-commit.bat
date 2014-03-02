@echo off
rem SVN强制写注释的hooks脚本(Windows)
rem 文件名是: pre-commit.bat,放到repository/hooks目录下
setlocal
set SVN_BINDIR="E:\programs\csvn\bin"
set REPOS=%1
set TXN=%2
rem 检查日志信息不少于10个字符
%SVN_BINDIR%\svnlook log "%REPOS%" -t "%TXN%" | findstr ".........." > nul
if %errorlevel% gtr 0 goto err
exit 0
:err
echo 提交时必须填写注释，并且不少于10个字符! 1>&2
exit 1 