Progr           segment
        assume  cs:Progr, ds:dane, ss:stosik
start:
                mov     ax,stosik			;nie ustawiamy adresu segmentu danych po to, aby domyslnie 
                mov     ss,ax				;wskazywal na PSP, czyli program segment prefix, w ktorym
                mov     ax,offset szczyt	;jest pamiec linii komend.
                mov     sp,ax
;/////////////////////////////////////////////////////
                mov     cx,0
                mov     cl,byte ptr ds:[080h] 	;przeniesienie do CL, bajtu danych znajdujacych sie pod
												;adresem ds:[080h], jest to bajt z PSP, przechowujacy
												;ilosc znakow podanych w wierszu polecen.
				cmp     cl,0					;sprawdzamy czy parametr wogole zostal podany
                jne     wczytaj					;skok do wczytywania nazwy pliku, jesli parametr istnieje.
                mov     ax,seg dane				;wyswietlamy powiadomienie o bledzie jesli wywolane
                mov     ds,ax					;polecenie nie posiadalo parametru, przestawiamy segment
                mov     dx,offset blad1			;danych na obszar <dane> i offset na blad1.
                mov     ax,0900h
                int     21h
                jmp     koniec_programu			;skok do konca programu: int 21h, ah=4ch
wczytaj:
                dec     cl						;zmniejszamy cl o 1, bo zawiera on dlugosc nazwy pliku wraz
												;ze spacja, CL jest naszym licznikiem petli.
                mov     si,082h					;ustawiamy SI na pierwszy znak nazwy pliku, 80h - dlugosc
												;w bajtach, 81h - spacja, 82h - pierwszy znak.
                mov     ax,seg nazwa_pliku		;ustawiamy ES na nazwe pliku jako bufor i DI jako offset
                mov     es,ax					;tej nazwy.
                mov     di,offset nazwa_pliku
zapisz:
                mov     al,byte ptr ds:[si]		;przenosimy do AL, bajt zawierajacy znak nazwy pliku tekstowego.
                mov     byte ptr es:[di],al		;przenosimy do tablicy nazwa_pliku, znak wyluskany z konsoli.
                inc     si						;przestawiamy oba liczniki na kolejne znaki.
                inc     di
                loop    zapisz
                mov     byte ptr es:[di],'$'	;ustawiamy bajt za ostatnim znakiem nazwy pliku na $ w celu
												;zakonczenia napisu.
otworz_plik:
                mov     ax,seg nazwa_pliku		;przestawienie rejestrów DS:DX na adres, pod ktorym znajduje
                mov     ds,ax					;sie nazwa pliku, do ktorego chcemy otworzyc dojscie.
                mov     dx,offset nazwa_pliku	;int 21h, AH=3dh to przerwanie otwierające dojscie do pliku.
                mov     cx,0					;w przypadku niepowodzenia, ustawia ono znacznik C oraz
                mov     ax,0					;wstawia do AX, kod bledu tego niepowodzenia.
                mov     ah,3dh					;jesli plik uda sie otworzyc, znacznik C jest nieustawiony
                int     21h						;a w AX znajduje sie numer dojscia (uchwyt do pliku).
                jnc     odczyt					;skok do odczytania zawartosci, jesli plik udalo sie otworzyc.
                mov     dx,offset blad2
                mov     ax,0900h
                int     21h
                jmp     koniec_programu
odczyt:
                mov     bx,ax   				;int 21h, AH=42h to przerwanie ustawiajace wskaznik w pliku
                mov     cx,0					;w AL podajemy sposob przesuwania (2-od konca pliku), w BX
                mov     dx,0					;podajemy uchwyt do pliku, CX i DX musza byc wyzerowane.
                mov     ax,4202h				;po zakonczeniu, rozmiar pliku (w bajtach) znajduje sie w
                int     21h						;rejestrach DX:AX
                mov     rozmiar_pliku,ax
                mov     dx,0					;ponowne wywolanie 42h, ktore ustawi wskaznik na poczatek
                mov     ax,4200h				;w celu umozliwienia pozniejszego odczytu
                int     21h
                mov     dx,offset tab			;int 21h, AH=3fh to przerwanie czytajace z pliku
                mov     cx,rozmiar_pliku		;w BX podajemy uchwyt, w CX liczbe bajtow do przeczytania
                mov     ax,3f00h				;a w DS:DX adres bufora, czyli miejsca gdzie zapisac dane.
                int     21h
                mov     ax,3e00h				;int 21h, AH=3eh to przerwanie zamykajace dojscie do pliku
                int     21h						;uchwyt pliku do zamkniecia podajemy w BX

                mov     di,0
                mov     si,6					;ustawiamy w SI domyslna wartosc oktawy
