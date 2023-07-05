from functools import lru_cache, cached_property
from datetime import datetime

def fib(i: int):
    if i == 1:
        return 0
    elif i == 2:
        return 1
    else:
        return fib(i-1)+fib(i-2)

@lru_cache(maxsize=100)
def fastFib(i: int):
    if i == 1:
        return 0
    elif i == 2:
        return 1
    else:
        return fib(i-1)+fib(i-2)

def compare(i: int):
    print(f"{i}-ty wyraz ciągu Fibbonacciego:")
    t1 = datetime.now()
    print(f"Bez cache'owania: {fib(i)}")
    t2 = datetime.now()
    print(f"czas: {(t2 - t1).total_seconds()}")
    t1 = datetime.now()
    print(f"Z cache'owaniem: {fastFib(i)}")
    t2 = datetime.now()
    print(f"czas: {(t2 - t1).total_seconds()}")

compare(36)