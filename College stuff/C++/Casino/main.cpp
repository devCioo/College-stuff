#include <iostream>
#include <cstdlib>
#include <fstream>
#include <windows.h>
#include "Gracz.h"
#include "BlackJack.h"
#include "Bandyta.h"
#include "Ruletka.h"
using namespace std;

void sortujGraczy();
int main() 
{
	int x, n;
	string nazwa;
	ofstream zapis;
	cout << "Podaj swoj\245 nazw\251: ";
	getline(cin,nazwa);
	cout << "Witaj " << nazwa << "!\n";
	Sleep(1000);
	system("cls");
	cout << "Losuj\251 dla Ciebie sum\251 pieni\251dzy...";
	Sleep(3000);
	system("cls");
	Gracz* g=new Gracz;
	g->setName(nazwa);
	cout << "Dostajesz na start: " << g->getMoney();
	Sleep(1500);
	system("cls");
	do
	{
		g->status();
		cout << "\n\nCo chcesz zrobi\206?" << endl;
		cout << "1. BlackJack\n";
		cout << "2. Jednor\251ki bandyta\n";
		cout << "3. Ruletka\n";
		cout << "4. Wyjd\253.\n";
		cout << ">> ";
		cin >> x;
		while(!cin.good())
		{
			cin.clear();
			cin.sync();
			cout << "Podaj poprawny numer opcji 1-4!\n>> ";
			cin >> x;		
		}
		switch(x)
		{
			case 1:
				{
					BlackJack* o=new BlackJack;
					o->showStanowisko(*g);
					delete o;
					break;
				}
			case 2:
				{
					Bandyta* b=new Bandyta;
					b->showStanowisko(*g);
					delete b;
					break;
				}
			case 3:
				{
					Ruletka* r=new Ruletka;
					r->showStanowisko(*g);
					delete r;
					break;
				}
			case 4:
				break;
			default:
				{
					cout << "Wybra\210e\230 z\210\245 opcj\251!";
					Sleep(1500);
					system("cls");	
				}
		}
	}
	while(g->getMoney()>0 && x!=4);
	if(g->getMoney()==0)
		cout << "Jeste\230 bankrutem!";
	zapis.open("sort.txt",ofstream::app);
	zapis << "\n" << g->getName() << "\t" << g->getMoney();
	zapis.close();
	sortujGraczy();
	delete g;
	return 0;
}

void sortujGraczy()
{
	int n;
	ifstream odczyt;
	ofstream zapis;
	string linia;
	string bufor1, bufor2;
	odczyt.open("sort.txt");
	do
	{
		odczyt >> linia;
		n++;
	}
	while(!odczyt.eof());
	odczyt.close();
	string tab[(n/2)][2];
	n=0;
	odczyt.open("sort.txt");
	do
	{
		for(int i=0; i<2; i++)
		{
			if(i==0)
				getline(odczyt, linia, '\t');
			if(i==1)
				getline(odczyt, linia);
			tab[n][i]=linia;
		}
		n++;
	}
	while(!odczyt.eof());
	odczyt.close();
	for(int i=0; i<n; i++)
	{
		for(int j=0; j<(n-1); j++)
		{
			if(stoi(tab[j][1])<stoi(tab[j+1][1]))
			{
				bufor1=tab[j][0];
				bufor2=tab[j][1];
				tab[j][0]=tab[j+1][0];
				tab[j][1]=tab[j+1][1];
				tab[j+1][0]=bufor1;
				tab[j+1][1]=bufor2;
			}
		}
	}
	zapis.open("top100.txt");
	for(int i=0; i<n; i++)
	{
		if(n==100)
			break;
		zapis << (i+1) << ". " << tab[i][0] << "\twynik: " << tab[i][1] << endl;
	}
	zapis.close();
}
