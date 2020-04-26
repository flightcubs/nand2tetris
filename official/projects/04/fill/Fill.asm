// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.

// A pixel is black when its value is 1, and vice versa
// The screens memory addresses are RAM[16384 + r * 32 + c / 16] with the bit c%16
// 256 rows of 512 pixels per row (256 rows of 32 16-bit registers)

// The keyboard is a single word register at RAM[24576]
// If no key is pressed, all bits at that RAM address are 0
// Let's use R0 to represent whether to whiten or blacken the screen

(LOOP)
    @24576 // Address to Keyboard RAM
    D=M  // Load the keyboard into D

    @0 // Previous keyboard input
    D=D-M  // Check if the new input is equal to the old input
    @LOOP
    D;JEQ  // If it is equal, restart the loop without drawing anything

    @24576 // Address to Keyboard RAM
    D=M  // Load the keyboard into D
    @WHITEN // Set address to go to WHITEN if the keyboard is 0
    D;JLE
    @BLACKEN  // Set address to go to BLACKEN in all other cases (to be optimized)
    0;JMP

(BLACKEN)
    @0  // Set address to R0
    M=-1  // Set all bits to 1 (-1 is 1111111111111111)
    @UPDATE  // Ready to update screen, set location to update
    0;JMP  // Unconditional jump

(WHITEN)
    @0 // Set address to R0
    M=0 // Set all bits to 0
    @UPDATE  // Ready to update screen, set location to update
    0;JMP  // Unconditional jump

(UPDATE)
    // Set start address
    @16384
    D=A // Set D to start address
    @1
    M=D // Store start address in R1

    (NEXT)
        // Exit condition of loop
        @1
        D=M  // Set current address to D
        @24576
        D=A-D  // Substract the current address from the last pixel plus one (16384 + 256 * 512 / 16 = 24576)
        @LOOP
        D;JLE  // Update is done if we've reached the last screen address. Jump to loop

        // Update the pixels (bits) of the current RAM address with the value of R0
        @0
        D=M  // Load R0 value into memory
        @1
        A=M // Load address stored in R1 into current address
        M=D  // Set value of D (R0) to current address

        // Increase address
        @1
        M=M+1  // Increase address by 1

        // Loop
        @NEXT
        0;JMP
