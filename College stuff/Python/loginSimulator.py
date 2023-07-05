import shelve, hashlib, binascii
from os import urandom
def hashPassword(password):
    salt = hashlib.sha256(urandom(60)).hexdigest().encode('ascii')
    pwdhash = hashlib.pbkdf2_hmac('sha512', password.encode('utf-8'), salt, 100000)
    pwdhash = binascii.hexlify(pwdhash)
    return [salt.decode('ascii'), pwdhash.decode('ascii')]    #używamy innego salta dla każdego użytkownika (bezpieczniej)

def userHandling(func):
    def checkLogin():
        login = input("Podaj login: ")
        doesNotExist = True
        shelf = shelve.open('passwords', 'c')
        for i in shelf:
            if i == login:
                doesNotExist = False
                break
        shelf.close()
        return func(login, doesNotExist)
    return checkLogin()

def createUser(login, doesNotExist):
    if doesNotExist is True:
        password = input("Podaj hasło: ")
        shelf = shelve.open('passwords', 'c')
        shelf[login] = hashPassword(password)
        shelf.close()
        print("Konto utworzone!\n")
    else:
        print("Konto o takim loginie już istnieje!\n")

def authUser(login, doesNotExist):
    if doesNotExist is True:
        print("Nie istnieje konto o takim loginie!\n")
    else:
        password = input("Podaj hasło: ")
        shelf = shelve.open('passwords', 'c')
        salt = shelf[login][0].encode('ascii')
        hash = hashlib.pbkdf2_hmac('sha512', password.encode('utf-8'), salt, 100000)
        hash = binascii.hexlify(hash)
        if hash == shelf[login][1].encode('ascii'):
            print("Zostałeś zalogowany!\n")
        else:
            print("Hasło jest błędne!\n")
        shelf.close()

def showUsers():
    shelf = shelve.open('passwords', 'c')
    for i in shelf:
        print(i, shelf[i])
    shelf.close()

choice = 0
while choice != 4:
    print("1. Utwórz konto.\n2. Zaloguj się.\n3. Wyświetl konta.\n4. Wyjdź.\n>>", end=" ")
    try:
        choice=int(input())
        if choice == 1:
            userHandling(createUser)
        elif choice == 2:
            userHandling(authUser)
        elif choice == 3:
            showUsers()
        elif choice < 1 or choice > 4:
            print("Podaj wartość od 1 do 4!")
    except:
        print("Wprowadzono błędną wartość!")