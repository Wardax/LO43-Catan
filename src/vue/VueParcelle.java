package vue;

import javafx.scene.shape.Polygon;
import model.Parcelle;

public class VueParcelle extends Polygon {
    private Parcelle parcelle;

    public VueParcelle(Parcelle parcelle) {
        super();
        this.parcelle = parcelle;
    }

    public Parcelle getParcelle() {
        return parcelle;
    }
}
