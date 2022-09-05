#include <stdio.h>
#include <stdlib.h>

typedef struct node
{
  int data;
  struct node *prev, *next;
} soldier;
soldier *head, *tail;

soldier* create_soldier(int sequence);
soldier* create_reverse_circle(int n);
soldier* rearrange_circle(soldier* head);
void display(soldier* head);
int kill(soldier* head, int n, int k);

soldier* delete_node(soldier*head, int item);
void insertEnd(soldier** head, soldier* newNode);

int main(void) {
  int numOfsoldiers, skipValue;
  printf("Enter numbers of soldiers and k: ");
  scanf("%d %d", &numOfsoldiers, &skipValue);
  create_reverse_circle(numOfsoldiers);
  //printf("\nhead: %d tail: %d before reversing\n", head->data, tail->data);
  display(head);
  //rearrange_circle(head);
  //display(head);
  int winner = kill(head, numOfsoldiers, skipValue);

  printf("\nSurvived: %d", winner);
  return 0;
}
soldier* create_reverse_circle(int n)
{
  if(n==0)
    return head;
  create_soldier(n);
  return create_reverse_circle(n-1);
}
soldier* rearrange_circle(soldier* head)
{
  if(!head)
    return NULL;
  soldier *temp, *newHead, *current, *prev;

  newHead = NULL;
  temp = head->prev;
  current = temp;

  do{
    prev = current->prev;
    insertEnd(&newHead, current);
    current = prev;
  }while(current->prev!=temp);
  insertEnd(&newHead, current);
  temp = head->next;
  return newHead;
}
int kill(soldier* head, int n, int k)
{
  soldier *ptr1, *ptr2;
  int ctr;
  ptr1 = head;
  ptr2 = head;
  while(ptr1->next != ptr1)
  {
    ctr = 1;
    while(ctr != k)
    {
      ptr2 = ptr1;
      ptr1 = ptr1->next;
      ctr++;
    }
    ptr2->next = ptr1->next;
    ptr1 = ptr2->next;
    head->data = ptr1->data;
  }
  return head->data;
}
soldier* create_soldier(int sequence)
{
  soldier *newNode = (soldier*)malloc(sizeof(soldier)); //creating a new node to insert w/ data
  newNode->data = sequence;
  if(head == NULL)
  {
    newNode->next = newNode;
    newNode->prev = newNode;
    head = newNode;
    tail = newNode;
  }
  tail = head->prev;
  tail->next = newNode;
  newNode->prev = tail;
  newNode->next = head;
  head->prev = newNode;
  tail = newNode;
  return head;
}
void display(soldier* head)
{
    soldier *temp = head;

    printf("\nList: ");
    while (temp->next != head)
    {
        printf("%d ", temp->data);
        temp = temp->next;
    }
    printf("%d ", temp->data);
    printf("\nAfter ordering: ");
    tail = head->prev;
    temp = tail;
    while (temp->prev != tail)
    {
        printf("%d ", temp->data);
        temp = temp->prev;
    }
    printf("%d ", temp->data);
}
void insertEnd(soldier** head, soldier* newNode)
{
  if (*head == NULL)
  {
    newNode->next = newNode->prev = newNode;
    *head = newNode;
    return;
 }
  soldier* tail = (*head)->prev;
  newNode->next = *head;
  (*head)->prev = newNode;
  newNode->prev = tail;
  tail->next = newNode;
}
