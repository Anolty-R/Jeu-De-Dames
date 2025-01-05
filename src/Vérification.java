public class Vérification {
//    Les constantes suivantes sont utilisées pour les pièces et les couleurs.
    private static final String PION_BLEU = Variables_Globales.PION_BLEU;
    private static final String DAME_BLEUE = Variables_Globales.DAME_BLEUE;
    private static final String PION_ROUGE = Variables_Globales.PION_ROUGE;
    private static final String DAME_ROUGE = Variables_Globales.DAME_ROUGE;

    private static final String BLUE = Variables_Globales.BLUE;
    private static final String RED = Variables_Globales.RED;

    private static final int TAILLE = 10;


    /**
     * peutManger vérifie si un joueur peut manger une pièce adverse.
     * @param plateau le plateau de jeu sur lequel on joue.
     * @return true si un joueur peut manger une pièce adverse, false sinon.
     */
    public static boolean peutManger(String[][] plateau) {
        String couleur = (Variables_Globales.tour % 2 == 0) ? Variables_Globales.RED : Variables_Globales.BLUE;
        String couleurAdverse = (couleur.equals(Variables_Globales.BLUE)) ? Variables_Globales.RED : Variables_Globales.BLUE;

//        On parcourt le plateau pour vérifier les pièces du joueur.
        for (int y = 0; y < TAILLE; y++) {
            for (int x = 0; x < TAILLE; x++) {
                if (plateau[y][x].contains(couleur)) {
                    if (plateau[y][x].equals(Variables_Globales.DAME_BLEUE) || plateau[y][x].equals(Variables_Globales.DAME_ROUGE)) {
//                        Vérification pour les dames
                        int[][] directions = {{-1, -1}, {1, -1}, {-1, 1}, {1, 1}};
                        for (int[] dir : directions) {
                            int dx = dir[0], dy = dir[1];
                            int xTemp = x + dx, yTemp = y + dy;
                            boolean pieceAdverseTrouvee = false;
//                            Parcours dans la direction jusqu'à la limite du plateau.
                            while (xTemp >= 0 && xTemp < TAILLE && yTemp >= 0 && yTemp < TAILLE) {
                                if (plateau[yTemp][xTemp].contains(couleurAdverse)) {
                                    pieceAdverseTrouvee = true;
                                } else if (pieceAdverseTrouvee && plateau[yTemp][xTemp].equals(Variables_Globales.CASE_NOIRE)) {
                                    return true;
                                } else if (!plateau[yTemp][xTemp].equals(Variables_Globales.CASE_NOIRE)) {
                                    break;
                                }
                                xTemp += dx;
                                yTemp += dy;
                            }
                        }
                    } else {
//                        Vérification pour les pions
                        int[][] directions = {{-2, -2}, {2, -2}, {-2, 2}, {2, 2}};
                        for (int[] dir : directions) {
                            int newX = x + dir[0];
                            int newY = y + dir[1];
                            int midX = x + dir[0] / 2;
                            int midY = y + dir[1] / 2;

//                            Verifies si le pion peut manger une pièce adverse.
                            if (newX >= 0 && newX < TAILLE && newY >= 0 && newY < TAILLE) {
                                if (plateau[newY][newX].equals(Variables_Globales.CASE_NOIRE) && plateau[midY][midX].contains(couleurAdverse)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * peutDeplacer vérifie si un joueur peut déplacer une pièce.
     * @param plateau le plateau de jeu sur lequel on joue.
     * @return true si un joueur peut déplacer une pièce, false sinon.
     */
    public static boolean peutDeplacer(String[][] plateau) {
//        Determine la couleur du joueur en fonction du tour actuel.
        String couleur = (Variables_Globales.tour % 2-1 == 0) ? Variables_Globales.RED : Variables_Globales.BLUE;

//        Parcours le plateau pour vérifier les pièces du joueur.
        for (int y = 0; y < TAILLE; y++) {
            for (int x = 0; x < TAILLE; x++) {
                if (plateau[y][x].contains(couleur)) {
                    if (plateau[y][x].equals(Variables_Globales.DAME_BLEUE) || plateau[y][x].equals(Variables_Globales.DAME_ROUGE)) {
//                        Vérification pour les dames
                        int[][] directions = {{-1, -1}, {1, -1}, {-1, 1}, {1, 1}};
                        for (int[] dir : directions) {
                            int dx = dir[0], dy = dir[1];
                            int xTemp = x + dx, yTemp = y + dy;
//                            Parcours dans la direction jusqu'à la limite du plateau.
                            while (xTemp >= 0 && xTemp < TAILLE && yTemp >= 0 && yTemp < TAILLE) {
                                if (plateau[yTemp][xTemp].equals(Variables_Globales.CASE_NOIRE)) {
                                    return true;
                                } else if (!plateau[yTemp][xTemp].equals(Variables_Globales.CASE_NOIRE)) {
                                    break;
                                }
                                xTemp += dx;
                                yTemp += dy;
                            }
                        }
                    } else {
//                        Vérification pour les pions
                        int[][] directions = {{-1, -1}, {1, -1}, {-1, 1}, {1, 1}};
                        for (int[] dir : directions) {
                            int newX = x + dir[0];
                            int newY = y + dir[1];
//                            Verifies si le pion peut se déplacer.
                            if (newX >= 0 && newX < TAILLE && newY >= 0 && newY < TAILLE) {
                                if (plateau[newY][newX].equals(Variables_Globales.CASE_NOIRE)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * estBloquee vérifie si un joueur est bloqué (qu'il ne peut deplacer aucuns pions ou en manger aucuns).
     * @param plateau le plateau de jeu sur lequel on joue.
     * @return true si un joueur est bloqué, false sinon.
     */
    public static boolean estBloquee(String[][] plateau) {
        return !peutManger(plateau) && !peutDeplacer(plateau);
    }

    /**
     * finPartie vérifie si la partie est terminée.
     * @param plateau le plateau de jeu sur lequel on joue.
     * @return 1 si les pions bleus ont gagné, 2 si les pions rouges ont gagné, 0 sinon.
     */
    public static int finPartie(String[][] plateau) {
        int nbPionsBleus = 0, nbPionsRouges = 0;

//        Compte le nombre de pions bleus et rouges sur le plateau.
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                if (plateau[i][j].contains(BLUE)) {
                    nbPionsBleus++;
                } else if (plateau[i][j].contains(RED)) {
                    nbPionsRouges++;
                }
            }
        }

//        Vérifie si un joueur n'a plus de pions.
        if (nbPionsBleus == 0) return 1;
        else if (nbPionsRouges == 0) return 2;
        else return 0;
    }

    /**
     * estDame vérifie si un pion est arrivé à l'autre bout du plateau pour le transformer en estDame.
     * @param plateau le plateau de jeu sur lequel on joue.
     */
    public static void estDame(String[][] plateau) {
        for (int i = 0; i < TAILLE; i++) {
//            Verifie si un pion rouge est arrivé à la premiere ligne du plateau pour le transformer en dame.
            if (plateau[0][i].contains(PION_ROUGE)) {
                plateau[0][i] = DAME_ROUGE;
//            Verifie si un pion bleu est arrivé à la dernière ligne du plateau pour le transformer en dame.
            } else if (plateau[TAILLE - 1][i].contains(PION_BLEU)) {
                plateau[TAILLE - 1][i] = DAME_BLEUE;
            }
        }
    }
}