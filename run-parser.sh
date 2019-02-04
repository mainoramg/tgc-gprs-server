#!/bin/bash
ps -edf | grep GPSParser | grep -v grep | awk '{print $2}' | xargs kill -9 2> /dev/null
cd /home/ec2-user/gprs
now=`date -u +"%Y-%m-%d_%H:%M:%S"`
gzip parser.out
mv parser.out.gz /home/ec2-user/backup/logs/gprs/parser_$now.out.gz
gzip parser.err
mv parser.err.gz /home/ec2-user/backup/logs/gprs/parser_$now.err.gz
touch parser.out
touch parser.err
export CLASSPATH=.:mysql-connector-java-5.1.47-bin.jar
rm DB.class
rm GPSEvent.class
rm GPSParser.class
javac GPSParser.java
java GPSParser >> parser.out 2>> parser.err &
tail -f parser.out
