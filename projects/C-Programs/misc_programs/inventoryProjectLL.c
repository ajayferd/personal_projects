#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define MAXSTRING 30
#define QUIT "quit"

struct node{
    int itemCount;
    char *itemName;
    struct node *next;
};
int c;

//function lists
struct node* insertNewnodeAtfront(struct node *head);
void display(struct node *head);
int menu();
 
int main()
{
    //possible way to have multiple lists...
    /*int n;
    scanf("%d", &n);
    struct node *head[n];*/
    
    struct node *head = NULL;

    /*printf("Insert title of project: ");
    char projectName[50];
    scanf("%49s", projectName);
    printf("%s\n", projectName);*/
    
    
    
    head = insertNewnodeAtfront(head);
    
    

    
    display(head);
    return 0;
}

struct node* insertNewnodeAtfront(struct node *head)
{
    struct node *temp = (struct node*)malloc(sizeof(struct node));
    if(!temp)
    {
        printf("Unable to allocate memory");
        return 0;
    }
    // when passing by reference use strcpy
    while ((c = getchar()) != '\n' && c != EOF);
    printf("\nInsert item name(s) with amount(s)\n");
    char *item;
    char helper[50];
    int value;

    //while ((c = getchar()) != '\n' && c != EOF);
    scanf(" %49s", helper);
    scanf("%d", &value);
    //while ((c = getchar()) != '\n' && c != EOF);

    strcpy(item,helper);
    temp->itemName = item;
    temp->itemCount = value;

    temp->next = head;
    return temp;
}
int menu();
void display(struct node *head)
{
    if(head == NULL)
        return;
    else
    {
        printf("%s: %d\n", head->itemName, head->itemCount);
        display(head->next);
    }
}