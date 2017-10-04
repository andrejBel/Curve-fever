package Hra;

import Vynimky.PrazdnyVstupException;
import Vynimky.RovnakeFarbyException;
import Vynimky.RovnakeKlavesyException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Trieda reprezentuje okno hry, zobrazuje základné menú, pomoc, údaje načítané
 * zo súboru,nadstavenia hry a samotnú hru
 *
 * @author Andrej Beliancin
 */
public class Okno {

    private final Dimension ROZMER;
    private JFrame okno;

    public Okno(Dimension rozmer) {
        this.okno = new JFrame("Games of curves");
        this.ROZMER = rozmer;
        this.okno.setResizable(false);
        this.okno.setLocation(100, 100);
        this.okno.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel hlavnyKontajner = new JPanel();
        hlavnyKontajner.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        hlavnyKontajner.setPreferredSize(this.ROZMER);
        JPanel obsahMenu = new JPanel();

        JPanel obsahVyberPoctuHracov = new JPanel();

        JPanel obsahVypisSkore = new JPanel();

        JPanel obsahPomoc = new JPanel();

        obsahMenu.setLayout(new BoxLayout(obsahMenu, BoxLayout.Y_AXIS));
        obsahMenu.setPreferredSize(this.ROZMER);
        obsahMenu.setBackground(Color.GREEN);

        obsahVyberPoctuHracov.setLayout(new FlowLayout(FlowLayout.LEFT));
        obsahVyberPoctuHracov.setPreferredSize(this.ROZMER);
        obsahVyberPoctuHracov.setBackground(Color.RED);

        obsahVypisSkore.setLayout(new BoxLayout(obsahVypisSkore, BoxLayout.Y_AXIS));
        obsahVypisSkore.setPreferredSize(this.ROZMER);
        obsahVypisSkore.setBackground(Color.BLUE);

        obsahPomoc.setLayout(new BoxLayout(obsahPomoc, BoxLayout.Y_AXIS));
        obsahPomoc.setPreferredSize(this.ROZMER);
        obsahPomoc.setBackground(Color.ORANGE);

        JButton tlacidloHraj = new JButton("Hraj");
        tlacidloHraj.setAlignmentX(Component.CENTER_ALIGNMENT);
        tlacidloHraj.setMaximumSize(new Dimension(100, 40));

        JButton tlacidloNacitajSkore = new JButton("Skóre");
        tlacidloNacitajSkore.setAlignmentX(Component.CENTER_ALIGNMENT);
        tlacidloNacitajSkore.setMaximumSize(new Dimension(100, 40));

        JButton tlacidloPomoc = new JButton("Pomoc");
        tlacidloPomoc.setAlignmentX(Component.CENTER_ALIGNMENT);
        tlacidloPomoc.setMaximumSize(new Dimension(100, 40));

        JButton tlacidloUkonci = new JButton("Ukonči");
        tlacidloUkonci.setAlignmentX(Component.CENTER_ALIGNMENT);
        tlacidloUkonci.setMaximumSize(new Dimension(100, 40));

        tlacidloHraj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hlavnyKontajner.removeAll();
                hlavnyKontajner.add(obsahVyberPoctuHracov);
                hlavnyKontajner.revalidate();
                hlavnyKontajner.repaint();
            }
        });

        tlacidloNacitajSkore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hlavnyKontajner.removeAll();
                ArrayList<String> zoznamHracov = null;
                try {
                    zoznamHracov = Parser.citajDataZoSuboru();
                } catch (IOException ex) {
                    System.out.println("Subor so skore nenajdeny");
                }
                JButton tlacidloSpat = new JButton("Späť");
                tlacidloSpat.setAlignmentX(Component.CENTER_ALIGNMENT);
                tlacidloSpat.setMaximumSize(new Dimension(100, 40));
                tlacidloSpat.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        obsahVypisSkore.removeAll();
                        hlavnyKontajner.removeAll();
                        hlavnyKontajner.add(obsahMenu);

                        hlavnyKontajner.revalidate();
                        hlavnyKontajner.repaint();
                    }
                });
                obsahVypisSkore.add(new VypisSkore(zoznamHracov));
                obsahVypisSkore.add(tlacidloSpat);
                hlavnyKontajner.add(obsahVypisSkore);
                hlavnyKontajner.revalidate();
                hlavnyKontajner.repaint();
            }
        });

        tlacidloPomoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hlavnyKontajner.removeAll();
                JButton tlacidloSpat = new JButton("Späť");
                tlacidloSpat.setAlignmentX(Component.CENTER_ALIGNMENT);
                tlacidloSpat.setMaximumSize(new Dimension(100, 40));
                tlacidloSpat.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        obsahPomoc.removeAll();
                        hlavnyKontajner.removeAll();
                        hlavnyKontajner.add(obsahMenu);

                        hlavnyKontajner.revalidate();
                        hlavnyKontajner.repaint();
                    }
                });
                obsahPomoc.add(new VypisPomoc());
                obsahPomoc.add(tlacidloSpat);
                hlavnyKontajner.add(obsahPomoc);
                hlavnyKontajner.revalidate();
                hlavnyKontajner.repaint();
            }
        });

        tlacidloUkonci.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(Okno.this.okno,
                        "Naozaj chcete opustiť hru?", "Ukoncenie",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE
                ) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
                Okno.this.okno.repaint();
            }
        });

        this.okno.add(hlavnyKontajner);

        hlavnyKontajner.add(obsahMenu);
        obsahMenu.add(Box.createRigidArea(new Dimension(50, 100)));
        obsahMenu.add(tlacidloHraj);
        obsahMenu.add(Box.createRigidArea(new Dimension(50, 50)));
        obsahMenu.add(tlacidloNacitajSkore);
        obsahMenu.add(Box.createRigidArea(new Dimension(50, 50)));
        obsahMenu.add(tlacidloPomoc);
        obsahMenu.add(Box.createRigidArea(new Dimension(50, 50)));
        obsahMenu.add(tlacidloUkonci);

        Integer[] pocetHracov = {2, 3, 4};
        JComboBox<Integer> vyberPoctuHracov = new JComboBox<Integer>(pocetHracov);
        JButton tlacidloPotvrdenie = new JButton("Potvrď");
        tlacidloPotvrdenie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hlavnyKontajner.removeAll();
                Hrac[] hraci = new Hrac[(int) vyberPoctuHracov.getSelectedItem()];
                for (int i = 0; i < (int) vyberPoctuHracov.getSelectedItem(); i++) {
                    hraci[i] = new Hrac(i + 1, (int) Okno.this.ROZMER.getWidth());
                    hlavnyKontajner.add(hraci[i]);
                }
                hlavnyKontajner.setBackground(Color.red);
                JButton tlacidloSpusti = new JButton("Spusti");
                hlavnyKontajner.add(tlacidloSpusti);
                tlacidloSpusti.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        try {
                            Okno.this.kontrolaMien(hraci);
                            Okno.this.kontrolaFarieb(hraci);
                            Okno.this.kontrolaOvladania(hraci);
                            hlavnyKontajner.removeAll();
                            Platno platno = new Platno((int) Okno.this.ROZMER.getHeight(), (int) Okno.this.ROZMER.getHeight(), hraci, hlavnyKontajner, obsahMenu);
                            hlavnyKontajner.add(platno);
                            platno.requestFocusInWindow();
                            hlavnyKontajner.revalidate();
                            hlavnyKontajner.repaint();
                        } catch (PrazdnyVstupException ex) {
                            JOptionPane.showMessageDialog(Okno.this.okno, ex.getMessage(), "Nesprávny vstup", JOptionPane.WARNING_MESSAGE);
                        } catch (RovnakeFarbyException exce) {
                            JOptionPane.showMessageDialog(Okno.this.okno, exce.getMessage(), "Nesprávny vstup", JOptionPane.WARNING_MESSAGE);
                        } catch (RovnakeKlavesyException exc) {
                            JOptionPane.showMessageDialog(Okno.this.okno, exc.getMessage(), "Nesprávny vstup", JOptionPane.WARNING_MESSAGE);
                        }

                    }
                });
                hlavnyKontajner.revalidate();
                hlavnyKontajner.repaint();
            }
        });
        obsahVyberPoctuHracov.add(new JLabel("Zadajte počet hráčov:  "));
        obsahVyberPoctuHracov.add(vyberPoctuHracov);
        obsahVyberPoctuHracov.add(tlacidloPotvrdenie);
        this.okno.pack();
        this.okno.toFront();
    }

    /**
     * Zobrazí okno
     */
    public void zobraz() {
        this.okno.setVisible(true);
    }

    /**
     * Skontroluje, či je vstup mien od používateĺov platný
     *
     * @param hraci vstup od používateľov
     */
    private void kontrolaMien(Hrac[] hraci) {
        for (Hrac hraci1 : hraci) {
            hraci1.dajMeno();
        }
    }

    /**
     * Skontroluje, či hráči nemajú rovnaké klávesy pre ovládanie alebo jeden
     * hráč rovnaké
     *
     * @param hraci vstup od používateľov
     */
    private void kontrolaOvladania(Hrac[] hraci) {
        ArrayList<Integer> tlacidla = new ArrayList<>();
        for (int i = 0; i < hraci.length; i++) {
            if (!tlacidla.contains(hraci[i].dajOvladanie().getX())) {
                tlacidla.add(hraci[i].dajOvladanie().getX());
            } else {
                throw new RovnakeKlavesyException();
            }
            if (!tlacidla.contains(hraci[i].dajOvladanie().getY())) {
                tlacidla.add(hraci[i].dajOvladanie().getY());
            } else {
                throw new RovnakeKlavesyException();
            }
        }
    }

    /**
     * Skontroluje, či krivky hráčov nemajú nadstavenú rovnakú farbu
     *
     * @param hraci vstup od používateľov
     */
    private void kontrolaFarieb(Hrac[] hraci) {
        ArrayList<Color> farby = new ArrayList<Color>();
        for (int i = 0; i < hraci.length; i++) {
            if (!farby.contains(hraci[i].dajFarbu())) {
                farby.add(hraci[i].dajFarbu());
            } else {
                throw new RovnakeFarbyException(i + 1);
            }
        }
    }

    /**
     * Panel pre výpis skóre zo súboru
     */
    private class VypisSkore extends JPanel {

        ArrayList<String> hraci;

        public VypisSkore(ArrayList<String> hraci) {
            this.setBackground(Color.BLUE);
            this.hraci = hraci;
            this.setMaximumSize(new Dimension(Okno.this.ROZMER.width, 80 + (hraci.size() * 30)));
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D grafika2D = (Graphics2D) g;
            grafika2D.setColor(Color.BLACK);
            Font font = new Font("Arial", Font.PLAIN, 20);
            grafika2D.setFont(font);
            for (int i = 0; i < this.hraci.size(); i++) {
                if (this.hraci.size() == 1) {
                    grafika2D.drawString(hraci.get(i), Okno.this.ROZMER.width / 2 - 50, 100 + 25 * i);
                } else {
                    grafika2D.drawString((i + 1) + ". " + hraci.get(i), Okno.this.ROZMER.width / 2 - 50, 100 + 25 * i);
                }
            }
        }

    }

    /**
     * Vypíše pomoc na okno
     */
    private class VypisPomoc extends JPanel {

        public VypisPomoc() {
            this.setBackground(Color.ORANGE);
            this.setMaximumSize(new Dimension(Okno.this.ROZMER.width, Okno.this.ROZMER.height / 2));
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D grafika2D = (Graphics2D) g;
            grafika2D.setColor(Color.BLACK);
            Font font = new Font("Arial", Font.PLAIN, 20);
            grafika2D.setFont(font);
            grafika2D.drawString("Ovládanie v hre: ", 20, 20);
            grafika2D.drawString("Každý hráč si vyberia klávesy na ovládanie svojej krivky, ktorou kontroluje", 20, 50);
            grafika2D.drawString("smer jej pohybu", 20, 80);
            grafika2D.drawString("Medzerník - štart/pauza", 20, 110);
            grafika2D.drawString("Esc - okamžitý koniec", 20, 140);
            grafika2D.drawString("Cieľom hry je zomrieť menej krát, ako Vaši súperi", 20, 170);
            grafika2D.drawString("Radšej raz hraj ako čítaj pomoc", 20, 200);
        }

    }

}
