#include <iostream>
#include <time.h>
using namespace std;
void bubble_sort(int *tab, int n);
void quick_sort(int *tab, int indexl, int indexr);
void insertion_sort(int *tab, int n);
void print_results(double t1, double t2, double t3, double t4, double t5, double t6);
int main()
{
	double tb1, tb2, tq1, tq2, ti1, ti2;
	clock_t start, stop;
	srand(time(NULL));
	int *arrb1=new int [100];
	int *arrb2=new int [10000];
	int *arrq1=new int [100];
	int *arrq2=new int [10000];
	int *arri1=new int [100];
	int *arri2=new int [10000];
	for(int i=0; i<100; i++)
	{
		arrb1[i]=rand()%100000+1;
		arrq1[i]=arrb1[i];
		arri1[i]=arrb1[i];
	}
	for(int i=0; i<10000; i++)
	{
		arrb2[i]=rand()%100000+1;
		arrq2[i]=arrb2[i];
		arri2[i]=arrb2[i];
	}
	start=clock();
	bubble_sort(arrb1, 100);
	stop=clock();
	tb1=(double)(stop-start)/CLOCKS_PER_SEC;
	delete [] arrb1;
	start=clock();
	bubble_sort(arrb2, 10000);
	stop=clock();
	tb2=(double)(stop-start)/CLOCKS_PER_SEC;
	delete [] arrb2;
	start=clock();
	quick_sort(arrq1, 0, 99);
	stop=clock();
	tq1=(double)(stop-start)/CLOCKS_PER_SEC;
	delete [] arrq1;
	start=clock();
	quick_sort(arrq2, 0, 9999);
	stop=clock();
	tq2=(double)(stop-start)/CLOCKS_PER_SEC;
	delete [] arrq2;
	start=clock();
	insertion_sort(arri1, 100);
	stop=clock();
	ti1=(double)(stop-start)/CLOCKS_PER_SEC;
	delete [] arri1;
	start=clock();
	insertion_sort(arri2, 10000);
	stop=clock();
	ti2=(double)(stop-start)/CLOCKS_PER_SEC;
	delete [] arri2;
	print_results(tb1, tb2, tq1, tq2, ti1, ti2);
}
void bubble_sort(int *tab, int n)
{
	int buf;
	for(int i=1; i<n; i++)
	{
		for(int j=0; j<(n-1); j++)
		{
			if(tab[j]>tab[j+1])
			{
				buf=tab[j];
				tab[j]=tab[j+1];
				tab[j+1]=buf;
			}
		}
	}
}
void quick_sort(int *tab, int indexl, int indexr)
{
	int v=tab[(indexl+indexr)/2];
	int i=indexl, j=indexr, x;
	do
	{
		while(tab[i]<v)
		{
			i++;
		}
		while(tab[j]>v)
		{
			j--;
		}
		if(i<=j)
		{
			x=tab[i];
			tab[i]=tab[j];
			tab[j]=x;
			i++;
			j--;
		}
	}
	while(i<=j);
	if(j>indexl)
	{
		quick_sort(tab, indexl, j);
	}
	if(i<indexr)
	{
		quick_sort(tab, i, indexr);
	}
}
void insertion_sort(int *tab, int n)
{
	int j, key;
	for(int i=1; i<n; i++)
	{
		key=tab[i];
		j=i-1;
		while(j>=0 && tab[j]>key)
		{
			tab[j+1]=tab[j];
			j--;
		}
		tab[j+1]=key;
	}
}
void print_results(double t1, double t2, double t3, double t4, double t5, double t6)
{
	cout << "\t\tWyniki sortowania:\n\n";
	cout << "\t\t  Bubble Sort\t Quick Sort\t Insertion Sort\n";
	cout << "100 elementow:\t     " << t1 << "s\t\t    " << t3 << "s\t\t    " << t5 <<"s\n";
	cout << "10 000 elementow:    " << t2 << "s\t    " << t4 << "s\t    " << t6 << "s\n";
}
