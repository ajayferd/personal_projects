import random
import math
first_name = "Bart"
last_name = "Simpson"
full_name = first_name + " " + last_name
print(type(first_name))
print("Hello " + full_name)

age = 21
age += 1

print(type(age))
print(age)
print("Your age is:", age)
print("Your age is: " + str(age))

height = 5.8
print(type(height))
print("Your height is: " + str(height) + "ft")

bool_val = False

name, income, pets = "Phill", 100000, True

print(name, income, pets)

print(len(full_name))
print(full_name.find("t"))

print(full_name.upper())

letters_digits = "Bro"
print(letters_digits.isalpha())
print(letters_digits.isdigit())
print("hello world ")


pi = 3.14
print(pow(pi,3))

# slicing = create a substring. 3 optional parameters. [start(inclusive):stop(exclusive):step]

person = "Bobby Stiner"
person_first = person[0]
# returns B since it is the first letter of the string at index 0 (1)
person_first = person[0:3]
print(person_first)
#               start, end, every 2 character
person_first = person[::2]
# reverse
person_first = person[::-1]

# slice uses , instead of :
# 
website_url = "http://hotmail.com"
slice = slice(7,-4)
print(website_url[slice])


# while None == None:
#    print("Hello World")

for index in range(50, 100 + 1, 5):
    print(index)

# lists = used to store mutiple items in a single variable, similar to array list

buildings = ["Empire State Building", "Effifel Tower", "Daytona Speedway"]
print(buildings)

buildings.append("Stone Mountain")
buildings.remove("Empire State Building")
print(buildings.pop())
print(buildings)
buildings.sort()
print(buildings)

# 2d lists

drinks = ["coffee", "soda", "tea"]
dinner = ["pizza", "hotdog", "beef"]
meat = ["pork", "lamb", "chicken"]

food = [drinks, dinner, meat]
print(food)
print(food[0])
print(food[0][2])
print(food[0][2][0])

# tuples = collections that are ordered and unchangeable, used to group together related data

student = ("Jamie", 20, "male")
print(student.count("Jamie"))
print(student.index("male"))

for x in student:
    print(x)

if "male" in student:
    print("Student is a male")

# set = collection which is unordered, unindexed. No duplicate values. Faster than lists and tuples!
"""
.add
.remove
.update
.clear
.union
.difference
.intersection
"""
stoplight = {"Green", "Yellow", "Red", "Stop"}
signs = {"Yield", "Stop", "Caution"}


traffic_set = stoplight.union(signs)
for x in traffic_set:
    print(x)

print(stoplight.difference(signs))
print(signs.difference(stoplight))
print(signs.intersection(stoplight))

# dictionary = a changeable, unordered collection of unique key value pairs
# Very fast due to hashing, allows quick access to values - essentially HashMap implementation for Python

capitals = {"USA": "DC",
            'India': 'New Dehli',
            'China':'Beijing',
            'Russia':'Moscow' }
print(capitals["Russia"])
print(capitals.get('Germany'))
print(capitals.keys())
print(capitals.values())
print(capitals.items())

for key,value in capitals.items():
    print(key,value)

print(capitals.pop('China'))

# index operator [] = gives access to a sequence elements [starting:ending] []
# functions = enuff' said
# keyword arguments = makes the position of passed arguments irrelveant vs positional arguements

def hello_world(f_name, l_name):
    return "Hello " + f_name + l_name

for i in range(2):
    print(hello_world(l_name="Jameson",f_name="James"))

# python follows LEGB for scope of variables
"""
L = local
E = enclosed
G = global
B = built-in
"""

# *(args) = parameter that will pack all arguments into a tuple 
# useful so that a function can accept a varying amount of arguments, similar to ... from java

def add(*inputs):
    sum = 0
    for i in inputs:
        sum += i
    return sum

print(add(1,2,3,4,5,6,7,8,9))

# **(kwargs) = parameter that will pack all arguments into a dicitonary, similar to *(args)
# end=" " -> gets rid of newline character following a print statment
def hi(**kwargs):
    print("Hello " + kwargs['first'] + " " + kwargs['last'])

hi(first="Bro", middle="Dude", last="Code")

# format() = different ways to modify and print strings

print("The {} jumped over the {}".format("cow","moon"))
print("The {animal} jumped over the {item}".format(animal="cow",item="moon"))

my_num = 1000000
print("The number is {:,}".format(my_num))
print("The number is {:X}".format(my_num))
print("The number is {:.2E}".format(my_num))

# random

x = random.randint(1,6)
print("Dice rolled a:", x)
moves = ['rock', 'paper', 'scissors']
z = random.choice(moves)
print(z)

# exception handling, exactly as in java
try:
    numerator = int(input("Enter a number to divide: "))
    denominator = int(input("Enter a number ot divide by: "))
    result = numerator / denominator
except ZeroDivisionError as e:
    print(e)
    print("Division by zero is illegal")
except ValueError as e:
    print(e)
    print("Error, divison by non-number")
else:
    print(result)
finally:
    print("Always executes...")

