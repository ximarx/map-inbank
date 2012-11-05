#!/bin/sh

JAVA_JRE=$JAVA_HOME
CLIENT_HOSTNAME=${rmi.client.hostname}

$JAVA_JRE/bin/java -Djava.rmi.server.hostname=$CLIENT_HOSTNAME -Djava.security.manager=java.rmi.RMISecurityManager -Djava.security.policy="cliente.policy" -classpath ".;inbank-common.jar;inbank-cliente.jar" -jar inbank-cliente.jar