package vue;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Croisement;
import model.Parcelle;
import model.Plateau;
import model.Route;
import model.constructions.Construction;
import model.constructions.Delorean;
import model.constructions.Monument;

import java.util.ArrayList;

public class VuePlateau extends Group {
    private ArrayList<VueParcelle> parcelles;
    private ArrayList<VueRoute> routes;
    private ArrayList<VueCroisement> croisements;
    private ArrayList<VueConstruction> constructions;
    private ImageView brigand;

    public VuePlateau(Plateau plateau){
        super();
        constructions = new ArrayList<>();
        brigand = new ImageView("images/thief.png");
        brigand.setPreserveRatio(true);
        brigand.setFitHeight(40);
        ArrayList<Parcelle> parcelles = plateau.getParcelles();
        this.parcelles = new ArrayList<>();

        for (Parcelle p : parcelles) {
            int x = p.getPosX();
            int y = p.getPosY();
            VueParcelle polygon = new VueParcelle(p);
            polygon.getPoints().addAll(30., 0.,
                    80., 0.,
                    110., 50.,
                    80., 100.,
                    30., 100.,
                    0., 50.);
            polygon.setFill(new ImagePattern(new Image("images/case" + p.getType() + ".jpg")));
            polygon.relocate(x, y);
            Group g = new Group();
            Circle c = new Circle();
            c.setRadius(15);
            c.setCenterX(x+55);
            c.setCenterY(y+50);
            c.setFill(Color.WHITE);
            Text t = new Text(x+45, y+57, "" + p.getNumero());
            t.setFont(Font.font ("Verdana", 18));
            g.getChildren().addAll(c, t);
            getChildren().addAll(polygon, g);
            this.parcelles.add(polygon);

            if(p.hasBrigand()) {
                brigand.relocate(x + 35, y + 35);
            }
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
        getChildren().add(brigand);
        relocate(200, 40);
    }

    public void addConstruction(Croisement c) {
        VueConstruction imageConstruction = new VueConstruction(generateConstructionUrl(c.getConstruction()), c.getConstruction());
        imageConstruction.relocate(c.getPosX()-20, c.getPosY()-20);
        imageConstruction.setPreserveRatio(true);
        imageConstruction.setFitHeight(40);
        constructions.add(imageConstruction);
        getChildren().add(imageConstruction);
    }

    private String generateConstructionUrl(Construction c) {
        String url = "images/";
        if (c instanceof Delorean && ((Delorean) c).getConvecteur() == null) {
            url += "player"+c.getJoueur().getNumero()+".png";
        } else if (c instanceof Monument) {
            url += "monument"+((Monument)c).getCroisement().getDate()+".png";
        } else {
            url += "convecteur.png";
        }
        return url;
    }

    public ArrayList<VueRoute> getRoutes() {
        return routes;
    }

    public ArrayList<VueCroisement> getCroisements() {
        return croisements;
    }

    public ArrayList<VueParcelle> getParcelles() {
        return parcelles;
    }

    public void actualisePositionBrigand(VueParcelle vueParcelle) {
        brigand.relocate(vueParcelle.getParcelle().getPosX() + 35, vueParcelle.getParcelle().getPosY() + 35);
    }

    public ArrayList<VueConstruction> getConstructions() {
        return constructions;
    }
}
