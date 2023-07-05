#pragma once
#include "Stanowisko.h"
class BlackJack : public Stanowisko
{
	public: 
		void obstaw(Gracz &gracz);
		int losujKarte();
};
