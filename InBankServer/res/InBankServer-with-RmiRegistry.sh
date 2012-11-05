JAVA_JDK=${JDKPath}
RMI_PORT=${rmi.port}

$JAVA_JDK/bin/rmiregistry $RMI_PORT &
./InBankServer.sh
killall rmiregistry