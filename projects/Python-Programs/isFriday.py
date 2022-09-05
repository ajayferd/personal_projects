import math

def isLeapYear(year):
    year += 2000
    if (((year % 4 == 0) and (year % 100 != 0)) or (year % 400 == 0)):
        return 1
    else:
        return 0

def isFriday(monthCode, day, year):
    # (year code + month code + century code + date number - leap year code) mod 7
    yearCode = ((year + (year/4)) % 7)
    centuryCode = 6
    leapCode = isLeapYear(year)
    
    dayNumber = math.floor((yearCode + monthCode + centuryCode + day - leapCode) % 7)
    
    # 5 is a friday
    if dayNumber == 5:
        return True
    else:
        return False

def monthCodeGenerator(month):
    if month == 1:
        return 0
    elif month == 2:
        return 3
    elif month == 3:
        return 3
    elif month == 4:
        return 6
    elif month == 5:
        return 1
    elif month == 6:
        return 4
    elif month == 7:
        return 6
    elif month == 8:
        return 2
    elif month == 9:
        return 5
    elif month == 10:
        return 0
    elif month == 11:
        return 3
    elif month == 12:
        return 5


selection = 'y'
while selection == 'y':
    userMonth = int(input("Enter the month: "))
    userDay = int(input("Enter the day: "))
    userYear = int(input("Enter the year (last 2 digits): "))

    if isFriday(monthCodeGenerator(userMonth), userDay, userYear) == True:
        print("It's Friday!!! Woooo!")
    else:
        print("It's not Friday yet :(")

    selection = input("Would you like to run the program again (y/n)? ")
    if selection == 'n':
        print("Thank you for using the program, goodbye!")
