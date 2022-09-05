// declare all libraries used
#include <stdio.h>
#include <iostream>
#include <unistd.h>     
#include <sys/wait.h> 
#include <fstream>
#include <cstring>
#include <stdlib.h>
#include <vector>
#include <bits/stdc++.h>
#include <signal.h>
#include <sys/types.h> 
#include <sys/stat.h>
#include <filesystem>
#include <dirent.h>
using namespace std;


// method prototype for all commands intergrated with shell
bool historyCommand(vector<string> &history);
bool replayCommand(char *arguments[], vector<string> &history);
bool startCommand(char *arguments[]);
bool backgroundCommand(char *arguments[]);
bool terminateCommand(char *arguments[]);
bool terminateAllCommand();
bool repeatCommand(char *arguments[], vector<string> &history, int count);

// new methods from HW5
bool dweltCommand(char *arguments[]);
bool maikCommand(char *arguments[], vector<string> fileNames);
bool coppyCommand(char *arguments[]);
bool searchDirectory(char *pathName, string fileName);
bool coppyAbodeCommand(char *arguments[]);

// some global varibales to deal with fork processes and command char sizes
int const MAX_COMMANDS_SIZE = 10;
int const MAX_FILES_COUNTED = 10;
int const MAX_PATH = 100;
vector <pid_t> childProcesses;
// string path = "/home/user";


