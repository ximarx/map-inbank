@ECHO OFF

REM Serve per indicare il percorso dove e' situata la JAVA_JRE
set JAVA_JRE=$JAVA_HOME
REM Indicare il percorso dove reperire il driver aggiuntivo per la periferica esterna
set EXTERNAL_PATH=${external.path}
set CLIENT_HOSTNAME=${rmi.client.hostname}

%JAVA_JRE%\bin\java.exe -Djava.rmi.server.hostname=%CLIENT_HOSTNAME% -Djava.security.manager=java.rmi.RMISecurityManager -Djava.security.policy="bancomat.policy" -classpath ".;inbank-common.jar;inbank-bancomat.jar;%EXTERNAL_PATH%" -jar inbank-bancomat.jar
