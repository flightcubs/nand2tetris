# Chapter 05 - Computer Architecture

Summary of Chapter 5 of *The Elements of Computing Systems* by Filip Hedberg

-----

In this book, the reader builds the *Hack* computer with a simple but powerful architecture. 

It uses the *stored program* concept, which means the programs code is stored and manipulated in the computer memory - just like data - hence the name *software*. This enables a fixed set of hardware to perform arbitrarily complex tasks. 

The stored program concept is a fundamental element of many computer models, in particular the *universal Turing machine* (1936) and the *von Neumann machine* (1945). The formal is mainly used to analyse the logical foundations of computer systems, while the latter is a practical architecture used in most computer platforms today.

The von Neumann architecture is based on a *central processing unit* (CPU), interacting with a *memory* device, receiving data from some *input* device and sending data to some *output* device. The memory does not only serve as a data storage, but also stores the very instructions that tell the computer what to do.

The memory of von Neumann machines holds *data items* in the *data memory* and *programming instructions* in the *instruction memory*. These instructions are written in *machine language* which must match the specification for the particular hardware it runs on. 

The *central processing unit* is in charge of executing the instructions of the currently loaded program. It is made of three main elements: an *Arithmetic-Logic Unit* (ALU), a set of *registers* and a *control unit*. The ALU performs all the low-level arithmetic and logical operations. The registers serve as a high-speed memory located. The control unit decodes the instructions and figures out which instruction to fetch next.

The CPU operation can be described as a repeated loop: fetch an instruction from memory, decode it, execute it, fetch the next instruction, and so on.

The registers serve as data retrieval and storage without taking a round-trip to the memory. There are typically only a handful of registers and they are physically located close to the ALU. Different computers employ different strategies for registers, but in the Hack computer three registers are used. Data registers give the CPU short-term memory services. Addressing registers keeps pointers to memory addresses. Program counter registers keep track of which instruction to execute next.

Input and output in the Hack computer uses a simple *memory-mapped I/O* strategy. The input/output device is mapped onto normal memory addresses.

The Hack platform is a 16-bit machine using two separate memory modules, the random access memory (RAM) and the read-only memory (ROM). The ROM chip is pre-burned with the required program and is used for instructions. 

The Hack platform is simple and the hardware can only perform a limited number of operations. Optimisations for enhancing the performance of computers have let to two main schools of hardware design. *Complex Instruction Set Computing* (CISC) prefer to provide rich and elaborate instruction sets, while the *Reduced Instruction Set Computing* (RISC) proposes using simpler instruction sets in order to promote as fast a hardware implementation as possible.