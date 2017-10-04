package Hra;

import Obrazky.ManazerObrazkov;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.Random;
import javax.swing.JOptionPane;


/**
 *Trieda s najvyšším postavením v programe, predstavuje plátno na ktoré sa inicializujú a vykresľujú krivky,
 * skóre, prijíma vstup z klávesnice, zabezpečuje plynutie programu v čase(pomocou časovača) a obsahuje logiku hry.
 * 
 * @author Andrej Beliancin
 */
public class Platno extends JPanel implements ActionListener, KeyListener {

    private int sirka;
    private int vyska;
    private Krivka[] krivky;
    private Random generator = new Random();
    private Timer casovac;
    private Kontrola kontrola;
    private long iterator = 0;
    private int pocetKriviek;
    private PanelSkore skore;
    private final int zaciatokVykreslovania = 100;
    private ManazerObrazkov manazerObrazkov;
    private boolean[] vstup = new boolean[256];
    private long intervalPrechadzaniaCezStenu = 0;
    private int pocetZivych;
    private final JPanel obsah;
    private final JPanel obsah1;

    /**
     * 
     * @param sirka šírka plátna
     * @param vyska výška plátna
     * @param hraci pole z ktorého sa inicializuju krivky
     * @param obsah najvyšší kontajner do ktorého sa vkladá grafický obsah
     * @param obsah1 kontajner, ktorý obsahuje hlavné munú
     */
    public Platno(int sirka, int vyska, Hrac[] hraci, JPanel obsah, JPanel obsah1) {
        super(null);
        this.sirka = sirka;
        this.vyska = vyska;
        this.setPreferredSize(new Dimension(sirka + 200, vyska));
        this.pocetKriviek = hraci.length;
        this.pocetZivych = this.pocetKriviek;
        super.setBackground(Color.BLACK);
        this.kontrola = new Kontrola(this);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.krivky = new Krivka[this.pocetKriviek];
        for (int i = 0; i < this.pocetKriviek; i++) {
            this.krivky[i] = new Krivka(hraci[i].dajMeno(), 250, 150 + 20 * i, 90 * i, hraci[i].dajFarbu(), 2, this.kontrola, i, hraci[i].dajOvladanie().getX(), hraci[i].dajOvladanie().getY());
        }
        this.skore = new PanelSkore(sirka, vyska, this.krivky);
        this.add(this.skore);
        this.manazerObrazkov = new ManazerObrazkov(this.kontrola, this.krivky, this);
        this.kontrola.setManazerObrazkov(this.manazerObrazkov);
        this.casovac = new Timer(10, this);
        this.casovac.setCoalesce(true);
        this.setDoubleBuffered(true);
        this.obsah = obsah;
        this.obsah1 = obsah1;
    }

    /**
     * Logika hry. Metóda, ktorá sa vykoná pri každom spustení časovača. <BR>
     * Generuje obrázky na plátno, posiela krivkám vstup z klávesnice. 
     * Kontroluje kolízie kriviek
     */
    public void krok() {
        if (this.iterator == this.manazerObrazkov.getCisloDoDalsiehoGenerovania()) {
            this.manazerObrazkov.nahodneVyberObrazkyNaKreslenie();
            this.manazerObrazkov.setCisloDoDalsiehoGenerovania(this.iterator);
        }
        for (int i = 0; i < this.krivky.length; i++) {
            this.krivky[i].citajVstup(this.vstup);
        }
        for (int i = 0; i < this.pocetKriviek; i++) {
            if (this.krivky[i].zije()) {
                this.krivky[i].vypocitajDalsieSuradnice();
                this.krivky[i].setIterator(this.iterator);
                if (this.iterator < this.zaciatokVykreslovania) {
                    if (!this.krivky[i].kontrolujBezUlozenia()) {
                        this.zvysSkore(i);
                        this.krivky[i].setUmrie();
                        this.pocetZivych--;
                        if (this.pocetZivych == 1) {
                            this.inicializuj();
                        }
                    }
                } else if (!this.krivky[i].polozSuradniceNaMapu()) {
                    this.zvysSkore(i);
                    this.krivky[i].setUmrie();
                    this.pocetZivych--;
                    if (this.pocetZivych == 1) {
                        this.inicializuj();
                    }
                }
            }
        }
        this.iterator++;
        this.repaint();
    }

    /**
     * Inicializuje krivke na ďalšie kole, ak bol dosihnutý cieľový počet bodov
     * tak vytvorí ponuku s výberom pre ďalšie pokračovanie v hre
     */
    private void inicializuj() {
        this.casovac.stop();
        if (!this.skore.koniecHry()) {
            this.iterator = 0;
            this.intervalPrechadzaniaCezStenu = 0;
            this.pocetZivych = this.pocetKriviek;
            this.kontrola.inicializuj();
            this.manazerObrazkov.nekresliZiadny();
            this.manazerObrazkov.setCisloDoDalsiehoGenerovania(200);
            for (int i = 0; i < this.krivky.length; i++) {
                this.krivky[i].nadstavX(20 + Platno.this.generator.nextInt(((Platno.this.sirka - 100) / Platno.this.pocetKriviek)) * (i + 1));
                this.krivky[i].nadstavY(20 + Platno.this.generator.nextInt(((Platno.this.vyska - 100) / Platno.this.pocetKriviek)) * (i + 1));
                this.krivky[i].setUhol(this.generator.nextInt(300));
                this.krivky[i].inicializuj();
            }
        } else {
            this.removeKeyListener(this);
            this.removeAll();
            this.addMouseListener(new Myska());
            this.skore.zobrazVyhodnotenie();
            this.obsah.revalidate();
            this.obsah.repaint();
        }
    }

