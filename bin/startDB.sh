#!/usr/bin/env bash
cd
cd hsqldb-2.7.2/hsqldb
java -classpath lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:hsqldb/demodb --dbname.0 testdb