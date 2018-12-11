#!/bin/sh
cd /home/ec2-user/gprs
javac ServerThread.java
javac GPRSServer.java
java GPRSServer 8090 >> log.out &
tail -f log.out
