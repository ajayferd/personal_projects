# As stated by Professor Guha, We can represent our integers as binary. As we start with our exponent, each 
# time we can mod and divide, we’re peeling off the last binary digit. If this is one, then we can 
# just multiply our current term and mod into our result.
def fastmodexpo(base,exp,mod):
    # check if exp is valid
    if exp == 0:
        return 1

    # initalize result
    result = 1
    base = base % mod

    # check if base value is valid
    if base == 0 or (base % mod) == 0:
        return 0 

    while (exp > 0):
        # see if exp is odd or even

        # if odd, multiply the base with result
        temp = exp & 1
        if temp == 1:
            result = (result * base) % mod
        
        # if even, left shift exp and multiply the base
        exp = exp >> 1
        base = (base * base) % mod
    
    return result