# Chapter 01 - Boolean Logic

Summary of Chapter 1 of *The Elements of Computing Systems* by Filip Hedberg

-----

Digital devices such as computers and smartphones appear magical. Streaming video, writing a document or using the calculator is taken for granted - but how does it work? Videos, text and numbers can all be described with many pieces of data that together represents something meaningful. In essence, those activities can all be described as storing and processing information. 

Data is typically stored and processed as Boolean values that can be labeled as 1/0, on/off, true/false, yes/no. A single Boolean value is also known as a *bit*. We can describe any information by combining enough Boolean values with a system that can interpret these values into something that we can understand.

If Boolean values is all that is needed, then a computer only needs to be able to represent and manipulate these values. This is called *Boolean algebra*, and a function that takes Boolean values as inputs and outputs is called a *Boolean function*. 

A Boolean function can be described in a number of ways. A *truth table* lists all possible inputs with the given output. A *Boolean expression* uses Boolean operators (much like normal math operators) to describe the function. Typical operators include And, Or and Not. An example of a B Boolean expression if `f(x, y) = x + y` where the `+` sign in this case means the Boolean operator or and not plus. 

Any Boolean function can also be expressed with a single Boolean expression which is called its *canonical representation*. To create this expression, take all input combinations that result in a truthy, describe the inputs as a Boolean expression, and combine all the resulting expressions with the operator or. Regardless of how complex a Boolean function is, it can be described in its entirety with three basic operators: Or, And and Not. 

The Boolean function *Nand*, short for Not-And, returns 0 if both input signals are 1, and returns 0 for the other three cases. As it turns out, it is possible to combine the Nand function alone to construct the functions And, Or and Not. As we previously noted, all Boolean functions can be described using those three basic functions, which in turn means all Boolean functions can be described only using Nand. 

So how does a computer actually process and store these Boolean values? It is done with a physical device that implements a Boolean function called a *gate*. Anything that can implement a Boolean function can be a gate. There are examples of implementations with magnetic, optical, biological, hydraulic and pneumatic mechanisms. The most commonly used way is by using *transistors* etched in silicon, packaged as *chips*. The physical implementation of a gate is abstracted away from computer science.

A *primitive* gate is some mechanism that implements an elementary logical operation. A *composite* gate can represent more complex operations, and in turn combines primitive gates to achieve its goal. The combination of gates into complex functionality is also called *logic design*. 

Instead of combining physical gates by hand, we can describe how the inputs and outputs of interconnected gates should be combined with something called *Hardware Description Language*, or HDL. 

Basic gates take two input signals and provides one output. Multi-bit versions of basic gates still take two input signals, but each signal is now an array of multiple bits called *buses*. A 32-bit computer must be able to compute functions on 32-bit buses. 