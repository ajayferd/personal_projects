// This program intentionally produces no output to showcase what happens when we pass pointers as a VALUE instead of a reference
// The fix to this error is passing in by reference or returning the same structure and passing it around
#include <stdio.h>
#include <stdbool.h>
#include <string.h>

struct player{
    int moves;
    char name[10];
    bool turn;
};

void testFunction(struct player *playerExample);
void printStructure(struct player *tester);

int main() {
    struct player *playerOne;
    struct player *playerTwo;
    playerOne->moves = 10;
    strcpy(playerOne->name,"Bob");
    playerOne->turn = true;

    testFunction(playerOne);
    testFunction(playerTwo);
    printStructure(playerOne);
    return 0;
}

void testFunction(struct player *playerExample)
{
    if (playerExample->turn = true)
        printf("It is %s turn!", playerExample->name);
    if (playerExample->turn = false)
        printf("It is NOT %s turn!", playerExample->name);

    
    return;
}


void printStructure(struct player *tester)
{
    printf("Printing!");
    printf("Moves: %d, Name: %s, Turn: %s", tester->moves, tester->name, tester->turn ? "true" : "false");
}