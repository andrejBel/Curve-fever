package Obrazky;

import Hra.Kontrola;
import Hra.Krivka;

/**
 * Potomok triedy obrazok, predstavuje bonus, po ktorého použití 
 * krivka môže prechádzať cez steny aj krivky na určitý časový interval
 * @author Andrej Beliancin
 */
public class ObrazokPrejdiCezVsetko extends Obrazok {

    private Krivka[] krivky;

    public ObrazokPrejdiCezVsetko(Kontrola kontrola, Krivka[] krivky) {
        super(kontrola, "Images/through.png");
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
                this.krivky[cisloKrivky].setIntervalPrechadzaniaCezStenu(super.getDLZKA_TRVANIA_BONUSU());
                this.krivky[cisloKrivky].setIntervalPrechadzaniaCezVsetko(super.getDLZKA_TRVANIA_BONUSU());
                return true;
            }
        }
        return false;
    }

}
