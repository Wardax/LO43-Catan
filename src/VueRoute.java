import javafx.scene.shape.Rectangle;

public class VueRoute extends Rectangle {
    private Route route;

    public VueRoute(double width, double height, Route route) {
        super(width, height);
        this.route = route;
    }

    public Route getRoute() {
        return route;
    }
}
