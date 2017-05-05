# Circuit
Converts a state table of a Finite State Machine with inputs to a specification of circuit inputs to JK flip flops

First, create file state.txt which contains the state table (size n by 5) input of the form:

Current State A, Current State B, Input, Next State A, Next State B

Example row:

1,1,1,0,1

Compile using:

javac Circuit.java

Run:

javac Circuit
