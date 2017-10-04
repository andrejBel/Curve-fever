package Hra;

import Obrazky.ManazerObrazkov;
import java.util.ArrayList;

/**
 * Trieda reprezentuje stavovú mapu hry a zabezpečuje kontrolu kolízii, či už
 * hráča s hráčom, hráča so stenou alebo hráča s bonusom
 *
 * @author Andrej Beliančín
 */
public class Kontrola {

    private Stav[][] mapaHry;
    private int pocetRiadkov;
    private int pocetStlpcov;
    private ArrayList<Bod> steny = new ArrayList<Bod>();
    private ManazerObrazkov manazerObrazkov;

    /**
     * Inicializuje počiatočný stav mapy hry
     *
     * @param platno Platno, podla ktorého sa nadstaví šírka a výška mapy hry,
     * na žiadne iné účely nie je potrebné
     */
    public Kontrola(Platno platno) {
        this.pocetRiadkov = platno.getVyska();
        this.pocetStlpcov = platno.getSirka();
        this.mapaHry = new Stav[this.pocetStlpcov][this.pocetRiadkov];
        this.inicializuj();
    }

    /**
     *
     * @return počet riadkov, ktorá ma mapa hry
     */
    public int getPocetRiadkov() {
        return this.pocetRiadkov;
    }

    /**
     *
     * @return počet stĺpcov, ktoré má mapa hry
     */
    public int getPocetStlpcov() {
        return this.pocetStlpcov;
    }

    /**
     *
     * @param manazer nadstaví manažéra obrázkov
     */
    public void setManazerObrazkov(ManazerObrazkov manazer) {
        this.manazerObrazkov = manazer;
    }

    /**
     * Na mapu hry položí bonus(stav buniek mapy hry sa zmení na BONUS)
     *
     * @param x1 x-ová súradnica ľavej hornej súradnice, na ktorú sa položí
     * bonus
     * @param y1 y-ová súradnica ľavej hornej súradnice, na ktorú sa položí
     * bonus
     * @param x2 x-ová súradnica pravej dolnej súradnice, na ktorú sa položí
     * bonus
     * @param y2 y-ová súradnica pravej dolnej súradnice, na ktorú sa položí
     * bonus
     */
    public void zaplnMapuBonusom(int x1, int y1, int x2, int y2) {
        for (int i = x1; i < x2; i++) {
            for (int j = y1; j < y2; j++) {
                if (this.mapaHry[i][j] != Stav.PLNA) {
                    this.mapaHry[i][j] = Stav.BONUS;
                }
            }
        }
    }

    /**
     * Z mapy hry sa odstráni bonus
     *
     * @param x1 x-ová súradnica ľavej hornej súradnice, z ktorej sa odstráni
     * bonus
     * @param y1 y-ová súradnica ľavej hornej súradnice, z ktorej sa odstráni
     * bonus
     * @param x2 x-ová súradnica pravej dolnej súradnice, z ktorej sa odstráni
     * bonus
     * @param y2 y-ová súradnica pravej dolnej súradnice, z ktorej sa odstráni
     * bonus
     */
    public void odstranBonusZMapy(int x1, int y1, int x2, int y2) {
        for (int i = x1; i < x2; i++) {
            for (int j = y1; j < y2; j++) {
                if (this.mapaHry[i][j] != Stav.PLNA) {
                    this.mapaHry[i][j] = Stav.PRAZDNA;
                }
            }
        }
    }

