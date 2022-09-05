def findFactor(LCD, denom):
    i = 0
    while(1):
        if ((denom * i) == LCD):
            return i
        i += 1

def findLCM(num, denom):
    LCM = num if num > denom else denom
    while(1):
        if ((LCM % num) == 0 and (LCM % denom) == 0):
            return LCM
        LCM += 1

def main():
    num, denom = map(int, input("Please enter two numbers: ").split())
    print("The LCM for both numbers is:", findLCM(num, denom))
    print("The factor to multiply the num is:", findFactor(findLCM(num, denom), num))
    print("The factor to multiply the denom is:", findFactor(findLCM(num, denom), denom))

if __name__ == "__main__":
    main()