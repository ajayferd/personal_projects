#include <stdio.h>

int main()
{
    char selection = 'y';
    while(selection == 'y' || selection == 'Y')
    {
        int userNumber, sum = 0;
        printf("Enter any number (integer): ");
        scanf("%d", &userNumber);

        for(int i = 0; i <= userNumber; i++)
        {
            if ((i % 2) == 0)
                sum += i;
        }
        printf("Sum of all even numbers between 1 and %d: %d", userNumber, sum);
        printf("\nWould you like to run the program again? (y/n): ");
        scanf(" %c", &selection);
        if(selection == 'n' || selection == 'N')
        {
            printf("Thank you for using the program, goodbye!");
            return 1;
        }
    }
    return 1;
}