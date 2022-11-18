# Arup Guha
# 11/5/2020
# RSA Example with just numbers.
# 11/1/2021 - Added conversion from words (transcription of RSA2BigInt.java)
# 10/28/2021 - Added processing multiple blocks at once. Removed some debug prints.

import random

def main():
    print(modInv(7455573413522025488689, 27299026172155563704424))
    # Get p, q.
    p = int(input("Enter the approximate value of p you want.\n"))
    p = getnextprime(p)
    q = int(input("Enter the approximate value of q you want.\n"))
    q = getnextprime(q)

    # Calculate n, phi(n).
    n = p*q
    phi = (p-1)*(q-1)

    done = False

    # Loop till we get a valid e.
    while not done:

        e = random.randint(3,phi-3)

        if gcd(e, phi) == 1:
            done = True
        else:
            print("Sorry that is not relatively prime to phi of n.")

    # d is always the modular inverse of e mod phi.
    d = modInv(e,phi)

    print("n =",n)
    print("e =",e)
    print("d =",d)

    # Get the blocksize
    blocksize = getBlockSize(n)

    # Get message.
    strmsg = input("Enter your message, lowercase letters\n")
    while len(strmsg)%blocksize != 0:
        strmsg = strmsg + "x"

    for i in range(0, len(strmsg), blocksize):

        # Convert to a number.
        tmp = strmsg[i:i+blocksize]
        msg = convert(tmp, blocksize)
        
        # This is the cipher text.
        cipher = fastmodexpo(msg, e, n)
        print(cipher)
        
        # Decrypt to get plaintext number.
        mback = fastmodexpo(cipher, d, n)

        # Now, print as string.
        origmsg = convertBack(mback, blocksize)

# Returns the max block size for integer n.
def getBlockSize(n):

    res = 0
    mult = 1

    # If we can multiply 26 into our current size, we can add 1 to our blocksize.
    while 26*mult <= n:
        res += 1
        mult *= 26

    # Here is our result.
    return res

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

# Run it!
main()