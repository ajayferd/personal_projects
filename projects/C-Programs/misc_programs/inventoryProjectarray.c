#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAXSIZE 25
//notes: too messy approach, try to limit menu items... focus on the solution. try linked list and applying a plan.
//dont get lost in the menu.
typedef struct data{
  char projectName[MAXSIZE];
  char itemName[MAXSIZE];
  int count;
} inventory;

inventory* getData()
{
  //FILE *newList;
  //newList = fopen("resource_list.txt","w");

  int c, N, i = 0, flag = 1;
  //printf("Type number of items in list: ");
  //scanf("%d",&N);
  inventory* temp = (inventory*)malloc(MAXSIZE*sizeof(inventory));


  printf("What is your title for this resource list?\nYour reponse: ");
  //while((c = getchar()) != '\n' && c != EOF);
  scanf("%s", temp->projectName);
  //printf("%s", temp->projectName);

  printf("\nInsert item name. Your reponse: ");
  while((c = getchar()) != '\n');
  scanf("%s", temp->itemName);
  //printf("%s", temp->itemName);
  
  printf("\nInsert item count. Your response: ");
  //while((c = getchar()) != '\n' && c != EOF);
  scanf("%d", &temp->count);
  //printf("%d", temp->count);
  
  printf("\n%s\n%s: %d", temp->projectName, temp->itemName, temp->count);

  return temp;
  //fclose(newList);
  }
int menu()
{
  char answer;
  inventory* temp;
  printf("Welcome to 'untitled project'\nDo you already have a resource list you'd like to access?\n");
  printf("a.) Yes\nb.) No\nc.) Quit Program\nYour response: ");
  scanf(" %c", &answer);
  if(answer == 'a')
  {
    //access and edit lists
  }
  if(answer == 'b')
  {
    printf("Would you like to create a new one?\na.) Yes\nb.) No\nYour response: ");
    scanf(" %c", &answer);
    if(answer == 'a')
      printf("\nCreating new list...\n");
      temp = getData();
      menu();
    if(answer == 'b')
      {
        printf("\nOkay, returning to root menu\n");
        menu();
      }
  }
  if(answer == 'c')
    printf("\nSaving... Goodbye.");
    return 0;
  return 0;
}
int findDifference(int needed, int currentAmt);

int main(void){
  menu();
  
  return 0;
}

