#!/bin/sh

JAVA_JRE=$JAVA_HOME
CODEBASE_SERVICES=${rmi.codebase}

$JAVA_JRE/bin/java -Djava.security.manager=java.rmi.RMISecurityManager -Djava.security.policy="server.policy" -Djava.rmi.server.codebase="$CODEBASE_SERVICES file:./inbank-common.jar file:./inbank-common-server.jar" -classpath ".;inbank-common-server.jar;inbank-server.jar" -jar inbank-server.jar