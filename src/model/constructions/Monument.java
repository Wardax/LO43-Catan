package model.constructions;

import model.Croisement;
import model.Joueur;

public class Monument extends Construction {
    public Monument(Joueur j, Croisement c) {
        super(j, c);
    }

    public int getNbPoint() {
        return 3;
    }

    public void recupereRessource(int type) {

    }
}
