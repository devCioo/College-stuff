import numpy as P
def extractCoeffs(poly):
    coeffs = []
    lst = []
    lst[:0] = poly
    lst.insert(0, '+')
    for i in range(len(lst)):
        if lst[i-1] == '+' or lst[i-1] == '-':
            if lst[i] == 'x':
                if lst[i-1] == '+':
                    coeffs.append(1)
                elif lst[i-1] == '-':
                    coeffs.append(-1)
            else:
                temp = ""
                if lst[i-1] == '-':
                    temp = temp + '-'
                while lst[i] != 'x':
                    temp = temp + lst[i]
                    i = i + 1
                    if i >= len(lst):
                        break
                coeffs.append(int(temp))
    return coeffs
def showRoot(poly):
    coefs = extractCoeffs(poly)
    for i in P.roots(coefs):
        yield i
poly = ""
choice = 0
while choice != 3:
    print(f"Wielomian: {poly}\n1. Wprowadź wielomian.\n2. Wyświetl miejsce zerowe.\n3. Wyjdź.\n>>", end=" ")
    try:
        choice=int(input())
        if choice == 1:
            poly = input("f(x) = ")
            # print(extractCoeffs(poly))
        elif choice == 2:
            print(next(showRoot(poly)))
        elif choice < 1 or choice > 3:
            print("Podaj wartość od 1 do 3!")
    except:
        print("Wprowadzono błędną wartość!")