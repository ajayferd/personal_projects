 /*COP 3502C Midterm Assignment One
This program written by: Alejandro Fernandez*/
#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <stdlib.h>
//#include <math.h>

#define SIZE 50
#define EMPTY -1

struct stack {

  char items[SIZE];

  int top;

};
typedef struct intStack {
  int items[SIZE];

  int top;

} intStack;

char* menu();
int isBalancedParenthesis(char* expression);

char* convertToPostfix(char* expression);
int isOperator(char c);
int getOperatorPriority(char c);
int customPow(int x,int n);

void evaluateix(char* expression);
int calculate(int a, int b, char op);
int convertToInt(char c);
void removeSpaces(char* expression);


void initialize(struct stack* stackPtr);
int full(struct stack* stackPtr);
int push(struct stack* stackPtr, char value);
int empty(struct stack* stackPtr);
char pop(struct stack* stackPtr);
char top(struct stack* stackPtr);

void int_initialize(intStack* intstackPtr);
void int_push(intStack* intstackPtr, int value);
int int_pop(intStack* intstackPtr);
int int_top(intStack* intstackPtr);
int int_full(intStack* intstackPtr);
int int_empty(intStack* intstackPtr);


int main() {
  char* str;
  char* postFix;
  //int expressionChecker = isBalancedParenthesis(str);
  //printf("%d", expressionChecker);
  while(strcmp(str = menu(), "Exit")!=0)
  {
    if(isBalancedParenthesis(str))
    {
      postFix = convertToPostfix(str);
      printf("Output: %s", postFix);
      evaluateix(postFix);
    }
    else
      printf("Parenthesis imbalanced\n");
  }
  printf("Bye!\nHappy Coding");
  return 0;
}

char* menu()
{
  char choice;
  printf("\nMenu:\ne: Enter infix\t x: Exit program\n");
  scanf("%c", &choice);
  while ((getchar()) != '\n');
  if(choice == 'e')
  {
    char inputstring[50];
    printf("Enter string: ");
    scanf("%s", inputstring);
    while ((getchar()) != '\n');
    char* infixOutput = (char*)malloc(sizeof(strlen(inputstring)));
    strcpy(infixOutput,inputstring);
    //printf("%s", infix);
    return infixOutput;
    free(infixOutput);
  }
  else //if(choice == 'x')
  {
    char exitMessage[] = "Exit";
    char* exitOutput = (char*)malloc(sizeof(strlen(exitMessage)));
    strcpy(exitOutput,exitMessage);
    return exitOutput;
    free(exitOutput);
  }
  /*else
  {
    char invalidInput[] = "Invalid input. Try again\n";
    char* invalidOutput = (char*)malloc(sizeof(strlen(invalidInput)));
    strcpy(invalidOutput,invalidInput);
    return invalidOutput;
  }*/
}
int isBalancedParenthesis(char* expression)
{
  int i;
  struct stack validator;
  initialize(&validator);

  for(i=0; i<strlen(expression); i++){
    if(expression[i] == '{' || expression[i] == '(' ||expression[i] == '[')
      push(&validator, expression[i]);
    else if(expression[i] == '}'){
      char value = pop(&validator);
      if(value != '{' || value == EMPTY){
        //printf("Invalid");
        return 0;
        break;
      }
    }
    else if(expression[i] == ')'){
      char value = pop(&validator);
      if(value != '(' || value == EMPTY){
        //printf("Invalid");
        return 0;
        break;
      }
    }
    else if(expression[i] == ']'){
      char value = pop(&validator);
      if(value != '[' || value == EMPTY){
        //printf("Invalid");
        return 0;
        break;
      }
    }
   
  }
  
  if(empty(&validator))
    {
      //printf("Valid");
      return 1;
    }
    return 1;
}
int isOperator(char c)
{
  if(c == '+' || c == '-' || c == '*' || c == '/' || c == '^')
    return 1;
  else 
    return 0;
}
int getOperatorPriority(char c)
{
  if(c == '+' || c == '-')
		return 1 ;
  
	else if(c == '/' || c == '*' || c == '%')
		return 2;

	else if(c == '^')
		return 3;

	else
		return 0;
}

int convertToInt(char c)
{
  int x;
  x = c - 48;
  return x;
}


