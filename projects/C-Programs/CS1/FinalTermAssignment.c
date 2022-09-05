#include <stdio.h>
#include <ctype.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>

typedef struct coordPoint
{
  int x;
  int y;
  int radius;
  int numberOfpoints;
} cpoint;

(*cpoint)ReadData(c)
{
  FILE *input = fopen("input.txt","r");

  if(!input)
  {
    printf("File not found");
    exit(-1);
  }

  fscanf(input,"%d%d%d%d", &data.x, &data.y, &data.radius, &data.numberOfpoints);


  fclose(input);
  return cpoint data;
}
void FilterData();
void MergeSort();
void Merge();
void BinarySearch();
void SearchPhase();
int main()
{
  int distance, radius, circleX, circleY, numberOfpoints;
  struct cpoint data;
  ReadData(&data);




//center of circle; x1, y1
//if distance<radius or distance == radius, point is in circle;
//if distance>radius, point is outside circle
//distance = sqrt((x2-x1,2)+(y2-y1,2));




  return 0;
}
