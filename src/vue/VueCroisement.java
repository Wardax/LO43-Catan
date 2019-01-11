package vue;

import javafx.scene.shape.Circle;
import model.Croisement;

public class VueCroisement extends Circle {
    private Croisement croisement;

    public VueCroisement(double centerX, double centerY, double radius, Croisement c) {
        super(centerX, centerY, radius);
        croisement = c;
    }

    public Croisement getCroisement() {
        return croisement;
    }
}
