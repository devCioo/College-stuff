#include <iostream>
#include <cstdlib>
#include <time.h>
using namespace std;
int main()
{
	int x, a=0, k=0;
	int* tab=new int[100];
	srand(time(NULL));
	x=rand()%100+1;
	cout << "Strzelaj:";
	while(a!=x)
	{
		cin >> a;
		while(!cin.good())
		{
			cin.clear();
			cin.sync();
			cout << "Podaj liczbe calkowita:";
			cin >> a;
		}
		tab[k]=a;
		k++;
		if(a>x)
		{
			cout << "To za duzo! Sprobuj jeszcze raz:";
		}
		else if(a<x)
		{
            cout << "To za malo! Sprobuj jeszcze raz:";
		}
		else
		{
			cout << "\nBrawo! Zgadnales w " << k << " probie!";
		}
	}
	cout << "\nTwoje strzaly";
	for(int i=0; i<k; i++)
	{
		if(i==0)
			cout << ": " << tab[i];
		else
			cout << ", " << tab[i];
	}
	cout << "\r\b";
	delete[] tab;
	return 0;
}

