import math
class KDTree:
    def __init__(self, tree_size: int):
        self.tree_size=tree_size
        self.lst=[]
        self.empty=('',)
        self.rows=1
        temp = ('',)
        for i in range(self.tree_size-1):
            self.empty=self.empty+temp
        print(f"Utworzono {self.tree_size}-wymiarowe drzewo!\n")
    def insertPoint(self, point: tuple):
        k=0
        m=0
        if len(point) != self.tree_size:
            print(f"Błąd. Próba dodania {len(point)}-wymiarowego węzła do {self.tree_size}-wymiarowego drzewa.")
        else:
            if len(self.lst) == 0:
                self.lst.append(point)
            else:
                while self.lst[k] != self.empty:
                    if m == self.tree_size:
                        m=0
                    if self.lst[k][m] > point[m]:
                        k=(k*2)+1
                    elif self.lst[k][m] <= point[m]:
                        k=(k*2)+2
                    if k > (len(self.lst)-1):
                        x=1
                        while x < (len(self.lst)+1):
                            x=x*2
                        for i in range(x, x*2):
                            self.lst.append(self.empty)
                        self.rows+=1
                    m=m+1
                self.lst[k]=point
    def findPoint(self, point: tuple):
        k=0
        l=1
        m=0
        n=0
        if len(point) != self.tree_size:
            print(f"Błąd. Próba znalezienia {len(point)}-wymiarowego węzła w {self.tree_size}-wymiarowym drzewie.\n")
        else:
            while l <= len(self.lst):
                l=l*2
                n=n+1
            for i in range(n):
                if self.lst[k] == point:
                    return k
                if m == self.tree_size:
                    m=0
                if self.lst[k][m] > point[m]:
                    k=(k*2)+1
                elif self.lst[k][m] <= point[m]:
                    k=(k*2)+2
                m=m+1
        return "Węzeł nie znajduje się w drzewie.\n"
    def findMin(self, position: int, dimension: int):
        found=False
        m=dimension+1
        while found != True:
            if m == self.tree_size:
                m=0
            if (((position*2)+1) > len(self.lst)) or ((((position*2)+1) and ((position*2)+2)) == self.empty):
                return position
            if (dimension == m) and self.lst[(position*2)+1] != self.empty:
                position=(position*2)+1
            else:
                v1=self.lst[position][dimension]
                v2=self.lst[(position*2)+1][dimension]
                v3=self.lst[(position*2)+2][dimension]
                if type(v2) == str:
                    v2=9999
                if type(v3) == str:
                    v3=9999
                if v2 < v1:
                    if v3 < v2:
                        position=(position*2)+2
                    else:
                        position=(position*2)+1
                else:
                    if v3 < v1:
                        position=(position*2)+2
                    else:
                        found=True
            m+=1
        return position
    def delEmptyRows(self):
        isEmpty=True
        for i in range((2**(self.rows-1))-1,(2**(self.rows))-2):
            if self.lst[i] == self.empty:
                continue
            else:
                isEmpty=False
                break;
        if isEmpty == True:
            for i in range((2**(self.rows))-2, (2**(self.rows-1))-2, -1):
                del self.lst[i]
            self.rows-=1
    def deletePoint(self, point: tuple):
        position=self.findPoint(point)
        if type(position) == str:
            return "Próba usunięcia nieistniejącego węzła!"
        if (self.lst[(position*2)+1] and self.lst[(position*2)+2]) == self.empty:
            self.lst[position] = self.empty
        if ((position*2)+1) >= len(self.lst):
            self.lst[position] = self.empty
        else:
            n=0
            dimension=0
            while n < position:
                if dimension == self.tree_size:
                    dimension=0
                n=(n*2)+2
                dimension=dimension+1
            if self.lst[(position*2)+2] != self.empty:
                k=(position*2)+2
                min_pos=self.findMin(k, dimension)
            elif self.lst[(position*2)+2] == self.empty and self.lst[(position*2)+1] != self.empty:
                k=(position*2)+1
                min_pos=self.findMin(k, dimension)
            self.lst[position] = self.lst[min_pos]
            self.lst[min_pos] = self.empty
            self.delEmptyRows()
    def showTree(self):
        curr_id=0
        x=1
        left_spc=0
        mid_spc=0
        for i in range(self.rows-1):
            left_spc=int((left_spc*2)+4)
            mid_spc+=8
        for i in range(self.rows):
            print(" "*(left_spc+(self.rows-i)), end=" ")
            for j in range(2**i):
                print(self.lst[curr_id], " "*(mid_spc+(2*(self.rows-i-1))), end=" ")
                curr_id+=1
            print("\n ")
            left_spc=int((left_spc-4)/2)
            mid_spc-=8
is_created=False
while is_created == False:
    p=input('Podaj pierwszy węzeł drzewa: ')
    p=tuple(int(x) for x in p.split(","))
    if len(p) <= 0:
        print("Błąd. Drzewo nie może być 0-wymiarowe!")
    else:
        is_created=True
t=KDTree(len(p))
t.insertPoint(p)
choice=0
while choice != 4:
    t.showTree()
    print("\n1: Dodaj węzeł.\n2: Znajdź węzeł.\n3: Usuń węzeł.\n4: Wyjdź\n>>", end=" ")
    try:
        choice=int(input())
    except:
        print("Liczba powinna być liczbą całkowitą!")
    if choice == 1 or choice == 2 or choice == 3:
        p=input("Podaj węzeł: ")
        if choice == 1:
            p=tuple(int(x) for x in p.split(","))
            t.insertPoint(p)
        elif choice == 2:
            p=tuple(int(x) for x in p.split(","))
            print(f"Węzeł {p} znajduje się na pozycji: {t.findPoint(p)+1}\n")
        elif choice == 3:
            p=tuple(int(x) for x in p.split(","))
            t.deletePoint(p)
    elif choice < 1 or choice > 4:
        print("Podano złą wartość!")