    /**
     * Zvyšuje skóre krivkám, ktoré sú nažive
     * @param číslo krivky, ktorej skóre nemá zvýšiť 
     */
    private void zvysSkore(int i) {
        for (int j = 0; j < this.krivky.length; j++) {
            if (j != i && this.krivky[i].zije()) {
                this.krivky[j].zvysSkore();
            }
        }
    }

    /**
     * 
     * @return širka plátna
     */
    public int getSirka() {
        return this.sirka;
    }

    /**
     * 
     * @return výška plátna
     */
    public int getVyska() {
        return this.vyska;
    }

    /**
     * Určí interval, v ktorom stena bude "blikať" <BR>
     * vykazovanie priechodnosti kriviek má na starosti iná trieda
     * @param intervalPrechadzaniaCezStenu 
     */
    public void setIntervalPrechadzaniaCezStenu(long intervalPrechadzaniaCezStenu) {
        this.intervalPrechadzaniaCezStenu = intervalPrechadzaniaCezStenu + this.iterator;
    }
    
/**
 * Vykresľuje prvky na plátno v nasledujúcom poradí<BR>
 * 1.Stena<BR>
 * 2.Obrázky predstavujúce bonusy<BR>
 * 3.Krivky <BR>
 * 4.Skóre
 * @param g Grafika 
 */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D grafika2D = (Graphics2D) g;
        if (this.iterator < this.zaciatokVykreslovania) {
            super.paintComponent(g);
        }
        if (this.iterator < this.intervalPrechadzaniaCezStenu) {
            if (this.iterator % 40 > 20) {
                grafika2D.setColor(Color.ORANGE);
            } else {
                grafika2D.setColor(Color.BLACK);
            }
        } else {
            grafika2D.setColor(Color.ORANGE);
        }
        grafika2D.setStroke(new BasicStroke(4));
        grafika2D.drawRect(2, 0, this.sirka - 2, this.vyska - 2);
        this.manazerObrazkov.kresli(g);
        for (int i = 0; i < this.pocetKriviek; i++) {
            this.krivky[i].paintComponent(g);
        }
        this.skore.paintComponent(g);
        grafika2D.dispose();
    }

    /**
     * Metóda, ktorá sa vykoná pri každom spustení časovača
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.krok();
    }

    /**
     * Prázdna metóda z dôvodu použitia interfacu KeyListener
     * @param e 
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Metóda reaguje na stlačenie klávesy
     * @param e 
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode > 0 && keyCode < 256) {
            this.vstup[keyCode] = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (this.casovac.isRunning()) {
                this.casovac.stop();
                //System.out.println(kontrola);
            } else {
                this.casovac.start();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    /**
     * Metóda reaguje na opustenie klávesu
     * @param e 
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode > 0 && keyCode < 256) {
            this.vstup[keyCode] = false;
        }
    }

    /**
     * @return čas v hre 
     */
    public long getIterator() {
        return this.iterator;
    }
    
    /**
     * Vnorená trieda, ktorá reaguje na udalosti typu MouseEvent, vytvára ponuku po skončení hry 
     */

    private class Myska extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            if ((x >= Platno.this.sirka + 4 && x <= Platno.this.sirka + 125) && (y >= 230 && y < 255)) {
                Platno.this.obsah.removeAll();
                Platno.this.obsah.add(Platno.this.obsah1);
                Platno.this.obsah.revalidate();
                Platno.this.obsah.repaint();
            }
            if ((x >= Platno.this.sirka + 4 && x <= Platno.this.sirka + 125) && (y >= 270 && y < 296)) {
                if (Platno.this.skore.zapisDoSuboru()) {
                    JOptionPane.showMessageDialog(Platno.this, "Úspešne uložené", "Stav uloženia", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(Platno.this, "Uloženie zlyhalo", "Stav uloženia", JOptionPane.WARNING_MESSAGE);
                }
            }
            if ((x >= Platno.this.sirka + 4 && x <= Platno.this.sirka + 125) && (y >= 310 && y < 336)) {
                Platno.this.iterator = 0;
                Platno.this.intervalPrechadzaniaCezStenu = 0;
                Platno.this.pocetZivych = Platno.this.pocetKriviek;
                Platno.this.kontrola.inicializuj();
                Platno.this.manazerObrazkov.nekresliZiadny();
                Platno.this.manazerObrazkov.setCisloDoDalsiehoGenerovania(200);
                for (int i = 0; i < Platno.this.krivky.length; i++) {
                    Platno.this.krivky[i].nadstavX(20 + Platno.this.generator.nextInt(((Platno.this.sirka - 100) / Platno.this.pocetKriviek)) * (i + 1));
                    Platno.this.krivky[i].nadstavY(20 + Platno.this.generator.nextInt(((Platno.this.vyska - 100) / Platno.this.pocetKriviek)) * (i + 1));
                    Platno.this.krivky[i].setUhol(Platno.this.generator.nextInt(300));
                    Platno.this.krivky[i].inicializuj();
                    for (int j = 0; j < Platno.this.vstup.length; j++) {
                        Platno.this.vstup[j] = false;
                    }
                    Platno.this.krivky[i].setSkore(0);
                }
                Platno.this.skore.setKrivky(Platno.this.krivky);
                Platno.this.addKeyListener(Platno.this);
                Platno.this.removeMouseListener(this);
                Platno.this.repaint();
            }
            if ((x >= Platno.this.sirka + 4 && x <= Platno.this.sirka + 125) && (y >= 350 && y < 376)) {
                JOptionPane.showMessageDialog(Platno.this, "Dovidenia!", "Koniec", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        }
    }

}
