#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <unistd.h>
#include <sys/wait.h>
#include <signal.h>

// buffer constants
#define MAX_BUFFER 1024   
#define MAX_ARGS 64          
#define SPACE " \t\n"  

int main (int argc, char **argv)
{
  char buffer[MAX_BUFFER];
  char *args[MAX_ARGS];
  char **arguments;
  char *shellPrompt = "#";
  char *stringArray[MAX_ARGS];
  int commandsCount = 0, process_gp = 0;
  

  while (!feof(stdin))
  {
    fputs(shellPrompt, stdout);
    
    // get command line arguments
    if (fgets(buffer, MAX_BUFFER, stdin))
    {
      stringArray[commandsCount] = (char *) malloc(strlen(buffer) + 1);
      strcpy(stringArray[commandsCount], buffer);
      
      for (int i = 0; i < MAX_ARGS; i++)
        args[i] = NULL;
      arguments = args;
      *(arguments++) = strtok(buffer, SPACE);
      
      while ((*arguments++ = strtok(NULL, SPACE)));
      
      if (strcmp(args[0], "history") == 0)
      {
        
      }
      
      if (strcmp(args[0], "replay") == 0)
      {

      }
      if (strcmp(args[0], "start") == 0)
      {
          int numOfArgs = 0;
          for (int i = 0; i < MAX_ARGS; i++)
          {
              if (args[i] == NULL) break;
              else numOfArgs++;
          }
          
          if (args[1][0] == '/')
          {
              int forking = fork();
              if (forking < 0)
              {
                  fprintf(stderr, "Fork error\n");
                  exit(1);
              }
              else if (forking == 0)
              {
                  char *tempNewProcess[numOfArgs];
                  for (int i = 0; i < MAX_ARGS; i++)
                  {
                      if(args[i] == NULL)
                      {
                          tempNewProcess[i - 1] = NULL;
                          break;
                      }
                      else
                        tempNewProcess[i - 1] = args[i];
                  }
                  chdir(tempNewProcess[0]);
                  execv(tempNewProcess[0], myargs);
                  fprintf("Process %s execution error, exec().\n", tempNewProcess[0]);
                  exit(1);
              }
              else
                waitpid(forking, NULL, 0);
          }
          else
          {
              int forking = fork();
              if (forking < 0)
              {
                  fprintf(stderr, "Fork error\n");
                  exit(1);
              }
              else if (forking == 0)
              {
                  char *tempNewProcess[numOfArgs];
                  for (int i = 0; i < MAX_ARGS; i++)
                  {
                      if(args[i] == NULL)
                      {
                          tempNewProcess[i - 1] = NULL;
                          break;
                      }
                      else
                        tempNewProcess[i - 1] = args[i];
                  }
            
                  execv(tempNewProcess[0], myargs);
                  fprintf("Process %s execution error, exec().\n", tempNewProcess[0]);
                  exit(1);
          }
      }
      if (strcmp(args[0], "background") == 0)
      {
          int numOfArgs = 0;
          for (int i = 0; i < MAX_ARGS; i++)
          {
              if (args[i] == NULL) break;
              else numOfArgs++;
          }

      }
      if (strcmp(args[0], "terminate") == 0)
      {

      }
      if (strcmp(args[0], "byebye") == 0)
        break;

      if (strcmp(args[0], "repeat") == 0)
      {

      }
      if (strcmp(args[0], "terminateall") == 0)
      {

      }
      
      
    }
  }
  
}