public class Plateau {

    /**
     * initialiserPlateau initialise le plateau de jeu avec les pions.
     * @param plateau le plateau de jeu sur lequel on joue.
     */
    public static void initialiserPlateau(String[][] plateau) {
        for (int i = 0; i < Variables_Globales.TAILLE; i++) {
            for (int j = 0; j < Variables_Globales.TAILLE; j++) {
                if ((i + j) % 2 != 0) {
//                    Place les pions bleus sur les 4 premières lignes et les pions rouges sur les 4 dernières lignes.
                    if (i < 4) {
                        plateau[i][j] = Variables_Globales.PION_BLEU;
                    } else if (i > 5) {
                        plateau[i][j] = Variables_Globales.PION_ROUGE;
//                    Place les cases noires dans les lignes intermediaires.
                    } else {
                        plateau[i][j] = Variables_Globales.CASE_NOIRE;
                    }
                } else {
//                    Place les cases blanches sur les cases restantes.
                    plateau[i][j] = Variables_Globales.CASE_BLANCHE;
                }
            }
        }
    }

    /**
     * afficherPlateau affiche le plateau de jeu.
     * @param plateau le plateau de jeu sur lequel on joue.
     */
    public static void afficherPlateau(String[][] plateau) {
        System.out.println("  | A | B | C | D | E | F | G | H | I | J |");
        System.out.println("  |---------------------------------------|");

        for (int i = 0; i < Variables_Globales.TAILLE; i++) {
            System.out.print(" " + i + "|");
            for (int j = 0; j < Variables_Globales.TAILLE; j++) {
                System.out.print(plateau[i][j] + "|");
            }
            System.out.println();
            System.out.println("  |---------------------------------------|");
        }
        System.out.println();
    }

    /**
     * copierPlateau copie le plateau de jeu.
     * @param plateau le plateau de jeu sur lequel on joue.
     * @return une copie du plateau de jeu.
     */
    public static String[][] copierPlateau(String[][] plateau) {
        String[][] copie = new String[Variables_Globales.TAILLE][Variables_Globales.TAILLE];
        for (int i = 0; i < Variables_Globales.TAILLE; i++) {
            for (int j = 0; j < Variables_Globales.TAILLE; j++) {
                copie[i][j] = plateau[i][j];
            }
        }
        return copie;
    }

}