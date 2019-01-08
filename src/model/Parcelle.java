package model;

public class Parcelle {
    private int type;
    private int numero;
    private Croisement[] croisements;
    private boolean brigand;
    private int posX;
    private int posY;

    public Parcelle(int type) {
        this.type = type;
        brigand = false;
    }

    public Croisement[] getCroisements() {
        return croisements;
    }

    public void produitRessource(){
        if (!brigand) {
            for(int i = 0; i<6; i++) {
                croisements[i].recupereRessource(type);
            }
        }
    }

    public void setBrigand(boolean brigand) {
        this.brigand = brigand;
    }

    public void setCroisements(Croisement[] croisements) {
        this.croisements = croisements;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPos(int x, int y) {
        posX = x;
        posY = y;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getType() {
        return type;
    }
}
