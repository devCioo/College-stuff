import random, math
pi=math.pi
t1,t2,t3=100,10000,1000000
def monte_carlo(trials):
    sum=0
    for i in range(trials):
        x=random.uniform(0,1)
        y=random.uniform(0,1)
        if (pow(x,2)+pow(y,2)<=1):
            sum+=1
    sum=(sum*4)/trials
    return sum
def mc_accuracy(sum):
    if sum<pi:
        acc=sum/pi
    if sum>pi:
        acc=pi/sum
    acc*=100
    acc=round(acc,2)
    return acc
x1,x2,x3=monte_carlo(t1),monte_carlo(t2),monte_carlo(t3)
print(f"Wartość \u03C0 dla {t1} prób: {x1}, dokładność: {mc_accuracy(x1)}%")
print(f"Wartość \u03C0 dla {t2} prób: {x2}, dokładność: {mc_accuracy(x2)}%")
print(f"Wartość \u03C0 dla {t3} prób: {x3}, dokładność: {mc_accuracy(x3)}%")