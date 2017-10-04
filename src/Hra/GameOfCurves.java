package Hra;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * Trieda predstavuje spúšťač aplikácie
 * @author Andrej Beliancin
 */
public class GameOfCurves {
    private static Dimension rozmer;
    /**
     * Metóda vytvorí okno, v ktorom si používateľ vyberie rozmer okna pre hru
     * @param args
     */
    public static void main(String[] args) {
        JFrame vstup = new JFrame("Veľkosť");
        JPanel obsah = null;
        GameOfCurves.rozmer = new Dimension(800, 600);
        obsah = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JRadioButton rozmer1 = new JRadioButton("800x600");
        JRadioButton rozmer2 = new JRadioButton("1000x800");
        rozmer1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameOfCurves.rozmer = new Dimension(800, 600);
            }
        });
        rozmer2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameOfCurves.rozmer = new Dimension(1000, 800);
            }
        });
        rozmer1.setBackground(Color.CYAN);
        rozmer2.setBackground(Color.CYAN);
        ButtonGroup tlacidla = new ButtonGroup();
        rozmer1.setSelected(true);
        tlacidla.add(rozmer1);
        tlacidla.add(rozmer2);
        obsah.add(rozmer1);
        obsah.add(rozmer2);
        JButton potvrdenie = new JButton("Potvrď");
        potvrdenie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    Okno okno = new Okno(GameOfCurves.rozmer);
                    okno.zobraz();
                vstup.dispose();
            }
        });
        obsah.add(potvrdenie);
        obsah.setPreferredSize(new Dimension(200, 100));
        obsah.setBackground(Color.CYAN);
        vstup.setDefaultCloseOperation(EXIT_ON_CLOSE);
        vstup.add(obsah);
        vstup.setVisible(true);
        vstup.pack();
        vstup.setLocationRelativeTo(null);
        vstup.setResizable(false);
        vstup.toFront();

    }

}
