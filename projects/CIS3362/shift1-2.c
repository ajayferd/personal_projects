/*
Alejandro Fernandez
This program solves a problem corresponding to Assignment #1
    <type of program>[assignment number] - [problem number]
*/

#include <stdio.h>
#include <string.h>

void printAllShiftPossibilities(char cipher[]) {
 int i, j;
 for (i = 0; i < 26; i++) {
    printf("shift %d\t", i);
    for (j = 0; j < strlen(cipher); j++)
            printf("%c", (cipher[j]-'a'+ i) %26 + 'a');
        printf("\n");
 }
}

int main(){
    char cipher[] = "chnbcmwfummqyqcffvlyuewixymugihainbylnbchamohzilnouhnyfsnbylyqcffvyhivolcyxnlyumolymvonnbylygcabnvyujlctyilnqi";
    printAllShiftPossibilities(cipher);
    return 1;
}