    /**
     * Zistí, či je zadaná plocha voľná
     *
     * @param x1 x-ová súradnica ľavej hornej súradnice plochy
     * @param y1 y-ová súradnica ľavej hornej súradnice plochy
     * @param x2 x-ová súradnica pravej dolnej súradnice plochy
     * @param y2 y-ová súradnica pravej dolnej súradnice plochy
     */
    public boolean jeVolnaPlocha(int x1, int y1, int x2, int y2) {
        for (int i = x1; i < x2; i++) {
            for (int j = y1; j < y2; j++) {
                if ((this.mapaHry[i][j] == Stav.PLNA) || (this.mapaHry[i][j] == Stav.BONUS)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Inicializuje mapu hry do počiatočného stavu stavu
     */
    public void inicializuj() {
        for (int i = 0; i < this.pocetStlpcov; i++) {
            for (int j = 0; j < this.pocetRiadkov; j++) {
                if (i == 0 || (i == this.pocetStlpcov - 1) || j == 0 || (j == this.pocetRiadkov - 1)) {
                    this.mapaHry[i][j] = Stav.PLNA;
                    this.steny.add(new Bod(i, j));
                } else if (i == 1 || (i == this.pocetStlpcov - 2) || j == 1 || (j == this.pocetRiadkov - 2)) {
                    this.mapaHry[i][j] = Stav.PLNA;
                    this.steny.add(new Bod(i, j));
                } else {
                    this.mapaHry[i][j] = Stav.PRAZDNA;
                }
            }
        }
    }

    /**
     * obnoví stenu z mapy hry
     */
    public void obnovStenu() {
        for (int i = 0; i < this.steny.size(); i++) {
            this.mapaHry[this.steny.get(i).getX()][this.steny.get(i).getY()] = Stav.PLNA;
        }
    }

    /**
     * odstráni stenu z mapy hry
     */
    public void odstranStenu() {
        for (int i = 0; i < this.steny.size(); i++) {
            this.mapaHry[this.steny.get(i).getX()][this.steny.get(i).getY()] = Stav.PRAZDNA;
        }
    }

    /**
     *
     * @param x x-ová súradnica bodu, ktorý ukladám na mapu hry bez ukladania
     * súradníc na mapu x
     * @param y y-ová súradnica bodu, ktorý ukladám na mapu hry bez kontroly
     * kolízie
     * @return Ak sa bod dostane do kolízir, vráti true, inak false
     */
    public boolean kontrolujBezUkladania(int x, int y) {
        if ((x > 0 && x < this.mapaHry.length) && (y > 0 && y < this.mapaHry.length)) {
            return (this.mapaHry[x][y] != Stav.PLNA);
        }
        return false;
    }

    /**
     * Metóda, ktorá zabezpečuje položenie bodu na mapu,<BR>
     * stav bunky mapy hry sa zmení na PLNA,<BR>
     * kontroluje, či nedošlo ku kolízii s krivkami(bunky mapy hry, ktoré majú
     * stav plná) <BR>
     * alebo bonusom
     *
     * @param x1 x-ová súradnica aktuálnej pozície krivky
     * @param y1 y-ová súradnica aktuálnej pozície krivky
     * @param x2 x-ová súradnica predchádzajúcej pozície krivky
     * @param y2 y-ová súradnica predchádzajúcej pozície krivky
     * @param krivka krivka, ktorej súradnice sa mahú položi´t na mapu hry
     * @return
     */
    public boolean setNavstiveny(int x1, int y1, int x2, int y2, Krivka krivka) {
        if ((x1 > 0 && x1 < this.mapaHry.length) && (y1 > 0 && y1 < this.mapaHry.length) && (x2 > 0 && x2 < this.mapaHry.length) && (y2 > 0 && y2 < this.mapaHry.length)) {
            if ((x1 != x2) || (y1 != y2)) {
                if (this.mapaHry[x1][y1] == Stav.PLNA) {
                    this.mapaHry[x1][y1] = Stav.DRUHYKRAT;
                    return false;
                } else {
                    if (this.mapaHry[x1][y1] == Stav.BONUS) {
                        this.manazerObrazkov.ktoryBonusVykonat(x1, y1, krivka.getCisloVKontajneri());
                    }
                    this.mapaHry[x1][y1] = Stav.PLNA;
                    if (((x1 < x2) && (y1 > y2)) || ((x1 < x2) && (y1 < y2))) {
                        this.mapaHry[x1 + 1][y1] = Stav.PLNA;
                        this.mapaHry[x1 + 2][y1] = Stav.PLNA;
                    } else if (((x1 > x2) && (y1 > y2)) || ((x1 > x2) && (y1 < y2))) {
                        this.mapaHry[x1 - 1][y1] = Stav.PLNA;
                        this.mapaHry[x1 - 2][y1] = Stav.PLNA;
                    }
                    return true;
                }
            }
            return true;
        }
        return true;
    }

    /**
     * Metóda, ktorá zabezpečuje položenie bodu na mapu,<BR>
     * stav bunky mapy hry sa zmení na PLNA,<BR>
     * kontroluje, či nedošlo ku kolízii s bonusom
     *
     * @param x1 x-ová súradnica aktuálnej pozície krivky
     * @param y1 y-ová súradnica aktuálnej pozície krivky
     * @param x2 x-ová súradnica predchádzajúcej pozície krivky
     * @param y2 y-ová súradnica predchádzajúcej pozície krivky
     * @param krivka krivka, ktorej súradnice sa mahú položi´t na mapu hry
     * @return
     */
    public boolean setNavstivenyBezKontrolySuradnic(int x1, int y1, int x2, int y2, Krivka krivka) {
        if ((x1 > 0 && x1 < this.mapaHry.length) && (y1 > 0 && y1 < this.mapaHry.length) && (x2 > 0 && x2 < this.mapaHry.length) && (y2 > 0 && y2 < this.mapaHry.length)) {
            if ((x1 != x2) || (y1 != y2)) {
                if (this.mapaHry[x1][y1] == Stav.BONUS) {
                    this.manazerObrazkov.ktoryBonusVykonat(x1, y1, krivka.getCisloVKontajneri());
                }
                this.mapaHry[x1][y1] = Stav.PLNA;
                if (((x1 < x2) && (y1 > y2)) || ((x1 < x2) && (y1 < y2))) {
                    this.mapaHry[x1 + 1][y1] = Stav.PLNA;
                    this.mapaHry[x1 + 2][y1] = Stav.PLNA;
                } else if (((x1 > x2) && (y1 > y2)) || ((x1 > x2) && (y1 < y2))) {
                    this.mapaHry[x1 - 1][y1] = Stav.PLNA;
                    this.mapaHry[x1 - 2][y1] = Stav.PLNA;
                }
            }
            return true;
        }
        return false;
    }

    /**
     *
     * @return Textová reprezentácia mapy hry
     */
    public String toString() {
        String retazec = "";
        for (int i = 0; i < this.mapaHry[0].length; i++) {
            for (int j = 0; j < this.mapaHry.length; j++) {
                if (this.mapaHry[j][i] == Stav.PRAZDNA) {
                    retazec += " ";
                } else if (this.mapaHry[j][i] == Stav.PLNA) {
                    retazec += "*";
                } else if (this.mapaHry[j][i] == Stav.DRUHYKRAT) {
                    retazec += "2";
                } else if (this.mapaHry[j][i] == Stav.AKTUALNY) {
                    retazec += "A";
                } else {
                    retazec += "B";
                }
            }
            retazec = retazec + "\n";
        }
        return retazec;
    }

}
