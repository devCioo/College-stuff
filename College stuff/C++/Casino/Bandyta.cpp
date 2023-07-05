#include <iostream>
#include <cstdlib>
#include <ctime>
#include <windows.h>
#include "Bandyta.h"

void Bandyta::obstaw(Gracz &gracz)
{
	char a, b, c;
	system("cls");
	gracz.status();
	cout << "\n\nBandyta losuje symbole...";
	Sleep(2000);
	for(int i=0; i<10; i++)
	{
		Sleep(350);
		system("cls");
		gracz.status();
		a=char(rand()%5+2);
		cout << "\n\n\t" << a;
	}
	for(int i=0; i<10; i++)
	{
		Sleep(350);
		system("cls");
		gracz.status();
		b=char(rand()%5+2);
		cout << "\n\n\t" << a << "  " << b;
	}
	for(int i=0; i<10; i++)
	{
		Sleep(350);
		system("cls");
		gracz.status();
		c=char(rand()%5+2);
		cout << "\n\n\t" << a << "  " << b << "  " << c;
	}
	Sleep(1000);
	if(a==b && b==c)
	{
		if(a==char(2))
		{
			cout << "\n\nWygra\210e\230 25-krotno\230\206 zak\210adu!";
			gracz.setMoney(gracz.getMoney()+(25*getZaklad()));
		}
		else
		{
			cout << "\n\nWygra\210e\230 8-krotno\230\206 zak\210adu!";
			gracz.setMoney(gracz.getMoney()+(8*getZaklad()));
		}
	}
	else
	{
		cout << "\n\nNiestety, nie uda\210o Ci si\251 wygra\206.";
	}
	replay(gracz);
}
