import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;

public class Minesveiper {

    static RuteKnapp[][] rutenett = null;
    static Minefelt minefelt = null;

    public static void main(String[] args) {
        JFrame vindu = new JFrame("Minesveiper Trix");
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        JPanel brett = new JPanel();

        int rader = 10;
        int kolonner = 10;
        final int ANTALL_MINER = 10;

        minefelt = new Minefelt(rader, kolonner, ANTALL_MINER);
        rutenett = new RuteKnapp[rader][kolonner];

        brett.setLayout(new GridLayout(rader, kolonner));

        for (int i = 0; i < rader; i++) {
            for (int j = 0; j < kolonner; j++) {
                RuteKnapp knapp = new RuteKnapp(i, j);
                knapp.setPreferredSize(new Dimension(35, 35));
                knapp.initGUI();
                brett.add(knapp);

                rutenett[i][j] = knapp;
            }
        }

        panel.add(brett);
        vindu.add(panel);

        vindu.pack();
        vindu.setVisible(true);
    }
}

class RuteKnapp extends JButton {
    protected int rad, kolonne;
    protected boolean markert = false;

    public RuteKnapp(int ra, int ko) {
        rad = ra;
        kolonne = ko;
    }

    private class Trykk implements ActionListener {
        ArrayList <Rute> naboer = null;

        @Override
        public void actionPerformed(ActionEvent e) {
            Rute klikk = Minesveiper.minefelt.hentRute(rad, kolonne);
            if (klikk.erBombe()) {
                markerBombe();

                markerAlleBomber();

                // System.exit(-1);
            }

            naboer = klikk.hentNaboer();
            int antallBombeNaboer = klikk.hentBombeNaboer();

            if (antallBombeNaboer > 0) {
                markerAntallBombeNaboer(antallBombeNaboer);
            }
            else {
                // marker();

                for (Rute rute : naboer) {
                    int rad = rute.hentRad();
                    int kolonne = rute.hentKolonne();
                    RuteKnapp nabo = Minesveiper.rutenett[rad][kolonne];

                    if (! nabo.erMarkert()) {
                        nabo.marker();
                        nabo.sjekkNaboer(rute);
                    }
                }
            }
        }
    }

    public void sjekkNaboer(Rute rekursivRute) {

        ArrayList<Rute> rekursiveNaboer = rekursivRute.hentNaboer();
        int antallBombeNaboer = rekursivRute.hentBombeNaboer();

        if (antallBombeNaboer > 0) {
            markerAntallBombeNaboer(antallBombeNaboer);
            return;
        }
        else {
            // marker();
            for (Rute rute : rekursiveNaboer) {
                int rad = rute.hentRad();
                int kolonne = rute.hentKolonne();
                int bombeNaboer = rute.hentBombeNaboer();

                RuteKnapp nabo = Minesveiper.rutenett[rad][kolonne];

                // nabo.marker();

                if (! nabo.erMarkert() && ! rute.erBombe()) {
                    nabo.marker();
                    nabo.sjekkNaboer(rute);
                }
            }
        }
    }

    public void markerBombe() {
        markert = true;
        super.setBackground(Color.RED);
        super.setOpaque(true);

        super.setBorder(null);
        super.setText("B");
    }

    public void marker() {
        markert = true;
        super.setBackground(Color.LIGHT_GRAY);
        super.setOpaque(true);
    }

    public void markerAntallBombeNaboer(int antallBombeNaboer) {
        super.setBorder(null);
        super.setText("" + antallBombeNaboer);

        if (antallBombeNaboer == 1) {
            super.setForeground(Color.BLUE);
        }
        else if (antallBombeNaboer == 2) {
            super.setForeground(Color.MAGENTA);
        }
        else {
            super.setForeground(Color.RED);
        }
    }

    public void markerAlleBomber() {
        ArrayList <Rute> alleBomber = Minesveiper.minefelt.hentAlleBomber();

        for (Rute bombe : alleBomber) {
            int rad = bombe.hentRad();
            int kolonne = bombe.hentKolonne();

            Minesveiper.rutenett[rad][kolonne].markerBombe();
        }
    }

    public boolean erMarkert() {
        return markert;
    }

    public void initGUI() {
        addActionListener(new Trykk());
    }
}
