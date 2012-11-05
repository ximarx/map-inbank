#!/bin/sh

JAVA_JRE=$JAVA_HOME
EXTERNAL_PATH=${external.path}
CLIENT_HOSTNAME=${rmi.client.hostname}

$JAVA_JRE/bin/java -Djava.rmi.server.hostname=$CLIENT_HOSTNAME -Djava.security.manager=java.rmi.RMISecurityManager -Djava.security.policy="bancomat.policy" -classpath ".;inbank-common.jar;inbank-bancomat.jar;$EXTERNAL_PATH" -jar inbank-bancomat.jar