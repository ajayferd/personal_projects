// Alejandro Fernandez
// Brandon Surh

#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#define nul -1


typedef enum { 
modsym = 1, identsym, numbersym, plussym, minussym,
multsym,  slashsym, oddsym, eqlsym, neqsym, lessym, leqsym,
gtrsym, geqsym, lparentsym, rparentsym, commasym, semicolonsym,
periodsym, becomessym, beginsym, endsym, ifsym, thensym, 
whilesym, dosym, callsym, constsym, varsym, procsym, writesym,
readsym , elsesym, returnsym } token_type;

int wsym [ ] =  {nul, beginsym, callsym, constsym, dosym, elsesym, endsym, ifsym, oddsym, procsym, readsym, thensym, varsym, whilesym, writesym};
char  *word [ ] = {"null", "begin", "call", "const", "do", "else", "end", "if", "odd", "procedure", "read", "then", "var", "while", "write"};
// for symbolsArray each index matches with the number associated with the symbol tokentype 
char symbolsArray [ ] = {"0%00+-*/0=0<0>0(),;.X"};

int main(int argc, char *argv[]) 
{
  	// read file from command line
	FILE* fp = fopen(argv[1], "r");
	char c, spec;
	char string[12];	
	char tokenlist[100] = "";
	char buffer[10];
	int i, j;
    int flag;
	printf("Lexeme Table:");
	printf("\nlexeme\t\ttoken type\n");
	c = fgetc(fp);

    // read in characters until we reach the end of file
	while (!feof(fp))
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
					
					
					c = fgetc(fp);
					break;
				}
				
				string[i] = c;		
				c = fgetc(fp);
				i++;
				string[i] = '\0';
			}
			
			if (strlen(string) > 11)
			{
				int len = strlen(string);
				printf("Error : Identifier names cannot exceed 11 characters\n");
			}
			else
			{
				printf("\t%s", string);
				for (int i = 0; i < 14; i++)
				{
					// strcmp returns 0 if the two strings being compared are equivalent
					// prints token if a reserved word (wsym)
					if (strcmp(string, word[i]) == 0)
					{
						printf("\t%d \n", wsym[i]);
						sprintf(buffer, "%d", wsym[i]);
						strcat(tokenlist, buffer);
						strcat(tokenlist, " ");
                        flag = 1;
						break;
					}
					
				}
                // runs if not a wsym
				if (flag != 1)		
				{
					printf("\t\t%d\n", identsym);
					sprintf(buffer, "%d", identsym);
					strcat(tokenlist, buffer);
					strcat(tokenlist, " ");
					strcat(tokenlist, string);
					strcat(tokenlist, " ");
					flag = 0;
				}
			}
            // if spec was filled print a line for it
			if (spec != '0')		
			{
				for(int p = 0; p < 23; p++)
				{
					if(spec == symbolsArray[p])
					{
						printf("\t%c", symbolsArray[p]);
						printf("\t\t%d\n", p);
						sprintf(buffer, "%d", p);
						strcat(tokenlist, buffer);
						strcat(tokenlist, " ");
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

					c = fgetc(fp);
					break;
				}
				// error if encounters a letter (ex. 2array)
				if(isdigit(digitString[0]) && isalpha(digitString[1]))
                {
					printf("Error: Identifiers cannot begin with a digit\n");
					c = fgetc(fp);
					while (isalpha(c))
					{
						c = fgetc(fp);
						for(int p = 0; p < 23; p++)
						{
							if(c == symbolsArray[p])
							{
								printf("\t%c", symbolsArray[p]);
								printf("\t\t%d\n", p);
								sprintf(buffer, "%d", p);
								strcat(tokenlist, buffer);
								strcat(tokenlist, " ");
								break;
							}
						}
					}
					digitString[0] = '\0';
					break;
				}   
				
				digitString[i] = c;
				c = fgetc(fp);
				i++;
			    digitString[i] = '\0';
			}
			
			if (strlen(digitString) > 5)
				printf("Error : Numbers cannot exceed 5 digits\n");
			else if (digitString[0] != '\0')
			{
				printf("\t%s\t\t%d\n", digitString, atoi(digitString) + 1);
				sprintf(buffer, "%d", atoi(digitString) + 1);
				strcat(tokenlist, buffer);
				strcat(tokenlist, " ");	
				strcat(tokenlist, digitString);
				strcat(tokenlist, " ");	
				
			}
            // if spec was filled print a line for it
			if (spec != '0')		
			{
				printf("\t%c", spec);
				if (spec == ',')
				{
					printf("\t\t%d\n", commasym);
					sprintf(buffer, "%d", commasym);
					strcat(tokenlist, buffer);
					strcat(tokenlist, " ");	
				}
				if (spec == ';')
				{
					printf("\t\t%d\n", semicolonsym);
					sprintf(buffer, "%d", semicolonsym);
					strcat(tokenlist, buffer);
					strcat(tokenlist, " ");	
				}
				if (spec == '.')
				{
					printf("\t\t%d\n", periodsym);	
					sprintf(buffer, "%d", periodsym);
					strcat(tokenlist, buffer);
					strcat(tokenlist, " ");	
				}	
			}
			
		}
		// checks if c is an unprintable character, also eats excess whitespace between chars
		else if (iscntrl(c) || c == ' ')
		{
			c = fgetc(fp);
            continue;
		}
        // if c is a symbol
        else
        {
            char charHelperArray[2] = {'\0', '\0'};
            
            charHelperArray[0] = c;         // put in the first symbol 
            charHelperArray[1] = fgetc(fp); // puts in the next character regardless of properities
			c = charHelperArray[1];

            if (charHelperArray[0] == '/' && charHelperArray[1] != '*')
            {   
                printf("\t%c", charHelperArray[0]);
                printf("\t\t%d\n", slashsym);
				sprintf(buffer, "%d", slashsym);
				strcat(tokenlist, buffer);
				strcat(tokenlist, " ");	
            }    
            else if (charHelperArray[0] == '/' && charHelperArray[1] == '*')
            {  
                while(1)    // read in the comment until we reach the end
                {
                    c = fgetc(fp);
                    if (c == '*')
                    {
                        c = fgetc(fp);
                        // reached the end of the comment 
                        if (c == '/') 
                            break;
                    }    
                        
                }
            }
            else if (charHelperArray[0] == ':' && charHelperArray[1] == '=')
            {
                printf("\t%c%c", charHelperArray[0], charHelperArray[1]);
                printf("\t\t%d\n", becomessym);
				sprintf(buffer, "%d", becomessym);
				strcat(tokenlist, buffer);
				strcat(tokenlist, " ");	
            }
            else if (charHelperArray[0] == '<' && charHelperArray[1] == '>')
            {
                printf("\t%c%c", charHelperArray[0], charHelperArray[1]);
                printf("\t\t%d\n", neqsym);
				sprintf(buffer, "%d", neqsym);
				strcat(tokenlist, buffer);
				strcat(tokenlist, " ");	
            }
            else if (charHelperArray[0] == '<' && charHelperArray[1] == '=')
             {
                printf("\t%c%c", charHelperArray[0], charHelperArray[1]);
                printf("\t\t%d\n", leqsym);
				sprintf(buffer, "%d", leqsym);
				strcat(tokenlist, buffer);
				strcat(tokenlist, " ");	
            }
            else if (charHelperArray[0] == '>' && charHelperArray[1] == '=')
             {
                printf("\t%c%c", charHelperArray[0], charHelperArray[1]);
                printf("\t\t%d\n", geqsym);
				sprintf(buffer, "%d", geqsym);
				strcat(tokenlist, buffer);
				strcat(tokenlist, " ");	
            }
			else
			{
				for(int p = 0; p < 23; p++)
				{
					if(charHelperArray[0] == symbolsArray[p])
					{
						printf("\t%c", symbolsArray[p]);
						printf("\t\t%d\n", p);
						sprintf(buffer, "%d", p);
						strcat(tokenlist, buffer);
						strcat(tokenlist, " ");
						break;
					}
					else if(symbolsArray[p] == 'X')
		        		printf("Error: Invalid Symbol\n");
				}
			}    
		}
		c = fgetc(fp);
	}
    printf("\nToken List:\n");
	printf("%s\n", tokenlist);
	fclose(fp);
  	return 0;
} 