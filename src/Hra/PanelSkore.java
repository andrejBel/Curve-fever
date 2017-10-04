package Hra;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 * Trieda reprezentuje panel so skóre hráčov, vie sa vykresliť,určuje víťaza hry a zoraďuje hráčov podľa skóre zostupne
 * @author Andrej Beliancin
 */
public class PanelSkore extends JPanel {

    private Krivka[] krivky;
    private int posunutieX;
    private int vyska;
    private int ciel;
    private final int sirka = 200;
    private boolean zobrazVyhodnotenie = false;

    /**
     * 
     * @param sirka počet bodov, o ktoré sa panel posunie doprava(šírka plátna)
     * @param vyska výška panelu(zhodná ako šírka plátna)
     * @param krivky krivky, ktoré bude vyhodnocovať
     */
    public PanelSkore(int sirka, int vyska, Krivka[] krivky) {
        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(sirka, vyska));
        this.krivky = new Krivka[krivky.length];
        for (int i = 0; i < krivky.length; i++) {
            this.krivky[i] = krivky[i];
        }
        this.posunutieX = sirka;
        this.vyska = vyska;
        this.ciel = (krivky.length - 1) * 3;
    }

    /**
     * 
     * @return oznamuje koniec hry
     */
    public boolean koniecHry() {
        return (this.ciel <= this.krivky[this.dajIndexNajlepsejKrivky()].getSkore());
    }
    
    /**
     * Zoradí krivky podľa skóre
     */
    private void zoradPodlaSkore() {
        for (int i = 0; i < this.krivky.length - 1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < this.krivky.length; j++) {
                if (this.krivky[j].getSkore() > this.krivky[maxIndex].getSkore()) {
                    maxIndex = j;
                }
            }
            Krivka pom = this.krivky[i];
            this.krivky[i] = this.krivky[maxIndex];
            this.krivky[maxIndex] = pom;
        }
    }

    /**
     * 
     * @return index krivky s najvyšším skóre 
     */
    private int dajIndexNajlepsejKrivky() {
        int najlepsi = 0;
        for (int i = 0; i < this.krivky.length; i++) {
            if (this.krivky[i].getSkore() > this.krivky[najlepsi].getSkore()) {
                najlepsi = i;
            }
        }
        return najlepsi;
    }

    /**
     * zobrazí vyhodnotenie hry(víťaza)
     */
    public void zobrazVyhodnotenie() {
        this.zobrazVyhodnotenie = true;
    }

    /**
     * 
     * @param krivky nadstaví krivky, ktoré sa majú vyhodnocovať 
     */
    public void setKrivky(Krivka[] krivky) {
        for (int i = 0; i < krivky.length; i++) {
            this.krivky[i] = krivky[i];
        }
    }

    /**
     * Vykreslí cieľ hry, hráčov vzostupne podľa počtu bodov a po
     * vyhodnotení aj ponuku pre pokračovanie ďalej(ovládanie tejto ponuky je v triede Platno vo vnorenej triede Myska). <BR>
     * Uvedomujem si vysokú implementačnú závislosť, ktorá vznikla týmto činom,
     * ale nevedel som pridať tlačidlá pre ovládanie po skončení hry
     * @param g Grafika
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D grafika2D = (Graphics2D) g;
        grafika2D.setColor(Color.BLACK);
        grafika2D.fillRect(this.posunutieX, 0, this.sirka, this.vyska);
        grafika2D.setColor(Color.BLUE);
        Font font = new Font("Verdana", Font.PLAIN, 35);
        grafika2D.setFont(font);
        grafika2D.drawString("Cieľ", this.posunutieX + 50, 30);
        grafika2D.setColor(Color.BLUE);
        grafika2D.drawString(this.ciel + "", this.posunutieX + 70, 60);
        Font font2 = new Font("Verdana", Font.PLAIN, 17);
        grafika2D.setFont(font2);
        this.zoradPodlaSkore();
        for (int i = 0; i < this.krivky.length; i++) {
            grafika2D.setColor(this.krivky[i].getFarba());
            grafika2D.drawString((i + 1) + ". " + this.krivky[i].getMeno() + " " + this.krivky[i].getSkore(), this.posunutieX + 10, 80 + 20 * i);
        }
        if (this.zobrazVyhodnotenie) {
            grafika2D.setColor(this.krivky[this.dajIndexNajlepsejKrivky()].getFarba());
            grafika2D.drawString("Vyhral hráč", this.posunutieX + 10, 200);
            grafika2D.drawString("" + this.krivky[this.dajIndexNajlepsejKrivky()].getMeno(), this.posunutieX + 10, 225);
            grafika2D.drawRect(this.posunutieX + 5, 231, 122, 25);
            grafika2D.drawString("Hlavné menú", this.posunutieX + 10, 250);
            grafika2D.drawRect(this.posunutieX + 5, 271, 122, 25);
            grafika2D.drawString("Ulož hru", this.posunutieX + 10, 290);
            grafika2D.drawRect(this.posunutieX + 5, 311, 122, 25);
            grafika2D.drawString("Hraj znova", this.posunutieX + 10, 330);
            grafika2D.drawRect(this.posunutieX + 5, 351, 122, 25);
            grafika2D.drawString("Koniec", this.posunutieX + 10, 370);
            this.zobrazVyhodnotenie = false;
        }
    }

    /**
     * Zapíše krivky vzostupne podľa skóre do súboru
     * @return Vráti, či bol zápis do súboru úspešný
     */
    public boolean zapisDoSuboru() {
        try {
            Parser.zapisDoSuboru(this.krivky);
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

}
