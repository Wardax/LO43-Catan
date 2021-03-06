package model.constructions;

import model.Croisement;
import model.Joueur;

public abstract class Construction {
    private Joueur joueur;
    private Croisement croisement;

    public Construction(Joueur j, Croisement c) {
        joueur = j;
        croisement = c;
    }

    public abstract void recupereRessource(int type);

    public Joueur getJoueur() {
        return joueur;
    }

    public Croisement getCroisement() {
        return croisement;
    }

}
