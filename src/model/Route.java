package model;


public class Route {
    // 0 non construit, x joueurX
    private int joueur;
    private Croisement[] croisements;
    private int posX;
    private int posY;
    private int rotation;

    public Route(Croisement c1, Croisement c2, int x, int y, int rota) {
        joueur = 0;
        croisements = new Croisement[2];
        croisements[0] = c1;
        croisements[1] = c2;
        posX = x;
        posY = y;
        rotation = rota;
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

    public int getRotation() {
        return rotation;
    }

    /**
     * Renvoie le croisement adjacent à celui envoyé en paramètre
     * @param croisement
     * @return
     */
    public Croisement getCroisementAdj(Croisement croisement) {
        if (croisements[0] == croisement) return croisements[1];
        return croisements[0];
    }

    public Croisement[] getCroisements() {
        return croisements;
    }
}
