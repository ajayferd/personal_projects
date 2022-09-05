// Alejandro Fernandez 
// Brandon Surh

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "compiler.h"

instruction *code;
symbol *table;
lexeme *list;
lexeme lex;
int token;
int codeidx = 0;
int symbolidx = 0;
int symbolCount = 0;
int listidx = 0;
int symIdx = 0;
int jpcIdx = 0;
int loopIdx = 0;
char savedName[13];
int address = 4;
int numConst = 0;

int level;
int numVars;

char *errormsg[] = {
	"",
	"Error : program must end with period\n", 													// 1
	"Error : const, var, and read keywords must be followed by identifier\n",					// 2
	"Error : symbol name has already been declared\n",											// 3
	"Error : constants must be assigned with =\n",												// 4
	"Error : constants mustbe assigned an integer value\n",										// 5
	"Error : constant and variable declarations must be followed by a semicolon\n",				// 6
	"Error : undeclared symbol\n",																// 7
	"Error : only variable values may be altered\n",											// 8
	"Error : assignment statements must use :=\n",												// 9
	"Error : begin must be followed by end\n",													// 10
	"Error : if must be followed by then\n",													// 11
	"Error : while must be followed by do\n",													// 12
	"Error : condition must contain comparison operator\n",										// 13
	"Error : right parenthesis must follow left parenthesis\n",									// 14
	"Error : arithmetic equations must contain operands, parentheses, numbers, or symbols\n"	// 15
};

void emit(int opcode, char op[], int l, int m);
void enter(int kind, char name[13], int parameter, int addr);
void program();
void block();
void const_decl();
int var_decl();
void statement();
void constAssignmentList();
void ident();
void number();
void identList();
void expression();
void statementList();
void condition();
void relation();
void term();
void addingOperation();
void factor();
void multiplyingOperator();
int get_token();
int symbolTableCheck(char *string);



// For constants, you must store kind, name and value.
// For variables, you must store kind, name, L and M.

instruction* parse(lexeme *tokenList, int print)
{

	list = tokenList;
	lex = list[0];

	// initialize code and values 
	code = malloc(500 * sizeof(instruction));
	for(int i = 0; i < 500; i++)
	{
		code[i].opcode = 0;
		strcpy(code[i].op, "");
		code[i].l = 0;
		code[i].m = 0;
	}

	level = 0;
	symbolidx = 0;
	int line = 0;
	
	token = lex.type = 1;
	
	// run program and print if needed
	program();
	
	if(print)
	{
		printf("\nGenerated Assembly:\n");
		printf("Line\tOP\tL\tM\n");

		for(int i = 0; i < codeidx; i++)
		{
			
			printf("%d\t\t%s\t%d\t%d\n", line, code[i].op, code[i].l, code[i].m);
			line++;
		}
		printf("\n\n");
	}


	return code;
}


void enter(int kind, char name[13], int parameter, int addr)
{

	table[symIdx].kind = kind;
	strcpy(table[symIdx].name, name);
	if (kind == 1)		// const
	{
		table[symIdx].val = parameter;
		table[symIdx].level = -1;
		table[symIdx].addr = addr;
		numConst++;
		numConst++;
	}
	else if (kind == 2)		// var
	{
		table[symIdx].val = 0;
		table[symIdx].level = parameter;
		table[symIdx].addr = address;
		
		address++;
	}
	else if (kind == 3)		//proc
	{
		table[symIdx].val = 0;
		table[symIdx].level = 0;
		level += 1;			//incrementing global level
		table[symIdx].addr = addr;
	}
	symIdx++;
	symbolCount++;

}

// generates code
void emit(int opcode, char op[], int l, int m)
{
	code[codeidx].opcode = opcode;
	strcpy(code[codeidx].op, op);
	code[codeidx].l = l;
	code[codeidx].m = m;
	codeidx++;
}

int get_token()
{
	if(lex.type != -1)
	{
		if(token > 0 && token < 35)
		{
			lex = list[listidx];	
			token = list[listidx++].type;
			return 1;	
		}
	}
	else if(token == -1)
	{
		return 0;
	}
	return 0;
	
	
}


int symbolTableCheck(char *string)
{

	for (int i = 0; i < symbolCount; i++)
	{	
	
		if (strcmp(string, table[i].name) == 0)
		{
			
			return i;
		}
	}
	return -1;		// not found
}


void program()
{
	table = malloc(500 * sizeof(symbol));

	get_token();
	block();
	if (token != periodsym)
	{
		printf("%s", errormsg[1]);
		exit(0);
	}
	emit(9, "SYS", 0, 3);
}

void block()
{
	const_decl();
	numVars = var_decl();
	emit(6, "INC", 0, 4 + numVars);
	statement();
}

void const_decl()
{
	if (token == constsym)
	{
		// check if grammar is correct, if not error
		do
		{
			get_token();
			if (token != identsym)
			{
				printf("%s", errormsg[2]);
				exit(0);
			}	

			if (symbolTableCheck(lex.name) != -1)
			{
				printf("%s", errormsg[3]);
				exit(0);
			}

			strcpy(savedName, lex.name);
			get_token();
			
			if (token != eqlsym)
			{
				printf("%s", errormsg[4]);
				exit(0);
			}

			get_token();
			if (token != numbersym)
			{
				printf("%s", errormsg[5]);
				exit(0);
			}
			
			enter(1, savedName, lex.value, 3);
			get_token();
		}while (token == commasym);
		


		if (token != semicolonsym)
		{
			

			printf("%s", errormsg[6]);
			exit(0);
		}
		get_token();
	}
}

