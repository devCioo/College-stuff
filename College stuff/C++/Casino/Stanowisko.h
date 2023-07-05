#pragma once
#include "Gracz.h"
class Stanowisko : public Gracz
{
	protected:
		int zaklad;
	public:
		int getZaklad();
		void setZaklad(int zaklad);
		void showStanowisko(Gracz &gracz);
		virtual void obstaw(Gracz &gracz)=0;
		void replay(Gracz &gracz);
};
