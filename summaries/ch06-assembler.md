# Chapter 06 - Assembler

Summary of Chapter 6 of *The Elements of Computing Systems* by Filip Hedberg

-----

A hardware’s logic design implements an interface on how to get the hardware to perform certain tasks. These instructions are represented as binary codes and can be understood by the underlying hardware. It’s not convenient for humans to remember all these different binary operations, so we introduce a symbolic version of this machine language called *assembly*. The program that translates symbolic assembly code into machine code is called an *assembler*. 

Binary instructions must, by definition, refer to memory address using actual numbers. For programmers, it’s convenient to create pointers to memory addresses instead of remembering the actual memory address, but these needs to be resolved before the machine can process the instructions. Symbols come from two sources, variables and labels. Variables are symbolic names and are automatically assigned a memory address. Labels are markers at certain locations in the code so that the programmer can branch and jump to these locations when needed.

The assembler is basically a test-processing program translating assembly commands into equivalent binary instructions. The assembler is the bridge between hardware and software, and needs knowledge of both the hardware contract,  typically called *machine language specification*, and the assembly syntax that will be the input to the program.

For each symbolic assembly command, the basic tasks of the assembler is to:
* Parse the command into the several underlying input commands (i.e. a command can contain both assignment, computation and a jump)
* For each underlying command, translate it into the corresponding machine language bits
* Replace all symbols with numeric addresses of memory locations
* Assemble the binary codes into a complete machine instruction

In the Hack language, in addition to variables and labels, there are predefined symbols for certain memory locations such as the keyboard.

Assembly programs allow referencing a symbolic label before it is defined, meaning it’s possible to jump to a location further down in the code. A common solution to this problem is to parse the assembly code in two passes. The first removes all labels and stores the subsequent row instruction number in a symbol table. The second pass parses the command and resolves any symbols. If it’s a new variable, it’s assigned a new memory address and is added to the symbol table so it can be used in the future.