// returns the number of variables that are declared
int var_decl()
{
	numVars = 0;
	if (token == varsym)
	{
		do
		{
			numVars++;
			get_token();
			if (token != identsym)
			{
				printf("%s", errormsg[2]);
				exit(0);
			}
			if (symbolTableCheck(lex.name) != -1)
			{
				printf("%s", errormsg[3]);
				exit(0);
			}
			
			enter(2, lex.name, 0, numVars + 3);
			get_token();
		}while (token == commasym);


		if (token != semicolonsym)
		{
			printf("%s", errormsg[6]);
			exit(0);
		}
		get_token();
	}
	return numVars;
}


void statement()
{
	// checks grammar, announces errors if any and quits
	if (token == identsym)
	{
		symIdx = symbolTableCheck(lex.name);
		if(symIdx == -1)
		{
			printf("%s", errormsg[7]);
			exit(0);
		}
		
		if(table[symIdx].kind != 2) // not a variable
		{
			printf("%s", errormsg[7]);
			exit(0);
		}
		get_token();
		if(token != becomessym)
		{
			printf("%s", errormsg[9]);
			exit(0);
		}
		get_token();
		expression();
		
		emit(4, "STO", level, table[symIdx -1 + numConst].addr);
		return;
	}
	if (token == beginsym)
	{
		do
		{
			get_token();
			statement();	
		} while(token == semicolonsym);

		if(token != endsym)
		{
			printf("%s", errormsg[10]);
			exit(0);
		}
		get_token();
		return;
	}
	if (token == ifsym)
	{
		get_token();
		condition();
		jpcIdx = codeidx;
		emit(8, "JPC", 0, jpcIdx);
		if(token != thensym)
		{
			printf("%s",  errormsg[11]);
			exit(0);
		}
		get_token();
		statement();
		code[jpcIdx].m = codeidx;
		return; 
	}
	if (token == whilesym)
	{
		get_token();
		loopIdx = codeidx;
		condition();
		if(token != dosym)
		{
			printf("%s",  errormsg[12]);
			exit(0);
		}
		get_token();
		jpcIdx = codeidx;
		emit(8, "JPC", 0, jpcIdx);
		statement();
		emit(7, "JMP", 0, loopIdx);
		code[jpcIdx].m = codeidx;
		return;
	}
	if(token == readsym)
	{
		get_token();
		if(token != identsym)
		{
			printf("%s",  errormsg[2]);
			exit(0);
		}
		symIdx = symbolTableCheck(lex.name);
	
		if(symIdx == -1)
		{
			
			printf("%s", errormsg[9]);	
			exit(0);
		}
			
		if(table[symIdx].kind != 2) // not a var
		{
			printf("%s",  errormsg[8]);
			exit(0);
		}
		
		get_token();
		emit(9, "SYS", 0, 2);
		emit(4, "STO", level, table[symIdx].addr);
		return;	
	}
	if(token == writesym)
	{
		get_token();
		expression();
		emit(9, "SYS", 0, 1);
		return;
	}
}


void condition()
{
	if(token == oddsym)
	{
		get_token();
		expression();
		emit(2, "OPR", 0, 6);
	}
	else
	{
		expression();
		if(token == eqlsym)
		{
			get_token();
			expression();
			emit(2, "OPR", 0, 8);
		}
		else if(token == neqsym)
		{
			get_token();
			expression();
			emit(2, "OPR", 0, 9);
		}
		else if(token == lessym)
		{
			get_token();
			expression();
			emit(2, "OPR", 0, 10);
		}
		else if(token == leqsym)
		{
			get_token();
			expression();
			emit(2, "OPR", 0, 11);
		}
		else if(token == gtrsym)
		{
			get_token();
			expression();
			emit(2, "OPR", 0, 12);
		}
		else if(token == geqsym)
		{
			get_token();
			expression();
			emit(2, "OPR", 0, 13);
		}
		else
		{
			printf("%s", errormsg[13]);
			exit(0);
		}
	}
}


void expression()
{
	if(token == minussym)
	{
		get_token();
		term();
		emit(2, "OPR", 0, 1);
		while(token == plussym || token == minussym)
		{
			if(token == plussym)
			{
				get_token();
				term();
				emit(2, "OPR", 0, 2);
			}
			else
			{
				get_token();
				term();
				emit(2, "OPR", 0, 3);
			}
		}
	}
	else
	{
		if(token == plussym)
			get_token();

		term();
		while(token == plussym || token == minussym)
		{
			if(token == plussym)
			{
				get_token();
				term();
				emit(2, "OPR", 0, 2);
			}
			else
			{
				get_token();
				term();
				emit(2, "OPR", 0, 3);
			}
		}
	}
}

void term()
{
	factor();
	while(token == multsym || token == slashsym || token == modsym)
	{
		if(token == multsym)
		{
			get_token();
			factor();
			emit(2, "OPR", 0, 4);
		}
		else if(token == slashsym)
		{
			get_token();
			factor();
			emit(2, "OPR", 0, 5);
		}
		else
		{
			get_token();
			factor();
			emit(2, "OPR", 0, 7);
		}
	} 	
}

void factor()
{
	if(token == identsym)
	{
		symIdx = symbolTableCheck(lex.name);

		if(symIdx == -1)
		{
			printf("%s", errormsg[9]);
			exit(0);
		}
		if(table[symIdx].kind == 1)
			emit(1, "LIT", 0, table[symIdx].val);
		else 
			emit(3, "LOD", level, table[symIdx].addr);
		get_token();
	}
	else if(token == numbersym)
	{
		emit(1, "LIT", 0, lex.value);
		get_token();
	}
	else if(token == lparentsym)
	{
		get_token();
		expression();
		if(token != rparentsym)
		{
			printf("%s", errormsg[14]);
			exit(0);
		}	

		get_token();
	}
	else
	{
		printf("%s", errormsg[15]);
		exit(0);
	} 
}