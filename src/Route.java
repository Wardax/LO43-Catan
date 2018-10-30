/**
 * Created by guill on 28/10/2018.
 */
public class Route {
    // 0 non construit, x joueurX
    private int joueur;
    private Croisement[] croisements;
    private Route[] routes;

    public Route() {
        joueur = 0;
        croisements = new Croisement[2];
        routes = new Route[4];
    }

    public void ammenagementRoute(int joueur){
        this.joueur = joueur;
    }



}
