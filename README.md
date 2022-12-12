# JAVA emulator for the Gamma 3 first generation computer

## Context - first generation computers and mecanography
(source: http://www.feb-patrimoine.com/projet/gamma3/gamma3.htm)

First generation of electronic computers were developped during WW2 (Colossus, ENIAC) based on then available technology: vacuum tubes (for processing) and delay lines (for memory). Although quite different than our current computers, they quickly adhered to the Von Neumann architecture (defined in 1945). 

From the business perspective, computers were introduced in a context of mecanographic processing. In the US, before introducing the 701 computer (1852), IBM developed electronic version of their electromechanical calculator (IBM 603 in 1946, 604 in 1948). The approach in France was a bit different: Bull directly developed the Gamma 3 as a then modern computing unit. Actually the first version (demonstrated i 1951) was a slave tabulator equipment for auxilliary computations. It then evolved to support storage from a magentic drum (about 50Kb for data and program storage). That ET ("extension tambour") version also freed the machine from the tabulator which became the peripherical and opened it to the computer market.

## Main features

The initial core of the machine supported 7 memories (called "banal") implemented using delay lines: 6 readable and 1 that could store the output of an operation, numbered from M0 to M7 (with M0 and M1 being logically the same memory). Those registers are one "Word" long, i.e. 12 characters. The character is 4 bits and can code an HEX or a BCD (as the computer ALU was initially designed to operate in decimal). 

The programming was initially done through a connection panel supporting 64 instructions. The instruction are specified in 4 characters with the following structure: 
* TO: operation type (with some mnemonic), like AN (add), SN (subtract), MR (reduced multiplication), MC (complete multiplication),...
* AD: address of the second operand (the first operand and result are always M0/M1)
* OD: start rank of data in the operand (as operation are performed on a subrange of the Word)
* OF: end rank of data in the operand (as operation are performed on a subrange of the Word)
This means a Word can also contain 3 instructions

Initial instructions types covered:
* resetting, loading data into memory
* moving data between banal memories
* ALU: shifts, add, subtract, multiplication (with in reduced and complete flavours), division (also with reduced and complete flavours)
* comparison
* jumps

Later evolution of the machine introduced more features and thus additional instruction types
* Extension Tambour (ET): load/save page in/from group
* Binary operation mode: in this mode Word are interpreted as 48 bits

## Java emulator/simulator

Emulator or simulator question

## Usage

## Limitation and future work

