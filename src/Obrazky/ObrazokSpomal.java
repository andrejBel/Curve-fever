package Obrazky;

import Hra.Kontrola;
import Hra.Krivka;

/**
 * Potomok triedy obrazok, predstavuje bonus, po ktorého použití sa sníži
 * rýchlosť pohybu všetkých ostatných kriviek okrem tej, ktorá bonus použila na určitý časový interval
 *
 * @author Andrej Beliancin
 */
public class ObrazokSpomal extends Obrazok {

    private Krivka[] krivky;

    public ObrazokSpomal(Kontrola kontrola, Krivka[] krivky) {
        super(kontrola, "Images/speed-2.png");
        this.krivky = krivky;
    }

    /**
     * Metóda, ktorá reaguje na prejdetie obrázka a vykoná bonus
     * @param x x-ová súradnica bodu, ktorého sa pýtam, či sa nachádza v mojich
     * súradniciach
     * @param y y-ová súradnica bodu, ktorého sa pýtam, či sa nachádza v mojich
     * súradniciach
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
                        this.krivky[j].setIntervalSpomalenia(super.getDLZKA_TRVANIA_BONUSU() / 2);
                    }
                }
                return true;
            }
        }
        return false;
    }
}
