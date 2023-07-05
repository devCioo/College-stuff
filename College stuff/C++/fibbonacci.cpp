#include <iostream>
using namespace std;
int fib(int x)
{
	int a=0, b=1;
	for(int i=0; i<x; i++)
	{
		b+=a;
		a=b-a;
	}
	return a;
}
int main()
{
	int n;
	do
	{
		cin.clear();
		cin.sync();
		cout << "Podaj n-ty wyraz ciagu Fibbonacciego:";
		cin >> n;
	}
	while(!cin.good());
	cout << fib(n);
}
