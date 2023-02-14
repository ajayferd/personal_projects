#include <iostream>
#include <string>
#include <fstream>

void creation(std::fstream& file);

int main(int argc, char* argv[])
{
    std::fstream csvFile;
    std::string fileName = "placesToEat"; 
    csvFile.open(fileName + ".csv");

    std::string input = "";
    std::cout << "Type your input";
    while ((input.compare("exit")))
    {
        std::cin >> input; 
        
    }

}

void creation(std::fstream& file)
{
    
}