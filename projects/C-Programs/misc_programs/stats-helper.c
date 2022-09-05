#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#define MAXSIZE 100
#define QUIT "quit"

//key feature functions
double findMode(double array[], int size);
double findMean(double array[], int size);
double findRange(double array[], int size);
double findMedian(double array[], int size);
double findVarianceAndSD(double array[], int size);

//helper functions
void swap(double* a, double* b);
double findMaxnumber(double array[], int size);
double findMinnumber(double array[], int size);
void printArray(double array[], int size);
void findFrequency(double array[], int size);
int partition (double array[], int low, int high);
void quickSort(double array[], int low, int high);

int main()
{
    int sizeOfarray, i;

    printf("\t\t~~~~~ Welcome to Stats Helper ~~~~~\n\nPlease enter the amount of numbers in the list: ");
    scanf("%d", &sizeOfarray);
    double *array = (double*)malloc(sizeof(double)*sizeOfarray);
    printf("\n");
    
    for(i = 0; i<sizeOfarray; i++)
    {
        //(array + i) is equivalent to &array[i]
        printf("Please insert the %d of the list: ", (i+1));
        scanf("%lf", (array + i));
    }
    quickSort(array,0,sizeOfarray-1);
    printf("\n\nSorted List: ");
    printArray(array,sizeOfarray);

   
    printf("The Mean of the numbers is: %.3lf\n", findMean(array, sizeOfarray));
    printf("The Median of the numbers is: %.3lf\n", findMedian(array, sizeOfarray));
    printf("The Range of the numbers is: %.3lf\n", findRange(array, sizeOfarray));
    printf("The Standard Deviation of the numbers is: %.3lf\n", findVarianceAndSD(array, sizeOfarray));

    findFrequency(array, sizeOfarray);
    free(array);
    
    return 0;
}

void swap(double* a, double* b) 
{ 
    double t = *a; 
    *a = *b; 
    *b = t; 
} 
int partition (double array[], int low, int high) 
{ 
    double pivot = array[high];    // pivot 
    int i = (low - 1);  // Index of smaller element 
  
    for (int j = low; j <= high- 1; j++) 
    { 
        // If current element is smaller than the pivot 
        if (array[j] < pivot) 
        { 
            i++;    // increment index of smaller element 
            swap(&array[i], &array[j]); 
        } 
    } 
    swap(&array[i + 1], &array[high]); 
    return (i + 1); 
} 
void quickSort(double array[], int low, int high) 
{ 
    if (low < high) 
    { 
        double pi = partition(array, low, high); 
        quickSort(array, low, pi - 1); 
        quickSort(array, pi + 1, high); 
    } 
}
double findMode(double array[], int size)
{
    double mode = 0;
    int count, mostCount = 0, i, j, temp;

    for(i = 0; i<size; ++i)
    {
        count = 0;
        for(j = 0; j<size; ++j)
        {
            if(array[j] == array[i])
                ++count;
        }
        if (count > mostCount)
        {
            mostCount = count;
            temp = count;
            mode = array[i];
        }
        
    }
    return mode;
}
double findMean(double array[], int size)
{
    double average = 0;
    int i;
    for(i = 0;i<size;i++)
        average += array[i];
    
    average /= size;
    return average;
}
double findMedian(double array[], int size)
{
    double median = 0;
    // if number of elements are even
    if(size%2 == 0)
        median = (array[(size-1)/2] + array[size/2])/2.0;
    // if number of elements are odd
    else
        median = array[size/2];
    
    return median;
}
double findVarianceAndSD(double array[], int size)
{
    int sum = 0;
    double average = 0, variance = 0, SD = 0;
    int i;
    for(i = 0;i<size;i++)
        average += array[i];
    
    average /= size;

    for(i = 0; i < size; i++)
        sum += pow((array[i]-average),2);
    
    variance = sum/(double)size;
    SD = sqrt(variance);

    printf("The Variance of the numbers is: %.3lf\n", variance);
    return SD;
}
double findMaxnumber(double array[], int size)
{
    int i;
    double maxNumber = 0;

    for(i = 0; i<size; i++)
    {
        if(maxNumber < array[i])
            maxNumber = array[i];
    }
    return maxNumber;
}
double findMinnumber(double array[], int size)
{
    {
    int i;
    double minNumber = 9999999999;

    for(i = 0; i<size; i++)
    {
        if(minNumber > array[i])
            minNumber = array[i];
    }
    return minNumber;
}
}
void printArray(double array[], int size)
{
    int i;
    for(i = 0; i<size; i++)
        printf("%.3lf, ", array[i]);
    printf("\n");
    return;
}
double findRange(double array[], int size)
{
    double range, tempMax, tempMin;
    tempMax = findMaxnumber(array, size);
    tempMin = findMinnumber(array, size);
    range = tempMax - tempMin;

    return range;
}
void findFrequency(double array[], int size)
{
    int freq[size];
    int i, j, count;

    for(i=0;i<size;i++)
        freq[i] = -1;
    
    for(i=0; i<size; i++)
    {
        count =1;
        for(j = i+1; j<size; j++)
        {
            if(array[i] == array[j])
            {
                count++;
                freq[j] = 0;
            }
        }

        if(freq[i] != 0)
            freq[i] = count;
    }

    printf("\n\t\t~~~~~ Number Frequencies ~~~~~\n\n");
    for(i=0; i<size; i++)
    {
        if(freq[i] != 0)
        {
            printf("The number < %.3lf > occurs %d time(s)\n", array[i], freq[i]);
        }
    }

    /*double maxCounti = 0, maxCountj = 0;
    for(i=0;i<size;i++)
    {
        for(j=size-1;j>=0;j--)
        {
            if(maxCounti < freq[i])
                maxCounti = freq[i];
            
            else if(maxCountj <freq[j])
                maxCountj = freq[j];
            
            else if(maxCountj == maxCounti && i = j)
            {
                printf("Multi-mode, thus no mode");
                return;

            }
            
        }
    }
    printf("the mode is %lf", maxCounti);*/

}