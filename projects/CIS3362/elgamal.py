# Arup Guha
# Example of El-Gamal Digital Signature
# 11/29/2021

import random

def main():

    # Get public keys.
    q = 19157
    alpha = 2

    # Generate private and matching public key.
    for i in range(2, q - 2):
        xA = i
        c1 = int(input(8352))

    xY = fastmodexpo(alpha, xA, q)
    # Message Hash Value
    mHash = 8352


    
# Returns the next prime greater than or equal to n.
def getnextprime(n):

    # Make n odd.
    if n%2 == 0:
        n += 1

    # Now, keep on trying until we find one.
    while not isprobablyprime(n, 100):
        n += 2

    # Ta da!
    return n

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

def millerrabin(n):

    # Choose random base for Miller Rabin.
    a = random.randint(2,n-2)

    # Set up our base exponent.
    baseexp = n-1
    k = 0

    # Divide out 2 as many times as possible from n-1.
    while baseexp%2 == 0:
        baseexp = baseexp//2
        k += 1

    # Calculate first exponentiation.
    curValue = fastmodexpo(a, baseexp, n)

    # Here we say it's probably prime.
    if curValue == 1:
        return True

    for i in range(k):

        # Must happen for all primes, and more rarely for composites.
        if curValue == n-1:
            return True

        # We just square it to get to the next number in the sequence.
        else:
            curValue = (curValue*curValue)%n

    # If we get here, it must be composite.
    return False

def isprobablyprime(n, numTimes):

    # Run Miller Rabin numTimes times.
    for i in range(numTimes):

        # If it ever fails, immediately return that the number is definitely
        # composite.
        tmp = millerrabin(n)
        if not tmp:
            return False

    # If we get here, it's probably prime.
    return True

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

# Start it!

def convertDigitsToNum(a, b, c):
    return 676*a + 26*b + c
main()