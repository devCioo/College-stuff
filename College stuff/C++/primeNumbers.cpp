#include <iostream>
using namespace std;
int main()
{
	for(int i=2; i<=100; i++)
	{
		if((i%2!=0 && i%3!=0 && i%5!=0 && i%7!=0) || (i==2 || i==3 || i==5 || i==7))
		{
			cout << i << endl;
		}
	}
	return 0;
}
