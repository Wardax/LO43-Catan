package model.cartes;

import model.Model;

public class CarteRoute extends CarteDeveloppement{
    public void activation(Model model){
        model.getJoueurActuel().setRoutesGratuites(model.getJoueurActuel().getRoutesGratuites() + 2);
    }
}
