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

int main(int argc, char* argv[]){
    FILE* matrix = fopen(argv[1], "r");
    FILE* text = fopen(argv[2], "r");
    int i, j, x = 0, textSize, sum, k;
    const int keySize;
    char plainText[MAX_STRING];
    char cipherText[MAX_STRING]; 
    char paddedPlaintext[MAX_STRING];

    fscanf(matrix,"%d", &keySize);
    int cipherMatrix[keySize][keySize];
    for(i = 0; i < keySize; i++){
        for(j = 0; j < keySize; j++){
            fscanf(matrix,"%d", &cipherMatrix[i][j]);
        }
    }
    

    fscanf(text,"%[^\n]s", plainText);
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
    int cipherHelper[textSize];
    int remainder = keySize - (textSize % keySize);
    int temp;

    if(temp > 0)
    for(x = textSize; x < textSize+remainder; x++)
        plainText[x] = 'x';
    for(x = 0; x < textSize; x++)
        cipherHelper[x] = (int)plainText[x]-'a';
  

    for(i = 0; i < keySize; i++){ 
        sum = 0;
        for(x = 0; x < textSize + remainder; x++){
            temp = cipherHelper[x];
            for(j = 0; j < keySize; j++){
                sum += cipherMatrix[i][j] * temp;
                //printf("sum: %d, cipherMatrix[%d][%d]: %d, cipherhelper[%d]: %d\n", sum, i, j, cipherMatrix[i][j], x, cipherHelper[x]);
            }
            cipherText[x] = sum%26;
        }
        
    }
    

    printf("Key Matrix:\n\n");
    for(i = 0; i < keySize; i++){
        for(j = 0; j < keySize; j++){
            printf("%d ", cipherMatrix[i][j]);
        }
        printf("\n");
    }

    printf("\n\nPlain text:\n\n");
    for(i = 0; i < textSize+remainder; i++){
        if((i%80)==0 && i != 0)
            printf("\n");
        printf("%c", plainText[i]);
        
    }
    printf("\n\nCipher text:\n\n");
    for(i = 0; i < textSize+remainder; i++){
        if((i%80)==0 && i != 0)
            printf("\n");
        printf("%c", cipherText[i]+97);
    }
    fclose(matrix);
    fclose(text);
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