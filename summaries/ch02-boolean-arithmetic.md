# Chapter 02 - Boolean Arithmetic

Summary of Chapter 2 of *The Elements of Computing Systems* by Filip Hedberg

-----

Most operations performed by a computer can be reduced to elementary numeric additions. Hence, we need a way to represent numbers using our only tool up to this point: Boolean values. 

Binary numbers are a way to express numbers as we know them in 1’s and 0’s. This number system is founded on base 2, which means we can summarise exponents of two’s to express any number. Each bit represents 2 to the exponent of that bits place in the series of bits. The general formula looks like this for 5 bits: 
`bit5 * 2 ^ 4 + bit4 * 2 ^ 3 + bit3 * 2 ^ 2 + bit2 * 2 ^ 1 + bit1 * 2 ^ 0`

There is no theoretical limit on how many bits you can use to represent a number, but the number of bits limits how large numbers that can be expressed. A binary system with n number of bits can produce `2 ^ n` unique combinations of bits. A two bit binary system can produce 00, 01, 10, and 11.

#### Binary addition
Binary numbers can be added similar to how we add normal decimal numbers. The numbers are summed from right to left in pairs, from the *least significant bit* (LSB) to the *most significant bit* (MSB). For each bit position, the result will be two bits: the sum and the carry. The sum can only be 0 or 1 (it must be represented in binary after all), so any larger number must carry over to the bit next up the significance ladder. For each position, the previous carry is summed together with the bits of the current position, resulting in the sum and carry for the next bit position.

If two numbers are added so that the most significant bit summation results in a carry, we have no more bits to store this carry number. This is called *overflow*.

#### Signed numbers
When performing algebraic calculations, it’s useful to not be restricted to only positive numbers. This means we need some way to tell if a number is positive or negative. We call numbers that can be either positive or negative *signed numbers*.

A natural approach is to split the number of available numbers in half. The most common method is called the *2’s complement* method (also known as *radix complement*). For a given number x, the 2’s complement is 0 if x is 0, else it is `(2 ^ n) - x`.

Let’s see an example for a 3 bit system. Three bits can generate `2 ^ 3 = 8` numbers, which means 4 positive and 4 negative numbers. We must be able to represent 0 as well, so we use one of the positive numbers for that. We can now represent the eight numbers `3, 2, 1, 0, -1, -2, -3, -4`.

The positive numbers are:
* 0 = 000
* 1 = 001
* 2 = 010
* 3 = 011

We can now write the negative numbers as `(2 ^ n) - x`:
* -1: 8 - 1 = 7 = 111
* -2: 8 - 2 = 6 = 110
* -3: 8 - 3 = 5 = 101
* -4: 8 - 4 = 4 = 100

We see that the first bit is in essence dedicated to the sign, where 1 means the numbers is negative. We also notice that the negative numbers “start” from the maximum number representable by the numbers of bits, and becomes lower for lower numbers.

Note that the maximal and minimal numbers are `2 ^ (n - 1) - 1` for positive numbers and `2 ^ (n - 1)` for negative numbers. We take `n - 1` because the one bit is used for the sign. The positive numbers are one fewer since they also take on the burden to represent the value 0.

To obtain the binary representation of `-x`, leave all the trailing least significant 0’s and the first least significant 1 intact, and flip all the remaining bits. Another way to achieve the same thing is to flip all bits and add 1 to the result.

A practical application of this method is that negative numbers can be added with the normal binary addition. Even though numbers will overflow, we can ignore the overflow as the result will be accurate. Let’s take the example above, and calculate `-1 + -2`. The natural numbers are 7 and 6, and the sum of those is 13. 13 can’t be represented in our system, as the carry overflows (the next bit would represent the number 8), so we can subtract 8 to see the actual result of our calculation which is `13 - 8 = 5`. And number does 5 represent in our system? -3.

#### Arithmetic Logic Unit (ALU)
We can now represent numbers with Binary bits, add them together, and get the negative value from a positive value, all through bitwise operations. This means we can group this logic together in a single hardware chip, called *Arithmetic Logical Unit* which can encapsulate all the basic arithmetic and logical operators we need.

An arithmetic logic unit can be designed in various ways. One example is two have two inputs of n bits each, and m number of single bit inputs called *control bits*. Each combination of control bits ask the chip to perform a certain function, and `m` control bits can generate `2 ^ m` possible functions. The proposed ALU can perform different functions for adding numbers and performing logic operations on Boolean values, all implemented with simple Boolean operations.
