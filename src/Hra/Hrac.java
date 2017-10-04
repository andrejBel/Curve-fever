
package Hra;

import Vynimky.PrazdnyVstupException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Trieda vytvára panel, cez ktorý používatelia zadávajú informácie o sebe(meno),
 * svojej krivke(farbu krivky) a ovládaní krivky (tlačidlá pre ovládanie doprava doľava)
 * @author Andrej Beliancin
 */
public class Hrac extends JPanel {

    private int cisloHraca;
    private JTextField menoHraca;
    private JTextField vlavo;
    private JTextField vpravo;
    private int cisloVlavo;
    private int cisloVPravo;
    private final String[] poleFarieb = {"Modrá", "Zelená", "Červená", "Ružová", "Azúrová", "Orandžová", "Fialová", "Biela", "Žltá"};
    private JComboBox<String> farby;
    
    /**
     * Konštruktor vytvorí JPanel, v ktorom sa dá nadstaviť meno hráča, 
     * farba jeho krivky a ovládanie krivky 
     * @param cisloHraca číslo hráča v kontajneri kriviek
     * @param sirka šírka panelu, výška je pevne daná(100px)
     */
    public Hrac(int cisloHraca, int sirka) {
        super(new FlowLayout(FlowLayout.LEFT));
        this.cisloVlavo = 0;
        this.cisloVPravo = 0;
        super.setBackground(Color.YELLOW);
        super.setPreferredSize(new Dimension(sirka, 100));
        super.setDoubleBuffered(false);
        this.cisloHraca = cisloHraca;
        this.farby = new JComboBox<String>(this.poleFarieb);
        this.farby.setSelectedIndex(this.cisloHraca - 1);
        this.menoHraca = new JTextField("Zadajte meno");
        this.add(new JLabel("Hráč č. " + this.cisloHraca + " "));
        this.add(this.menoHraca);
        this.add(new JLabel("Farba "));
        this.add(this.farby);
        this.vlavo = new JTextField();
        this.vlavo.setPreferredSize(new Dimension(100, 20));
        this.vlavo.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                Hrac.this.cisloVlavo = e.getKeyCode();
                Hrac.this.vlavo.setText(KeyEvent.getKeyText(Hrac.this.cisloVlavo));
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        this.add(new JLabel("Ovládanie: Vľavo"));
        this.add(this.vlavo);

        this.vpravo = new JTextField();
        this.vpravo.setPreferredSize(new Dimension(100, 20));
        this.vpravo.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                Hrac.this.cisloVPravo = e.getKeyCode();
                Hrac.this.vpravo.setText(KeyEvent.getKeyText(Hrac.this.cisloVPravo));
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        this.add(new JLabel("Vpravo"));
        this.add(this.vpravo);
        switch (this.cisloHraca) {
            case 1: {
                this.vlavo.setText("Left");
                this.vpravo.setText("Right");
                this.cisloVPravo = 39;
                this.cisloVlavo = 37;
                break;
            }
            case 2: {
                this.vlavo.setText("A");
                this.vpravo.setText("D");
                this.cisloVPravo = 68;
                this.cisloVlavo = 65;
                break;
            }
            case 3: {
                this.vlavo.setText("J");
                this.vpravo.setText("L");
                this.cisloVPravo = 76;
                this.cisloVlavo = 74;
                break;
            }
            case 4: {
                this.vlavo.setText("V");
                this.vpravo.setText("N");
                this.cisloVPravo = 78;
                this.cisloVlavo = 86;
                break;
            }
        }
    }

    /**
     * 
     * @return keykódy klávies, ktoré slúžia na ovládanie krivky
     */
    public Bod dajOvladanie() {
        return new Bod(this.cisloVPravo, this.cisloVlavo);
    }

    /**
     * 
     * @return farbu krivky vybranú používateľom
     */
    public Color dajFarbu() {
        switch ((String)this.farby.getSelectedItem()) {
            case "Modrá": {
                return Color.BLUE;
            }
            case "Zelená": {
                return Color.GREEN;
            }
            case "Červená": {
                return Color.RED;
            }
            case "Ružová": {
                return Color.PINK;
            }
            case "Azúrová": {
                return Color.CYAN;
            }
            case "Orandžová": {
                return Color.ORANGE;
            }
            case "Fialová": {
                return Color.MAGENTA;
            }
            case "Biela": {
                return Color.WHITE;
            }
            case "Žltá": {
                return Color.YELLOW;
            }
            default: {
                return Color.GREEN;
            }
        }
    }

    /**
     * 
     * @return meno hráča
     */
    public String dajMeno() {
        if (this.menoHraca.getText() == null || this.menoHraca.getText().isEmpty() || this.menoHraca.getText().equals("Zadajte meno")) {
            throw new PrazdnyVstupException(this.cisloHraca);
        }
        String meno = this.menoHraca.getText();
        if (this.menoHraca.getText().length() > 11) {
            meno = this.menoHraca.getText().substring(0, 11);
        }
        return meno;
    }



}
