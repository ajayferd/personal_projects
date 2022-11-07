 
def gcdExtended(a, b):
 
    # Base Case
    if a == 0:
        return b, 0, 1
 
    gcd, x1, y1 = gcdExtended(b % a, a)
 
    # Update x and y using results of recursive
    # call
    x = y1 - (b//a) * x1
    y = x1
 
    return gcd, x, y
 
 
# Driver code
a, b = 922535452715757606722838121, 2765039178267556004970549684
g, x, y = gcdExtended(a, b)
print("gcd(", a, ",", b, ") = ", g)