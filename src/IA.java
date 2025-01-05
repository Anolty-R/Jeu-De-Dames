import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IA {
//    Les constantes suivantes sont utilisées pour les pièces et les couleurs.
    private static final String CASE_NOIRE = Variables_Globales.CASE_NOIRE;
    private static final String PION_BLEU = Variables_Globales.PION_BLEU;
    private static final String DAME_BLEUE = Variables_Globales.DAME_BLEUE;
    private static final String PION_ROUGE = Variables_Globales.PION_ROUGE;
    private static final String DAME_ROUGE = Variables_Globales.DAME_ROUGE;

    private static final String BLUE = Variables_Globales.BLUE;
    private static final String RED = Variables_Globales.RED;

    private static final int TAILLE = Variables_Globales.TAILLE;

    /**
     * deplacerDame déplace une dame sur le plateau.
     * @param plateau le plateau de jeu sur lequel on joue.
     * @param i la ligne de la dame.
     * @param j la colonne de la dame.
     * @param random un objet Random pour générer des nombres aléatoires.
     */
    private static void deplacerDame(String[][] plateau, int i, int j, Random random) {
        int[][] directions = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
//        Recherche la direction dans laquelle la dame peut se déplacer.
        for (int[] dir : directions) {
            int maxDistance = 0;
            int x = i + dir[0], y = j + dir[1];
            while (x >= 0 && x < TAILLE && y >= 0 && y < TAILLE && plateau[x][y].equals(CASE_NOIRE)) {
                maxDistance++;
                x += dir[0];
                y += dir[1];
            }
//            Si la dame peut se déplacer, on la déplace.
            if (maxDistance > 0) {
                int distance = random.nextInt(maxDistance) + 1;
                x = i + dir[0] * distance;
                y = j + dir[1] * distance;
                plateau[x][y] = plateau[i][j];
                plateau[i][j] = CASE_NOIRE;
                System.out.println("Dame déplacée de (" + (char) (65 + j) + i + ") vers (" + (char) (65 + y) + x + ")");
                return;
            }
        }
    }

    /**
     * IALvl1 est une IA qui joue le premier coup possible.
     * @param plateau le plateau de jeu sur lequel on joue.
     * @param couleurJoueur la couleur de l'IA.
     */
    public static void IALvl1(String[][] plateau, String couleurJoueur) {
        Random random = new Random();

//        Vérifie si l'IA peut manger un pion.
        if (Vérification.peutManger(plateau)) {
            System.out.println("L'IA peut manger un pion. Déplacement automatique.");
            trouverMeilleurDeplacementIA1(plateau, couleurJoueur);
//        Si l'IA ne peut pas manger de pion, elle joue le premier coup possible.
        } else {
            if (couleurJoueur.equals(RED)) {
                for (int i = 0; i < TAILLE; i++) {
                    for (int j = 0; j < TAILLE; j++) {
//                        Regarde si c'est une pièce rouge ou une dame rouge.
                        if (plateau[i][j].contains(PION_ROUGE) || plateau[i][j].contains(DAME_ROUGE)) {
//                            Si c'est une dame rouge, on appelle la méthode deplacerDame.
                            if (plateau[i][j].contains(DAME_ROUGE)) {
                                deplacerDame(plateau, i, j, random);
                                return;
                            }
//                            Si c'est un pion rouge, on regarde si le pion peut se déplacer en haut à gauche.
                            if (i - 1 >= 0 && j - 1 >= 0 && plateau[i - 1][j - 1].equals(CASE_NOIRE)) {
                                plateau[i - 1][j - 1] = plateau[i][j];
                                plateau[i][j] = CASE_NOIRE;
                                System.out.println("Pièce déplacée de (" + (char) (65 + j) + i + ") vers (" + (char) (65 + (j - 1)) + (i - 1) + ")");
                                return;
//                                Si le pion ne peut pas se déplacer en haut à gauche, on regarde si le pion peut se déplacer en haut à droite.
                            } else if (i - 1 >= 0 && j + 1 < TAILLE && plateau[i - 1][j + 1].equals(CASE_NOIRE)) {
                                plateau[i - 1][j + 1] = plateau[i][j];
                                plateau[i][j] = CASE_NOIRE;
                                System.out.println("Pièce déplacée de (" + (char) (65 + j) + i + ") vers (" + (char) (65 + (j + 1)) + (i - 1) + ")");
                                return;
                            }
                        }
                    }
                }
//                Si c'est un pion bleu ou une dame bleue.
            } else {
                for (int i = TAILLE - 1; i >= 0; i--) {
                    for (int j = TAILLE - 1; j >= 0; j--) {
//                        Si c'est une dame bleue, on appelle la méthode deplacerDame.
                        if (plateau[i][j].contains(PION_BLEU) || plateau[i][j].contains(DAME_BLEUE)) {
                            if (plateau[i][j].contains(DAME_BLEUE)) {
                                deplacerDame(plateau, i, j, random);
                                return;
                            }
//                            Si c'est un pion bleu, on regarde si le pion peut se déplacer en bas à gauche.
                            if (i + 1 < TAILLE && j - 1 >= 0 && plateau[i + 1][j - 1].equals(CASE_NOIRE)) {
                                plateau[i + 1][j - 1] = plateau[i][j];
                                plateau[i][j] = CASE_NOIRE;
                                System.out.println("Pièce déplacée de (" + (char) (65 + j) + i + ") vers (" + (char) (65 + (j - 1)) + (i + 1) + ")");
                                return;
//                                Si le pion ne peut pas se déplacer en bas à gauche, on regarde si le pion peut se déplacer en bas à droite.
                            } else if (i + 1 < TAILLE && j + 1 < TAILLE && plateau[i + 1][j + 1].equals(CASE_NOIRE)) {
                                plateau[i + 1][j + 1] = plateau[i][j];
                                plateau[i][j] = CASE_NOIRE;
                                System.out.println("Pièce déplacée de (" + (char) (65 + j) + i + ") vers (" + (char) (65 + (j + 1)) + (i + 1) + ")");
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * trouverMeilleurDeplacementIA1 trouve le meilleur déplacement pour l'IA de niveau 1 et selectionne automatiquement le premier coup possible.
     * @param plateau le plateau de jeu sur lequel on joue.
     * @param couleurJoueur la couleur de l'IA.
     */
    public static void trouverMeilleurDeplacementIA1(String[][] plateau, String couleurJoueur) {
        String couleurAdverse = (couleurJoueur.equals(BLUE)) ? RED : BLUE;
        List<List<int[]>> deplacements = new ArrayList<>();

        for (int y = 0; y < TAILLE; y++) {
            for (int x = 0; x < TAILLE; x++) {
                if (plateau[y][x].contains(couleurJoueur)) {
                    List<int[]> chemin = new ArrayList<>();
                    Déplacement.trouverDeplacementsRecursifs(plateau, x, y, couleurJoueur, couleurAdverse, chemin, deplacements);
                }
            }
        }

        if (!deplacements.isEmpty()) {
            List<int[]> meilleurDeplacement = deplacements.get(0); // Prends toujours le premier chemin
            int pionsManges = 0;
            for (int i = 0; i < meilleurDeplacement.size() - 1; i++) {
                int xDepart = meilleurDeplacement.get(i)[0];
                int yDepart = meilleurDeplacement.get(i)[1];
                int xArrivee = meilleurDeplacement.get(i + 1)[0];
                int yArrivee = meilleurDeplacement.get(i + 1)[1];
                if (Déplacement.deplacerPiece(plateau, meilleurDeplacement.get(i)[0], meilleurDeplacement.get(i)[1], meilleurDeplacement.get(i + 1)[0], meilleurDeplacement.get(i + 1)[1], false)) {
                    pionsManges++;
                    System.out.println("Déplacement de (" + (char) (65 + xDepart) + yDepart + ") vers (" + (char) (65 + xArrivee) + yArrivee + ")");
                }
            }
            System.out.println("Nombre de pions mangés: " + pionsManges);
        } else {
            System.out.println("Aucun déplacement optimal trouvé.");
        }
    }

    /**
     * IALvl2 est une IA qui joue un coup aléatoire parmi les coups possibles.
     * @param plateau le plateau de jeu sur lequel on joue.
     * @param couleurJoueur la couleur de l'IA.
     */
    public static void IALvl2(String[][] plateau, String couleurJoueur) {
        String couleurAdverse = (couleurJoueur.equals(BLUE)) ? RED : BLUE;
        List<List<int[]>> deplacements = new ArrayList<>();
        Random random = new Random();

//        Vérifie si l'IA peut manger un pion.
//        Si oui, on lance la méthode trouverMeilleurDeplacementIA2 pour trouver le meilleur déplacement possible.
        if (Vérification.peutManger(plateau)) {
            System.out.println("L'IA peut manger un pion. Déplacement automatique.");
            trouverMeilleurDeplacementIA2(plateau, couleurJoueur);
        }

//        Trouve tous les déplacements possibles
        for (int y = 0; y < TAILLE; y++) {
            for (int x = 0; x < TAILLE; x++) {
                if (plateau[y][x].contains(couleurJoueur)) {
                    List<int[]> chemin = new ArrayList<>();
                    Déplacement.trouverDeplacementsRecursifs(plateau, x, y, couleurJoueur, couleurAdverse, chemin, deplacements);
                }
            }
        }

        if (!deplacements.isEmpty()) {
//            Si des déplacements sont possibles, on en choisit un aléatoirement.
//            Si aucun déplacement n'est possible, on affiche un message.
            List<int[]> deplacementChoisi = deplacements.get(random.nextInt(deplacements.size()));
            int pionsManges = 0;
            for (int i = 0; i < deplacementChoisi.size() - 1; i++) {
                int xDepart = deplacementChoisi.get(i)[0];
                int yDepart = deplacementChoisi.get(i)[1];
                int xArrivee = deplacementChoisi.get(i + 1)[0];
                int yArrivee = deplacementChoisi.get(i + 1)[1];
                if (Déplacement.deplacerPiece(plateau, xDepart, yDepart, xArrivee, yArrivee, false)) {
                    pionsManges++;
                    System.out.println("Déplacement de (" + (char) (65 + xDepart) + yDepart + ") vers (" + (char) (65 + xArrivee) + yArrivee + ")");
                }
            }
            System.out.println("Nombre de pions mangés: " + pionsManges);
        } else {
            System.out.println("Aucun déplacement possible.");
        }
    }

    /**
     * trouverMeilleurDeplacementIA2 trouve le meilleur déplacement pour l'IA de niveau 2 et selectionne un déplacement aléatoire parmi les déplacements possibles.
     * @param plateau le plateau de jeu sur lequel on joue.
     * @param couleurJoueur la couleur de l'IA.
     */
    public static void trouverMeilleurDeplacementIA2(String[][] plateau, String couleurJoueur) {
        String couleurAdverse = (couleurJoueur.equals(BLUE)) ? RED : BLUE;
        List<List<int[]>> deplacements = new ArrayList<>();

        for (int y = 0; y < TAILLE; y++) {
            for (int x = 0; x < TAILLE; x++) {
                if (plateau[y][x].contains(couleurJoueur)) {
                    List<int[]> chemin = new ArrayList<>();
                    Déplacement.trouverDeplacementsRecursifs(plateau, x, y, couleurJoueur, couleurAdverse, chemin, deplacements);
                }
            }
        }

        if (!deplacements.isEmpty()) {
            List<int[]> meilleurDeplacement = deplacements.get(0); // Prends toujours le premier chemin
            int pionsManges = 0;
            for (int i = 0; i < meilleurDeplacement.size() - 1; i++) {
                int xDepart = meilleurDeplacement.get(i)[0];
                int yDepart = meilleurDeplacement.get(i)[1];
                int xArrivee = meilleurDeplacement.get(i + 1)[0];
                int yArrivee = meilleurDeplacement.get(i + 1)[1];
                if (Déplacement.deplacerPiece(plateau, meilleurDeplacement.get(i)[0], meilleurDeplacement.get(i)[1], meilleurDeplacement.get(i + 1)[0], meilleurDeplacement.get(i + 1)[1], false)) {
                    pionsManges++;
                    System.out.println("Déplacement de (" + (char) (65 + xDepart) + yDepart + ") vers (" + (char) (65 + xArrivee) + yArrivee + ")");
                }
            }
            System.out.println("Nombre de pions mangés: " + pionsManges);
        } else {
            System.out.println("Aucun déplacement optimal trouvé.");
        }
    }

    /**
     * IALvl3 est une IA qui mange un pion si possible, sinon elle joue le meilleur coup possible.
     * @param plateau le plateau de jeu sur lequel on joue.
     * @param couleurJoueur la couleur de l'IA.
     */
    public static void IALvl3(String[][] plateau, String couleurJoueur) {
//        Verifie si l'IA peut manger un pion.
        if (Vérification.peutManger(plateau)) {
            System.out.println("L'IA mange un pion.");
            trouverMeilleurDeplacementIA3(plateau, couleurJoueur);
            return;
        }

        List<String> coupsPossibles = new ArrayList<>();
        Random random = new Random();
//        Parcours le plateau pour trouver les coups possibles.
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
//                Regarde si c'est une dame rouge, un pion rouge, un pion bleu ou une dame bleue.
                if (plateau[i][j].contains(couleurJoueur.equals(RED) ? PION_ROUGE : PION_BLEU) || plateau[i][j].contains(couleurJoueur.equals(RED) ? DAME_ROUGE : DAME_BLEUE)) {
//                    Si c'est une dame, on appelle la méthode deplacerDame.
                    if (plateau[i][j].contains(couleurJoueur.equals(RED) ? DAME_ROUGE : DAME_BLEUE)) {
                        deplacerDame(plateau, i, j, random);
                        return;
                    }
//                    Regarde les deplacement possibles pour un pion rouge.
                    if (couleurJoueur.equals(RED)) {
                        if (i - 1 >= 0 && j - 1 >= 0 && plateau[i - 1][j - 1].equals(CASE_NOIRE)) {
                            coupsPossibles.add(i + " " + j + " " + (i - 1) + " " + (j - 1));
                        }
                        if (i - 1 >= 0 && j + 1 < TAILLE && plateau[i - 1][j + 1].equals(CASE_NOIRE)) {
                            coupsPossibles.add(i + " " + j + " " + (i - 1) + " " + (j + 1));
                        }
//                    Regarde les deplacements possibles pour un pion bleu.
                    } else {
                        if (i + 1 < TAILLE && j - 1 >= 0 && plateau[i + 1][j - 1].equals(CASE_NOIRE)) {
                            coupsPossibles.add(i + " " + j + " " + (i + 1) + " " + (j - 1));
                        }
                        if (i + 1 < TAILLE && j + 1 < TAILLE && plateau[i + 1][j + 1].equals(CASE_NOIRE)) {
                            coupsPossibles.add(i + " " + j + " " + (i + 1) + " " + (j + 1));
                        }
                    }
                }
            }
        }

//        Si des coups sont possibles, on en choisit un aléatoirement.
        if (coupsPossibles.size() > 0) {
            String[] coup = coupsPossibles.get(random.nextInt(coupsPossibles.size())).split(" ");
            int xArrivee = Integer.parseInt(coup[3]);
            int yArrivee = Integer.parseInt(coup[2]);
            int xDepart = Integer.parseInt(coup[1]);
            int yDepart = Integer.parseInt(coup[0]);
            plateau[yArrivee][xArrivee] = plateau[yDepart][xDepart];
            plateau[yDepart][xDepart] = CASE_NOIRE;
            System.out.println("Pièce déplacée de (" + (char) (65 + xDepart) + yDepart + ") vers (" + (char) (65 + xArrivee) + yArrivee + ")");
            return;
        }
    }

    /**
     * trouverMeilleurDeplacementIA3 si l'Ia peut manger un pion, trouve le meilleur déplacement pour l'IA de niveau 3 et choisie un coup aléatoirement.
     * @param plateau le plateau de jeu sur lequel on joue.
     * @param couleurJoueur la couleur de l'IA.
     */
    public static void trouverMeilleurDeplacementIA3(String[][] plateau, String couleurJoueur) {
        String couleurAdverse = (couleurJoueur.equals(BLUE)) ? RED : BLUE;
        List<List<int[]>> deplacements = new ArrayList<>();
        Random random = new Random();


//        Parcours le plateau pour trouver les déplacements possibles.
        for (int y = 0; y < TAILLE; y++) {
            for (int x = 0; x < TAILLE; x++) {
                if (plateau[y][x].contains(couleurJoueur)) {
                    List<int[]> chemin = new ArrayList<>();
                    Déplacement.trouverDeplacementsRecursifs(plateau, x, y, couleurJoueur, couleurAdverse, chemin, deplacements);
                }
            }
        }

        if (!deplacements.isEmpty()) {
            List<int[]> meilleurDeplacement = null;
//            Parcours les déplacements possibles pour trouver le meilleur déplacement.
            for (List<int[]> deplacement : deplacements) {
                boolean safe = true;
                for (int i = 0; i < deplacement.size() - 1; i++) {
                    int xArrivee = deplacement.get(i + 1)[0];
                    int yArrivee = deplacement.get(i + 1)[1];
                    if (Vérification.peutManger(plateau)) {
                        safe = false;
                        break;
                    }
                }
                if (safe) {
                    meilleurDeplacement = deplacement;
                    break;
                }
            }

//            Si aucun déplacement n'est sûr, on choisit un déplacement aléatoire.
            if (meilleurDeplacement == null) {
                meilleurDeplacement = deplacements.get(random.nextInt(deplacements.size()));
            }

            int pionsManges = 0;
//            Execute le déplacement.
            for (int i = 0; i < meilleurDeplacement.size() - 1; i++) {
                int xDepart = meilleurDeplacement.get(i)[0];
                int yDepart = meilleurDeplacement.get(i)[1];
                int xArrivee = meilleurDeplacement.get(i + 1)[0];
                int yArrivee = meilleurDeplacement.get(i + 1)[1];
                if (Déplacement.deplacerPiece(plateau, xDepart, yDepart, xArrivee, yArrivee, false)) {
                    pionsManges++;
                    System.out.println("Déplacement de (" + (char) (65 + xDepart) + yDepart + ") vers (" + (char) (65 + xArrivee) + yArrivee + ")");
                }
            }
            System.out.println("Nombre de pions mangés: " + pionsManges);
        } else {
            System.out.println("Aucun déplacement optimal trouvé.");
        }
    }
}