/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vynimky;

/**
 *Výnimka, ktorá sa vyhodí, ak zvolili dvaja hráči rovnakú farbu krivky
 * @author Andrej Beliancin
 */
public class RovnakeFarbyException extends IllegalArgumentException {

    public RovnakeFarbyException(int cislo) {
        super("Hráč " + cislo + " má rovnakú farbu ako jeden z hráčov pred ním");
    }
    
}
