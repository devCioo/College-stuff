#pragma once
#include <string>
using namespace std;
class Gracz
{
	protected:
		string name="";
		int money;
	public:
		Gracz();
		int getMoney();
		void setMoney(int money);
		string getName();
		void setName(string name);
		void status();
};
