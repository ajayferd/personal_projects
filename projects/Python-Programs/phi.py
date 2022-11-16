import math

# generic Euler's totient function that finds the number of values in a set
# that are relatively prime with respect to n
def phi(n):
    result = 1
    for i in range(2,n):
        if(math.gcd(i, n) == 1):
            result += 1
    return result

# recursively finds the answer to each new input as we build the length
def recursive_phi(n, counter):
    result = phi(n)
    if n <= 1:
        return counter

    return recursive_phi(result, counter + 1)

# sets the sequence length we try to build to with phi 
def phi_sequence_length(n):
    counter = 2
    while(True):
        if (recursive_phi(counter, 1) == n):
            print(counter)
            break
        else:
            counter += 1

# driver function
def main():
    n = int(input("Input a number to find the length of a phi sequence: "))
    phi_sequence_length(n)

main()
