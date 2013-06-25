#!/bin/bash

#DESTPATH为删除的目标目录
DESTPATH="/home/oracle/dbbackup/"

if [ "oracle" != "$LOGNAME" ]
then
    echo "您没有执行权限!请联系管理员!"
    exit 1
fi

cd $DESTPATH 2>/dev/null

if [ $? -ne 0 ]
then
    echo "没有找到目标目录!"
    exit 1
fi

#不考虑隐藏目录和文件
FileList=`ls 2>/dev/null`


 for pFile in $FileList
    do
        if [ -f ${pFile} ]
        then
        #    echo "检查到 ${pFile} 为文件 " >> clean_dmp.log
            echo "正在删除文件 ${pFile......"  >> clean_dmp.log
            rm -rf ${pFile} 2>/dev/null
        fi
    done
