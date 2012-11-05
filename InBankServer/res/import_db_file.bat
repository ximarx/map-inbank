set MYSQL_HOME=%1
set INSTALL_PATH=%7
call %MYSQL_HOME%\mysqladmin.exe --connect_timeout=5 -h %2 -P %5 -u %3 --password=%4 create %6
call %MYSQL_HOME%\mysql.exe --connect_timeout=5  -h %2 -P %5 -u %3 --password=%4 %6 < %INSTALL_PATH%\db_mysql.sql