granie_melodii:
                push    cx						;format nut:
                mov     dl,tab[di]				;[dlugosc][oktawa][wartosc]
                mov     ax,0200h				;dlugosci:
                int     21h						;['^','*','%','&','@']
                mov     ax,0					;oktawy:
                call    grajDzwiek				;['1','2','3','4','5','6','7']
                pop     cx						;wartosci:
                inc     di						;['c','D','E','F','G','A','H','C','s','d','e','f','g','a']
                loop    granie_melodii			;pauzy:
;////////////////////////////////////////////////[' ','.',',',':',';']
koniec_programu:
                mov     ah,4ch					;czytanie z pliku wywołujemy wpisując w konsolę
                mov     al,0					; [nazwa_tego_pliku][nazwa_pliku_z_melodią].txt
                int     21h						; przykładowo:
grajDzwiek:										;>playSong piosenka.txt
                cmp     dl,'$'
                je      koniec_programu
;sprawdzenie dlugosci/////////////////////////////////////////////////////////
        sprDlug:
                cmp     dl,'^'
                je      cala_nuta
                cmp     dl,'*'
                je      polnuta
                cmp     dl,'%'
                je      cwiercnuta
                cmp     dl,'&'
                je      osemka
                cmp     dl,'@'
                je      szesnastka

                jmp     sprOkt
;dlugosci dzwiekow////////////////////////////////////////////////////////////
                cala_nuta:
                mov     dlNuty1,0fh
                mov     dlNuty2,4240h
                ret
                polnuta:
                mov     dlNuty1,07h
                mov     dlNuty2,0a120h
                ret
                cwiercnuta:
                mov     dlNuty1,03h
                mov     dlNuty2,0d090h
                ret
                osemka:
                mov     dlNuty1,01h
                mov     dlNuty2,0e848h
                ret
                szesnastka:
                mov     dlNuty1,00h
                mov     dlNuty2,0f424h
                ret
;sprawdzenie oktawy///////////////////////////////////////////////////////////
         sprOkt:
                cmp     dl,'1'
                je      pierwsza
                cmp     dl,'2'
                je      druga
                cmp     dl,'3'
                je      trzecia
                cmp     dl,'4'
                je      czwarta
                cmp     dl,'5'
                je      piata
                cmp     dl,'6'
                je      szosta
                cmp     dl,'7'
                je      siodma

                jmp     sprNaz
;wartosci oktaw//////////////////////////////////////////////////////////////
                pierwsza:
                mov     si,0
                ret
                druga:
                mov     si,2
                ret
                trzecia:
                mov     si,4
                ret
                czwarta:
                mov     si,6
                ret
                piata:
                mov     si,8
                ret
                szosta:
                mov     si,10
                ret
                siodma:
                mov     si,12
                ret
;sprawdzenie nazwy////////////////////////////////////////////////////////////
         sprNaz:
                cmp     dl,'c'
                je      soundCLow
                cmp     dl,'D'
                je      soundD
                cmp     dl,'E'
                je      soundE
                cmp     dl,'F'
                je      soundF
                cmp     dl,'G'
                je      soundG
                cmp     dl,'A'
                je      soundA
                cmp     dl,'H'
                je      soundH
                cmp     dl,'C'
                je      soundCHigh
                cmp     dl,'s'
                je      soundCis
                cmp     dl,'d'
                je      soundDis
                cmp     dl,'e'
                je      soundEis
                cmp     dl,'f'
                je      soundFis
                cmp     dl,'g'
                je      soundGis
                cmp     dl,'a'
                je      soundAis

				jmp		sprPauza
;wartosci dzwiekow wraz z oktawami////////////////////////////////////////////
                soundCLow:
                mov     ax,nutyCn[si]
                jmp     graj
                soundD:
                mov     ax,nutyD[si]
                jmp     graj
                soundE:
                mov     ax,nutyE[si]
                jmp     graj
                soundF:
                mov     ax,nutyF[si]
                jmp     graj
                soundG:
                mov     ax,nutyG[si]
                jmp     graj
                soundA:
                mov     ax,nutyA[si]
                jmp     graj
                soundH:
                mov     ax,nutyH[si]
                jmp     graj
                soundCHigh:
                mov     ax,nutyCw[si]
                jmp     graj
                soundCis:
                mov     ax,nutyCis[si]
                jmp     graj
                soundDis:
                mov     ax,nutyDis[si]
                jmp     graj
                soundEis:
                mov     ax,nutyEis[si]
                jmp     graj
                soundFis:
                mov     ax,nutyFis[si]
                jmp     graj
                soundGis:
                mov     ax,nutyGis[si]
                jmp     graj
                soundAis:
                mov     ax,nutyAis[si]
                jmp     graj
