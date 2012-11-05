#!/bin/sh

JAVA_JRE=$JAVA_HOME
CLIENT_HOSTNAME=${rmi.client.hostname}

$JAVA_JRE/bin/java -Djava.rmi.server.hostname=$CLIENT_HOSTNAME -Djava.security.manager=java.rmi.RMISecurityManager -Djava.security.policy="impiegato.policy" -classpath ".;inbank-common.jar;inbank-impiegato.jar" -jar inbank-impiegato.jar