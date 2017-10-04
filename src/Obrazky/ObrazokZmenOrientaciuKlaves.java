package Obrazky;


import Hra.Kontrola;
import Hra.Krivka;

/**
 * Potomok triedy obrazok, predstavuje bonus, po ktorého použití sa
 * zmení ovládanie kláves na určitý časový interval, to,
 * čo slúžilo na ovládanie doprava teraz slúži na ovládanie vľavo a naopak
 * @author Andrej Beliancin
 */
public class ObrazokZmenOrientaciuKlaves extends Obrazok {

    private Krivka[] krivky;
    
    public ObrazokZmenOrientaciuKlaves(Kontrola kontrola, Krivka[] krivky) {
        super(kontrola, "Images/reverse.png");
        this.krivky = krivky;
    }
    
    /**
     * Metóda, ktorá reaguje na prejdetie obrázka a vykoná bonus 
     * @param x x-ová súradnica bodu, ktorého sa pýtam, či sa nachádza v mojich súradniciach
     * @param y y-ová súradnica bodu, ktorého sa pýtam, či sa nachádza v mojich súradniciach
     * @param cisloKrivky číslo krivky, ktoré sa dostala na zadané súradnice
     * @return 
     */
    @Override
    public boolean mojeSuradnice(int x, int y, int cisloKrivky) {
        for (int i = 0; i < super.getKosNavstivenychBodov().size(); i++) {
            if (super.getKosNavstivenychBodov().get(i).getX() == x && super.getKosNavstivenychBodov().get(i).getY() == y) {
                super.dajPrecZMapy();
                for (int j = 0; j < this.krivky.length; j++) {
                    if (cisloKrivky != j) {
                        this.krivky[j].setIntervalZmenyOrientacie(super.getDLZKA_TRVANIA_BONUSU() / 3);
                    }
                }
                return true;
            }
        }
        return false;
    }
    
}
