#include <stdio.h>
#include <string.h>

//calculating the index of coincidence
double IC(char * plaintext) {
    int i, freq[26], length = strlen(plaintext);

    for (i = 0; i < 26; i++)
        freq[i] = 0;
    for (i = 0; i < length; i++)
        freq[plaintext[i]-'A']++;
    
    double top = 0.0, bottom = (double)(length*(length-1));
    for (i = 0; i < 26; i++)
        top += (double)(freq[i]*(freq[i]-1));
    return top/bottom;
}

//checking coprimality of the determinant
int coprime(int a, int b, int c, int d) {
    int val = a*d - b*c;
    while (val < 0)
        val += 26;
    val %= 26;
    if (val%2 == 0)
        return 0;
    if (val == 13)
        return 0;
    return 1;
}

int main() {
    char ciphertext[500];
    int a, b, c, d, i, length = 0;

    //setting up ciphertext and plaintext placeholder
    printf("Enter the ciphertext:\n");
    scanf("%s", ciphertext);
    length = strlen(ciphertext);
    char plaintext[length+1];

    //iterating through possible matrices {a, b, c, d}
    for (int a = 0; a < 26; a++) {
        for (int b = 0; b < 26; b++) {
            for (int c = 0; c < 26; c++) {
                for (int d = 0; d < 26; d++) {
                    //checking determinant is coprime
                    if (coprime(a,b,c,d) <= 0)
                        continue;
                    //iterating through plaintext, decrypting
                    for (i = 0; i < length; i+=2) {
                        int first = ciphertext[i] - 'A', second = ciphertext[i+1] - 'A', x, y;
                        x = (a*first + b*second)%26;
                        y = (c*first + d*second)%26;
                        plaintext[i] = x+'A';
                        plaintext[i+1] = y+'A';
                    }
                    //printing "valid" results
                    plaintext[i] = '\0';
                    double ic_val = IC(plaintext);
                    if (ic_val > 0.06 && strstr(plaintext, "THE") != NULL) {
                        printf("\nDecryption matrix {%d, %d, %d, %d}:\n", a,b,c,d);
                        printf("IC: %lf\n", ic_val);
                        printf("%s\n", plaintext);
                    }
                }
            }
        }
    }

    return 0;
}