package vue;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Croisement;
import model.Parcelle;
import model.Plateau;
import model.Route;

import java.awt.*;
import java.util.ArrayList;

public class VuePlateau extends Group {
    private ArrayList<VueRoute> routes;
    private ArrayList<VueCroisement> croisements;
    public VuePlateau(Plateau plateau){
        super();
        ArrayList<Parcelle> parcelles = plateau.getParcelles();

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
            //polygon.setFill(new ImagePattern());
            polygon.setFill(findColor(p.getType()));
            polygon.relocate(x, y);
            Text t = new Text(x+45, y+55, "" + p.getNumero());
            t.setFont(Font.font ("Verdana", 18));
            getChildren().addAll(polygon, t);

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
        relocate(200, 40);
    }

    private Paint findColor(int numero) {
        switch (numero) {
            case 0 : return Color.BROWN;
            case 1 : return Color.DARKGRAY;
            case 2 : return Color.GRAY;
            case 3 : return Color.GOLD;
            case 4 : return Color.LIGHTGREEN;
        }
        return null;
    }

    public ArrayList<VueRoute> getRoutes() {
        return routes;
    }

    public ArrayList<VueCroisement> getCroisements() {
        return croisements;
    }
}
