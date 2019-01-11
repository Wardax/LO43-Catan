package model.constructions;

import model.Croisement;
import model.Joueur;

public class Delorean extends Construction {
    private Convecteur convecteur;

    public Delorean(Joueur j, Croisement c) {
        super(j, c);
    }

    public void setConvecteur(Convecteur convecteur) {
        this.convecteur = convecteur;
    }

    /**
     * Ajoute 1 ressource au joueurs possèdant la construction
     * en ajoute 2 si un convecteur est lié à la delorean
     * @param type
     */
    public void recupereRessource(int type){
        if (convecteur == null) getJoueur().ajouteRessource(type, 1);
        else getJoueur().ajouteRessource(type, 2);
    }

    public Convecteur getConvecteur() {
        return convecteur;
    }
}
