#!/bin/sh
javac Quine.java
java Quine | diff Quine.java -
rm Quine.class
