package Hra;

/*
 * 
 */

/**
 * Trieda, ktorá reprezentuje bod v rovine.
 * 
 * @author Andrej Beliancin
 */
public class Bod {

    private final int x;
    private final int y;
    
    /**
     * Konštruktor vytvorí bod v rovine.
     * @param x x-ová súradnica bodu
     * @param y yóvá súradnica bodu 
     */
    public Bod(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * 
     * @return x-ová súradnica bodu
     */
    public int getX() {
        return this.x;
    }
    
    /**
     * 
     * @return y-ová súradnica bodu 
     */
    public int getY() {
        return this.y;
    }
    
    
}
