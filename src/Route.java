/**
 * Created by guill on 28/10/2018.
 */
public class Route {
    // 0 non construit, x joueurX
    private int joueur;
    private Croisement[] croisements;

    public Route() {
        joueur = 0;
        croisements = new Croisement[2];
    }

    public void ammenagementRoute(int joueur){
        this.joueur = joueur;
    }



}