int main()
{
    // initalize varibles so we can start processing commands
	char prompt = '#';
	string commandLine;
	char* commands[MAX_COMMANDS_SIZE];
	vector <string> fileNames(MAX_FILES_COUNTED);
	vector<string> history;
	bool error = false;
	int i = 0, temp = 0, sctr = 0;
	
    // will loop forever until we break or encounter some type of error
	while(!error)
		{

			i = 0;
            // display prompt and take in entire line as a string
            cout << prompt << " ";
			getline(cin, commandLine);
			
            // create copy of string into a char
			char *commandString = new char[commandLine.length() + 1];

			
            // copy string into char and tokenize the first word for the command
			strcpy(commandString, commandLine.c_str());
			char *tokens = strtok(commandString, " ");

            // put the command line entered into the history vector for safe keeping
			history.push_back(commandLine);

            // tokenize
			while(true)
				{
					if (tokens == NULL)
						break;
					commands[i] = tokens;
					tokens = strtok(NULL, " ");
					i++;
				}

            // get the command from the string for strcmp's
            // compare string until we find the matching command and run
			char* command = commands[0];
			if (!(strcmp(command,"history")))
				error = historyCommand(history);

				// if user inputs byebye we exited out of the loop
			else if (!(strcmp(command,"byebye")))
			{
                error = true;
                break;
            }
			else if (!(strcmp(command,"replay")))
				error = replayCommand(commands, history);
        
			else if (!(strcmp(command,"start")))
				error = startCommand(commands);
        
			else if (!(strcmp(command,"background")))
				error = backgroundCommand(commands);
        
			else if (!(strcmp(command,"terminate")))
				error = terminateCommand(commands);

			else if (!(strcmp(command,"terminateall")))
				error = terminateAllCommand();

			else if (!(strcmp(command,"repeat")))
				error = repeatCommand(commands, history, temp);

			
			else if (!(strcmp(command, "dwelt")))
				error = dweltCommand(commands);
				
			else if (!(strcmp(command, "maik")))
			{
				error = maikCommand(commands, fileNames);
				fileNames[sctr] = commands[1]; 
				sctr++;
			}
				
			else if (!(strcmp(command, "coppy")))
				error = coppyCommand(commands);

			else if (!(strcmp(command, "coppyabode")))
				error = coppyAbodeCommand(commands);
					
            // if we ever reach here the command was invalid
			if (error)
				cout << endl << "Invalid command" << endl;	
		}

    // mysh.history file keeps track of all command line entries
	ofstream records("mysh.history");
	ostream_iterator<string> record_iterator(records, "\n");
    copy(history.begin(), history.end(), record_iterator);
    records.close();
    return 0;
}
bool searchDirectory(string pathName, string fileName)
{
	char *path = new char[pathName.length() + 1]; 
	strcpy(path, pathName.c_str());
	DIR *directory;   // creating pointer of type dirent
	struct dirent *w;   // pointer represent directory stream
	bool result = false;
	if ((directory = opendir(path)) != NULL)
	{
		while ((w = readdir (directory)) != NULL)
			{
				if (fileName == w->d_name)
				{
					return true;
				}
			}
		closedir (directory);
	}
	return false;
}
bool dweltCommand(char *arguments[])
{
	char *inputString = arguments[1];
	string relativePath, fileName;
	int x = 0;
	
	for (int i = strlen(inputString) - 1; i >= 0; i--)
		{
			// end of relative path
			if (inputString[i] == '/')
				{
					x = i;
					break;
				}	
				
		}
	
	// get path
	for (int i = 0; i < x; i++)
		relativePath += inputString[i];

	// get filename
	for (int i = x; i < strlen(arguments[1]); i++)
		fileName += inputString[i];

	char *pathName = new char[relativePath.length() + 1]; 
	strcpy(pathName, relativePath.c_str());
	

	struct dirent *de; 
    DIR *dr = opendir(".");

    while ((de = readdir(dr)) != NULL)
    {

        if (de->d_name == relativePath)
		{
			cout << "Abode is" << endl;
			return false;
		}
        else if(de->d_name == fileName)
		{
			cout << "Dwelt indeed" << endl;
			return false;
		}
    }
  
    closedir(dr); 
	cout << "Dwelt not" << endl;
	return false;
}
bool maikCommand(char *arguments[], vector<string> fileNames)
{
	for (int i = 0; i < MAX_FILES_COUNTED; i++)
		{
			
			if (arguments[1] == fileNames[i])
			{
				cout << "Error: File of same name already exists" << endl;
				return true;
			}
		}	
	
	int x = 0;
	string relativePath, fileName;
	char *inputString = arguments[1];
	
	for (int i = strlen(inputString) - 1; i >= 0; i--)
		{
			// end of relative path
			if (inputString[i] == '/')
				{
					x = i;
					break;
				}	
				
		}
	
	// get path
	for (int i = 0; i < x; i++)
		relativePath += inputString[i];
	
	// get filename
	for (int i = x; i < strlen(arguments[1]); i++)
		fileName += inputString[i];
	
	
	char *directoryName = new char[relativePath.length() + 1];
	strcpy(directoryName, relativePath.c_str());
	
	int check = mkdir(directoryName, S_IRWXU | S_IRWXG | S_IROTH | S_IXOTH);
	
	ofstream outfile(inputString);
	outfile << "Draft" << endl;
	outfile.close();

	return false;
}
bool coppyCommand(char *arguments[])
{
	int x = 0;
	string relativePathFrom, fileNameFrom, relativePathTo, fileNameTo;
	char *inputStringFrom = arguments[1];
	char *inputStringTo = arguments[2];
	
	for (int i = strlen(inputStringFrom) - 1; i >= 0; i--)
		{
			// end of relative path
			if (inputStringFrom[i] == '/')
				{
					x = i;
					break;
				}	
				
		}
	
	// get path
	for (int i = 0; i < x; i++)
		relativePathFrom += inputStringFrom[i];
	
	// get filename
	for (int i = x; i < strlen(arguments[1]); i++)
		fileNameFrom += inputStringFrom[i];

	for (int i = strlen(inputStringTo) - 1; i >= 0; i--)
		{
			// end of relative path
			if (inputStringTo[i] == '/')
				{
					x = i;
					break;
				}	
				
		}
	
	// get path
	for (int i = 0; i < x; i++)
		relativePathTo += inputStringTo[i];
	
	// get filename
	for (int i = x; i < strlen(arguments[1]); i++)
		fileNameTo += inputStringTo[i];

	

	
	if ((searchDirectory(relativePathFrom, fileNameFrom)))
	{
		cout << "Source file does not exist" << endl;
		return false;
	}
	if ((searchDirectory(relativePathTo, fileNameTo)))
	{
		cout << "Destination file does not exist" << endl;
		return false;
	}
	if ((searchDirectory(relativePathFrom, relativePathTo)))
	{
		cout << "Destintion file's directory does not exist" << endl;
		return false;
	}

	string from = relativePathFrom + fileNameFrom;
	string to = relativePathTo + fileNameTo;
	char *source = new char[from.length() + 1]; 
	strcpy(source, from.c_str());
	char *target = new char[to.length() + 1]; 
	strcpy(source, to.c_str());
	
	FILE *fs, *ft;
	fs = fopen(source, "r");
	ft = fopen(target, "w");
	char ch = fgetc(fs);
	while(ch != EOF)
		{
			fputc(ch, ft);
			ch = fgetc(fs);
		}
	fclose(fs);
	fclose(ft);
	
	
	return false;
	
}
bool coppyAbodeCommand(char *arguments[])
{
	return false;
}

// this method takes in the history vector and displays all inputs
// while returning a bool to mark if we encounter an error
bool historyCommand(vector<string> &history)
{
    // we check if we find a '-c' denoting we clear the cache.
	string lastCommand = history.back();
	if(lastCommand.find("-c") != string::npos)
	{
		history.clear();
		return false;
	}

	for (int i = history.size() - 1; i >= 0; i--)
		cout << history.size() - i + 1 << ": " << history[i] << endl;
   
	return false;
}

