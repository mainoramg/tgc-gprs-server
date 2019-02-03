#!/bin/sh
ps -edf | grep GPRSServer | awk '{printf " " $2}' | xargs kill
