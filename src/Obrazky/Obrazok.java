package Obrazky;

import Hra.Kontrola;
import Hra.Bod;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


/**
 *Abstraktná trieda predstavuje základ pre obrázok(bonus) generovaný v hre, 
 * dokáže ho načítať, pamätá si súradnice polohy obrázka na mape hry a vykresľuje ho na plátno
 * @author Andrej Beliancin
 */
public abstract class Obrazok extends JPanel {

    private Image obrazok;
    private static final int ROZMER = 40;
    private int suradnicaX = 50;
    private int suradnicaY = 50;
    private Kontrola kontrola;
    private boolean kresli = false;
    private boolean akcia = false;
    private ArrayList<Bod> kosNavstivenychBodov = new ArrayList<Bod>();
    private static int DLZKA_TRVANIA_BONUSU = 1000;

    /**
     * 
     * @param kontrola Mapa hry, na ktorú sa obrázok položí
     * @param cesta Relatívna cesta k obrázku 
     */
    public Obrazok(Kontrola kontrola, String cesta) {
        super();
        try {
        URL url = this.getClass().getResource(cesta);
        this.obrazok = new ImageIcon(url).getImage();
        } catch (Exception e) {
            System.out.println("Nepodarilo sa načítať obrázok");
        }
        this.kontrola = kontrola;
    }

    /**
     * @return X-ová Súradnica ľavého horného rohu obrázka 
     */
    public int getSuradnicaX() {
        return this.suradnicaX;
    }

    /**
     * @param suradnicaX  nadstaví x-ovú súradnicu ľavého horného rohu obrázka 
     */
    public void setSuradnicaX(int suradnicaX) {
        this.suradnicaX = suradnicaX;
    }

    /**
     * @return Y-ová Súradnica ľavého horného rohu obrázka 
     */
    public int getSuradnicaY() {
        return this.suradnicaY;
    }

    /**
     * @param suradnicaY nadstaví y-ovú súradnicu ľavého horného rohu obrázka 
     */
    public void setSuradnicaY(int suradnicaY) {
        this.suradnicaY = suradnicaY;
    }

    /**
     * @return načítaný obrázok 
     */
    protected Image getObrazok() {
        return this.obrazok;
    }

    /**
     * 
     * @return rozmer obrázka(je to jeho šírka aj výška)
     */
    public int getROZMER() {
        return Obrazok.ROZMER;
    }

    /**
     * 
     * @return dĺžka trvania bonusu
     */
    public static int getDLZKA_TRVANIA_BONUSU() {
        return DLZKA_TRVANIA_BONUSU;
    }

    
    /**
     * 
     * @return kôš súradníc, na ktorých sa nachádza obrázok na mape hry 
     */
    protected ArrayList<Bod> getKosNavstivenychBodov() {
        return this.kosNavstivenychBodov;
    }

    /**
     * 
     * @return Vráti, či sa daný obrázok vykresľuje 
     */
    public boolean isKresli() {
        return this.kresli;
    }

    /**
     * 
     * @return Vráti, či obrázok vykonáva akciu(po kolízii s krivkou)
     */
    public boolean isAkcia() {
        return this.akcia;
    }

    /**
     * Nadstaví vykonávanie akcie
     */
    public void setZaciatokAkcie() {
        this.akcia = true;
    }

    /**
     * Nadstaví koniec vykonávania akcie
     */
    public void setKoniecAkcie() {
        this.akcia = false;
    }

    /**
     * Nadstaví kreslenie obrázka na hraciu plochu
     */
    public void setNekresli() {
        this.kresli = false;
    }

    /**
     * Nadstaví kreslenie obrázka na hraciu plochu
     */
    public void setKresli() {
        this.kresli = true;
    }

    /**
     * 
     * @param x x-ová súradnica bodu, ktorého sa pýtam, či sa nachádza v mojich súradniciach
     * @param y y-ová súradnica bodu, ktorého sa pýtam, či sa nachádza v mojich súradniciach
     * @param cisloKrivky číslo krivky, ktoré sa dostala na zadané súradnice
     * @return 
     */
    public boolean mojeSuradnice(int x, int y, int cisloKrivky) {
        for (int i = 0; i < this.kosNavstivenychBodov.size(); i++) {
            if (this.kosNavstivenychBodov.get(i).getX() == x && this.kosNavstivenychBodov.get(i).getY() == y) {
                this.dajPrecZMapy();
                return true;
            }
        }
        return false;
    }
    
    /**
     * Pokladá súradnice obrázka na mapu hry
     */
    public void polozNaMapu() {
        this.kontrola.zaplnMapuBonusom(this.suradnicaX, this.suradnicaY, this.suradnicaX + Obrazok.ROZMER, this.suradnicaY + Obrazok.ROZMER);
        for (int i = this.suradnicaX; i < this.suradnicaX + Obrazok.ROZMER; i++) {
            for (int j = this.suradnicaY; j < this.suradnicaY + Obrazok.ROZMER; j++) {
                this.kosNavstivenychBodov.add(new Bod(i, j));
            }
        }
    }
    
    /**
     * Odstraňuje súradnice obrázka z mapy hry
     */
    public void dajPrecZMapy() {
        this.kontrola.odstranBonusZMapy(this.suradnicaX, this.suradnicaY, this.suradnicaX + Obrazok.ROZMER, this.suradnicaY + Obrazok.ROZMER);
        this.kosNavstivenychBodov.removeAll(this.kosNavstivenychBodov);
        this.akcia = true;
    }

    /**
     * Kreslí obrázok a v prípade jeho použitia ho prekreslí na čierno
     * @param g Grafika 
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D grafika2D = (Graphics2D) g;
        if (this.kresli && !this.akcia) {
            grafika2D.setColor(Color.BLACK);
            grafika2D.fillOval(this.suradnicaX - 1, this.suradnicaY - 1, Obrazok.ROZMER + 2, Obrazok.ROZMER + 2);
            grafika2D.drawImage(this.obrazok, this.suradnicaX, this.suradnicaY, Obrazok.ROZMER, Obrazok.ROZMER, null);
        }
        if (this.akcia) {
            grafika2D.setColor(Color.BLACK);
            grafika2D.fillOval(this.suradnicaX - 2, this.suradnicaY - 2, Obrazok.ROZMER + 3, Obrazok.ROZMER + 3);
            this.akcia = false;
            this.kresli = false;
        }
    }

}
