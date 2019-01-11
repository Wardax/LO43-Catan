package model.constructions;

import model.Croisement;
import model.Joueur;

public class Monument extends Construction {
    public Monument(Joueur j, Croisement c) {
        super(j, c);
    }


    /**
     * ajoute 2 ressources aux joueurs possédant le monument
     * @param type
     */
    public void recupereRessource(int type) {
        getJoueur().ajouteRessource(type, 2);
    }
}
