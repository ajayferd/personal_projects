#include <stdio.h>

void main(){
    int digits = 0, i, j, result = 1, number, temp;
    printf("Enter a number: ");
    scanf("%d", &number);
    temp = number;
    
    while(temp > 0){
        temp /= 10;
        digits++;
    }

    temp = number;
    int array[digits];
    i = 0;

    while(temp > 0){
        array[i] = temp % 10;
        temp /= 10;
        i++;
    }

    for(i = 1; i < digits; i++)
    {
        int uniqueNumber = 1;
        for(j = 0; uniqueNumber && j < i; j++)
        {
            if(array[j] == array[i])
                uniqueNumber = 0;
        }
        if(uniqueNumber)
            result++;
    }

    printf("%d has %d digits\n", number, digits);
    printf("%d has %d unique numbers.\n", number, result);
}
