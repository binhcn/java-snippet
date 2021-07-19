servers=(10.60.45.54 10.60.45.55)
home=/home/zdeploy/binhcn

case "$1" in
  start)
    mvn clean package -DskipTests
    for server in "${servers[@]}"
    do
#      ssh zdeploy@$server "mkdir -p $home/target"
      scp -r target/*.jar zdeploy@$server:$home/target
      ssh zdeploy@$server "cd $home && /zserver/jdk1.8.0_181/bin/java -jar -Dserver.port=9104 target/java-snippet-0.0.1-SNAPSHOT.jar"
    done
    ;;
  stop)
     ssh zdeploy@$serve "lsof -Pi:9104 | awk '/[0-9]/{print $2}'"
#    ssh zdeploy@$server "kill -9 $(lsof -Pi:9104 | awk '/[0-9]/{print $2}')"
    ;;
  *)
    echo "Usage: `basename $0` start|stop"
    exit 1
esac