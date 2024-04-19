@echo off
cd C:\hsqldb-2.7.2\hsqldb-2.7.2\hsqldb
start cmd /k "java -classpath lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:hsqldb/demodb --dbname.0 testdb"
