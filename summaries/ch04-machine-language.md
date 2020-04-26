# Chapter 04 - Machine Language

Summary of Chapter 4 of *The Elements of Computing Systems* by Filip Hedberg

-----

Machine language controls and executes a given hardware platform. A machine language is designed to manipulate a *memory* using a *processor* and a set or *registers*. 

The memory is some hardware device that stores data. It can be treated as a continuous array of cells with a fixed width, each with having a unique address.

The processor, also called *Central Processing Unit* (CPU), is a device capable of performing a fixed set of elementary operations. Actions include arithmetics, logic, memory access and control (used for branching). These operations take binary values as inputs and operates on selected memory locations. Outputs of operations are stored either in registers or in memory locations.

Registers serve as a high-speed local memory, and is useful because normal memory access is a relatively slow operation requiring long instructions. Each register is capable of holding a single value, and is located in the processors immediate proximity.

A machine language is a series of coded instructions. An instruction for an n-bit computer is n binary values. What a given combination of values means, such as 1010010110101101, depends on how the underlying platform is designed to interpret instructions. Different bits of the instruction is used for different purposes.

In addition to its binary representation, machine language usually also has symbolic mnemonics using text symbols to represent binary codes. The symbolic notation is called *assembly language*, or assembly, and the program that translates from assembly to binary is called *assembler*.

### Commands
Computers are able to execute basic arithmetics and logic operations. Examples include addition, subtraction, and logic operations.

Computers can also read and manipulate memory. Typically they include *load* and *store* commands, designed to move data between registers and memory. 

Memory can be addressed in different addressing modes. *Direct addressing* is to provide a specific address (or using a symbol that refers to a specific address). *Immediate addressing* is used to load constants, i.e. values that are written in the instruction code. The value is not treated as an address. *Indirect addressing* specifies a memory location that holds another address. This mode is used to handle *pointers*.

While commands normally executes in a linear fashion, having *flow of control* enables programs for branch to other locations given certain conditions. Branching includes repetition, subroutine calling and conditional execution. An unconditional jump always jumps to the target location, while conditional jumps must satisfy a condition. 

### Hack language
The Hack computer is a 16 bit machine consisting of a CPU, two separate memory modules serving as instruction memory and data memory, and two memory-mapped IO/ devices: a screen and a keyboard.

There are two address spaces; the instruction memory and the data memory. Both are 16-bit wide and have a 15-bit address space. There are two 16 bit registers: D and A. 

The Hack language has two generic instructions: address instructions (A-instruction) and compute instructions (C-instruction). Each has a binary representation, a symbolic representation, and an effect on the computer.

The A instruction is used for three different purposes.
1. To enter a constant into the computer
2. Set the location or value for a subsequent C-instruction that manipulates the given memory location
3. Set the location of a subsequent C-instruction that specifies a jump

The C-instruction answers three things:
1. What to compute (comp)
2. Where to store the computed value (dest)
3. What to do next? (jump)

The leftmost bit of an instruction specifies whether an instruction is an A-instruction or a C-instruction (0 and 1 respectively). 

The Hack ALU computes one of a number of fixed functions on the D, A and M register, where M is RAM[A]. The instruction is given by an a-bit and six c-bits specifying which function to apply. The a-bit tells us which registers to use, D and A or D and M. The c-bits specifies a function.

The destination is specified with three bits. The first two bits code whether to store the computed value in the A register and the D register respectively. The third bit codes whether to store the value in M.

The jump specification tells the computer what to do next. Either fetch and execute the next instruction (default), or fetch and execute an instruction located elsewhere. In the latter, we assume that location is already loaded into the A register. 

#### Symbols
Symbols can be used as a way to refer to memory locations (addresses) in three ways:
- Predefined symbols
	- Virtual registers: R0 to R15 are predefined to refer to RAM 0 to 15
	- Predefined pointers: SP, LCL, ARG, THIS, and THAT refer to RAM 0 to 4. 
	- I/O pointers, such as SCREEN and KBD which point to their respective start addresses in memory
- Label symbols: user defined symbols which serve to label destinations of jump commands. 
- Variable symbols: any user-defined symbol that is not predefined or defined elsewhere is treated as a *variable* and is assigned a unique memory address by the assembler, starting at RAM address 16.

### Input/output handling
Input devices use *memory maps*, which means each input or output is directly mapped to a single bit in memory. 