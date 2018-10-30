/**
 * Created by guill on 28/10/2018.
 */
public class Croisement {
    private Joueur joueur;
    private Parcelle[] parcelles;
    private Route[] routes;
    private boolean ameliore;

    public Croisement() {
        joueur = null;
        parcelles = new Parcelle[3];
        routes = new Route[3];
        ameliore = false;
    }

    public void construction(Joueur joueur) {
        this.joueur = joueur;
        for (Parcelle p : parcelles) {
            joueur.ajouteRessource(p.getRessource());
        }
    }

    public void amelioration() {
        ameliore = true;
    }

    public void recupereRessource(int type) {
        if (joueur != null) {
            joueur.ajouteRessource(type);
        }
    }

}
