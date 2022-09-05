#include <iostream>
#include <sys/types.h> 
#include <unistd.h>     
#include <fstream>
#include <string.h>
#include <string>
#include <vector>
#include <algorithm>
#include <stdlib.h>
#include <bits/stdc++.h>
#include <sys/wait.h>  
#include <signal.h>
using namespace std;

//function definition
int history(vector<string> &history);
int replay(char *arguments[], vector<string>& history);
int start(char *arguments[]);
int background(char *arguments[]);
int terminate(char *arguments[]);
int terminateall();
int repeat(char*arguments[], vector<string>& history);

//vector to store all the childProcesses pid for the terminateall function
vector<pid_t> childProcesses;

int main(void){
    //create historyory vector
    vector<string> history;
    //create base variables needed
    int i, success;
    bool loopcheck = true;
    string commandLine;
    char* command[10];
    cout << "#";

    //start the main loop that handles user input and tokenizing and starting the functions
    while(loopcheck){
        i = 0;
        
        //get user input
        
        getline(cin, commandLine);
        
        //push command on the history vector
        history.push_back(commandLine);

        //copy string format to char* format bc it wasnt working befor
        char *cstr = new char[commandLine.length() + 1];
        strcpy(cstr, commandLine.c_str());
        char *token = strtok(cstr, " ");
        
        while(token != NULL){
            command[i++] = token;
            token = strtok(NULL, " ");
        }
        
        

        

        //find which command was typed and trigger accompanying function
        if(strcmp(command[0], "history") == 0){
            success = history(history);
        }else if(strcmp(command[0], "byebye") == 0){
            loopcheck = false;
            success = 0;
            break;
        }else if(strcmp(command[0], "replay") == 0){
            success = replay(command, history);
        }else if(strcmp(command[0], "start") == 0){
            success = start(command);
        }else if(strcmp(command[0], "background") == 0){
            success = background(command);
        }else if(strcmp(command[0], "terminate") == 0){
            success = terminate(command);
        }else if(strcmp(command[0], "terminateall") == 0){
            success = terminateall();
        }else if(strcmp(command[0], "repeat") == 0){
            success = repeat(command, history);
        }
        if(success != 0){
            cout << "Your command failed, try running this shell with sudo";
        }
        cout << "#";
    }
    //save vector to history file
    std::ofstream output_file("mysh.history");
    std::ostream_iterator<std::string> output_iterator(output_file, "\n");
    std::copy(history.begin(), history.end(), output_iterator);
    output_file.close();

    //end program
    exit(0);
    return 0;
}


int history(vector<string>& hist){
    //clear history if command is passed
    if(hist.back().find("-c") != string::npos){
        hist.clear();
        return 0;
    }
    //print out history vector contents
    for (int j = hist.size()-1; j>=0;j--){
        cout << hist.size()-(j+1) << ": " << hist[j] << endl;
    }
    //read from history file if it exists
    std::ifstream input_file("mysh.history");
    std::string content;
    std::vector<std::string> numbers;

    while(input_file >> content) 
        numbers.push_back(content);
    for(int i = numbers.size() - 1; i >= 0; i--)
        std::cout << numbers[i] << endl;

    //close file
    input_file.close();

    return 0;
}


int replay(char*arguments[], vector<string>& hist){
    //get proper position and make other varables set up
    int pos = (hist.size()-1)-(atoi(args[1])+1);
    int i = 0;
    char *cstrs = new char[hist[pos].length() + 1];
    strcpy(cstrs, hist[pos].c_str());
    char *tokens = strtok(cstrs, " ");
    char* rcommand[10];
    //tokenize again
    while(tokens != NULL){
        rcommand[i++] = tokens;
        tokens = strtok(NULL, " ");
    }
    //run proper command
    if(strcmp(rcommand[0], "history") == 0){
        history(hist);
    }else if(strcmp(rcommand[0], "start") == 0){
        start(rcommand);
    }else if(strcmp(rcommand[0], "background") == 0){
        background(rcommand);
    }else if(strcmp(rcommand[0], "terminate") == 0){
        terminate(rcommand);
    }else if(strcmp(rcommand[0], "terminateall") == 0){
        terminateall();
    }else if(strcmp(rcommand[0], "repeat") == 0){
        repeat(rcommand, hist);
    }
    return 0;
}

int start(char*arguments[]){
    //set up variables
    pid_t pid;
    pid = fork();
    //run the process
    if(pid < 0){
        cout << stderr<< "fork fail"<< endl;
        return 1;
    }else if(pid == 0){
        execvp(args[1], args);
    }else {
        pid = wait(NULL);
    }
    


    return 0;
}

int background(char*arguments[]){
    //make variables
    pid_t cpid;
    cpid = fork();
    //run process in background
    if(cpid < 0){
        cout << stderr<< "fork fail"<< endl;
        return 1;
    }else if(cpid == 0){
        cpid = execvp(args[1], args);
    }
    //add to child vector
    childProcesses.push_back(cpid);
    //print pid
    cout<<cpid<<endl;
    
    return 0;
}

int terminate(char*arguments[]){
    int val = 1;
    //send kill signal
    if(0==kill(atoi(args[1]), 0)){
        val = kill(atoi(args[1]), SIGKILL);
    }
    //remove terminated process from vector
    std::vector<pid_t>::iterator pid, el;
    pid = std::find(childProcesses.begin(), childProcesses.end(), atoi(args[1]));
    if(pid != childProcesses.end()) {
        childProcesses.erase(pid);
    }
    return val;
}

int terminateall(){
    //terminate all pids in child vector
    for(int i = 0; i < childProcesses.size();i++){
        kill(childProcesses[i], SIGKILL);
    }
    //clear bc all pids terminated
    childProcesses.clear();
    return 0;
}

int repeat(char*arguments[], vector<string>& hist){
    //variables
    int i = 0;
    char* argv[10];
    //pull out command to be repeated
    while(args[i] != NULL){
        argv[i-2] = args[i];
    }
    //run command the specified number of times
    if(strcmp(argv[0], "history") == 0){
        for(int j = 0; j < atoi(args[1]); j++){
            history(hist);
        }    
    }else if(strcmp(argv[0], "start") == 0){
        for(int j = 0; j < atoi(args[1]); j++){
            start(argv);
        }
    }else if(strcmp(argv[0], "background") == 0){
        for(int j = 0; j < atoi(args[1]); j++){
            background(argv);
        }
    }else if(strcmp(argv[0], "terminate") == 0){
        for(int j = 0; j < atoi(args[1]); j++){
            terminate(argv);
        }
    }else if(strcmp(argv[0], "terminateall") == 0){
        for(int j = 0; j < atoi(args[1]); j++){
            terminateall();
        }
    }
    return 0;
}