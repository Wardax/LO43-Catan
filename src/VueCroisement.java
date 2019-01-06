import javafx.scene.shape.Circle;

public class VueCroisement extends Circle {
    Croisement croisement;

    public VueCroisement(double centerX, double centerY, double radius, Croisement c) {
        super(centerX, centerY, radius);
        this.croisement = c;
    }

    public Croisement getCroisement() {
        return croisement;
    }
}
