package Obrazky;

import Hra.Kontrola;
import Hra.Krivka;

/**
 * Potomok triedy obrazok, predstavuje bonus, po ktorého použití sa zvýši
 * rýchlosť pohybu krivky, ktorá bonus použila na určitý časový interval
 *
 * @author Andrej Beliancin
 */
public class ObrazokZrychli extends Obrazok {

    private Krivka[] krivky;

    public ObrazokZrychli(Kontrola kontrola, Krivka[] krivky) {
        super(kontrola, "Images/speed+.png");
        this.krivky = krivky;
    }

    @Override
    public boolean mojeSuradnice(int x, int y, int cisloKrivky) {
        for (int i = 0; i < super.getKosNavstivenychBodov().size(); i++) {
            if (super.getKosNavstivenychBodov().get(i).getX() == x && super.getKosNavstivenychBodov().get(i).getY() == y) {
                super.dajPrecZMapy();
                this.krivky[cisloKrivky].setIntervalZrychlenia(super.getDLZKA_TRVANIA_BONUSU() / 2);
                return true;
            }
        }
        return false;
    }

}
