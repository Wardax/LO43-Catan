import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;

public class VuePlateau extends Group {
    private ArrayList<VueRoute> routes;
    private ArrayList<VueCroisement> croisements;
    public VuePlateau(Plateau plateau){
        super();
        Parcelle[] parcelles = plateau.getParcelles();

        for (Parcelle p : parcelles) {
            int x = p.getPosX();
            int y = p.getPosY();
            Polygon polygon = new Polygon();
            polygon.getPoints().addAll(new Double[]{
                    30., 0.,
                    80., 0.,
                    110., 50.,
                    80., 100.,
                    30., 100.,
                    0., 50.});
            polygon.setFill(Color.DARKBLUE);
            polygon.relocate(x, y);
            getChildren().add(polygon);

        }

        routes = new ArrayList<>();
        for (Route r : plateau.getRoutes()) {
            int x = r.getPosX();
            int y = r.getPosY();
            int rota = r.getRotation();
            if (rota == 90) {
                y-=25;
                x+=10;
            }
            VueRoute vueRoute = new VueRoute(10, 50, r);
            vueRoute.setRotate(rota);
            vueRoute.relocate(x+10, y);
            vueRoute.setFill(Color.TRANSPARENT);
            routes.add(vueRoute);
        }
        getChildren().addAll(routes);

        croisements = new ArrayList<>();
        for (Croisement c : plateau.getCroisements()) {
            int x = c.getPosX();
            int y = c.getPosY();
            VueCroisement vc = new VueCroisement(x, y, 10, c);
            vc.setFill(Color.TRANSPARENT);
            croisements.add(vc);
        }
        getChildren().addAll(croisements);
        relocate(200, 150);
    }

    public ArrayList<VueRoute> getRoutes() {
        return routes;
    }

    public ArrayList<VueCroisement> getCroisements() {
        return croisements;
    }
}
