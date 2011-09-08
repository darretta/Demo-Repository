#!/bin/sh

rm -rf classes
mkdir classes
javac -Xlint:unchecked -sourcepath src -classpath classes src/com/jboss/demo/mrg/messaging/Main.java -d classes
