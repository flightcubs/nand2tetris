// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)

// Put your code here.

// Multiplying something with x is the same as adding it to itself that many times (duh...)
// Since we want the end result to be stored in R2, let's use that memory space as the tallying sum
// Let R1 be the number of times we want to add R0

// Set the sum start at 0
@0 // The constant 0
D = A  // D = 0
@2  // Refer to the address 2
M = D   // Set the current address (2) to the value of D (0)

(LOOP)
    // First, let's check if R1 is <= 0. If it is, we're done and it's time to end our loop
    @1
    D = M // Load R1 into D
    @END
    D;JLE // If D (M[1]) is less or equal than 0, jump to A (END)

    // We're still in the loop, which means we should do one addition of R0 to the sum R2
    @0
    D = M // Load R0 into D
    @2 // Set the current address to our sum R2
    M = D+M // Add the current value of M and D into M

    // We've done one addition, now we need to substract 1 from R1
    // in order to keep track of how many additions that are left
    @1
    D = A  // Set D to the constant 1
    @1 // Set the address to 1 (R1)
    M = M-D // Substract D (1) from R1 and save to R1

    // It's time to check if we have any additions left to do,
    // so we go back to the start of the loop
    @LOOP
    0;JMP // Unconditional jump
(END)
    @END
    0;JMP // Infinite loop (standard way to terminate the execution of a Hack program)