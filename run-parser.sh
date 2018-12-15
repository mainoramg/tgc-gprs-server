#!/bin/sh

ps -edf | grep GPSParser | grep -v grep | awk '{print $2}' | xargs kill -9 2> /dev/null

cd /home/ec2-user/gprs

export CLASSPATH=.:mysql-connector-java-5.1.47-bin.jar
rm DB.class
rm GPSEvent.class
rm GPSParser.class

javac GPSParser.java
java GPSParser >> parser.out 2>> parser.err &

tail -f parser.out
