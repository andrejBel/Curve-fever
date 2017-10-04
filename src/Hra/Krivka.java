package Hra;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;

/**
 * Trieda uchováva všetky informácie o krivke. Krivka neuchováva informácie o
 * všetkých svojich bodoch ale pamätá si len hlavičku svojho tela a vie si na
 * základe svojich atribútov vypočítať ďaľšie súradnice. Trieda krivka nemá priamo na
 * starosti detekciu kolízii, jej úlohou je vykresľovanie krivky a ovlyvňovanie
 * rýchlosti a smeru vykresľovania, vrátana reakcie na bonusy v hre 
 *
 * @author Andrej Beliancin
 */
public class Krivka extends JPanel {

    private double x;
    private double y;
    private int predposlednyX;
    private int predposlednyY;
    private double uhol;
    private final Color farba;
    private final int hrubka;
    private boolean zije;
    private Kontrola kontrola;
    private String meno;
    private int skore = 0;
    private static final double POVODNARYCHLOST = 0.8;
    private double rychlost;
    private final int cisloVKontajneri;
    private final int pohniVpravo;
    private final int pohniVlavo;
    private int orientacia = 1;
    private long iterator = 0;
    private long intervalZmenyOrientacie = 0;
    private long intervalPrechadzaniaCezStenu = 0;
    private long intervalSpomalenia = 0;
    private long intervalZrychlenia = 0;
    private long intervalPrechadzaniaCezVsetko = 0;

    /**
     * 
     * @param meno meno hráča, ktorý vytvoril danú krivku
     * @param x x-ová súradnica počiatočnej polohy krivky
     * @param y y-ová súradnica počiatočnej polohy krivky
     * @param uhol počiatočný uhol, pod ktorým sa krivka hýbe
     * @param farba farba krivky
     * @param hrubka hrúbka krivky
     * @param kontrola kontrola(mapa hry) na ktorú sa pokladajú súradnice krivky
     * @param cisloVKontajneri číslo krivky medzi vytvorenými krivkami(0,1...)
     * @param pohniVpravo keycode klávesu pre zmenu smeru vpravo
     * @param pohniVlavo keycode klávesu pre zmenu smeru vľavo
     */
    public Krivka(String meno, double x, double y, double uhol, Color farba, int hrubka, Kontrola kontrola, int cisloVKontajneri, int pohniVpravo, int pohniVlavo) {
        this.x = x;
        this.y = y;
        this.predposlednyX = (int) Math.round(x);
        this.predposlednyY = (int) Math.round(y);
        this.uhol = uhol;
        this.farba = farba;
        this.hrubka = hrubka;
        this.zije = true;
        this.rychlost = Krivka.POVODNARYCHLOST;
        this.kontrola = kontrola;
        this.meno = meno;
        this.cisloVKontajneri = cisloVKontajneri;
        this.pohniVpravo = pohniVpravo;
        this.pohniVlavo = pohniVlavo;
    }

    /**
     * 
     * @return meno krivky 
     */
    public String getMeno() {
        return this.meno;
    }

    /**
     * 
     * @return farba krivky
     */
    public Color getFarba() {
        return this.farba;
    }

    /**
     * 
     * @return skóre, ktoré krivka nahrala 
     */
    public int getSkore() {
        return this.skore;
    }

    /**
     * Zvýši skóre o jeden
     */
    public void zvysSkore() {
        this.skore++;
    }

    /**
     * 
     * @param skore nadstaví skóre na želanú hodnotu
     */
    public void setSkore(int skore) {
        this.skore = skore;
    }

    /**
     * 
     * @param x nadstaví x-ovú súradnicu krivky 
     */
    public void nadstavX(double x) {
        this.predposlednyX = (int) Math.round(x);
        this.x = x;
    }

    /**
     * 
     * @param y nadstaví y-ovú súradnicu krivky 
     */
    public void nadstavY(double y) {
        this.predposlednyY = (int) Math.round(y);
        this.y = y;
    }

