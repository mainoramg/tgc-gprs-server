#!/bin/sh
ps -edf | grep GPRSServer | awk '{printf " " $2}' | xargs kill
cd /home/ec2-user/gprs
export CLASSPATH=.:mysql-connector-java-5.1.47-bin.jar
rm DB.class
rm ServerThread.class
rm GPRSServer.class
javac ServerThread.java
javac GPRSServer.java
java GPRSServer 8090 >> log.out &
tail -f log.out
