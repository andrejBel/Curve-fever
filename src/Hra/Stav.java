package Hra;

/**
 *Enumerácia možných stavov buniek mapy hry. 
 * @author Andrej Beliancin
 */
public enum Stav {
    PLNA('1'), DRUHYKRAT('2'), BONUS('B'), PRAZDNA('0'), AKTUALNY('A');

    private final char znak;

    private Stav(char znak) {
        this.znak = znak;
    }

    @Override
    public String toString() {
        return this.znak + "";
    }
    
    
    
}