// this method takes in the command line arguments and the history vector
// and replays the previously entered command line entry
bool replayCommand(char *arguments[], vector<string> &history)
{
    // first we have to find the position of the line we want to replay
	int commandPosition = (history.size() - 1) - atoi(arguments[1] + 1);
	char *commandString = new char[history[commandPosition].length() + 1];
	strcpy(commandString, history[commandPosition].c_str());
    // tokenize
	char *tokens = strtok(commandString, " ");
	char *replayCommand[MAX_COMMANDS_SIZE];
	int i = 0;
	
	while (true)
		{
			if (tokens == NULL)
				break;
			replayCommand[i] = tokens;
			tokens = strtok(NULL, " ");
			i++;
		}

    // now after finding the correct command we can replay it by passing it
    // in to the appropriate command method
	char* command = replayCommand[0];
	if(!(strcmp(command, "history")))
		historyCommand(history);
   
	else if (!(strcmp(command, "start")))
		startCommand(replayCommand);
   
	else if (!(strcmp(command, "background")))
		backgroundCommand(replayCommand);
   
	else if (!(strcmp(command, "terminate")))
		terminateCommand(replayCommand);

	return false;
	
}

// this command starts a program process and returns
// a bool to denote if it encountered any errors along the way
bool startCommand(char *arguments[])
{
	pid_t pid;
    pid = fork();


    // we use fork to run the processes
    // and we return an error if the fork fails
	if (pid == 0)
		execvp(arguments[1], arguments);
	else if (pid < 0)
	{
		cout << stderr << "Failed fork" << endl;
		return true;
	}
	else
		pid = wait(NULL);

	return false;
}

// this command checks for any processes running in the background and 
// prints out the pid while returning a bool if it encounters
// any errors
bool backgroundCommand(char *arguments[])
{
	pid_t pid;
    pid = fork();

    // similar fork method to run the process
    // and we return if any error is found
	if (pid == 0)
		pid = execvp(arguments[1], arguments);
	else if (pid < 0)
	{
		cout << stderr << "Failed fork" << endl;
		return true;
	}
  
    childProcesses.push_back(pid);
	cout << "# " << pid << endl;
	return false;
}

// this method kills a process pid entered in as a argument from 
// the command line and reports if it was successful or not
bool terminateCommand(char *arguments[])
{
    // attempts to kill the process
	bool alive = true;
	if (!kill(atoi(arguments[1]), 0))
		alive = kill(atoi(arguments[1]), SIGKILL);
  
  // if we are successful we output the status
  if (!alive)
    cout << "Successfully killed " << arguments[1] << endl;
  else
    cout << "Failed to terminate " << arguments[1] <<endl;

    // keeping track of the child processes
  vector<pid_t>::iterator locationPid;
  locationPid = find(childProcesses.begin(), 
    childProcesses.end(), atoi(arguments[1]));
  
  if (locationPid != childProcesses.end())
    childProcesses.erase(locationPid);
    
	return alive;
}

// bonus:: this command terminates all processes running and outputs 
// what processes were killed
bool terminateAllCommand()
{
  bool alive = true;
  if (childProcesses.size() > 0)
    cout << "Successfully terminated: ";
	else
	  	cout << " Failed to terminate ";
  for (int i = 0; i < childProcesses.size(); i++)
  {
    kill(childProcesses[i], SIGKILL);
    cout << childProcesses[i] << " ";
  }

	cout << endl;
  childProcesses.clear();
  return false;
}


// bonus:: this method takes in n as an argument for the number of times
// a command must be run
bool repeatCommand(char *arguments[], vector<string> &history, int count)
{
  char *argv[MAX_COMMANDS_SIZE];
  while(arguments[count] != NULL) argv[count - 2] = arguments[count];

	char *command = argv[0];
  if (!(strcmp(command, "history")))
  {
    for(int i = 0; i < atoi(arguments[1]); i++)
      historyCommand(history);
  }    
  else if (!(strcmp(command, "start")))
  {
    for(int i = 0; i < atoi(arguments[1]); i++)
      startCommand(argv);
  }
  else if (!(strcmp(command, "background")))
  {
    for(int i = 0; i < atoi(arguments[1]); i++)
      backgroundCommand(argv);
  }
  else if (!(strcmp(command, "terminate")))
  {
    for(int i = 0; i < atoi(arguments[1]); i++)
      terminateCommand(argv);
  }
             
  else if (!(strcmp(command, "terminateall")))
  {
     for(int i = 0; i < atoi(arguments[1]); i++)
       terminateAllCommand();
  }
  
  return false;
}
