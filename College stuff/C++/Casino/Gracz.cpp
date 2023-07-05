#include <iostream>
#include <cstdlib>
#include <ctime>
#include "Gracz.h"
using namespace std;

Gracz::Gracz()
{
	Gracz::name="Test";
	srand(time(NULL));
	Gracz::money=rand()%1000+1;
}

int Gracz::getMoney()
{
	return money;
}
void Gracz::setMoney(int money)
{
	Gracz::money=money;
}

string Gracz::getName()
{
	return name;
}

void Gracz::setName(string name)
{
	Gracz::name=name;
}

void Gracz::status()
{
	cout << "Gracz: " << Gracz::name << "\t\tBalans: " << Gracz::money;
}
