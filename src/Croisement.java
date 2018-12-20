
public class Croisement {
    private Route[] routes;
    private Construction construction;

    public Croisement() {
        routes = new Route[3];
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
}
