#!/bin/bash

rm submission.zip 2> /dev/null
zip -j submission.zip Summary.md src/main/java/student/Solver.java src/main/java/student/Appetizer.java src/test/java/student/SolverTest.java src/test/java/student/AppetizerTest.java
