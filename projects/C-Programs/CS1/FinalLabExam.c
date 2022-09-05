/* COP 3502C Final Lab Exam
This program is written by: Alejandro Fernandez*/
#include <stdio.h>
#include <stdlib.h>

typedef struct treeNode {
  int income, count;
  struct treeNode *left, *right;
} treeNode;

struct treeNode* createNode(int income, int count)
{
  treeNode *temp = (treeNode*)malloc(sizeof(treeNode));
  temp->income = income;
  temp->count = count;
  temp->left = NULL;
  temp->right = NULL;
  return temp;
}

struct treeNode* insertNode(treeNode *root, int income, int count)
{
  if(root == NULL)
    return createNode(income, count);

  if(income < root->income)
    root->left = insertNode(root->left, income, count);
  else if(income > root->income)
    root->right = insertNode(root->right, income, count);
  
  return root;
}


void printInorder(treeNode *ptr, FILE* output)
{
  if(ptr != NULL)
  {
    printInorder(ptr->left, output);
    fprintf(output,"(%d, %d),",ptr->income, ptr->count);
    printf("(%d, %d),",ptr->income, ptr->count);
    printInorder(ptr->right, output);
  }
}

struct treeNode* searchHighestincome(treeNode *root)
{
  if(root->right == NULL || root == NULL)
    return root;
  else
    return searchHighestincome(root->right);
}

int totalSinglechildren(treeNode *root)
{
  if (root == NULL)
    return 0;
  
  int count = 0;
  if(root->right != NULL && root->left == NULL)
    count++;
  else if(root->left != NULL && root->right == NULL)
    count++;
  
  count += (totalSinglechildren(root->left) + totalSinglechildren(root->right));

  return count;
}  

int totalIncome(treeNode *root)
{
  if (root == NULL)
    return 0;
  else
  {
    int total = root->income * root->count;
    return total + totalIncome(root->left) + totalIncome(root->right);
  }
}
int main(void) {
  treeNode *root = NULL;
  treeNode *helper = NULL;
  FILE* input = fopen("in.txt","r");
  FILE* output = fopen("out.txt","w");

  int i, N, income, count, result;
  fscanf(input,"%d",&N);
  for(i=0; i<N; i++)
  {
    fscanf(input,"%d%d",&income, &count);
    root = insertNode(root, income, count);
  }
  fclose(input);
  printf("Tree constructed from the file");
  fprintf(output,"Tree constructed from the file");

  printf("\nInorder: ");
  fprintf(output,"\nInorder: ");
  printInorder(root, output);

  helper = searchHighestincome(root);
  printf("\nHighest income: %d. Total people with highest income: %d", helper->income, helper->count);
  fprintf(output,"\nHighest outcome: %d. Total people with highest income: %d", helper->income, helper->count);

  printf("\nTotal single children in the tree: %d\n", totalSinglechildren(root));
  fprintf(output,"\nTotal single children in the tree: %d\n", totalSinglechildren(root));

  result = totalIncome(root);
  printf("Total income in the area: %d",result);
  fprintf(output,"Total income in the area: %d",result);
  fclose(output);
  return 0;
}