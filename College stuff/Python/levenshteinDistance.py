def levenshtein_distance(string1, string2):
    len1,len2=len(string1),len(string2)
    arr=[[0 for i in range(0,len2+1)] for j in range(0,len1+1)]
    for i in range(1,len1+1):
        arr[i][0]=i
    for i in range(1,len2+1):
        arr[0][i]=i
    for i in range(1,len1+1):
        for j in range(1,len2+1):
            price=0
            if string1[i-1]!=string2[j-1]:
                price=1
            prev=arr[i-1][j-1]+price
            previ=arr[i-1][j]+1
            prevj=arr[i][j-1]+1
            arr[i][j]=min(prev, previ, prevj)
    for row in arr:
        print(row)
    return arr[len1][len2]
napis1=input("Podaj pierwsze słowo: ")
napis2=input("Podaj drugie słowo: ")
print(f"Długość Levenshteina dla podanych słów wynosi: {levenshtein_distance(napis1, napis2)}")