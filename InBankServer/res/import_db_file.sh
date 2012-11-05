#!/bin/sh

MYSQL_HOME=$1
INSTALL_PATH=$7

$MYSQL_HOME/mysqladmin --connect_timeout=5 -h $2 -P $5 -u $3 --password=$4 create $6
$MYSQL_HOME/mysql --connect_timeout=5 -h $2 -P $5 -u $3 --password=$4 $6 < $INSTALL_PATH/db_mysql.sql
