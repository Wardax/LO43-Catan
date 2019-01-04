import java.util.ArrayList;

public class Partie {
    private Plateau[] plateaux;
    private Joueur[] joueurs;
    private ArrayList<CarteDeveloppement> cartes;
    private Joueur joueurActuel;
    private boolean verifSecond;

    public Partie() {
    }

    public ArrayList<Route> rechercheRoutesPossibles() {
        verifSecond =true;
        ArrayList<Route> routesPossible;
        ArrayList<Construction> cs = joueurActuel.getConstructions();
        Construction c = cs.get(0);
        Route[] rs = c.getRoutes();
        for (Route r : rs) {
            if (r.getJoueur() == null) {
                routesPossible
            }
        }
    }

    public ArrayList<Croisement> rechercheConstructionsPossibles() {}

    public ArrayList<Delorean> rechercheConvecteursPossibles() {}

    public ArrayList<CarteDeveloppement> getCartes() {
        return cartes;
    }
}
