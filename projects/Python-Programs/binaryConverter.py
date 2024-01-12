import this
print("Welcome to the Binary-Decimal Converter!\n(1) Convert Decimal to Binary\n(2) Convert Binary to Decimal")
selection = 'y'
while selection == 'y':
    mode = int(input("Choose an option (Enter 1 or 2): "))
    if (mode == 1):
        inputDecimal = int(input("Enter a decimal number: "))
        print(inputDecimal, "converted to binary:", bin(inputDecimal))
    elif (mode == 2):
        inputBinary = int(input("Enter a binary number: "), 2)
        print(bin(inputBinary),"converted to a decimal:", inputBinary)
    selection = input("Would you like to use the converter again (y/n)? ")
    if selection == 'n':
        print("Thank you for using this program. Goodbye!")