def prime_numbers(start, end):
    if start>end:
        print("Błędny przedział.")
        exit(0)
    elif start<0 or end<0:
        print("Wprowadzono ujemny ogranicznik przedziału.")
        exit(0)
    lst=[]
    temp=0
    for i in range(start,end+1):
        temp=0
        for j in range(2,i):
            if i%j==0:
                temp+=1
        if temp==0:
            lst.append(i)
    return lst
a=input("Podaj początek przedziału: ")
b=input("Podaj koniec przedziału: ")
print(prime_numbers(int(a),int(b)))
