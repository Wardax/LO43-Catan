package model;

import java.util.ArrayList;

public class Brigand {
    private Parcelle p;

    public Brigand(Parcelle p) {
        this.p = p;
    }

    public void volRessource(Joueur j1, Joueur j2, int type) {
        j1.ajouteRessource(type, 1);
        j2.supprimeRessource(type, 1);
    }

    public ArrayList<Joueur> deplacement(Parcelle p) {
        this.p.setBrigand(false);
        this.p = p;
        p.setBrigand(true);
        Croisement[] croisements = p.getCroisements();
        ArrayList<Joueur> joueursVolables = new ArrayList<>();
        for(int i = 0; i<3; i++) {
            if(croisements[i].hasConstruction()) {
                Joueur joueur = croisements[i].getConstruction().getJoueur();
                boolean estDejaPresent = false;
                for (Joueur j : joueursVolables) {
                    if (joueur == j) estDejaPresent = true;
                }
                if (!estDejaPresent) joueursVolables.add(joueur);
            }
        }

        return joueursVolables;

    }
}
