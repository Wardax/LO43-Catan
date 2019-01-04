public class Plateau {
    private Parcelle[] parcelles;
    private Brigand brigand;


    public Plateau(int date) {
        int n = 19;
        parcelles = new Parcelle[n];
        for (int i =0; i < n; i++) {
            parcelles[i] = new Parcelle(i, type);
        }
        Croisement[] croisements = new Croisement[54];
        for (int i = 0; i < 54; i++) {
            croisements[i] = new Croisement();
        }
        for (int i =0; i < n; i++) {
            liaisonParcelleCroisements(croisements, n);
        }

        // Création et liaison des routes avec les croisements
        // cas route entre les 2 intersections de la même ligne
        for (int i = 0; i < n; i+=2) {
            creationRoutes(croisements[i], croisements[i+1]);
        }
        // cas route entre les 2 intersection de lignes différentes
        // cas simple
        for (int i = 6; i < 42; i++) {
            creationRoutes(croisements[i], croisements[i+6]);
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
        creationRoutes(croisements[50], croisements[53]);
        creationRoutes(croisements[51], croisements[54]);

    }


    private void liaisonParcelleCroisements(Croisement[] c, int n) {
        int base = 2 * n;
        int add1 = 6;
        int add2 = 6;
        if (n == 0) {
            base = 0;
            add1 = 3;
            add2 = 5;
        } else if (n < 3) {
            add1 = 5;
        } else if (n < 6);
        else if (n < 8) base++;
        else if (n < 11) base+=2;
        else if (n < 13) base+=3;
        else if (n < 16) base+=4;
        else if (n < 18)  {
            base+=5;
            add2 = 5;
        } else {
            base +=6;
            add1 = 5;
            add2 = 3;
        }
        Croisement[] croisements = new Croisement[6];
        croisements[0] = c[base];
        croisements[1] = c[base + 1];
        base += add1;
        croisements[0] = c[base];
        croisements[1] = c[base + 1];
        base += add2;
        croisements[0] = c[base];
        croisements[1] = c[base + 1];
        parcelles[n].setCroisements(croisements);
    }

    private void creationRoutes(Croisement c1, Croisement c2) {
        Route r = new Route(c1, c2);
        c1.setRoute(r);
        c2.setRoute(r);
    }
}