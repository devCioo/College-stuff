Progr           segment
                assume  cs:Progr, ds:dane, ss:stosik

start:          mov     ax,dane
                mov     ds,ax
                mov     ax,stosik
                mov     ss,ax
                mov     ax,offset szczyt
                mov     sp,ax
;/////////////////////////////////////////////////////
                mov     ah,0  ;przerwanie 10h z funkcja 00h - ustalenie
                mov     al,3  ;trybu wyswietlania, dla AL=3 jest to tryb
                int     10h   ;tekstowy 80x25 z 16 kolorami.
                lea     dx,ask ;przerwanie 21h z funkcja 09h - wyswietlenie
                mov     ah,9    ;lancucha zaczynajacego sie od adresu
                int     21h     ;podanego w DS:DX (DS jest ustawiony na
                                ;poczatek segmentu danych, DX to offset w
                                ;ktorym jest zmienna ask.
                mov     dl,offset max ;21h z funkcja 0ah - czytanie wiersza
                mov     ah,0ah ;az do wcisniecia klawisza Enter, do pierwszego
                int     21h ;bajtu bufora zaladowany jest max rozmiar lancucha
                            ;do drugiego dlugosc tego lancucha, a do reszty
                            ;po kolei kazdy ze znakow.
;string -> decimal
                mov     si,offset len ;wstawienie do si offsetu zmiennej len.
                mov     cl,[si] ;wyluskanie dlugosci napisu (licznik petli).
                mov     ch,0    ;zerowanie CH.
                inc     si      ;przestawienie si na pierwszy znak.
                mov     bp,10   ;ustawienie mnoznika na 10.
convert:        mov     ax,suma ;przeniesienie sumy do akumulatora.
                mul     bp      ;mnozenie akumulatora przez wskaznik bazy.
                jc      blad1   ;skok do bledu, jesli po mnozeniu razy 10
                                ;liczba jest wieksza niz 2^16-1.
                mov     suma,ax ;przeniesienie akumulatora do sumy.
                mov     ax,0    ;zerowanie AX.
                mov     al,[si] ;przeniesienie do AL znaku na ktory wskazuje
                                ;rejestr SI.
                sub     al,48   ;zamiana znaku zapisanego w ASCII na wartosc
                                ;dziesietna.
                cmp     al,10   ;porownanie czy jest to wartosc <0,9>
                jnc     blad2   ;skok do bledu, jesli wartosc byla spoza
                                ;zakresu.
                add     suma,ax ;dodanie liczby do sumy.
                jc      blad1   ;sprawdzenie czy po dodaniu wartosci, liczba
                                ;jest mniejsza niz 2^16, jesli nie - skok
                                ;do bledu.
                inc     si      ;przestawienie SI na kolejny znak
                loop    convert ;powtarzanie petli, dopoki CX!=0
;print_decimal
                lea     dx,print_dec ;zaladowanie adresu napisu
                mov     ax,0
                mov     ah,9
                int     21h    ;wywolanie int 21h 09h - drukowania napisu
                xor     bh,bh  ;wyzerowanie BH, xor porownuje pary bitow,
                               ;jesli sa rozne wstawia 1, jesli takie same 0
                mov     bl,len ;wstawienie do BL dlugosci napisu
                mov     znaki[bx],'$';zastapienie znaku Enter(0dh), znakiem
                                     ;konca napisu
                mov     dx,offset znaki ;zaladowanie tablicy znakow
                int     21h
;print_binary
                lea     dx,print_bin
                mov     ah,9
                int     21h
                mov     ah,2 ;ustawienie ah na 2
                mov     bx,suma ;skopiowanie sumy do BX
                mov     cx,16 ;ustawienie licznika petli na 16
printb:         mov     dl,'0';ustawienie znaku na 0 w ASCII
                rcl     bx,1 ;przesuniecie (obrot) bitowe BX w lewo z
                             ;uwzglednieniem bitu Carry
                jnc     print0 ;jesli nie ma przeniesienia, skok do print0
                inc     dl ;ustawienie znaku na 1, jesli Carry=1
print0:         int     21h ;przerwanie 21h 02h - druk znaku o kodzie w DL
                loop    printb
;print_hexadecimal
                lea     dx,print_hex
                mov     ah,9
                int     21h
                mov     ah,2
                mov     bx,suma
                mov     cx,4 ;ustawienie licznika petli na 4
printh:         mov     si,000Fh ;ustawienie SI na 15
                rol     bx,4 ;obrot bitowy o 4 miejsca w lewo
                and     si,bx ;mnozenie logiczne SI i BX:
                              ;BX=123=0000000001111011
                              ;rol bx,4
                              ;   BX=0000011110110000
                              ;SI=15=0000000000001111
                              ;  AND=0000000000000000
                              ;SI=0
                mov     dl,hex[si] ;wstawienie do DL znaku o indeksie SI
                                   ;z tablicy <0,F>
skip:           int     21h        ;wydruk znaku
                loop    printh
;/////////////////////////////////////////////////////
                mov     ah,4ch
                mov     al,0
                int     21h
blad1:          lea     dx,print_err1
                mov     ah,9
                int     21h
                jmp     quit
blad2:          lea     dx,print_err2
                mov     ah,9
                int     21h
                jmp     quit
quit:           mov     ah,4ch
                int     21h
Progr           ends

dane            segment
    max         db 6
    len         db ?
    znaki       db 6 dup(1)
    suma        dw 0
    hex         db '0123456789ABCDEF'
    ask         db 'Podaj liczbe: $'
    print_dec   db 10,13,'Dziesietnie: $'
    print_bin   db 10,13,'Binarnie: $'
    print_hex   db 10,13,'Heksadecymalnie: $'
    print_err1  db 10,13,'Podana liczba jest za duza!$'
    print_err2  db 10,13,'W liczbie znajduja sie bledne znaki!$'
dane            ends

stosik          segment
                dw    100h dup(0)
szczyt          Label word
stosik          ends

end start
