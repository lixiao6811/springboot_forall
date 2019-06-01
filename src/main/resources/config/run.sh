 #!/bin/bash
 #--------------------------------------------------------------------
 #作者：xiao.li
 #日期：2016-09-09
 #参数：{start|stop|restart}
 #功能：对customer-union-innisfree.jar程序的启停
  #-------------------------------------------------------------------

JAR="lib/customer-union-innisfree.jar"

function start(){
    echo "开始启动 ...."
    source /etc/profile
    num=`ps -ef|grep java |grep $JAR|wc -l`
    if [ "$num" = "0" ] ; then
       /data/java/jdk1.8.0_144/bin/java -Xmx1024m -jar $JAR >> /dev/null 2>&1 &
       echo "启动成功...."
    else
       echo "进程已经存在，启动失败，请检查....."
       exit 0
    fi
}

function stop(){
   echo "开始关闭进程...."
   num=`ps -ef|grep java |grep $JAR|wc -l`
   if [ "$num" != "0" ] ; then
     ps -ef|grep java|grep $JAR|awk '{print $2;}'|xargs kill -15
     echo "进程关闭成功..."
   else
     echo "服务未启动，无需停止..."
   fi
}


function restart(){
 echo "begin stop process ..."
 stop
 sleep 2000
 echo "process stoped,and starting ..."
 start
 echo "started ..."
}

if [ "$#" = 1 ] ; then
   cmd=$1
   case "$cmd" in
      "start")
         start
         exit 0
       ;;
      "stop")
         stop
         exit 0
        ;;
      "restart")
          restart
          exit 0
        ;;
      *)
          echo "用法： $0 {start|stop|restart}"
          exit 1
       ;;
     esac
else
  echo "参数不正确，使用方法: $0 {start|stop|restart}"
  exit 0
fi
