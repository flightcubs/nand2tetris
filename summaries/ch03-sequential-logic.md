# Chapter 03 - Sequential Logic

Summary of Chapter 3 of *The Elements of Computing Systems* by Filip Hedberg

-----

There are two main types of chips: *combinatorial* and *sequential*. Combinatorial chips take its input values and compute a results purely based on its inputs. These chips can’t maintain any state or remember values. Sequential chips has the ability to remember things and has a *memory*. 

Time is an essential component in memory. At a given point in time, a chip remembers what was previously put into its memory. Thus time must be represented as an input to a sequential chip. 

The passage of time is represented by a master *clock* in most computers. The clock continuously delivers a train of alternating signals to all sequential chips in the computer. As with primitive gates, a clock can be implemented in a number of physical ways, but a common implementation is based on an oscillator alternating between two phases (1-0, low-high, tick-tock, etc). 

A clock *cycle* is the time it takes from the beginning of a “tick” and the end of the subsequent “tock”. 

The most elementary sequential element is called a *flip-flop*, and in this book a variant called data flip-flop, or DFF. A data flip flop has a single bit input and a single bit output, and since it’s a sequential chip it also receives the clock input. A DFF does not transform the input signal in any way except introducing a delay based on the clock. The output of the DFF is simply the input of the previous clock cycle, i.e. `out(t) = in(t-1)`. 

A DFF has a very short "memory" of one clock cycle, and continuously needs an input signal to keep outputting the same value. A *register* however is a storage device that can store a value over time. In addition, it also has an interface where one can actually write a value to the register. Unless the register is being written to, also known as loaded, it will output its previous value `out(t) = out(t-1)`.  A register can be built using a single DFF and combinatory logic. 

With a device capable of storing a single bit, we can combine it to construct arbitrarily wide registers. The *width* is simply the number of bits a register can hold. The multi-bit contents of a register are known as *words*. 

With registers capable of storing words, we can combine any number of such registers to build memory banks. Stacking registers together form a *Random Access Memory RAM* unit. Its name comes from its characteristic to be able to access randomly chosen words without restrictions on the order which they are accessed. 

When many registers are stacked together to form a RAM, we use an *address* to identify each register. It’s worth noting that this address is not marked in any physical way, but the access is done via logic. A classical RAM has three inputs: a data input, an address, and a load bit. The data can be multiple bits, but must match the width of the RAM. The address is an integer of the *size*, or the number of words, of the ram (from 0 to size -1). The load bit specifies if the current operation is a read or a write operation.

There’s another practical sequential chip called a *counter*. It can increment its value every time unit. It usually also allows for setting the value to a given number or resetting the value to 0. A counter chip can be created using a standard register and combinatory logic.

Another thing to note is how and when input and output values are used in a clock cycle, and how a chips internal state changes over time. Sequential chips may be unstable during clock cycles, performing logic operations on input signals, and its output only changes at the point of transition from one clock cycle to the next. Since combinatory chips does not have a sense of time, input signals can arrive at different times, which means the output of the combinatory chip will change depending on when each input signal arrives. The characteristic of sequential chips can be used to *synchronise* the overall computer, as it can make sure no output signal is sent before all input signals have arrived and been processed successfully. 