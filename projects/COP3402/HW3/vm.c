// Alejandro Fernandez 
// Brandon Surh

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "compiler.h"

#define MAX_CODE_LENGTH 500
#define MAX_STACK_HEIGHT 100

int base(int stack[], int level, int BP);

// Virtual Machine 
void execute(instruction *code, int print)
{
	int *stack = malloc(MAX_STACK_HEIGHT * sizeof(int));
	int i, size;
	instruction *IR;

	// initialize sp, bp, pc
	int sp = -1;
	int bp = 0;
	int pc = 0;
	
	// initialize auxillary variables
	int oldpc = 0;

	if(print)
	{
		printf("\t\t\t\t\tPC\t\tBP\t\tSP\t\tstack\n");
		printf("Initial values:\t\t%d \t\t%d \t\t%d\n", pc, bp, sp);
	}

	i = 0;
	int haltFlag = 1;
 	
	while (haltFlag == 1)		// stops at sys 0 3 or (9 0 3)
	{
		// fetch instruction into the ir array, increase pc by 1
		IR = &code[pc];
   		pc++;
		if (IR->opcode == 0)
			break;
 	 	// get op names
		switch(IR->opcode)
		{
			case 1:
			{
				strcpy(IR->op,"LIT");
				break;
			}
			
			case 2:
			{
				strcpy(IR->op,"OPR");
				break;
			}
			
			case 3:
			{
				strcpy(IR->op,"LOD");
				break;
			}
			case 4:
			{
				strcpy(IR->op,"STO");
				break;
			}

			case 5:
			{
				strcpy(IR->op,"CAL");
				break;
			}

			case 6:
			{
				strcpy(IR->op,"INC");
				break;
			}

			case 7:
			{
				strcpy(IR->op,"JMP");
				break;
			}

			case 8:
			{
				strcpy(IR->op,"JPC");
				break;
			}

			case 9:
			{
				strcpy(IR->op,"SYS");
				break;
			}
			default:  // default should never be reached
			{
				printf("%d ERROR, opcode is %d\n", __LINE__, IR->opcode);
				exit(0);
			}	
		}
		// execute instruction
		switch (IR->opcode)
		{
			case 1:     // LIT case
			{
				++sp;
				stack[sp] = IR->m;
				break;
			}
			
			case 2:		// OPR case
			{
				switch (IR->m)
				{
					case 0:
					{
						strcpy(IR->op, "OPR");
						stack[bp - 1] = stack[sp];
						sp = bp - 1;
						bp = stack[sp + 2];
						pc = stack [sp + 3];
						break;	// return from subroutine
					}
					  
					case 1:		// neg
					{
						strcpy(IR->op, "OPR");
    			        stack[sp] = -1 * stack[sp];
						break;
					}
					case 2: 	// add
					{
						strcpy(IR->op, "OPR");
						--sp;		// decrease top by one
						stack[sp] = stack[sp] + stack[sp + 1];
						break;
					}
					case 3:		// sub
					{
						strcpy(IR->op, "OPR");
						--sp;		// decrease top by one
						
						stack[sp] = stack[sp] - stack[sp + 1];
						break;
					}
					case 4:		// mul
					{
						strcpy(IR->op, "OPR");
						--sp;		// decrease top by one
						 
						stack[sp] = stack[sp] * stack[sp + 1];
					
						break;
					}
					case 5:		// div
					{
						strcpy(IR->op, "OPR");
						--sp;		// decrease top by one
						stack[sp] = stack[sp] / stack[sp + 1];
						break;
					}
					case 6:		// odd
					{
						strcpy(IR->op, "OPR");
						stack[sp] = stack[sp]%2;
						break;
					}
					case 7:		// mod
					{
						strcpy(IR->op, "OPR");
						--sp;		// decrease top by one
						stack[sp] = stack[sp] % stack[sp + 1];
						break;
					}
					case 8:		// eql
					{
						strcpy(IR->op, "OPR");
						--sp;		// decrease top by one
						if (stack[sp] == stack[sp + 1])
							stack[sp] = 1;
						else
							stack[sp] = 0;
						break;
					}
					case 9:		// neq
					{
						strcpy(IR->op, "OPR");
						--sp;		// decrease top by one
						if (stack[sp] != stack[sp + 1])
							stack[sp] = 1;
						else
							stack[sp] = 0;
						break;
					}
					case 10:		// lss
					{
						strcpy(IR->op, "OPR");
						--sp;		// decrease top by one
						if (stack[sp] < stack[sp + 1])
							stack[sp] = 1;
						else
							stack[sp] = 0;
						break;
					}
					case 11:		// leq
					{
						strcpy(IR->op, "OPR");
						--sp;		// decrease top by one
						if (stack[sp] <= stack[sp + 1])
							stack[sp] = 1;
						else
							stack[sp] = 0;
						break;
					}
					case 12:		// gtr
					{
						strcpy(IR->op, "OPR");
						--sp;		// decrease top by one
						if (stack[sp] > stack[sp + 1])
							stack[sp] = 1;
						else
							stack[sp] = 0;
						break;
					}
					case 13:		// geq
					{
						strcpy(IR->op, "OPR");
						--sp;		// decrease top by one
						if (stack[sp] >= stack[sp + 1])
							stack[sp] = 1;
						else
							stack[sp] = 0;
						break;
					}
					
					default:  // should never be reached
					{
						printf("%d ERROR", __LINE__);
						exit(0);
						break;
					}
				}
        		break;
			}
			
			case 3:      // LOD case
			{
				++sp;
				stack[sp] = stack[base(stack, IR->l, bp) + IR->m];
				break;
			}

			case 4:       // STO case
			{
				stack[base(stack, IR->l, bp) + IR->m] = stack[sp];
				--sp;
				break;
			}

			case 5:      // CAL case
			{
				stack[sp + 1] = base(stack, IR->l, bp);
				stack[sp + 2] = bp;
				stack[sp + 3] = pc;
				stack[sp + 4] = stack[sp];
				bp = sp + 1;
				pc = IR->m;
				break;
			}

			case 6:     // INC case
			{
				sp += IR->m;
				break;
			}

			case 7:     // JMP case
			{
				pc = IR->m;
				break;
			}

			case 8:     //JPC case
			{
				if (stack[sp] == 0)
					pc = IR->m;
				--sp;
				break;
			}

			case 9:     // SYS case
			{
				if (IR->m == 1)
				{
					printf("Top of Stack Value: %d\n", stack[sp]);
					--sp;
         			break;
				}
				else if (IR->m == 2)
				{
         			printf("Please Enter an Integer: \n"); 
					++sp;
					scanf("%d", &stack[sp]);
          			break;
				}
				else if (IR->m == 3)
				{
					haltFlag = 0;
          			break;
				}
          		break;
			}

			default:    // default should never be reached
			{
				printf("%d ERROR", __LINE__);
				exit(0);
				break;
			}

			
		} // end of switch case

		if (print)
		{
			
			printf("%d\t%s  %d\t%d\t\t%d\t\t%d\t\t%d\t\t", oldpc, IR->op, IR->l, IR->m, pc, bp, sp);
		
			oldpc = pc;
			for(i = 0; i <= sp; i++)
			{
				if (i != (bp) || i == 0)
					printf("%d ", stack[i]);
				else
				{
       				printf("| ");;
					printf("%d ", stack[i]);
					continue;
				}
			}
		printf("\n");
		}
	} // end of while loop
	return;
}

int base(int stack[], int level, int BP)
{
	int base = BP;
	int counter = level;
	while (counter > 0)
	{
		base = stack[base];
		counter--;
	}
	return base;
}