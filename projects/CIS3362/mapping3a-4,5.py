#   Alejandro Fernandez
#   This program solves the problem found in Assignment #3A
#   Find all unique sequences of possible keys 

import math
from itertools import permutations

def find_all_keys(alpha_size):
    print("Total number of all possible keys: {}".format(math.factorial(alpha_size)))
   
def find_all_unique_keys(alpha_size, list_of_permutations):
    if alpha_size < 0 or alpha_size > 45:
        print("Invalid input")
        return -1
    
    counter = 0
    for perms in list_of_permutations:
        uniquePerm = True
        for i in range(0, alpha_size):
            if perms[i] is i:
                uniquePerm = False
                break
        # if we have found a unique permutation we can print it out and increase the counter
        if uniquePerm is True:
            counter += 1
            # print(perms)
    return counter

def find_all_unique_keys_really_fast(n):
    der = [0 for i in range(n + 1)] 
      
    # Base cases 
    der[0] = 1
    der[1] = 0
    der[2] = 1
      
    # Fill der[0..n] in bottom up manner  
    # using above recursive formula 
    for i in range(3, n + 1): 
        der[i] = (i - 1) * (der[i - 1] + 
                            der[i - 2]) 
          
    # Return result for n 
    return der[n] 
    
def main():
    alpha_size = int(input("Please enter the alphabet size: "))
    # find_all_keys(alpha_size)
    # list_of_permutations = list(permutations(range(0, alpha_size)))
    # print("Size of unique permutations: {}".format(find_all_unique_keys(alpha_size, list_of_permutations)))
    print("Size of unique permutations really fast: {}".format(find_all_unique_keys_really_fast(alpha_size)))


if __name__ == "__main__":
    main()
