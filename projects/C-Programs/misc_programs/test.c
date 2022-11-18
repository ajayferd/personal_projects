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
void updateTest(struct player *testing);

int main() {
    struct player playerOne;
    struct player playerTwo;
    playerOne.moves = 10;
    strcpy(playerOne.name,"Bob");
    playerOne.turn = true;
    testFunction(&playerOne);
    printStructure(&playerOne);
    return 0;
}

void testFunction(struct player *playerExample)
{
    if (playerExample->turn = true)
        printf("It is %s turn!", playerExample->name);
    if (playerExample->turn = false)
        printf("It is NOT %s turn!", playerExample->name);

    updateTest(playerExample);
    return;
}

void updateTest(struct player *testing)
{
    if (testing->turn == true)
        testing->turn = false;
    if (testing->turn == false)
        testing->turn = true;
    int temp = testing->moves;
    temp++;
    testing->moves = temp;
}
void printStructure(struct player *tester)
{
    printf("Printing!");
    printf("Moves: %d, Name: %s, Turn: %s", tester->moves, tester->name, tester->turn ? "true" : "false");
}