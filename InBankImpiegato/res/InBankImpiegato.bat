@ECHO OFF

REM Serve per indicare il percorso dove e' situata la JAVA_JRE
set JAVA_JRE=$JAVA_HOME
set CLIENT_HOSTNAME=${rmi.client.hostname}

%JAVA_JRE%\bin\java.exe -Djava.rmi.server.hostname=%CLIENT_HOSTNAME% -Djava.security.manager=java.rmi.RMISecurityManager -Djava.security.policy="impiegato.policy" -classpath ".;inbank-common.jar;inbank-impiegato.jar" -jar inbank-impiegato.jar
