#include <stdio.h>
int factorial(int number)
{
    if (number == 0)
        return 1;
    printf("%d x ", number);
    return number * factorial(number - 1);
}

int main()
{
    printf("\n%d",factorial(6));
    return 1;
}