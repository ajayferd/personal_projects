/*
Alejandro Fernandez
This program solves a problem corresponding to Assignment #1
    <type of program>[assignment number] - [problem number]
*/

#include <stdio.h>
#include <string.h>

void encryptPlaintextWithAffineCipher(char cipher[], int a, int b) {
 for (int j = 0; j < strlen(cipher); j++)
        printf("%c", (a * (cipher[j] - 'a') + b) % 26 + 'a');
 
}
int main() {
    char plaintext[] = "artemisonemoonrocketarrivesatlaunchpadaheadofhistoricmission";
    int aKey = 5;
    int bKey = 21;
    encryptPlaintextWithAffineCipher(plaintext, aKey, bKey);
    return 1;
}