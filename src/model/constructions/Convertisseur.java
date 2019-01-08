package model.constructions;

import model.Croisement;

public class Convertisseur {

    private Croisement acces;

    public Convertisseur(Croisement c) {
        acces = c;
    }

    public Croisement getAcces() {
        return acces;
    }
}
