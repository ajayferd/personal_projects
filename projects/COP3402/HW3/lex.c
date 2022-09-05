// Alejandro Fernandez 
// Brandon Surh

#include "compiler.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>
#define nul -1



int wsym [ ] =  {nul, beginsym, callsym, constsym, dosym, elsesym, endsym, ifsym, oddsym, procsym, readsym, thensym, varsym, whilesym, writesym};
char*  word [ ] = {"null", "begin", "call", "const", "do", "else", "end", "if", "odd", "procedure", "read", "then", "var", "while", "write"};

// for symbolsArray, each index matches with the number associated with the symbol tokentype
char symbolsArray [ ] = {"0%00+-*/0=0<0>0(),;.X"};

lexeme* lex_analyze(char *inputfile, int print)
{
	lexeme *list = malloc(500 * sizeof(lexeme));
	char c, spec;
	char string[12];	
	char tokenlist[200] = "";
	char buffer[100];
	int i, p;
	int u = 0;
    int flag;
	int counter = 0;

	if (print)
	{
		printf("Lexeme Table:");
		printf("\nlexeme\t\ttoken type\n");
	}

	c = inputfile[counter++];

    // read in characters until we reach the end of file
	while (inputfile[counter] != '\0')
	{
		i = 0;

		// if c is a letter
		if (isalpha(c))
		{
			char string[30];	// holds word
			spec = '0';			// resets special char holder
			flag = 0;			// resets flag for wsym
			
			// runs until space (end of word)
			while (!(c == ' '))	
			{
				// if character met is not number or letter take in special char
				if (!isalpha(c) && !isdigit(c))		
				{
					spec = c;
					if (spec != ',' && spec != ';' && spec != '.' && !iscntrl(spec))
					{
                        // takes care of spec chars right before new line
						if (iscntrl(spec))		
						{
							break;
						}
						break;
					}
					
					c = inputfile[counter++];
					break;
				}
				
				string[i] = c;	
				c = inputfile[counter++];
				i++;
				string[i] = '\0';
			}
			
			if (strlen(string) > 11)
			{
				int len = strlen(string);
				printf("Error : Identifier names cannot exceed 11 characters\n");
				exit(0);
			}
			else
			{
				for (i = 0; i < 15; i++)
				{
					// strcmp returns 0 if the two strings being compared are equivalent
					// prints token if a reserved word (wsym)
					if (strcmp(string, word[i]) == 0)
					{
						if(print)
						{
							printf("\t%s\t%d \n", word[i], wsym[i]);
						}

						sprintf(buffer, "%d", wsym[i]);
						strcat(tokenlist, buffer);
						strcat(tokenlist, " ");
                        flag = 1;

						
						strcpy(list[u].name, string);
						list[u].type = wsym[i];
						list[u++].value = 0;
						
						break;
					}
					
				}
                // runs if not a wsym
				if (flag != 1)		
				{
					if(print)
						printf("\t%s\t\t%d\n", string, identsym);
					
					sprintf(buffer, "%d", identsym);
					strcat(tokenlist, buffer);
					strcat(tokenlist, " ");
					strcat(tokenlist, string);
					strcat(tokenlist, " ");

					strcpy(list[u].name, string);
					list[u].type = identsym;
					list[u++].value = 0;

					flag = 0;
				}
			}
            // if spec was filled print a line for it
			if (spec != '0')		
			{
				for (p = 0; p < 25; p++)
				{
					if(spec == symbolsArray[p])
					{
						if(print)
							printf("\t%c\t\t%d\n", symbolsArray[p], p);
						
						sprintf(buffer, "%d", p);
						strcat(tokenlist, buffer);
						strcat(tokenlist, " ");
						
						list[u].name[0] = symbolsArray[p];
						list[u].name[1] = '\0';
						list[u].type = p;
						list[u++].value = 0;

						break;
					}
				}
			}
		}

		// runs if is a number
		else if (isdigit(c))
		{
			char digitString[30];
			while (!(c == ' '))	
			{
				// if character met is not number or letter take in special char
				if (!isalpha(c) && !isdigit(c))		
				{
					spec = c;
					if (spec != ',' && spec != ';' && spec != '.' && !iscntrl(spec))
					{
						if (iscntrl(spec))		// takes care of spec chars right before new line
						{
							break;
						}
						break;
					}
					c = inputfile[counter++];
					break;
				}
				// error if encounters a letter (ex. 2array)
				if(isdigit(digitString[0]) && isalpha(digitString[1]))
                {
					printf("Error: Identifiers cannot begin with a digit\n");
					exit(0);
					c = inputfile[counter++];

					while (isalpha(c))
					{
						c = inputfile[counter++];
						for (p = 0; p < 25; p++)
						{
							if(c == symbolsArray[p])
							{
								if(print)
									printf("\t%c\t\t%d\n", symbolsArray[p], p);

								sprintf(buffer, "%d", p);
								strcat(tokenlist, buffer);
								strcat(tokenlist, " ");

								list[u].type = p;
								list[u++].value = 0;

								break;
							}
						}
					}
					digitString[0] = '\0';
					break;
				}   
				
				digitString[i] = c;
				c = inputfile[counter++];
				i++;
			    digitString[i] = '\0';
			}
			
			if (strlen(digitString) > 5)
			{
				printf("Error : Numbers cannot exceed 5 digits\n");
				exit(0);
			}
			else if (digitString[0] != '\0')
			{
				if(print)
					printf("\t%s\t\t%d\n", digitString, numbersym);

				sprintf(buffer, "%d", 3);
				strcat(tokenlist, buffer);
				strcat(tokenlist, " ");	
				strcat(tokenlist, digitString);
				strcat(tokenlist, " ");	

				strcpy(list[u].name, digitString);
				list[u].type = numbersym;
				list[u++].value = atoi(digitString);
				
			}
            // if spec was filled print a line for it
			if (spec != '0')		
			{
				if (spec == ',')
				{
					if(print)
						printf("\t%c\t\t%d\n", spec, commasym);

					sprintf(buffer, "%d", commasym);
					strcat(tokenlist, buffer);
					strcat(tokenlist, " ");

					list[u].name[0] = spec;
					list[u].name[1] = '\0';
					list[u].type = commasym;
					u++;	
				}
				if (spec == ';')
				{
					if(print)
						printf("\t%c\t\t%d\n", spec, semicolonsym);

					sprintf(buffer, "%d", semicolonsym);
					strcat(tokenlist, buffer);
					strcat(tokenlist, " ");

					list[u].name[0] = spec;
					list[u].name[1] = '\0';
					list[u].type = semicolonsym;
					u++;	
				}
				if (spec == '.')
				{
					if(print)
						printf("\t%c\t\t%d\n", spec, periodsym);

					sprintf(buffer, "%d", periodsym);
					strcat(tokenlist, buffer);
					strcat(tokenlist, " ");

					list[u].name[0] = spec;
					list[u].name[1] = '\0';
					list[u].type = periodsym;
					u++;	
				}
			
			}
		}
		// checks if c is an unprintable character, also eats excess whitespace between chars
		else if (iscntrl(c) || c == ' ')
		{
			c = inputfile[counter++];
            continue;
		}
        // if c is a symbol
        else
        {
            char charHelperArray[2] = {'\0', '\0'};
            
            charHelperArray[0] = c;         // put in the first symbol 
			c = inputfile[counter++];
            charHelperArray[1] = c; // puts in the next character regardless of properities
			c = charHelperArray[1];

			// else if chain for all 2 character symbols
            if (charHelperArray[0] == '/' && charHelperArray[1] != '*')
            {
				if(print)   
                	printf("\t%c\t\t%d\n", charHelperArray[0], slashsym);
              
				sprintf(buffer, "%d", slashsym);
				strcat(tokenlist, buffer);
				strcat(tokenlist, " ");	
				list[u].type = slashsym;
            }    
            else if (charHelperArray[0] == '/' && charHelperArray[1] == '*')
            {  
                while(1)    // read in the comment until we reach the end
                {
                    c = inputfile[counter++];
                    if (c == '*')
                    {
                        c = inputfile[counter++];
                        // reached the end of the comment 
                        if (c == '/') 
                            break;
                    }    
                        
                }
            }
            else if (charHelperArray[0] == ':' && charHelperArray[1] == '=')
            {
				if(print)
                	printf("\t%c%c\t\t%d\n", charHelperArray[0], charHelperArray[1], becomessym);
                
				sprintf(buffer, "%d", becomessym);
				strcat(tokenlist, buffer);
				strcat(tokenlist, " ");

				list[u].type = becomessym;
				list[u].name[0] = charHelperArray[0];
				list[u].name[1] = charHelperArray[1];
				list[u].name[2] = '\0';
            }
            else if (charHelperArray[0] == '<' && charHelperArray[1] == '>')
            {
                if(print)
					printf("\t%c%c\t\t%d\n", charHelperArray[0], charHelperArray[1], neqsym);
                
				sprintf(buffer, "%d", neqsym);
				strcat(tokenlist, buffer);
				strcat(tokenlist, " ");

				list[u].type = neqsym;
				list[u].name[0] = charHelperArray[0];
				list[u].name[1] = charHelperArray[1];
				list[u].name[2] = '\0';
            }
            else if (charHelperArray[0] == '<' && charHelperArray[1] == '=')
             {
                if(print)
					printf("\t%c%c\t\t%d\n", charHelperArray[0], charHelperArray[1], leqsym);
              
				sprintf(buffer, "%d", leqsym);
				strcat(tokenlist, buffer);
				strcat(tokenlist, " ");

				list[u].type = leqsym;
				list[u].name[0] = charHelperArray[0];
				list[u].name[1] = charHelperArray[1];
				list[u].name[2] = '\0';
            }
            else if (charHelperArray[0] == '>' && charHelperArray[1] == '=')
             {
				if(print)
                	printf("\t%c%c\t\t%d\n", charHelperArray[0], charHelperArray[1], geqsym);
              
				sprintf(buffer, "%d", geqsym);
				strcat(tokenlist, buffer);
				strcat(tokenlist, " ");

				list[u].type = geqsym;
				list[u].name[0] = charHelperArray[0];
				list[u].name[1] = charHelperArray[1];
				list[u].name[2] = '\0';
            }
			else
			{
				// iterate through all remaining 1 character symbols
				for (p = 0; p < 25; p++)
				{
					if(charHelperArray[0] == symbolsArray[p])
					{
						if(print)
							printf("\t%c\t\t%d\n", symbolsArray[p], p);
					
			

						sprintf(buffer, "%d", p);
						strcat(tokenlist, buffer);
						strcat(tokenlist, " ");

						list[u].type = p;
						list[u].name[0] = charHelperArray[0];
						list[u].name[1] = '\0';

						break;
					}
					else if(symbolsArray[p] == 'X')
					{
		        		printf("Error: Invalid Symbol\n");
						exit(0);
					}
				}
			}
			// symbols are not names
			list[u++].value = 0;
			
		}
		c = inputfile[counter++];
	}
	
	// mark end of list with a -1
	list[u++].type = -1;
	if(print)
	{
		printf("\nToken List:\n%s\n\n", tokenlist);
	}
	return list;
}