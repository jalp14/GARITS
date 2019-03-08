#!/bin/bash
cd src/TwentyThreeProductions/GARITSpayload
java -cp h2*.jar org.h2.tools.Restore -file "$1.zip" -dir "~"
