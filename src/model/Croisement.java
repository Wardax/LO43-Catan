package model;

import model.constructions.Construction;

import java.util.ArrayList;

public class Croisement {
    private ArrayList<Route> routes;
    private Construction construction;
    private int posX;
    private int posY;
    private int date;

    public Croisement(int date) {
        routes = new ArrayList<>();
        this.date = date;
    }

    public void setConstruction(Construction c) {
        construction = c;
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

    /**
     * Un croisement est constructible lorsqu'il n'y a pas de construction sur le croisement et sur les croisements adjacents
     * @return
     */
    public boolean isConstructible() {
        boolean constructible = construction == null;
        for (Route r : routes) {
            if (r.getCroisementAdj(this).getConstruction() != null) constructible = false;
        }
        return constructible;
    }

    public void setPos(int x, int y) {
        posX = x;
        posY = y;
    }

    /**
     * @return index du plateau du croisement
     */
    public int getDate() {
        return date;
    }
}
