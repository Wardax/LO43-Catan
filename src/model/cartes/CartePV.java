package model.cartes;

import model.Model;

public class CartePV extends CarteDeveloppement{
    public void activation(Model model){
        model.getJoueurActuel().setScore(model.getJoueurActuel().getScore()+1);
    }
}
