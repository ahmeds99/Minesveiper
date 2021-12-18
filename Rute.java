import java.util.*;

public class Rute {
    private Minefelt minefelt;
    private int rad, kolonne;
    private boolean erBombe = false;
    private ArrayList <Rute> naboer = new ArrayList <>();

    public Rute(Minefelt felt, int ra, int ko) {
        minefelt = felt;
        rad = ra;
        kolonne = ko;
    }

    public int hentBombeNaboer() {
        int antallBombeNaboer = 0;

        for (Rute rute : naboer) {
            int rad = rute.hentRad();
            int kolonne = rute.hentKolonne();
            RuteKnapp nabo = Minesveiper.rutenett[rad][kolonne];

            if (rute.erBombe()) {
                antallBombeNaboer++;
            }
        }
        return antallBombeNaboer;
    }

    public void omgjorTilBombe() {
        erBombe = true;
    }

    public boolean erBombe() {
        return erBombe;
    }

    public void leggTilNabo(Rute nabo) {
        naboer.add(nabo);
    }

    public ArrayList <Rute> hentNaboer() {
        return naboer;
    }

    public int hentRad() {
        return rad;
    }

    public int hentKolonne() {
        return kolonne;
    }
}
