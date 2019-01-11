package model.cartes;

import model.Joueur;
import model.Model;

public class CarteMonopole extends CarteDeveloppement{
    private int typeRessource;
    public void activation(Model model){
        Joueur j = model.getJoueurActuel();
        int n = 0;
        for (Joueur joueur : model.getJoueurs()) {
            if (joueur != j) {
                n+= joueur.getRessources()[typeRessource];
                joueur.supprimeRessource(typeRessource, joueur.getRessources()[typeRessource]);
            }
        }
        j.ajouteRessource(typeRessource, n);
    }

    public void setTypeRessource(int typeRessource) {
        this.typeRessource = typeRessource;
    }
}
