#!/bin/bash
ps -edf | grep MoveData | grep -v grep | awk '{print $2}' | xargs kill -9 2> /dev/null
cd /home/ec2-user/gprs
export CLASSPATH=.:mysql-connector-java-5.1.47-bin.jar
rm MoveData.class
javac MoveData.java
java MoveData gps_raw_data_processed stamp >> move_data.out 2>> move_data.err &
