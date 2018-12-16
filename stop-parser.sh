#!/bin/sh
ps -edf | grep GPSParser | awk '{printf " " $2}' | xargs kill
