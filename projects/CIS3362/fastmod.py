# Arup Guha
# 10/28/2022
# Example of Fast Modular Exponentiation

import random
import time

NUMTESTS = 50

# Returns base raised to the power mod mod. (mod > 1)
def fastmodexpo(base,exp,mod):

    # Our base case.
    if exp == 0:
        return 1

    # Case to get savings.
    if exp%2 == 0:

        # Go halfway.
        tmp = fastmodexpo(base, exp//2, mod)

        # So we square, mod and return.
        return (tmp*tmp)%mod

    # Regular recursive breakdown.
    rest = fastmodexpo(base, exp-1, mod)
    return (rest*base)%mod

def main():

    bad = 0

    sTime = time.time()
    
    # Repeat our test.
    for i in range(NUMTESTS):

        # Make a random problem.
        base = random.randint(2, 10000000000000000000000000000000000000000)
        exp = random.randint(2, 10000000000000000000000000000000000000000)
        mod = random.randint(2, 10000000000000000000000000000000000000000)

        # Get my answer.
        myans = fastmodexpo(base, exp, mod)

        ''' Initially did this to see if it worked.'''
        # Get real answer.
        realans = pow(base, exp, mod)

        # See if mine failed.
        if myans != realans:
           bad += 1

    # See how long this took.
    eTime = time.time()
    print(sTime,eTime)

    # Print
    if bad > 0:
        print("Oops, did not work.")
    else:
        print("Great, our function matched Pythons!")

# Run it.
main()