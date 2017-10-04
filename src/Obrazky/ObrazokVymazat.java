package Obrazky;


import Hra.Kontrola;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;


/**
 * Potomok triedy obrazok, predstavuje bonus, po ktorého použití sa
 * vymaže herné plátno
 * @author Andrej Beliancin
 */
public class ObrazokVymazat extends Obrazok {

    private Kontrola kontrola;
    private Image obrazok;
    private ManazerObrazkov manazerObrazkov;

    public ObrazokVymazat(Kontrola kontrola, ManazerObrazkov manazer) {
        super(kontrola, "Images/erase.png");
        this.kontrola = kontrola;
        this.obrazok = super.getObrazok();
        this.manazerObrazkov = manazer;
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
                this.kontrola.inicializuj();
                return true;
            }
        }
        return false;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D grafika2D = (Graphics2D)g;
        if (super.isKresli() && !super.isAkcia()) {
            grafika2D.setColor(Color.BLACK);
            grafika2D.fillOval(super.getSuradnicaX() - 1, super.getSuradnicaY() - 1, super.getROZMER() + 2, super.getROZMER() + 2);
            grafika2D.drawImage(this.obrazok, super.getSuradnicaX(), super.getSuradnicaY(), super.getROZMER(), super.getROZMER(), null);
        }
        if (super.isAkcia()) {
            grafika2D.setColor(Color.BLACK);
            this.manazerObrazkov.nekresliZiadny();
            grafika2D.fillRect(0, 0, this.kontrola.getPocetStlpcov(), this.kontrola.getPocetRiadkov());
            super.setKoniecAkcie();
            super.setNekresli();
        }
    }

}