void initialize(struct stack* stackPtr) 
{
     stackPtr->top = -1;
}
char pop(struct stack* stackPtr) 
{

    int retval;

    if (empty(stackPtr))
        return EMPTY;

    retval = stackPtr->items[stackPtr->top];
    (stackPtr->top)--;
    return retval;
}
int push(struct stack* stackPtr, char value) 
{
  if (full(stackPtr))
    return 0;
  stackPtr->items[stackPtr->top+1] = value;
  (stackPtr->top)++;
    return 1;
}
int empty(struct stack* stackPtr) 
{
  return (stackPtr->top == -1);
}
int full(struct stack* stackPtr) 
{
    return (stackPtr->top == SIZE - 1);
}
char top(struct stack* stackPtr) 
{

    // Take care of the empty case.
    if (empty(stackPtr))
        return EMPTY;

    // Return the desired item.
    return stackPtr->items[stackPtr->top];
}

void int_initialize(intStack* intstackPtr)
{
  intstackPtr->top = -1;
}
void int_push(intStack* intstackPtr, int value)
{
  intstackPtr->top = intstackPtr->top + 1;
	intstackPtr->items[intstackPtr->top] = value;
}
int int_pop(intStack* intstackPtr)
{
  int x;
	x = intstackPtr->items[intstackPtr->top];
	intstackPtr->top = intstackPtr->top - 1;
  return x;
}
int int_full(intStack* intstackPtr)
{
  return (intstackPtr->top == SIZE - 1);
}
int int_empty(struct intStack* intstackPtr)
{
  return (intstackPtr->top == -1);
}

void removeSpaces(char* expression)
{
	int i;
  int k = 0;
 
	 for(i=0;expression[i];++i)
    {
     	expression[i]=expression[k+i];
 
     	
     	if(expression[i]==' ')
     	{
		    k++;
		    i--;
	    }
     	printf("removed spaces");
    }
  
}
char* convertToPostfix(char* expression)
{
  int i = 0, j = 0, x; 
  char *item = expression;
  struct stack postfixStack;
  initialize(&postfixStack);
  //removeSpaces(expression);
    for (j = -1, i = 0; expression[i]; ++i) 
    {  
      if(isalnum(expression[i]))
        expression[++j] = expression[i];

      else if (expression[i] == '(') 
        push(&postfixStack, expression[i]); 
    
      else if (expression[i] == ')') 
      { 
        while (!empty(&postfixStack) && top(&postfixStack) != '(') 
          expression[++j] = pop(&postfixStack); 
        if (!empty(&postfixStack) && top(&postfixStack) != '(') 
          continue;     
        else
          pop(&postfixStack);
      } 
      else 
      { 
        while (!empty(&postfixStack) && getOperatorPriority(expression[i]) <= getOperatorPriority(top(&postfixStack)))
          expression[++j] = pop(&postfixStack); 
        push(&postfixStack, expression[i]);
      } 
  
    } 
   
    while (!empty(&postfixStack)) 
      expression[++j] = pop(&postfixStack); 
  
    expression[++j] = '\0'; 
    //printf("%s", expression);
    return expression;
}
int calculate(int a, int b, char op)
{
  if(op=='+')
		return(a+b);

	else if(op=='-')
		return(a-b);

	else if(op=='*')
		return(a*b);

	else if(op=='/')
		return(a/b);

	else if(op=='%')
		return(a%b);
  
  else if(op=='^')
    return customPow(a,b);

  else
    return 0;
}
void evaluateix(char* expression)
{
  //removeSpaces(expression);
  int operandA, operandB, operation, value, i;
  intStack evaluator;
  int_initialize(&evaluator);

  while(*expression != '\0')
  {
    if(*expression == ' ')
      continue;
    else if(isdigit(*expression))//cannot accept multidigit numbers because of these lines
      int_push(&evaluator, convertToInt(*expression));//problem area
    else
    {
      operandB = int_pop(&evaluator);
      operandA = int_pop(&evaluator);
      value = calculate(operandA, operandB, *expression);
      int_push(&evaluator,value);
    }
    expression++;
  }
  value = int_pop(&evaluator);
  printf("\nEvaluation: %d", value);
}
int customPow(int x,int n)
{
  int i, result = 1;
  for (i = 0; i < n; ++i)
    result *= x;
  return(result);
}