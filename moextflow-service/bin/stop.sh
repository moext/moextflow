pid=`ps ax | grep java | grep flowservice | grep -v grep| awk '{print $1}'`
if [ -z "$pid" ] ; then
        echo "No process is running."
        exit -1;
fi

kill ${pid}
echo "The process ${pid} have been killed" 
