/**
 * Created by Guillaume on 28/10/2018.
 */
public class Parcelle {
    // 0 res1, 1 res2, 2 res3, 3 res4, 4desert
    private int type;
    private int numero;
    private Croisement[] croisements;
    private int pos;
    private boolean brigand;

    public Parcelle(int type, int numero, int pos) {
        this.type = type;
        this.numero = numero;
        croisements=new Croisement[6];
        brigand=false;
        this.pos=pos;
    }

    public int getPos() {
        return pos;
    }

    public Croisement[] getCroisements() {
        return croisements;
    }

    public int getNumero(){
        return numero;
    }

    public int getRessource() {
        if (!brigand) {
            return type;
        }
        return 4;
    }

    public void produitRessource(){
        if (!brigand && type!=4) {
            for(int i = 0; i<6; i++) {
                croisements[i].recupereRessource(type);
            }
        }
    }


}
