import java.util.ArrayList;

public class Croisement {
    private ArrayList<Route> routes;
    private Construction construction;
    private int posX;
    private int posY;

    public Croisement() {
        routes = new ArrayList<>();
    }

    public void setConstruction(Construction c) {
        construction = c;
    }

    public void recupereRessource(int type) {
        construction.recupereRessource(type);
    }

    public boolean hasConstruction() {
        return construction != null;
    }

    public Construction getConstruction() {
        return construction;
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setRoute(Route route) {
        routes.add(route);
    }
}
