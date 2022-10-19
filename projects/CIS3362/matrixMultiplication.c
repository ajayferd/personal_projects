#include <stdio.h>

void matMult(int mat1[][10], int mat2[][10], int mat3[][10]);

int main()
{
    return 0;
}

void matMult(int mat1[][10], int mat2[][10], int mat3[][10])
{
    for (int i = 0; i < 10; i++)
    {
        for (int j = 0; j < 10; j++)
        {
            mat3[i][j] = 0;
            for (int k = 0; k < 10; k++)
            {
                mat3[i][j] += mat1[i][k] * mat2[k][j];
            }
        }
    }
}
