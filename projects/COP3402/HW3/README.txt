Created By:
Alejandro Fernandez
Brandon Surh

These files:
	lex.c
	parser.c
	vm.c
	driver.c
	compiler.h
	Makefile

Are included to implement a Recursive Descent Parse and Intermediate Code Generator for tiny PL/0 with the aid of a compiler driver to combine several programs into one.
If any error is encounter, the program stops executing immediately. 

To compile: 	$ make
To run: 	$ ./a.out <input file name> <directives>
Ex:			$ ./a.out input.txt -l -a -v


Notes about directives aka flags (-l, -a, -v):
		-l : print the list and table of lexemes/tokens (lex.c) to the screen
		-a : print the generated assembly code (parser.c) to the screen
		-v : print virtual machine execution trace (vm.c) to the screen