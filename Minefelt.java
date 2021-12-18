import java.util.Random;
import java.util.*;

public class Minefelt {
    private Rute[][] minefelt;
    private int rader, kolonner, antallMiner;
    private ArrayList <Rute> alleBomber = new ArrayList <>();

    public Minefelt(int ra, int ko, int antall) {
        rader = ra;
        kolonner = ko;
        antallMiner = antall;

        minefelt = new Rute[rader][kolonner];

        for (int i = 0; i < rader; i++) {
            for (int j = 0; j < kolonner; j++) {
                Rute rute = new Rute(this, i, j);
                minefelt[i][j] = rute;
            }
        }

        for (int i = 0; i < rader; i++) {
            for (int j = 0; j < kolonner; j++) {
                Rute rute = minefelt[i][j];

                // Hvis øverst, sett naboen under
                if (i == 0) {
                    rute.leggTilNabo(minefelt[i+1][j]);

                    // Diagonal: øverst til venstre
                    if (j == 0) {
                        rute.leggTilNabo(minefelt[i+1][j+1]);
                    }

                    // Digonal: øverst til høyre
                    else if (j == kolonner - 1) {
                        rute.leggTilNabo(minefelt[i+1][kolonner-2]);
                    }

                    else {
                        rute.leggTilNabo(minefelt[i+1][j+1]);
                        rute.leggTilNabo(minefelt[i+1][j-1]);
                    }
                }

                // Hvis nederst, sett naboen over
                if (i == rader - 1) {
                    rute.leggTilNabo(minefelt[i-1][j]);

                    // Diagonal: Hvis nederst til venstre
                    if (j == 0) {
                        rute.leggTilNabo(minefelt[i-1][j+1]);
                    }
                    // Diagonal: nederst til høyre
                    else if (j == kolonner - 1) {
                        rute.leggTilNabo(minefelt[i-1][j-1]);
                    }

                    else {
                        rute.leggTilNabo(minefelt[i-1][j+1]);
                        rute.leggTilNabo(minefelt[i-1][j-1]);
                    }
                }
                // Hvis helt til venstre, sett naboen til høyre
                if (j == 0) {
                    rute.leggTilNabo(minefelt[i][j+1]);

                    if (i != 0 && i != (rader - 1)) {
                        rute.leggTilNabo(minefelt[i+1][j+1]);
                        rute.leggTilNabo(minefelt[i-1][j+1]);
                    }
                }
                // Hvis helt til høyre, sett naboen til venstre
                if (j == kolonner - 1) {
                    rute.leggTilNabo(minefelt[i][j-1]);

                    if (i != 0 && i != (rader - 1)) {
                        rute.leggTilNabo(minefelt[i+1][j-1]);
                        rute.leggTilNabo(minefelt[i-1][j-1]);
                    }
                }

                // Hvis jeg ikke er helt til venstre, eller helt til høyre
                if (j != 0 && j != kolonner - 1) {
                    rute.leggTilNabo(minefelt[i][j-1]);
                    rute.leggTilNabo(minefelt[i][j+1]);
                }

                // Hvis jeg ikke er øverst, eller nederst
                if (i != 0 && i != rader - 1) {
                    rute.leggTilNabo(minefelt[i-1][j]);
                    rute.leggTilNabo(minefelt[i+1][j]);
                }

                if (i != 0 && i != (rader - 1) && j != 0 && j != (kolonner - 1)) {
                    rute.leggTilNabo(minefelt[i+1][j+1]);
                    rute.leggTilNabo(minefelt[i+1][j-1]);
                    rute.leggTilNabo(minefelt[i-1][j+1]);
                    rute.leggTilNabo(minefelt[i-1][j-1]);
                }
            }
        }
        plantMiner();
    }

    private void plantMiner() {
        Random rand = new Random();

        for (int i = 0; i < antallMiner; i++) {
            int rad = rand.nextInt(rader);
            int kolonne = rand.nextInt(kolonner);

            minefelt[rad][kolonne].omgjorTilBombe();
            alleBomber.add(minefelt[rad][kolonne]);
        }
    }

    public Rute hentRute(int rad, int kolonne) {
        return minefelt[rad][kolonne];
    }

    public ArrayList <Rute> hentAlleBomber() {
        return alleBomber;
    }
}
