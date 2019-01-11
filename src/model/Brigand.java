package model;

import java.util.ArrayList;

public class Brigand {
    private Parcelle p;

    public Brigand(Parcelle p) {
        this.p = p;
        p.setBrigand(true);
    }

    /**
     * vol une ressource aléatore au 2ème joueur pour la donner au premier joueur
     * @param j1
     * @param j2
     */
    public void volRessource(Joueur j1, Joueur j2) {
        ArrayList<Integer> rand = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            if (j2.getRessources()[i] > 0) rand.add(i);
        }
        if (rand.size()>0) {
            int type = rand.get(Model.rand.nextInt(rand.size()));
            j1.ajouteRessource(type, 1);
            j2.supprimeRessource(type, 1);
        }
    }

    /**
     * Déplace le brigand sur la parcelle
     * @param parcelle
     * @return
     */
    public ArrayList<Joueur> deplacement(Parcelle parcelle) {
        this.p.setBrigand(false);
        this.p = parcelle;
        parcelle.setBrigand(true);
        Croisement[] croisements = parcelle.getCroisements();
        ArrayList<Joueur> joueursVolables = new ArrayList<>();
        for(int i = 0; i<croisements.length; i++) {
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
