package Obrazky;

import Hra.Platno;
import Hra.Kontrola;
import Hra.Krivka;

/**
 * Potomok triedy obrazok, predstavuje bonus, po ktorého použití sa
 * odstáni stena na určitý časový interval
 * @author Andrej Beliancin
 */
public class ObrazokStenaFuc extends Obrazok {

    private Kontrola kontrola;
    private Platno platno;
    private Krivka[] krivky;

    public ObrazokStenaFuc(Kontrola kontrola, Krivka[] krivky, Platno platno) {
        super(kontrola, "Images/wall2.png");
        this.kontrola = kontrola;
        this.platno = platno;
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
                this.kontrola.odstranStenu();
                for (int j = 0; j < this.krivky.length; j++) {
                    this.krivky[j].setIntervalPrechadzaniaCezStenu(super.getDLZKA_TRVANIA_BONUSU());
                    this.platno.setIntervalPrechadzaniaCezStenu(super.getDLZKA_TRVANIA_BONUSU());
                }
                return true;
            }
        }
        return false;
    }

}
