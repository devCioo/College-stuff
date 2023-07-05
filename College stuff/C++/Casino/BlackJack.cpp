#include <iostream>
#include <cstdlib>
#include <ctime>
#include <windows.h>
#include "BlackJack.h"

void BlackJack::obstaw(Gracz &gracz)
{
	string kk="Karty krupiera: "; 
	string kg="Twoje karty: ";
	int los, wk=0, wg=0, wybor;
	system("cls");
	gracz.status();
	cout << "\n\nKrupier rozdaje karty...";
	Sleep(3000);
	system("cls");
	for(int i=0; i<2; i++)
	{
		los=losujKarte();
		if(los==1)
			los=11;
		wk+=los;
		if(i==0)
			kk=kk+to_string(los)+" ";
		else
			kk=kk+"? ";
	}
	for(int i=0; i<2; i++)
	{
		los=losujKarte();
		if(los==1)
		{
			cout << "Wylosowa\210e\230 asa! Jak\245 chcesz przypisa\206 mu warto\230\206? (1/11)\n>> ";
			cin >> los;
			while(!cin.good() || (los!=1 && los!=11))
			{
				cin.clear();
				cin.sync();
				cout << "Podaj warto\230\206 1 lub 11!";
				cin >> los;
			}
		}
		wg+=los;
		kg=kg+to_string(los)+" ";
	}
	system("cls");
	gracz.status();
	cout << "\n\n" << kg << "\t\t" << kk;
	cout << "\nKrupier dobiera sobie karty...";
	Sleep(2500);
	do
	{
		los=losujKarte();
		wk+=los;
		kk=kk+to_string(los)+" ";
	}
	while(wk<17);
	system("cls");
	gracz.status();
	cout << "\n\n" << kg << "\t\t" << kk;
	do
	{
		if(wk>21)
			break;
		cout << "\n\nCo chcesz zrobi\206?";
		cout << "\n1. Dobra\206 kart\251.";
		cout << "\n2. Passuje\n>> ";
		cin >> wybor;
		while(!cin.good() || (wybor!=1 && wybor!=2))
		{
			cin.clear();
			cin.sync();
			cout << "Podaj warto\230\206 1 lub 2!";
			cin >> wybor;
		}
		switch(wybor)
		{
			case 1:
				{
					los=losujKarte();
					if(los==1)
					{
						cout << "Wylosowa\210e\230 asa! Jak\245 chcesz przypisa\206 mu warto\230\206? (1/11)\n>> ";
						cin >> los;
						while(!cin.good() || (los!=1 && los!=11))
						{
							cin.clear();
							cin.sync();
							cout << "Podaj warto\230\206 1 lub 11!";
							cin >> los;
						}
					}
					wg+=los;
					kg=kg+to_string(los)+" ";
					system("cls");
					gracz.status();
					cout << "\n\n" << kg << "\t\t" << kk;
					break;
				}
			case 2:
				{
					system("cls");
					gracz.status();
					break;
				}
		}
	}
	while(wg<21 && wybor!=2);
	cout << "\n\nTw\242j wynik: " << wg << "\t\tWynik krupiera: " << wk;
	if(wk>21)
	{
		cout << "\nFura! Krupier przegrywa!";
		gracz.setMoney(gracz.getMoney()+(2*getZaklad()));
	}
	else if(wg>21)
	{
		cout << "\nFura! Przegrywasz.";
	}
	else if(wg==21)
	{
		if(wk!=21)
		{
			cout << "\nOczko! Wygrywasz!";
			gracz.setMoney(gracz.getMoney()+(2*getZaklad()));
		}
		else
		{
			cout << "\nRemis!";
			gracz.setMoney(gracz.getMoney()+getZaklad());
		}
	}
	else if(wg==wk)
	{
		cout << "\nRemis!";
		gracz.setMoney(gracz.getMoney()+getZaklad());
	}
	else if(wg<=21 && wk<=21)
	{
		if(wg>wk)
		{
			cout << "\nWygrywasz!";
			gracz.setMoney(gracz.getMoney()+(2*getZaklad()));
		}
		if(wg<wk)
		{
			cout << "\nPrzegrywasz!";
		}
	}
	Sleep(1000);
	replay(gracz);
}

int BlackJack::losujKarte()
{
	int x=rand()%13+1;
	switch(x)
	{
		case 11:
		case 12:
		case 13:
			x=10;
			break;
		default:
			break;
	}
	return x;
}
