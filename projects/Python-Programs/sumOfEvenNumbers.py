selection = 'y'
while selection == 'y':
    userNum = int(input("Enter any number (integer): "))
    sum = 0
    for numbers in range(userNum + 1):
        if((numbers % 2) == 0): 
            sum += numbers
    print("Sum of all even numbers between 1 and", userNum,":", sum)
    selection = (input("Would you like to run the program again? (y/n) "))
    if(selection == 'n'):
        print("Thank you for using the program, goodbye!") 