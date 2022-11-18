# Arup Guha
# 11/5/2020
# RSA Example with just numbers.
# 11/1/2021 - Added conversion from words (transcription of RSA2BigInt.java)
# 10/28/2021 - Added processing multiple blocks at once. Removed some debug prints.

import random

def main():
    # Get p, q, e.
    p = 221122112213
    q = 123456790003
    e = 7455573413522025488689  
    blocksize = 16
    numBlocks = int(13)

    # Calculate n, phi(n).
    n = p*q
    phi = (p-1)*(q-1)

    d = modInv(e, phi)

    print("p =",p)
    print("q =",q)
    print("n =",n)
    print("e =",e)
    print("d =",d)
    print("phi =",phi)

    for loop in range(numBlocks):

        # Convert to a number.
        cipher = int(input(""))
        
        # Decrypt to get plaintext number.
        mback = fastmodexpo(cipher, d, n)

        # Now, print as string.
        origmsg = convertBack(mback, blocksize)
        print(origmsg)

def convert(mystr, blocksize):

    val = 0

    # Go through each letter.
    for i in range(blocksize):

        # Get value of current letter.
        num = 0
        if i < len(mystr):
            num = ord(mystr[i]) - ord('a')

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