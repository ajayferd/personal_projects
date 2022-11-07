# Arup Guha
# 11/5/2020
# RSA Example with just numbers.
# 11/1/2021 - Added conversion from words (transcription of RSA2BigInt.java)

# Edited on 11/3/2021 to complete the solution for Homework #, Problem #6
# Took out Miller-Rabin, since it's not needed (we already knew p,q).

import random

def main():

    # Get p, q.
    p = 36279836279899
    q = 76214213232259

    # Calculate n, phi(n).
    n = p*q
    phi = (p-1)*(q-1)
    e = 922535452715757606722838121

    # d is always the modular inverse of e mod phi.
    d = modInv(e,phi)

    # Get number of blocks from file. (I'll pipe the file in as input...)
    numBlocks = int(input(""))

    for loop in range(numBlocks):

        # Read in this ciphertext block.
        cipher = int(input(""))
    
        # Decrypt to get plaintext number.
        mback = fastmodexpo(cipher, d, n)

        # Print this number.
        # print("Numerically, we recovered", mback)

        # Now, print as string.
        origmsg = convertBack(mback, 19)
        print(origmsg)

def convert(mystr, blocksize):

    val = 0

    # Go through each letter.
    for i in range(blocksize):

        # Get value of current letter.
        num = 0
        if i < len(mystr):
            num = ord(mystr[i]) - ord('A')

        # Add in the contribution of this letter.
        val = 26*val + num

    # Ta da!
    return val
        
def convertBack(msg, blocksize):

    res = ""

    # Concatenate letters from back to front.
    for i in range(blocksize):
        let = chr(msg%26 + ord('A'))
        res = let + res
        msg = msg//26

    # Ta da!
    return res

# Returns (base**exp) % mod, efficiently.
def fastmodexpo(base,exp,mod):

    # Base case.
    if exp == 0:
        return 1

    # Speed up here with even exponent.
    if exp%2 == 0:
        tmp = fastmodexpo(base,exp//2,mod)
        return (tmp*tmp)%mod

    # Odd case, must just do the regular ways.
    return (base*fastmodexpo(base,exp-1,mod))%mod

# Returns the gcd of a and b.
def gcd(a,b):
    if b == 0:
        return a
    return gcd(b, a%b)

# Returns a list storing [x, y, gcd(a,b)] where ax + by = gcd(a,b).
def EEA(a,b):

    # End of algorithm, 1*a + 0*b = a
    if b == 0:
        return [1,0,a]

    # Recursive case.
    else:

        # Next quotient and remainder.
        q = a//b
        r = a%b

        # Algorithm runs on b, r.
        rec = EEA(b,r)

        # Here is how we put the solution back together!
        return [rec[1], rec[0]-q*rec[1], rec[2]]

# Returns the modular inverse of x mod n. Returns 0 if there is no modular
# inverse.
def modInv(x,n):

    # Call the Extended Euclidean.
    arr = EEA(n, x)

    # Indicates that there is no solution.
    if arr[2] != 1:
        return 0

    # Do the wrap around, if necessary.
    if arr[1] < 0:
        arr[1] += n

    # This is the modular inverse.
    return arr[1]

# Run it!
main()