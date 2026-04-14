i = 1
j = 100
answer = 0
number = 1
while number <= 100:
    print(str(i) + " + " + str(j) + " = " + str(i+j))
    i += 1
    j -= 1
    answer += number
    number += 1

print("The answer is: " + str(answer))