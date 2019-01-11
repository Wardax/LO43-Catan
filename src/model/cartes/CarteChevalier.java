package model.cartes;

import model.Joueur;
import model.Model;

public class CarteChevalier extends CarteDeveloppement{
    public void activation(Model model){
        model.getJoueurActuel().setNbChevalier(model.getJoueurActuel().getNbChevalier()+1);
        Joueur joueurPlusGrandeArmee = null;
        for (Joueur joueur : model.getJoueurs()) {
            if (joueur.isArmeLaPlusPuissante()) {
                joueurPlusGrandeArmee = joueur;
            }
        }
        if (joueurPlusGrandeArmee == null && model.getJoueurActuel().getNbChevalier() == 3) model.getJoueurActuel().setArmeLaPlusPuissante(true);
        else if (joueurPlusGrandeArmee != null && joueurPlusGrandeArmee.getNbChevalier() < model.getJoueurActuel().getNbChevalier()) {
            model.getJoueurActuel().setArmeLaPlusPuissante(true);
            joueurPlusGrandeArmee.setArmeLaPlusPuissante(false);
        }
    }
}
