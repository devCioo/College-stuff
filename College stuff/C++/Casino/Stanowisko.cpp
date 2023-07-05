#include <iostream>
#include <windows.h>
#include "Stanowisko.h"
using namespace std;

int Stanowisko::getZaklad()
{
	return zaklad;
}
void Stanowisko::setZaklad(int zaklad)
{
	Stanowisko::zaklad=zaklad;
}

void Stanowisko::showStanowisko(Gracz &gracz)
{
	int zaklad;
	char choice;
	system("cls");
	gracz.status();
	cout << "\n\nPodaj warto\230\206 zak\210adu:\n>> ";
	cin >> zaklad;
	while(!cin.good() || zaklad>gracz.getMoney() || zaklad<=0)
	{
		if(!cin.good())
			cout << "Podaj warto\230\206 liczbow\245!";
		else if(zaklad>gracz.getMoney())
			cout << "Nie masz tyle pieni\251dzy!";
		else if(zaklad<=0)
			cout << "Warto\230\206 zak\210adu musi by\206 wi\251ksza od 0!";
		cout << "\n>> ";
		cin.clear();
		cin.sync();
		cin >> zaklad;
	}
	setZaklad(zaklad);
	system("cls");
	gracz.status();
	gracz.setMoney(gracz.getMoney()-zaklad);
	obstaw(gracz);
}

void Stanowisko::replay(Gracz &gracz)
{
	Sleep(2500);
	system("cls");
	if(gracz.getMoney()>0)
	{
		gracz.status();
		char ponow;
		cout << "\n\nCzy chcesz zagra\206 ponownie? (t-tak/n-nie)\n";
		cout << ">> ";
		cin >> ponow;
		while(ponow!='t' && ponow!='n')
		{
			cin.clear();
			cin.sync();
			cout << "Podaj warto\230\206 t lub n!\n>> ";
			cin >> ponow;
		}
		if(ponow=='t')
			showStanowisko(gracz);
		else
			system("cls");
	}
}