    /**
     * K aktuálnej hodnote x-ovej súradnice pripočíta parameter<BR>
     * Potrebné pre vypočítanie nasledujúcej súradnice
     * @param x koľko sa má pripočítať
     */
    private void setX(double x) {
        this.predposlednyX = (int) Math.round(this.x);
        this.x += x;
    }

    /**
     * K aktuálnej hodnote y-ovej súradnice pripočíta parameter<BR>
     * Potrebné pre vypočítanie nasledujúcej súradnice
     * @param y koľko sa má pripočítať
     */
    private void setY(double y) {
        this.predposlednyY = (int) Math.round(this.y);
        this.y += y;
    }

    /**
     * 
     * @return či krivka žije alebo nie(či sa má ďalej vykresľovať na plátno alebo nie)
     */
    public boolean zije() {
        return this.zije;
    }

    /**
     * 
     * @return číslo krivky v kontajneri kriviek 
     */
    public int getCisloVKontajneri() {
        return this.cisloVKontajneri;
    }

    /**
     * 
     * @param uhol nadstaví aktuálny uhol 
     */
    public void setUhol(double uhol) {
        this.uhol += uhol * this.rychlost * this.orientacia;
    }

    /**
     * Nadstaví umretie krivky(prestane sa vykresľovať na plátno a prepočítavať)
     */
    public void setUmrie() {
        this.zije = false;
    }

    /**
     * Iterátor slúži na určovanie plynutia času v hre 
     * @param iterator nadstaví iterátor na danú hodnotu
     */
    public void setIterator(long iterator) {
        this.iterator = iterator;
    }

    /**
     * 
     * @param intervalZmenyOrientacie čas zmeny orientácie kláves
     */
    public void setIntervalZmenyOrientacie(long intervalZmenyOrientacie) {
        this.intervalZmenyOrientacie = intervalZmenyOrientacie + this.iterator;
    }

    /**
     * 
     * @param intervalPrechadzaniaCezStenu čas prechádzania cez stenu 
     */
    public void setIntervalPrechadzaniaCezStenu(long intervalPrechadzaniaCezStenu) {
        this.intervalPrechadzaniaCezStenu = intervalPrechadzaniaCezStenu + this.iterator;
    }

    /**
     * 
     * @param intervalSpomalenia čas zníženia rýchlosti krivky
     */
    public void setIntervalSpomalenia(long intervalSpomalenia) {
        this.intervalSpomalenia = intervalSpomalenia + this.iterator;
    }

    /**
     * 
     * @param intervalZrychlenia čas zvýšenia rýchlosti krivky 
     */
    public void setIntervalZrychlenia(long intervalZrychlenia) {
        this.intervalZrychlenia = intervalZrychlenia + this.iterator;
    }

    /**
     * 
     * @param intervalPrechadzaniaCezVsetko čas pre prechádzanie krivky cez ľuvoboľný objekt
     */
    public void setIntervalPrechadzaniaCezVsetko(long intervalPrechadzaniaCezVsetko) {
        this.intervalPrechadzaniaCezVsetko = intervalPrechadzaniaCezVsetko + this.iterator;
    }

    /**
     * 
     * @return textová reprezentácia krivky 
     */
    @Override
    public String toString() {
        return "Krivka číslo " + this.cisloVKontajneri + " s menom " + this.meno + " , aktuálne súradnice [" + String.format("%3.1f ; %3.1f", this.x, this.y) + "],\n farba " + this.farba.toString() + ", skóre " + this.skore + "\n tlačidlo pre pohyb vľavo "  + KeyEvent.getKeyText(this.pohniVlavo) + "\n tlačidlo pre pohyb vpravo " +  KeyEvent.getKeyText(this.pohniVpravo);
    }

