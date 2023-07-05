#include <iostream>
#include <cstring>
using namespace std;
int main()
{
	string ciag, szyfr;
	int wybor;
	cout << "Podaj ciag:";
	getline(cin, ciag);
	cout << "Wybierz szyfr:";
	cin >> wybor;
	switch(wybor)
	{
		case 1:
		{
			szyfr="gaderypoluki";
			break;
		}
		case 2:
		{
			szyfr="politykarenu";
			break;
		}
		case 3:
		{
			szyfr="kaceminutowy";
			break;
		}
		default:
			cout << "Podales zly numer szyfru!";
		}
	for(int i=0; i<ciag.length(); i++)
	{
		for(int j=0; j<12; j++)
		{
			if(ciag[i]==szyfr[j])
			{
				if(j%2==0)
				{
					ciag[i]=szyfr[j+1];
					break;
				}
				else if(j%2==1)
				{
					ciag[i]=szyfr[j-1];
					break;
				}
			}
		}
	}
	cout << ciag;
}
