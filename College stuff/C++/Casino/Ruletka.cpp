#include <iostream>
#include <cstdlib>
#include <ctime>
#include <windows.h>
#include "Ruletka.h"

void Ruletka::obstaw(Gracz &gracz)
{
	int x;
	char kolor, los;
	system("cls");
	gracz.status();
	cout << "\n\nWybierz kolor (r-czerwony, b-czarny, g-zielony)\n";
	cout << ">> ";
	cin >> kolor;
	while(kolor!='r' && kolor!='b' && kolor!='g')
	{
		cin.clear();
		cin.sync();
		cout << "Podaj warto\230\206 r, b lub g!\n>> ";
		cin >> kolor;
	}
	system("cls");
	gracz.status();
	cout << "\n\nRuletka losuje kolor...";
	Sleep(4000);
	system("cls");
	gracz.status();
	x=rand()%37;
	if(x%2!=0)
	{
		los='r';
		cout << "\n\nWylosowano kolor czerwony!";
	}
	else if(x==0)
	{
		los='g';
		cout << "\n\nWylosowano kolor zielony!";
	}
	else
	{
		los='b';
		cout << "\n\nWylosowano kolor czarny!";
	}
	if(kolor==los)
	{
		if(kolor=='g')
		{
			cout << "\nWygra\210e\230 14-krotno\230\206 zak\210adu!";
			gracz.setMoney(gracz.getMoney()+(14*getZaklad()));
		}
		else
		{
			cout << "\nWygrywasz 2-krotno\230\206 zak\210adu!";
			gracz.setMoney(gracz.getMoney()+(2*getZaklad()));
		}
	}
	else
	{
		cout << "\n\nNiestety, nie uda\210o Ci si\251 wygra\206.";
	}
	replay(gracz);
}