    /**
     * Vypočíta nasledujúce súradnice a zabezpečuje vykonávanie bonusov 
     */
    public void vypocitajDalsieSuradnice() {
        if (this.iterator < this.intervalSpomalenia) {
            this.intervalZrychlenia = 0;
            this.rychlost = 0.3;
        }
        if (this.iterator == this.intervalSpomalenia) {
            this.rychlost = Krivka.POVODNARYCHLOST;
        }
        if (this.iterator < this.intervalZrychlenia) {
            this.intervalSpomalenia = 0;
            this.rychlost = 1;
        }
        if (this.iterator == this.intervalZrychlenia) {
            this.rychlost = Krivka.POVODNARYCHLOST;
        }
        if (this.iterator < this.intervalPrechadzaniaCezStenu) {
            if (this.x > this.kontrola.getPocetRiadkov() - 3) {
                this.x = 3;
            } else if (this.x < 3) {
                this.x = this.kontrola.getPocetRiadkov() - 3;
            }
            if (this.y > this.kontrola.getPocetStlpcov() - 3) {
                this.y = 3;
            } else if (this.y < 3) {
                this.y = this.kontrola.getPocetStlpcov() - 3;
            }
        }
        if (this.iterator == this.intervalPrechadzaniaCezStenu) {
            this.kontrola.obnovStenu();
        }
        this.setX(Math.cos(this.uhol) * this.rychlost);
        this.setY(Math.sin(this.uhol) * this.rychlost);
    }

    /**
     * Číta vstup z klávesnice a reaguje na bonus zmena orientácie klávesnice
     * @param vstup vstup z klávesnice
     */
    public void citajVstup(boolean[] vstup) {
        if (this.iterator < this.intervalZmenyOrientacie) {
            this.orientacia = -1;
        } else {
            this.orientacia = 1;
        }
        if (vstup[this.pohniVpravo]) {
            this.uhol += 0.035 * this.rychlost * this.orientacia;
        } else if (vstup[this.pohniVlavo]) {
            this.uhol += -0.035 * this.rychlost * this.orientacia;
        }
    }

    /**
     * Inicializuje krivku pre ďaľšie kolo v hre
     */
    public void inicializuj() {
        this.zije = true;
        this.orientacia = 1;
        this.intervalPrechadzaniaCezStenu = 0;
        this.intervalZmenyOrientacie = 0;
        this.intervalSpomalenia = 0;
        this.intervalZrychlenia = 0;
        this.rychlost = Krivka.POVODNARYCHLOST;
        this.intervalPrechadzaniaCezVsetko = 0;
    }

    /**
     * Pokladá súradnice krivky na mapu hry
     * @return true, ak nedošlo ku kolízii so stenou alebo s inou krivkou, false ak nie
     */
    public boolean polozSuradniceNaMapu() {
        if (this.iterator < this.intervalPrechadzaniaCezVsetko) {
            return this.kontrola.setNavstivenyBezKontrolySuradnic((int) Math.round(this.x), (int) Math.round(this.y), this.predposlednyX, this.predposlednyY, this);
        } else {
            return this.kontrola.setNavstiveny((int) Math.round(this.x), (int) Math.round(this.y), this.predposlednyX, this.predposlednyY, this);
        }
    }

    /**
     * Nepokladá súradnice krivky na mapu hry, iba kontroluje či nedošlo ku kolízii 
     * @return true, ak došlo ki kolízii, false ak nie
     */
    public boolean kontrolujBezUlozenia() {
        return this.kontrola.kontrolujBezUkladania((int) Math.round(this.x), (int) Math.round(this.y));
    } 
    /**
     * Kreslí krivku na plátno
     * @param g Grafika
     */
    @Override
    public void paintComponent(Graphics g) {
        if (this.zije) {
            Graphics2D grafika2D = (Graphics2D) g;
            grafika2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Ellipse2D ciara = new Ellipse2D.Double(this.x, this.y, this.hrubka, this.hrubka);
            grafika2D.setColor(this.farba);
            if (this.iterator < this.intervalPrechadzaniaCezVsetko) {
                if (this.iterator % 20 > 10) {
                    if (this.farba == Color.GREEN) {
                        grafika2D.setColor(Color.RED);
                    } else {
                        grafika2D.setColor(Color.GREEN);
                    }
                } else {
                    grafika2D.setColor(this.farba);
                }
            } else {
                grafika2D.setColor(this.farba);
            }
            grafika2D.fill(ciara);
        }
    }

}
