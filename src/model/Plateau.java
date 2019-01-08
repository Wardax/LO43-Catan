package model;

import java.util.ArrayList;
import java.util.Collections;

public class Plateau {
    private ArrayList<Parcelle> parcelles;
    private Brigand brigand;
    private Croisement[] croisements;
    private ArrayList<Route> routes;
    int date;


    public Plateau(int date) {

        parcelles = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            parcelles.add(new Parcelle(0));
        }
        for (int i = 0; i < 4; i++) {
            parcelles.add(new Parcelle(1));
        }
        for (int i = 0; i < 4; i++) {
            parcelles.add(new Parcelle(2));
        }
        for (int i = 0; i < 4; i++) {
            parcelles.add(new Parcelle(3));
        }
        for (int i = 0; i < 3; i++) {
            parcelles.add(new Parcelle(4));
        }

        Collections.shuffle(parcelles);
        int[] nums = {2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12};
        for(int i = 0; i < 19; i++){
            parcelles.get(i).setNumero(nums[i]);
        }

        Collections.shuffle(parcelles);

        croisements = new Croisement[54];
        for (int i = 0; i < 54; i++) {
            croisements[i] = new Croisement();
        }

        lien(parcelles.get(0), 160, 0, 0, 3, 8);
        lien(parcelles.get(1), 80, 50, 2, 7, 13);
        lien(parcelles.get(2), 240, 50, 4, 9, 15);
        lien(parcelles.get(3), 0, 100, 6, 12, 18);
        lien(parcelles.get(4), 160, 100, 8, 14, 20);
        lien(parcelles.get(5), 320, 100, 10, 16, 22);
        lien(parcelles.get(6), 80, 150, 13, 19, 25);
        lien(parcelles.get(7), 240, 150, 15, 21, 27);
        lien(parcelles.get(8), 0, 200, 18, 24, 30);
        lien(parcelles.get(9), 160, 200, 20, 26, 32);
        lien(parcelles.get(10), 320, 200, 22, 28, 34);
        lien(parcelles.get(11), 80, 250, 25, 31, 37);
        lien(parcelles.get(12), 240, 250, 27, 33, 39);
        lien(parcelles.get(13), 0, 300, 30, 36, 42);
        lien(parcelles.get(14), 160, 300, 32, 38, 44);
        lien(parcelles.get(15), 320, 300, 34, 40, 46);
        lien(parcelles.get(16), 80, 350, 37, 43, 48);
        lien(parcelles.get(17), 240, 350, 39, 45, 50);
        lien(parcelles.get(18), 160, 400, 44, 49, 52);

        // Création et liaison des routes avec les croisements
        routes = new ArrayList<>();
        // cas route entre les 2 intersections de la même ligne
        for (int i = 0; i < 11; i += 2) {
            creationRoutes(croisements[i], croisements[i + 1]);
        }
        for (int i = 18; i < 24; i += 2) {
            creationRoutes(croisements[i], croisements[i + 1]);
        }
        for (int i = 30; i < 36; i += 2) {
            creationRoutes(croisements[i], croisements[i + 1]);
        }
        for (int i = 42; i < 54; i += 2) {
            creationRoutes(croisements[i], croisements[i + 1]);
        }
        creationRoutes(croisements[13], croisements[14]);
        creationRoutes(croisements[15], croisements[16]);
        creationRoutes(croisements[25], croisements[26]);
        creationRoutes(croisements[27], croisements[28]);
        creationRoutes(croisements[37], croisements[38]);
        creationRoutes(croisements[39], croisements[40]);
        // cas route entre les 2 intersection de lignes différentes
        // cas simple
        for (int i = 6; i < 42; i++) {
            creationRoutes(croisements[i], croisements[i + 6]);
        }
        // plus compliqué pour les 2 premières et 2 dernières lignes
        creationRoutes(croisements[0], croisements[3]);
        creationRoutes(croisements[1], croisements[4]);
        creationRoutes(croisements[2], croisements[7]);
        creationRoutes(croisements[3], croisements[8]);
        creationRoutes(croisements[4], croisements[9]);
        creationRoutes(croisements[5], croisements[10]);
        creationRoutes(croisements[43], croisements[48]);
        creationRoutes(croisements[44], croisements[49]);
        creationRoutes(croisements[45], croisements[50]);
        creationRoutes(croisements[46], croisements[51]);
        creationRoutes(croisements[49], croisements[52]);
        creationRoutes(croisements[50], croisements[53]);

    }

    private void lien(Parcelle p, int x, int y, int c0, int c1, int c2) {
        p.setPos(x, y);
        Croisement[] cs = new Croisement[6];
        cs[0] = croisements[c0];
        cs[1] = croisements[c0 + 1];
        cs[2] = croisements[c1];
        cs[3] = croisements[c1 + 1];
        cs[4] = croisements[c2];
        cs[5] = croisements[c2 + 1];
        p.setCroisements(cs);
        cs[0].setPos(x + 30, y);
        cs[1].setPos(x + 80, y);
        cs[2].setPos(x, y + 50);
        cs[3].setPos(x + 110, y + 50);
        cs[4].setPos(x + 30, y + 100);
        cs[5].setPos(x + 80, y + 100);
    }

    private void creationRoutes(Croisement c1, Croisement c2) {
        int x = c1.getPosX() < c2.getPosX() ? c1.getPosX() : c2.getPosX();
        int y = c1.getPosY() < c2.getPosY() ? c1.getPosY() : c2.getPosY();
        int rota = 0;
        if (c1.getPosY() == c2.getPosY()) rota = 90;
        else if (c1.getPosX() > c2.getPosX()) rota =30;
        else rota = 150;
        Route r = new Route(c1, c2, x, y, rota);
        c1.setRoute(r);
        c2.setRoute(r);
        routes.add(r);
    }

    public ArrayList<Parcelle> getParcelles() {
        return parcelles;
    }

    public Croisement[] getCroisements() {
        return croisements;
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public int getDate() {
        return date;
    }
}