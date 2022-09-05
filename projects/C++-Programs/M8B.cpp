#include <iostream>
using namespace std;

class GradeCalculator
{
public:
	GradeCalculator() = default;
      GradeCalculator(double array[], int size);
      const double avgGrade();
      const double highestGrade();
      const double lowestGrade();
      void print();
private:
      double array[10] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
      int size;
};

int main()
{
  int size;
  cout << "How many grades would you like to enter? (max is 10): ";
  cin >> size;
  double darray[size];
  cout << "Enter your grades, seperated by spaces:" << endl;
  for (int i = 0; i < size; i++)
    {
      cin >> darray[i];
    }

  cout << endl << endl << "Grade are now stored! Now displaying statistics..." << 		endl;
  GradeCalculator grades(darray, size);
  grades.print();
  cout << "The average grade: " << grades.avgGrade() << "%" << endl;
  cout << "The lowest grade: " << grades.lowestGrade() << "%" << endl;
  cout << "The highest grade: " << grades.highestGrade() << "%" << endl;

  return 0;
}


GradeCalculator::GradeCalculator(double darray[], int size)
	//: array(array), size(size)
{
		for (int i = 0; i < size; i++)
		array[i] = darray[i];
}

const double GradeCalculator::avgGrade()
{
  double sum = 0;
  for(int i = 0; i < size; i++)
    {
      sum += array[i];
    }
  
  return (sum / size);
}

const double GradeCalculator::highestGrade()
{
  double max = 0;
  for(int i = 0; i < size; i++)
    {
      if(array[i] > max)
      {
        max = array[i];
      }
    }

  return max;
}

const double GradeCalculator::lowestGrade()
{
  double min = 1000;
  for(int i = 0; i < size; i++)
    {
      if(array[i] < min)
      {
        min = array[i];
      }
    }

  return min;
}

void GradeCalculator::print()
{
  for (int i = 0; i < size; i++)
    {
      cout << array[i] << " ";
    }
}