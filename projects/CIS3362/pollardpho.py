# Arup Guha
# 10/20/21
# Converting Pollard-Rho to Python

# Pre-condition: n is an int and n > 1000
# Returns a factor of n via Pollard-Rho factorization. If the factorization
# fails, -1 is returned.
def getFactor(n):

    a = 2
    b = 2

    while (True):

        # a steps once, b steps twice in the sequence.
        a = (a*a+1)%n
        b = (b*b+1)%n
        b = (b*b+1)%n

        # Get the difference between a,b.
        diff = a-b
        if diff < 0:
            diff += n

        # Give this a shot!
        factor = gcd(n, diff)

        # Found a factor.
        if factor > 1 and factor < n:
            return factor

        # Test failed.
        elif factor == n:
            return -1

def gcd(a,b):
    if b == 0:
        return a
    return gcd(b, a%b)


def main():
    a = 221122112212 
    b = 123456790002
    print(a*b)
    n = int(input("Enter a number to factorize\n"))
    factor = getFactor(n)

    if factor == -1 or factor == 0:
        print("sorry test failed")
    else:
        print(n,"=",factor,"x",n//factor)

main()
    