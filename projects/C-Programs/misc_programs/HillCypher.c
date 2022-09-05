/*=============================================================================
| Assignment: HW 01 – Encrypting a plaintext file using the Hill cipher in the key file
|
| Author: Alejandro Fernandez
| Language: C
|
| To Compile: javac Hw01.java
|
| To Execute: java Hw01 hillcipherkey.txt plaintextfile.txt
| where the files in the command line are in the current directory.
| The key text contains a single digit on the first line defining the size of the key
| while the remaining lines define the key, for example:
| 3
| 1 2 3
| 4 5 6
| 7 8 9
| The plain text file contains the plain text in mixed case with spaces & punctuation.
|
| Class: CIS3360 - Security in Computing - Fall 2020
| Instructor: McAlpin
| Due Date: 10/11/2020
|
+=============================================================================*/
#include <stdio.h>
#include <string.h>
#include <ctype.h>
#define MAX_STRING 10000
//read file
//computer cipher key
//apply key to text
//echo key and text - text should only be at most 80 wide before new line
int main(){
    FILE* inCipherkey = fopen("k1.txt","r");
    FILE* inPlainstext = fopen("p1.txt","r");
    int i, j, x = 0, textSize, sum, k;
    const int keySize;
    char plainText[MAX_STRING];
    char cipherText[MAX_STRING]; 

    fscanf(inCipherkey,"%d", &keySize);
    int cipherMatrix[keySize][keySize];
    for(i = 0; i < keySize; i++){
        for(j = 0; j < keySize; j++){
            fscanf(inCipherkey,"%d", &cipherMatrix[i][j]);
        }
    }
    

    fscanf(inPlainstext,"%[^\n]s", plainText);
    i = 0;
    while(plainText[i] != '\0'){
        plainText[i] = tolower(plainText[i]);
        i++;
    }

    for(i = 0, j; plainText[i] != '\0'; i++){
        while (!(plainText[i] >= 'a' && plainText[i] <= 'z') && !(plainText[i] == '\0')){
            for (j = i; plainText[j] != '\0'; ++j) {
                plainText[j] = plainText[j + 1];
            }
         plainText[j] = '\0';
        }
    }
    textSize = strlen(plainText);
    
    /*int testMatrix[3][3] =
        {
            2, 3, 5,
            7, 1, 0,
            4, 10, 11
        };
    char testPlaintext[7] = {'t', 'h', 's', 'd', 'z', 'a', '\0'};
    int testhelper[3];

    testhelper[0] = (int)testPlaintext[0]-97;
    testhelper[1] = (int)testPlaintext[1]-97;
    testhelper[2] = (int)testPlaintext[2]-97;
    sum = 0;

    sum += testMatrix[0][0] * testhelper[0];
    printf("sum: %d, testMatrix[0][0]: %d, testhelper[0][0]: %d\n", sum, testMatrix[0][0], testhelper[0]);
    sum += testMatrix[0][1] * testhelper[1];
    printf("sum: %d, testMatrix[0][1]: %d, testhelper[1][0]: %d\n", sum, testMatrix[0][1], testhelper[1]);
    sum += testMatrix[0][2] * testhelper[2];
    printf("sum: %d, testMatrix[0][2]: %d, testhelper[2][0]: %d\n", sum, testMatrix[0][2], testhelper[2]);

    sum = (sum % 26);
    char testCiphertext[7];
    testCiphertext[0] = sum;
    printf("%c\n", testCiphertext[0]+97);*/
    

    int cipherHelper[textSize];
    int remainder = keySize - (textSize % keySize);
    int temp = remainder;
    for(x = 0; x < textSize; x++){
        cipherHelper[x] = (int)plainText[x]-'a';
    }
    for(i = 0; i < keySize; i++){ 
        sum = 0;
        for(j = 0; j < keySize; j++){
            sum += cipherMatrix[i][j] * (int)cipherHelper[j];
            //printf("sum: %d, cipherMatrix[%d][%d]: %d, cipherhelper[%d]: %d\n", sum, i, j, cipherMatrix[i][j], j, cipherHelper[j]);
        }
        cipherText[i] = sum%26;
    }
    

    printf("Key Matrix:\n\n");
    for(i = 0; i < keySize; i++){
        for(j = 0; j < keySize; j++){
            printf("%d ", cipherMatrix[i][j]);
        }
        printf("\n");
    }

    printf("\n\nPlain text:\n\n");
    for(i = 0; i < textSize; i++){
        if((i%80)==0 && i != 0)
            printf("\n");
        printf("%c", plainText[i]);
        
    }
    printf("\n\nCipher text:\n\n");
    for(i = 0; i < textSize; i++){
        if((i%80)==0 && i != 0)
            printf("\n");
        printf("%c", cipherText[i]+97);
    }
    fclose(inCipherkey);
    fclose(inPlainstext);
    return 0;
}
/*=============================================================================
| I Alejandro Fernandez (al720940) affirm that this program is
| entirely my own work and that I have neither developed my code together with
| any another person, nor copied any code from any other person, nor permitted
| my code to be copied or otherwise used by any other person, nor have I
| copied, modified, or otherwise used programs created by others. I acknowledge
| that any violation of the above terms will be treated as academic dishonesty.
+=============================================================================*/