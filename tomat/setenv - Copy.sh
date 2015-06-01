#export JAVA_OPTS="-server -Xms257m -Xmx1024m -XX:PermSize=128M -XX:MaxPermSize=512M"
export JAVA_OPTS="-server -Xloggc:logs/gc.log -Xms2048m -Xmx2048m -XX:PermSize=128M -Xss1m -XX:+HeapDumpOnOutOfMemoryError -XX:+UseParallelGC"
