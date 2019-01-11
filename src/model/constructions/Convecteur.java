package model.constructions;

import model.Croisement;

public class Convecteur {

    private Croisement acces;

    public Convecteur(Croisement c) {
        acces = c;
    }

    public Croisement getAcces() {
        return acces;
    }
}
