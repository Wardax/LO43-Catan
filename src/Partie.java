import java.util.ArrayList;

public class Partie {
    private Plateau[] plateaux;
    private Joueur[] joueurs;
    private ArrayList<CarteDeveloppement> cartes;
    private Joueur joueurActuel;
    boolean verifSecond;

    public Partie() {
    }

    public ArrayList<Route> chercheRoutesPossible() {
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

}
