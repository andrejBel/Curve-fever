package Hra;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Trieda na zápis a čítanie súboru <br>
 * Používa dátový súbor
 * @author Andrej Beliancin
 */
public class Parser {

    /**
     * 
     * @return Zapísane informácie o krivkách(Meno a počet nahraných bodov)
     * @throws IOException 
     */
    public static ArrayList<String> citajDataZoSuboru() throws IOException {
        boolean koniec = false;
        File subor = null;
        FileInputStream stream = null;
        DataInputStream obalovac = null;
        ArrayList<String> nacitaneData = new ArrayList<>();
        try {
            subor = new File("info.dat");
            stream = new FileInputStream(subor);
            obalovac = new DataInputStream(stream);
            while (!koniec) {
                String data = "";
                int k = obalovac.readInt();
                for (int i = 0; i < k; i++) {
                    char znak = obalovac.readChar();
                    data += znak;
                }
                nacitaneData.add(data);
            }
        } catch (EOFException ex) {
            koniec = true;
        } catch (FileNotFoundException e) {
            nacitaneData.add("Súbor so skóre neexistuje");
        } finally {
            if (obalovac != null) {
                obalovac.close();
            }
            return nacitaneData;
        }

    }

    /**
     * Zapíše krivky do súboru
     * @param krivky
     * @throws IOException 
     */
    public static void zapisDoSuboru(Krivka[] krivky) throws IOException {
        File subor = null;
        FileOutputStream stream = null;
        DataOutputStream obalovac = null;
        try {
            subor = new File("info.dat");
            stream = new FileOutputStream(subor);
            obalovac = new DataOutputStream(stream);
            for (int i = 0; i < krivky.length; i++) {
                obalovac.writeInt(krivky[i].getMeno().length() + 1 + Integer.toString(krivky[i].getSkore()).length());
                obalovac.writeChars(krivky[i].getMeno() + " " + krivky[i].getSkore());
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (obalovac != null) {
                obalovac.close();
            }
        }
    }

}
