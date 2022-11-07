def gcd(a, b):
    if(a == 0):
        return b
    return gcd(b % a, a)

def phi(n):
    res = 1
    for i in range(2,n):
        if(gcd(i, n) == 1):
            res += 1
    return res

def recursive_phi(n, counter):
    res = phi(n)
    if n <= 1:
        return counter

    return recursive_phi(res, counter + 1)

def fn(n):
    counter = 2
    while(True):
        if (recursive_phi(counter, 1) == n):
            print(counter)
            break
        else:
            counter += 1

fn(10)
