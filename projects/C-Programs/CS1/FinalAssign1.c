#include <stdio.h>
#include <stdlib.h>
#include <math.h>
struct coordPoint
{
  int x;
  int y;
};

void printArray(int A[], int size)
{ 
  int i;
  for (i=0; i < size; i++)   
  printf("%d ", A[i]);    
  printf("\n");
}
void printData(struct coordPoint *temp, int n)
{
  int i;
  for(i=0;i<n;i++)
  {
    printf("%d %d\n", temp[i].x, temp[i].y);
  }
}
struct coordPoint* ReadData(FILE* input, struct coordPoint circle, int radius, int *numberOfpoints)
{
  //FILE *input = fopen("input.txt","r");
  if(!input)
  {
    printf("File not found");
    exit(-1);
  }

  //fscanf(input,"%d%d%d%d", &circle.x, &circle.y, radius, numberOfpoints);

  int n = *numberOfpoints;

  struct coordPoint *temp = (struct coordPoint*)malloc(n*sizeof(struct coordPoint));
  for(int i=0;i<n;i++)
    fscanf(input,"%d %d", &temp[i].x, &temp[i].y);
  //printData(temp, n);
  fclose(input);
  return temp;
  free(temp);
}

void Merge(struct coordPoint *data, int left, int middle, int right)
{
  int i, j, k;
  int temp1 = middle - left + 1;
  int temp2 = right - middle;

  struct coordPoint L[temp1+1];
  struct coordPoint R[temp2+1];

  for(i=0;i<temp1;i++)
    L[i] = data[left + i];
  for(j=0;j<temp2;j++)
    R[j] = data[middle+1+j];
  
  struct coordPoint temp3;
  temp3.x = 100;
  temp3.y = 100;

  L[temp1] = temp3;
  R[temp2] = temp3;
  i = 0;
  j = 0;

  for(k = left; k<=right; k++)
  {
    if(L[i].x == R[j].x && L[i].y < R[j].y)
    {
      data[k] = L[i];
      i++;
    }
    else if(L[i].x < R[j].x)
    {
      data[k] = L[i];
      i++;
    }
    else
    {
      data[k] = R[j];
      j++;
    }
  }

  /*while(i < temp1 && j < temp2)
  {
    if(Lx[i] <= Rx[j])
    {
      arr[k] = Lx[i];
      i++;
    }
    else
    {
      arr[k] = Rx[j];
      j++;
    }
    k++;
  }

  while(i < temp1)
  {
    arr[k] = Lx[i];
    i++;
    k++;
  }
  while(j < temp2)
  {
    arr[k] = Rx[j];
    j++;
    k++;
  }*/
}
void MergeSort(struct coordPoint *data, int left, int right)
{
  int i, middle;
  if(left < right)
  {
    middle = (left+right)/2;
    MergeSort(data, left, middle);
    MergeSort(data, middle+1, right);

    Merge(data, left, middle, right);
  }
}
struct coordPoint* FilterData(struct coordPoint *data, struct coordPoint circle, int radius, int *numberOfpoints)
{
  /*
  //FILE* output;
  //output = fopen("output.txt","w");
  int i, distance;
  int n = *numberOfpoints;
  for(i=0;i<n;i++)
  {
    distance = sqrt(pow(data[i].x - circle.x,2) + pow(data[i].y - circle.y,2)); //distance = sqrt((x2-x1,2)+(y2-y1,2)); 
    if(distance < radius || distance == radius) //if distance<radius or distance == radius, point is in circle;
    {
      fprintf(output,"%d %d\n", data[i].x, data[i].y);
    }
    else if(distance > radius)//if distance>radius, point is outside circle
      continue;
  }   
  //fclose(output);

  FILE* tempOutput;
  tempOutput = fopen("output.txt","r");
  char ch;
  int count = 0;
  while ((ch=getc(tempOutput)) != EOF)//count how many points are in the output
  {
     if (ch == '\n')
      count++;
  }*/
  
  int n = *numberOfpoints;
  int i, j, q;
  double distance;
  int* temp = (int*)calloc(sizeof(int),n);
  for(i =0, j=0; i<n; i++)
  {
    distance = sqrt(pow(data[i].x - circle.x,2) + pow(data[i].y - circle.y,2));
    if(distance <= radius) //if distance<radius or distance == radius, point is in circle;
    {
      temp[j] = i;
      j++;
    }
  }
  *numberOfpoints = j;
  struct coordPoint *filteredData = (struct coordPoint*)malloc(sizeof(struct coordPoint)*j);

  for(i=0;i<*numberOfpoints;i++)
  {
    q = temp[i];
    filteredData[i] = data[q];
  }
 
  return filteredData;
  free(filteredData);
  free(temp);
}
void BinarySearch(struct coordPoint *data, int n, struct coordPoint point)
{
  int left = 0, h = n-1, middle, index = 0;
  while(left<= h)
  {
    middle = (left + h)/2;
    if(data[middle].x == point.x && data[middle].y == point.y)
    {
      index = 1;
      break;
    }
    else if(point.x == data[middle].x && point.y < data[middle].y)
      h = middle -1;
    else if(point.x < data[middle].x)
      h = middle -1;
    else
      left = middle + 1;
  }
  if(index==1)
    printf("\nOutput: Found at record %d", middle+1);
  else if(index==0)
    printf("\nOutput: Not found");
}
void SearchPhase(struct coordPoint *sortedData, int n)
{
  struct coordPoint key;
  while(1)
  {
    printf("\nSearch input (x y): ");
    scanf("%d %d", &key.x, &key.y);
    if(key.x ==-999 || key.y==-999)
    {
      printf("\nOutput: exit\n");
      break;  
    }
    else
      BinarySearch(sortedData, n, key);
  }
}
int main()
{
  FILE *input = fopen("input.txt","r");
  FILE* output = fopen("output.txt","w");
  int radius, numberOfpoints, i;
  struct coordPoint circle;
  fscanf(input,"%d%d%d%d", &circle.x, &circle.y, &radius, &numberOfpoints);
  struct coordPoint *helper = ReadData(input,circle, radius, &numberOfpoints);
  //printData(helper, numberOfpoints);

  struct coordPoint *filteredData = FilterData(helper, circle, radius, &numberOfpoints);

  MergeSort(filteredData,0, numberOfpoints-1);
  for(int i= 0; i <numberOfpoints; i++)
    fprintf(output,"%d %d\n", filteredData[i].x, filteredData[i].y);
  fclose(output);
  SearchPhase(filteredData, numberOfpoints);
  //printData(filteredData, numberOfpoints);
  return 0;
}