/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vynimky;

/**
 *Výnimka, ktorá sa vyhodí, ak používatľ zadá prázdny vstup
 * @author Andrej Beliancin
 */
public class PrazdnyVstupException extends IllegalArgumentException {

    public PrazdnyVstupException(int cislo) {
        super("Hráč číslo " + cislo + " nezadal svoje meno" );
    }
    
}
