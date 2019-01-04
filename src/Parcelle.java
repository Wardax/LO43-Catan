
public class Parcelle {
    private int type;
    private int numero;
    private Croisement[] croisements;
    private boolean brigand;
    private int posX;
    private int posY;

    public Parcelle(int type, int numero, int pos) {
        this.type = type;
        this.numero = numero;
        croisements=new Croisement[6];
        brigand=false;
    }

    public Croisement[] getCroisements() {
        return croisements;
    }

    public void produitRessource(){
        if (!brigand && type!=4) {
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
}
