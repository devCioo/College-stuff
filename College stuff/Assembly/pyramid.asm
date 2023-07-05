Progr           segment
                assume  cs:Progr, ds:dane, ss:stosik

start:          mov     ax,dane
                mov     ds,ax
                mov     ax,stosik
                mov     ss,ax
                mov     ax,offset szczyt
                mov     sp,ax
;/////////////////////////////////////////////////////
                mov     ax,0b800h ;ustawienie pamieci na poczatek pamieci wideo
                mov     dx,2000   ;licznik (ekran 80x25=2000)
                mov     ds,ax     ;przeniesienie adresu do segmentu danych
                mov     cl,' '    ;symbol, ktory chcemy wydrukowac
                mov     ch,00000000b  ;wartosci tego symbolu
                mov     bx,0000h      ;offest wzgledem poczatku
clear:
                mov     [bx],cx  ;przeniesienie symbolu w okreslone miejsce pamieci
                inc     bx       ;zwiekszenie bx 2 razy, kazdy char ma 2 bajty
                inc     bx       ;jeden na kod znaku, drugi na jego modyfikacje
                dec     dx       ;zmniejszenie licznika
                jnz     clear    ;skok warunkowy, jesli licznik!=0

                mov     di,40
                mov     ah,40
                mov     al,0
                mov     bx,0000h
print:
                dec     ah
                mov     dh,ah
                inc     al
                inc     al
                mov     dl,al
prints1:
                mov     [bx],cx
                inc     bx
                inc     bx
                dec     dh
                jnz     prints1
printz:
                mov     cl,'*'
                mov     ch,00001111b
                mov     [bx],cx
                inc     bx
                inc     bx
                dec     dl
                jnz     printz
                mov     dh,ah
prints2:
                mov     cl,' '
                mov     ch,00000000b
                mov     [bx],cx
                inc     bx
                inc     bx
                dec     dh
                jnz     prints2

                dec     di
                jnz     print
;/////////////////////////////////////////////////////
                mov     ah,4ch
                mov     al,0
                int     21h

Progr           ends

dane            segment

dane            ends

stosik          segment
                dw    100h dup(0)
szczyt          Label word
stosik          ends

end start
