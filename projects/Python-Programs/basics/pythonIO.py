import os

path = "C:\\Users\\Administrator\\Desktop\\projects\\Python-Programs\\basics\\testfile.txt"
text = "I wanted to say something else\nGet fucked idiot!"

if os.path.exists(path):
    print("That location exists!")
    if os.path.isfile(path):
        print("That is a file")
    elif os.path.isdir(path):
        print("That is a directory")
else:
    print("Location not found!")

"""
 r = read
 w = write
 a = append

 w overwrites all previous data
 a appends data on file
"""

try:
    with open(path,'w') as file:
        file.write(text)
    with open(path,'a') as file:
        file.write("\nGood morning!")

    # by default a file is 'r' mode
    with open(path) as file:
        file_text = file.read() 
        print(file_text)

except FileNotFoundError:
    print("File not found!")

