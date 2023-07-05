from collections import namedtuple
import random
def createBinaryTree(elems):
    if elems < 1:
        return "Podano błędną wartość!"
    if elems == 1:
        return "Nie da się stworzyć drzewa z jednego elementu!"
    Node=namedtuple('Node',['value'])
    lst=[]
    k=1
    while k<elems:
        k*=2
    for i in range(k-1):
        lst.append(Node('--'))
    x=random.randint(10,99)
    root=Node(x)
    lst[0]=root
    for i in range(elems-1):
        found_pos = False
        relpos = 1
        x=random.randint(10,99)
        while found_pos==False:
            if relpos>(k-1):
                for j in range(k,k*2):
                    lst.append(Node('--'))
                k=k*2
            if lst[relpos-1].value == '--':
                lst[relpos-1]=Node(x)
                found_pos=True
            elif x <= int(lst[relpos-1].value):
                relpos=relpos*2
            elif x > int(lst[relpos-1].value):
                relpos=(relpos*2)+1
    printBinaryTree(lst)
    return "\nDrzewo stworzone!"
def printBinaryTree(lst):
    k=1
    n=0
    while k<len(lst):
        k*=2
        n+=1
    x=0
    for i in range(n):
        print("\t" * (n-i), end=" ")
        for j in range(2**i):
            print(f"{lst[x].value}", end=" ")
            print(" " * (n-i), end=" ")
            x+=1
        print("")
print(createBinaryTree(7))