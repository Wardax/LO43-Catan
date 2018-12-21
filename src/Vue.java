import java.util.ArrayList;

public class Vue {
    private Partie p;
    private VuePlateau[] vuesPlateaus;
    private ArrayList<VueConstruction> VuesConstructions;
    private ArrayList<VueJoueur> VuesJoueurs;

    public Vue(Partie p) {
        this.p = p;
    }
}
