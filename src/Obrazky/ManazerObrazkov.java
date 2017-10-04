package Obrazky;

import Hra.Platno;
import Hra.Kontrola;
import Hra.Krivka;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

/**
 * Trieda sa stará o generovanie obrázkov(bonusov) na hraciu plochu, ich správne
 * načasovanie, kreslenie a zistenie, ktorý bonus sa má vykonať Každý obrázok sa
 * bude generovať s rovnakou pravdepodobnosťou, na hracej ploche sa môže
 * nachádzať najmenej 0 a najviac 3 obrazky
 *
 * @author Andrej Beliancin
 *
 */
public class ManazerObrazkov {

    private ArrayList<Obrazok> obrazky;
    private int sirka;
    private int vyska;
    private Random generator;
    private long cisloDoDalsiehoGenerovania = 200;
    private Kontrola kontrola;

    /**
     * Konštruktor inicializuje kontajner obrázkov, ktoré predstavujú bonusy,
     * ktoré sa budú generovať
     *
     * @param kontrola Kontola(mapa hry), na ktorú sa budú bonusy pokladať
     * @param krivky krivky, ktoré budú reagovať na bonus
     * @param platno 
     */

    public ManazerObrazkov(Kontrola kontrola, Krivka[] krivky, Platno platno) {
        this.obrazky = new ArrayList<Obrazok>();
        this.obrazky.add(new ObrazokVymazat(kontrola, this));
        this.obrazky.add(new ObrazokStenaFuc(kontrola, krivky, platno));
        this.obrazky.add(new ObrazokZmenOrientaciuKlaves(kontrola, krivky));
        this.obrazky.add(new ObrazokSpomal(kontrola, krivky));
        this.obrazky.add(new ObrazokZrychli(kontrola, krivky));
        this.obrazky.add(new ObrazokSpomalMna(kontrola, krivky));
        this.obrazky.add(new ObrazokPrejdiCezVsetko(kontrola, krivky));
        this.obrazky.add(new ObrazokVymazat(kontrola, this));
        this.obrazky.add(new ObrazokStenaFuc(kontrola, krivky, platno));
        this.obrazky.add(new ObrazokZmenOrientaciuKlaves(kontrola, krivky));
        this.obrazky.add(new ObrazokSpomal(kontrola, krivky));
        this.obrazky.add(new ObrazokZrychli(kontrola, krivky));
        this.obrazky.add(new ObrazokSpomalMna(kontrola, krivky));
        this.obrazky.add(new ObrazokPrejdiCezVsetko(kontrola, krivky));        
        this.sirka = platno.getSirka();
        this.vyska = platno.getVyska();
        this.kontrola = kontrola;
        this.generator = new Random();
    }

    /**
     *
     * @return počet nakreslených obrázkov
     */
    public int pocetNakreslenych() {
        int pocet = 0;
        for (int i = 0; i < this.obrazky.size(); i++) {
            if (this.obrazky.get(i).isKresli()) {
                pocet++;
            }
        }
        return pocet;
    }

    /**
     *
     * @return číslo do ďalšieho generovania náhodného obrázka
     */
    public long getCisloDoDalsiehoGenerovania() {
        return this.cisloDoDalsiehoGenerovania;
    }

    /**
     *
     * @param cisloDoDalsiehoGenerovania zvyšuje zadanú hodnotu konštantu a
     * náhodne generované číslo
     */
    public void setCisloDoDalsiehoGenerovania(long cisloDoDalsiehoGenerovania) {
        this.cisloDoDalsiehoGenerovania = cisloDoDalsiehoGenerovania + this.generator.nextInt(100) + 100;
    }

    /**
     * Metóda náhodne vyberá obrázok na kreslenie, najprv skontroluje, či je
     * náhodne vygenerovaná pozícia voľná, ak áno, tak nakreslí obrázok, a
     * položí ho na mapu hry
     */
    public void nahodneVyberObrazkyNaKreslenie() {
        int povodnyPocet = this.pocetNakreslenych();
        if (this.pocetNakreslenych() < 3) {
            int nahodneCislo;
            int nahodneX = this.generator.nextInt(this.sirka - 60);
            int nahodneY = this.generator.nextInt(this.vyska - 60);
            if (this.kontrola.jeVolnaPlocha(15 + nahodneX, 15 + nahodneY, 15 + nahodneX + this.obrazky.get(0).getROZMER(), 15 + nahodneY + this.obrazky.get(0).getROZMER())) {
                do {
                    nahodneCislo = this.generator.nextInt(this.obrazky.size());
                    this.obrazky.get(nahodneCislo).setKresli();
                } while (povodnyPocet == this.pocetNakreslenych());
                this.obrazky.get(nahodneCislo).setSuradnicaX(15 + nahodneX);
                this.obrazky.get(nahodneCislo).setSuradnicaY(15 + nahodneY);
                this.obrazky.get(nahodneCislo).polozNaMapu();
            }
        }
    }

    /**
     * Metóda rozhoduje o tom, ktorý bonus vykonať, a to tak, že kontroluje
     * súradnice nakreslených obrázkov
     *
     * @param x
     * @param y
     * @param cisloKrivky
     */
    public void ktoryBonusVykonat(int x, int y, int cisloKrivky) {
        for (int i = 0; i < this.obrazky.size(); i++) {
            if (this.obrazky.get(i).isKresli()) {
                if (this.obrazky.get(i).mojeSuradnice(x, y, cisloKrivky)) {
                    break;
                }
            }

        }
    }

    /**
     * Metóda zabezpečí, aby sa nevykresľoval žiadny obrázok
     */
    public void nekresliZiadny() {
        for (int i = 0; i < this.obrazky.size(); i++) {
            this.obrazky.get(i).setNekresli();
        }
    }

    /**
     * Kreslí obrázky na plátno
     *
     * @param g Grafika
     */
    public void kresli(Graphics g) {
        for (int i = 0; i < this.obrazky.size(); i++) {
            if (this.obrazky.get(i).isKresli()) {
                this.obrazky.get(i).paintComponent(g);
            }
        }
    }

}
