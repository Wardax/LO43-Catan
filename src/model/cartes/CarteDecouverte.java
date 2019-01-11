package model.cartes;


import model.Model;

public class CarteDecouverte extends CarteDeveloppement {
    private int typeRessource;
    public void activation(Model model) {
        model.getJoueurActuel().ajouteRessource(typeRessource, 2);
    }

    public void setTypeRessource(int typeRessource) {
        this.typeRessource = typeRessource;
    }
}
