# Iterative Function to calculate
# (x^y) % p in O(log y)
def modular_exponents(x, y, p) :
    res = 1
 
    # Update x if it is more
    # than or equal to p
    x = x % p    
    if (x == 0) :
        return 0
 
    while (y > 0) :
         
        # If y is odd, multiply
        # x with result
        if ((y & 1) == 1) :
            res = (res * x) % p
 
        # y must be even now
        y = y >> 1      # y = y/2
        print("ans: {}, x^x: {}, p: {}".format((x*x)%p, x*x, p))
        x = (x * x) % p
         
    return res

x = 2
y = 5
p = 13

print("Output ", modular_exponents(x,y,p))