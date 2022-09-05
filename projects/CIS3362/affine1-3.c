/*
Alejandro Fernandez
This program solves a problem corresponding to Assignment #1
    <type of program>[assignment number] - [problem number]
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void printAllAffinePossibilities(char cipher[], int keys[]) {
 int a, b, j;
 for (b = 0; b < 26; b++) 
    {
        for (a = 0; a < 13; a++)
        {
            for (j = 0; j < strlen(cipher); j++)
            {
                printf("%c", ((keys[a] * (cipher[j] - 'a') + b) % 26) + 'a');
            }

            printf("\tb value:%d\ta value:%d\n", b , a);
        }
    }
 
}
int main() {
    char cipher[] = "ptuellkrumkztuhotkjuripyumghuckxuyauerexurgupivhiemtrgavuhptuihwesuwmiazirurpilptkymjeyy";
    int nonCommonFactorKeys[] = {1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25};
    printAllAffinePossibilities(cipher, nonCommonFactorKeys);
    return 1;
}