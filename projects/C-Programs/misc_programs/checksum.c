/*=============================================================================
| Assignment: HW 02 – Calculating the 8, 16, or 32 bit checksum for a
| given input file
|
| Author: Alejandro Fernandez
| Language: C
|
| To Compile: javac Hw02.java
|
| To Execute: java Hw02 textfile.txt checksum_size
| where the files in the command line are in the current directory.
|
| The text file contains text is mixed case with spaces, punctuation,
| and is terminated by the hexadecimal ‘0A’, inclusive.
| (The 0x’0A’ is included in the checksum calculation.)
|
| The checksum_size contains digit(s) expressing the checksum size
| of either 8, 16, or 32 bits
 |
| Class: CIS3360 - Security in Computing - Fall 2020
| Instructor: McAlpin
| Due Date: per assignment
|
+=============================================================================*/
#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#define MAX_SIZE 500


uint8_t checksum8(const unsigned char *buff, size_t length){
   unsigned int checkSum;
    for (checkSum = 0 ; length != 0 ; length--){
        checkSum += *(buff);
        buff++;
   }
    return (uint8_t)checkSum;
    //checkSum for 8 bits takes in each byte at a time
}
uint16_t checksum16(const unsigned char *buff, size_t length){
   uint16_t checkSum;
   while(length > 0){
      checkSum += *(buff);
      buff++;
      if(checkSum >> 16)
         checkSum = (checkSum & 0x0000FFFF) + (checkSum >> 16);
      length -= 2;
 }
 return (~checkSum);
}
   //checkSum for 16 bits takes in 2 byte at a time, hence why you pad with an 'X' if odd
uint32_t checksum32(const unsigned char *buff, size_t length){
  uint32_t checkSum;
  unsigned shift = 0;
  while(length > 0){
     checkSum += (*buff << shift);
     shift += 8;
     if(shift == 32)
      shift = 0;
     length -= 4;
  }
  return checkSum;
  //checkSum for 32 bits takes in 4 byte at a time, hence why you pad with up to three 'X'.
}

void main(int argc, char *argv[]){
   FILE *input = fopen(argv[1],"r");
   int checkSumSize = atoi(argv[2]);
   int characterCount, i = 0;
   long unsigned int checkSum8 = 0, checkSum16 = 0, checkSum32 = 0;
   char ch, myString[MAX_SIZE];
   
   /*printf("The argument supplied is %s\n", argv[1]);
   printf("The provided int: %d\n", checkSumSize);
    if(!(checkSumSize == 8 || checkSumSize == 16 || checkSumSize == 32)){
      fprintf(stderr,"Valid checksum sizes are 8, 16, or 32\n");
      return;
   }*/
   
   while((ch = fgetc(input)) != EOF)
   {
      myString[i] = ch;
      ++i;
   }
   characterCount = i;
   
   if ((characterCount % 2) == 1 && checkSumSize == 16){
      myString[characterCount] = 'X';
      characterCount++;
   }

   if(checkSumSize == 32){
      for(i = characterCount; i < characterCount + characterCount % 4; ++i){
         myString[i] = 'X';
         characterCount++;
      }
   }

   if(checkSumSize == 8)
      checkSum8 = checksum8(myString, characterCount);
   else if(checkSumSize == 16)
      checkSum16 = checksum16(myString, characterCount);
   else if(checkSumSize == 32)
      checkSum32 = checksum32(myString, characterCount);
   else
   {
      fprintf(stderr,"Valid checksum sizes are 8, 16, or 32\n");
      return;
   }
   for(i = 0; i < characterCount; i++){
        if((i%80)==0 && i != 0)
            printf("\n");
        printf("%c", myString[i]);
        
   }
   if(checkSumSize == 8)
      printf("\n%2d bit checksum is %8lx for all %4d chars\n", checkSumSize, checkSum8, characterCount);
   else if(checkSumSize == 16)
       printf("\n%2d bit checksum is %8lx for all %4d chars\n", checkSumSize, checkSum16, characterCount);
   else if(checkSumSize == 32)
       printf("\n%2d bit checksum is %8lx for all %4d chars\n", checkSumSize, checkSum32, characterCount);
   
   fclose(input); 
}
/*=============================================================================
| I Alejandro Fernandez 4600541 affirm that this program is
| entirely my own work and that I have neither developed my code together with
| any another person, nor copied any code from any other person, nor permitted
| my code to be copied or otherwise used by any other person, nor have I
| copied, modified, or otherwise used programs created by others. I acknowledge
| that any violation of the above terms will be treated as academic dishonesty.
+=============================================================================*/