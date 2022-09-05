#include <iostream>
#include <string>
using namespace std;

class Employee
{
public:
	Employee();
	Employee(string name, double salary);
	string getName();
	double getSalary();

private:
	string name;
	double salary;
};

class Company
{
public:
	Company(int maxNumEmployees);
	~Company();
	void addEmployee(Employee newHire);
	double averageSalary();

private:
	int maxNumEmployees;
	int currentNumEmployees;
	Employee* companyEmployees;
};

int main()
{
	cout.setf(ios::fixed);
	cout.setf(ios::showpoint);
	cout.precision(2);

	int answer = 1;
	int maxNumEmployees = 0;

	cout << "Number of employees in the company: ";
	cin >> maxNumEmployees;
	Company test(maxNumEmployees);
	
	while (answer == 1 || answer == 2)
	{
		cout << "Main Menu" << endl;
		cout << "1. Add employee" << endl;
		cout << "2. Print average salary" << endl;
		cout << "3. Quit" << endl;
		cout << "Choose an option: ";
		cin >> answer;

		if (answer == 1)
		{
			cout << "Enter employee name and salary, separated by a space: " << endl;
			string name;
			double salary;
			cin >> name >> salary;
			Employee temp = Employee(name, salary);

			test.addEmployee(temp);
			cout << "here" << endl << endl;
		}
		else if (answer == 2)
			cout << "Average salary: $" << test.averageSalary() << endl;
		else
		{
			cout << "Thank you for using this program. Goodbye!";
			return 0;
		}
	}

	return 0;
}

Employee::Employee()
		: name("unnamed"), salary(0.0)
{

}

Employee::Employee(string name, double salary)
		: name(name), salary(salary)
{

}

string Employee::getName()
{
	return name;
}

double Employee::getSalary()
{
	return salary;
}


Company::Company(int maxNumEmployees)
	   : maxNumEmployees(maxNumEmployees), currentNumEmployees(0)
{
	companyEmployees = new Employee[maxNumEmployees];  
}

Company::~Company()
{
	delete[] companyEmployees;
}

void Company::addEmployee(Employee newHire)
{
	companyEmployees[currentNumEmployees++] = newHire;
}

double Company::averageSalary()
{
	double total = 0;
	for (int i = 0; i < currentNumEmployees; i++)
	{
		total += companyEmployees[i].getSalary();
	}

	return total/currentNumEmployees;
}