/**
 * Created by guill on 28/10/2018.
 */
public class Route {
    // 0 non construit, x joueurX
    private int joueur;
    private Croisement[] croisements;
    private int posX;
    private int posY;

    public Route(Croisement c1, Croisement c2) {
        joueur = 0;
        croisements = new Croisement[2];
        croisements[0] = c1;
        croisements[1] = c2;
    }

    public void ammenagementRoute(int joueur){
        this.joueur = joueur;
    }

    public int getJoueur() {
        return joueur;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void addCroisement(Croisement c){}
}
