#include <iostream>
using namespace std;
void binarna(int x)
{
	int z=2, p=0;
	while(z<=x)
	{
		z*=2;
		p++;
	}
	int* tab=new int[p];
	for(int i=0; i<=p; i++)
	{
		tab[i]=x%2;
		x/=2;
	}
	cout << "Liczba w systemie binarnym: ";
	for(int i=p; i>=0; i--)
	{
		cout << tab[i];
	}
	cout << "\n";
	delete[] tab;
}
void szesnastkowa(int x)
{
	int z=16, p=0;
	while(z<x)
	{
		z*=16;
		p++;
	}
	int* tab=new int[p];
	for(int i=0; i<=p; i++)
	{
		tab[i]=x%16;
		x/=16;
	}
	cout << "Liczba w systemie szesnastkowym: ";
	for(int i=p; i>=0; i--)
	{
		switch(tab[i])
		{
			case 10:
				cout << "A"; break;
			case 11:
				cout << "B"; break;
			case 12:
				cout << "C"; break;
			case 13:
				cout << "D"; break;
			case 14:
				cout << "E"; break;
			case 15:
				cout << "F"; break;
			default:
				cout << tab[i]; break;	
		}
	}
	delete[] tab;	
}
int main()
{
    int a;
	cout << "Podaj liczbe: ";
	cin >> a;
	while(!cin.good() || a<0)
	{
		cin.clear();
		cin.sync();
		cout << "Blad wejscia. Podaj dodatnia liczbe calkowita:" << endl;
		cin >> a;
	}
    binarna(a);
    szesnastkowa(a);
}
