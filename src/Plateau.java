public class Plateau {
    private Parcelle[] parcelles;
    private Brigand brigand;


    public Plateau(int date) {
        int n = 0;
        parcelles = new Parcelle[n];
        for (int i =0; i < n; i++) {
            parcelles[i] = new Parcelle(i, date);
        }
        creationLiens(1, null, parcelles[0], null);
        creationLiens(2, null, null, parcelles[0]);
        creationLiens(1, null, parcelles[1], parcelles[0]);
        creationLiens(2, null, null, parcelles[1]);
        creationLiens(1, null, null, parcelles[1]);
        creationLiens(1, null, parcelles[2], null);
        creationLiens(2, null, parcelles[0], parcelles[2]);
        creationLiens(1, parcelles[0], parcelles[3], parcelles[2]);

        int l = 0;
        int v = 0;
        for (int i = 0; i < 9; i++) {
            Parcelle p1 = i != v ? parcelles[i - 2 - l] : null;
            Parcelle p2 = l > 0 && i - 1 - l >= 0 ? parcelles[i - 1 - l] : null;
            Parcelle p3 = i != v ? parcelles[i - 1] : null;
            Parcelle p4 = i < n ? parcelles[i] : null;
            Parcelle p5 = i + 1 < n ? parcelles[i + 1]
            creationLiens();

            if (l < 3) if (i == 1 + l) {
                l++;
                v = v + 2 + l;
            }
        }


    }


    private void creationLiens(int type, Parcelle p1, Parcelle p2, Parcelle p3) {

    }

}
