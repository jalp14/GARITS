#!/bin/bash
cd src/TwentyThreeProductions/GARITSpayload/
java -cp h2*.jar org.h2.tools.Restore -file "DBBackups/$1" -dir "~"
