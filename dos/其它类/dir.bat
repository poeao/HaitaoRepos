@rem ��BAT�ļ���һ��������"tree.bat",����tree�����ʵЧ,��֪��Ϊʲô
@rem ��������ΪϵͳĿ¼��"tree.com",ϵͳ�������.
@rem ��"dir.bat"���ļ���ȴ����.
@echo off
rem �ڵ�ǰĿ¼���ɺ�Ư���Ľṹ��
tree /f > 1.TXT

rem ��DIR����һ��,����ÿ���¼�Ŀ¼�ļ�Ҳ�г�
DIR /S > 2.txt

rem �����г�Ŀ¼��ǰĿ¼�ļ�
DIR /d > 3.txt