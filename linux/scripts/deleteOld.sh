#!/bin/bash

#DESTPATHΪɾ����Ŀ��Ŀ¼
DESTPATH="/home/oracle/dbbackup/"

if [ "oracle" != "$LOGNAME" ]
then
    echo "��û��ִ��Ȩ��!����ϵ����Ա!"
    exit 1
fi

cd $DESTPATH 2>/dev/null

if [ $? -ne 0 ]
then
    echo "û���ҵ�Ŀ��Ŀ¼!"
    exit 1
fi

#����������Ŀ¼���ļ�
FileList=`ls 2>/dev/null`


 for pFile in $FileList
    do
        if [ -f ${pFile} ]
        then
        #    echo "��鵽 ${pFile} Ϊ�ļ� " >> clean_dmp.log
            echo "����ɾ���ļ� ${pFile......"  >> clean_dmp.log
            rm -rf ${pFile} 2>/dev/null
        fi
    done
