@ECHO OFF

REM #### CONFIGURAZIONI ####
REM Serve per indicare eventuali percorsi aggiuntivi per servizi extra
REM oppure per indicare percorsi remoti dove sono posti i servizi 
set CODEBASE_SERVICES=${rmi.codebase}

REM Serve per indicare il percorso dove e' situata la JAVA_JRE
set JAVA_JRE=$JAVA_HOME
REM #### FINE CONFIGURAZIONI ####

echo Percorso JRE: %JAVA_JRE%
echo Codebase aggiuntivo: %CODEBASE_SERVICES%

%JAVA_JRE%\bin\java.exe -Djava.security.manager=java.rmi.RMISecurityManager -Djava.security.policy="server.policy" -Djava.rmi.server.codebase="%CODEBASE_SERVICES% file:./inbank-common-server.jar file:./inbank-common.jar" -classpath ".;inbank-common-server.jar;inbank-server.jar" -jar inbank-server.jar
