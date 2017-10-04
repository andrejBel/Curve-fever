/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vynimky;

/**
 *Výnimka sa vyhodí, ak si hráči zvolili rovnaké klávesy na ovládanie v hre alebo ak má hráč obidve klávesy rovnaké
 * @author Andrej Beliancin
 */
public class RovnakeKlavesyException extends IllegalArgumentException {

    public RovnakeKlavesyException() {
        super("Hráči majú rovnaké klávesy na ovládania");
    }
    
}