;sprawdzenie pauzy////////////////////////////////////////////////////////////
		sprPauza:
				cmp		dl,' '	
				je		pauzaCala
				cmp		dl,'.'
				je		pauzaPol
				cmp		dl,','
				je		pauzaCwier
				cmp		dl,':'
				je		pauzaOsiem
				cmp		dl,';'
				je		pauzaSzesn
				
				ret
;/////////////////////////////////////////////////////////////////////////////
				pauzaCala:
				mov		dlPauzy1,0fh
				mov		dlPauzy2,4240h
				jmp		pauza
				pauzaPol:
				mov		dlPauzy1,07h
				mov		dlPauzy2,0a120h
				jmp		pauza
				pauzaCwier:
				mov		dlPauzy1,03h
				mov		dlPauzy2,0d090h
				jmp		pauza
				pauzaOsiem:
				mov		dlPauzy1,01h
				mov		dlPauzy2,0e848h
				jmp		pauza
				pauzaSzesn:
				mov		dlPauzy1,00h
				mov		dlPauzy2,0f424h
				jmp		pauza
;odegranie dzwieku////////////////////////////////////////////////////////////
           graj:
				out     42h,al
                mov     al,ah
                out     42h,al
				
                in      al,61h
                or      al,3
                out     61h,al

                mov     cx,dlNuty1
                mov     dx,dlNuty2
                mov     ah,86h
                int     15h

                in      al,61h
                and     al,not 3
                out     61h,al
				
				call	pauza
                mov     dlNuty1,0fh
                mov     dlNuty2,4240h
				mov		dlPauzy1,00h
				mov		dlPauzy2,0f424h
                mov     si,6
                ret
		  pauza:
				mov		cx,dlPauzy1
				mov		dx,dlPauzy2
				mov		ah,86h
				int		15h
				ret
Progr           ends

dane            segment
    nazwa_pliku     db 50 dup(0)
    rozmiar_pliku   dw ?
    tab             db 10000 dup('$')
    dlNuty1         dw 0fh
    dlNuty2         dw 4240h
	dlPauzy1		dw 00h
	dlPauzy2		dw 0f424h
    nutyCn          dw (1193181/33),(1193181/65),(1193181/131),(1193181/262),(1193181/523),(1193181/1047),(1193181/2093)
    nutyD           dw (1193181/37),(1193181/73),(1193181/147),(1193181/294),(1193181/587),(1193181/1175),(1193181/2349)
    nutyE           dw (1193181/41),(1193181/82),(1193181/165),(1193181/330),(1193181/659),(1193181/1319),(1193181/2637)
    nutyF           dw (1193181/44),(1193181/87),(1193181/175),(1193181/349),(1193181/698),(1193181/1397),(1193181/2794)
    nutyG           dw (1193181/49),(1193181/98),(1193181/196),(1193181/392),(1193181/784),(1193181/1568),(1193181/3136)
    nutyA           dw (1193181/55),(1193181/110),(1193181/220),(1193181/440),(1193181/880),(1193181/1760),(1193181/3520)
    nutyH           dw (1193181/62),(1193181/123),(1193181/247),(1193181/494),(1193181/988),(1193181/1976),(1193181/3951)
    nutyCw          dw (1193181/65),(1193181/131),(1193181/262),(1193181/523),(1193181/1047),(1193181/2093),(1193181/4186)
    nutyCis         dw (1193181/35),(1193181/69),(1193181/139),(1193181/277),(1193181/554),(1193181/1109),(1193181/2217)
    nutyDis         dw (1193181/39),(1193181/77),(1193181/156),(1193181/311),(1193181/622),(1193181/1245),(1193181/2489)
    nutyEis         dw (1193181/42),(1193181/84),(1193181/170),(1193181/339),(1193181/678),(1193181/1357),(1193181/2714)
    nutyFis         dw (1193181/46),(1193181/92),(1193181/185),(1193181/370),(1193181/740),(1193181/1480),(1193181/2960)
    nutyGis         dw (1193181/52),(1193181/104),(1193181/208),(1193181/415),(1193181/831),(1193181/1661),(1193181/3322)
    nutyAis         dw (1193181/58),(1193181/116),(1193181/233),(1193181/466),(1193181/932),(1193181/1865),(1193181/3729)
    blad1           db 10,13,"Nie podano pliku.$"
    blad2           db 10,13,"Blad w odczycie pliku.$"
dane            ends

stosik          segment
                dw    100h dup(0)
szczyt          Label word
stosik          ends

end start