import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Déplacement {

//    Les constantes suivantes sont utilisées pour les pièces et les couleurs.
    private static final String CASE_NOIRE = Variables_Globales.CASE_NOIRE;
    private static final String PION_BLEU = Variables_Globales.PION_BLEU;
    private static final String DAME_BLEUE = Variables_Globales.DAME_BLEUE;
    private static final String PION_ROUGE = Variables_Globales.PION_ROUGE;
    private static final String DAME_ROUGE = Variables_Globales.DAME_ROUGE;

    private static final String BLUE = Variables_Globales.BLUE;
    private static final String RED = Variables_Globales.RED;

    private static final int TAILLE = 10;


    /**
     * deplacerPiece déplace une pièce d'une position à une autre, si le déplacement est valide.
     * @param plateau Le plateau de jeu sur lequel on joue.
     * @param x1 La position x de la pièce à déplacer.
     * @param y1 La position y de la pièce à déplacer.
     * @param x2 La position x de la destination.
     * @param y2 La position y de la destination.
     * @param afficherMessage Permet de choisir si on affiche un message (exemple : "Vous avez une estDame !").
     * @return true si le déplacement est effectué (valide), false sinon.
     */
    public static boolean deplacerPiece(String[][] plateau, int x1, int y1, int x2, int y2, boolean afficherMessage) {
        String piece = plateau[y1][x1];
        String couleur = piece.contains(BLUE) ? BLUE : RED;
        String couleurAdverse = couleur.equals(BLUE) ? RED : BLUE;

        switch (piece) {
//            On utilise un switch pour vérifier le type de pièce.
            case PION_BLEU, PION_ROUGE:
//                Si les pions sont déplacés de 2 cases et que l'arrivée est une case noire, on vérifie si un pion adverse est présent entre les deux cases.
                if ((x2 == x1 + 2 || x2 == x1 - 2) && (y2 == y1 + 2 || y2 == y1 - 2) && plateau[y2][x2].equals(CASE_NOIRE)) {
                    int xMid = (x1 + x2) / 2, yMid = (y1 + y2) / 2;
                    if (plateau[yMid][xMid].contains(couleurAdverse)) {
                        plateau[y2][x2] = piece;
                        plateau[y1][x1] = CASE_NOIRE;
                        plateau[yMid][xMid] = CASE_NOIRE;
                        if (afficherMessage) System.out.println("Vous avez mangé un pion !");
                        return true;
                    }
                } else if ((x2 == x1 + 1 || x2 == x1 - 1) && (y2 == y1 + 1 || y2 == y1 - 1) && plateau[y2][x2].equals(CASE_NOIRE)) {
//                    Si les pions sont déplacés de 1 case, on vérifie si le pion est à la dernière ligne du plateau.
//                    Si c'est le cas, on transforme le pion en dame.
                    if ((couleur.equals(BLUE) && y2 == 9) || (couleur.equals(RED) && y2 == 0)) {
                        plateau[y2][x2] = (couleur.equals(BLUE)) ? DAME_BLEUE : DAME_ROUGE;
                        if (afficherMessage) System.out.println("Vous avez une estDame !");
//                    Sinon, on déplace simplement le pion.
                    } else {
                        plateau[y2][x2] = piece;
                    }
                    plateau[y1][x1] = CASE_NOIRE;
                    return true;
                }
                break;
            case DAME_BLEUE, DAME_ROUGE:
//                Verifie si la destination est dans le plateau et si la dame est déplacée en diagonale.
                if (x2 >= 0 && x2 < TAILLE && y2 >= 0 && y2 < TAILLE && Math.abs(x2 - x1) == Math.abs(y2 - y1) && Math.abs(x2 - x1) != 0) {
                    int dx = (x2 - x1) / Math.abs(x2 - x1); // Direction de x.
                    int dy = (y2 - y1) / Math.abs(y2 - y1); // Direction de y.
                    int x = x1 + dx, y = y1 + dy;
                    boolean pathClear = true; // Vérifie si le chemin est libre.
                    boolean pieceAdverseTrouvee = false; // Vérifie si une pièce adverse est trouvée.

//                    Traverse le plateau jusqu'à la destination.
                    while (x != x2 && y != y2) {
                        if (plateau[y][x].contains(couleurAdverse)) {
//                            Si une pièce adverse est trouvée et qu'on en a deja trouvee une, le chemin n'est pas libre.
                            if (pieceAdverseTrouvee) {
                                pathClear = false;
                                break;
                            }
                            pieceAdverseTrouvee = true; // On a trouvé une pièce adverse.
                        } else if (!plateau[y][x].equals(CASE_NOIRE)) {
                            pathClear = false; // Si une pièce alliée est trouvée, le chemin n'est pas libre.
                            break;
                        }
                        x += dx;
                        y += dy;
                    }
//                    Si le chemin est libre, on déplace la dame.
                    if (pathClear && pieceAdverseTrouvee && plateau[y2][x2].equals(CASE_NOIRE)) {
                        plateau[y2][x2] = piece; // Bouge la pièce à la destination.
                        plateau[y1][x1] = CASE_NOIRE; // Enlève la pièce deplacee.
                        plateau[y - dy][x - dx] = CASE_NOIRE; // Retire la pièce adverse.
                        return true; // Retourne vrai pour indiquer que le déplacement est valide.
                    }
                }
                break;
        }
        if (afficherMessage) System.out.println("Déplacement invalide."); // Affiche un message si le déplacement est invalide.
        return false; // Retourne faux pour indiquer que le déplacement est invalide.
    }

    /**
     * trouverMeilleurDeplacement fait appel à trouverDeplacementsRecursifs pour trouver tous les déplacements possibles pour une pièce, et affiche les déplacements optimaux.
     * Une fois tous les déplacements trouvés, l'utilisateur peut choisir le déplacement qu'il souhaite effectuer.
     * @param plateau Le plateau de jeu sur lequel on joue.
     */
    public static void trouverMeilleurDeplacement(String[][] plateau) {
        Scanner scanner = new Scanner(System.in);

//        Determine la couleur du joueur et de son adversaire basé sur le tour.
        String couleur = (Variables_Globales.tour % 2 == 0) ? RED : BLUE;
        String couleurAdverse = (couleur.equals(BLUE)) ? RED : BLUE;
        List<List<int[]>> deplacements = new ArrayList<>();

//        Parcours le plateau pour trouver tous les déplacements possibles pour le joueur actuel.
        for (int y = 0; y < TAILLE; y++) {
            for (int x = 0; x < TAILLE; x++) {
                if (plateau[y][x].contains(couleur)) {
                    List<int[]> chemin = new ArrayList<>();
                    trouverDeplacementsRecursifs(plateau, x, y, couleur, couleurAdverse, chemin, deplacements);
                }
            }
        }

//        Si des déplacements sont trouvés, on affiche les déplacements optimaux.
        if (!deplacements.isEmpty()) {
            int maxDeplacementSize = deplacements.stream().mapToInt(List::size).max().orElse(0);
            List<List<int[]>> meilleursDeplacements = new ArrayList<>();
            for (List<int[]> deplacement : deplacements) {
                if (deplacement.size() == maxDeplacementSize) {
                    meilleursDeplacements.add(deplacement);
                }
            }

//            Retire les doublons
            for (int i = 0; i < meilleursDeplacements.size(); i++) {
                for (int j = meilleursDeplacements.size() - 1; j > i; j--) {
                    if (Arrays.deepEquals(meilleursDeplacements.get(i).toArray(), meilleursDeplacements.get(j).toArray())) {
                        meilleursDeplacements.remove(j);
                    }
                }
            }

//            Affiche les déplacements optimaux.
            System.out.println("Meilleurs déplacements:");
            for (int i = 0; i < meilleursDeplacements.size(); i++) {
                System.out.print((i + 1) + ": ");
                for (int[] pos : meilleursDeplacements.get(i)) {
                    System.out.print("(" + (char) (pos[0] + 'A') + pos[1] + ") -> ");
                }
                System.out.println();
            }

//            Demande à l'utilisateur de choisir un déplacement.
            int choix;
            do {
                System.out.print("Choisissez un déplacement (1-" + meilleursDeplacements.size() + "): ");
                choix = scanner.nextInt();
            } while (choix < 1 || choix > meilleursDeplacements.size());

//            Effectue le déplacement choisi.
            List<int[]> meilleurDeplacement = meilleursDeplacements.get(choix - 1);
            int pionsManges = 0;
            for (int i = 0; i < meilleurDeplacement.size() - 1; i++) {
                deplacerPiece(plateau, meilleurDeplacement.get(i)[0], meilleurDeplacement.get(i)[1], meilleurDeplacement.get(i + 1)[0], meilleurDeplacement.get(i + 1)[1], false);
                pionsManges++;
            }
            System.out.println("Nombre de pions mangés: " + pionsManges);
        } else {
            System.out.println("Aucun déplacement optimal trouvé.");
        }
    }


    /**
     * trouverDeplacementsRecursifs trouve tous les déplacements possibles pour une pièce, en utilisant la récursivité.
     * @param plateau Le plateau de jeu sur lequel on joue.
     * @param x La position x de la pièce.
     * @param y La position y de la pièce.
     * @param couleur La couleur de la pièce.
     * @param couleurAdverse La couleur de l'adversaire.
     * @param chemin La liste des positions déjà visitées.
     * @param deplacements La liste des déplacements possibles.
     */
    public static void trouverDeplacementsRecursifs(String[][] plateau, int x, int y, String couleur, String couleurAdverse, List<int[]> chemin, List<List<int[]>> deplacements) {
//        Si le chemin est vide, on ajoute la position actuelle à la liste des positions.
        if (chemin.isEmpty()) {
            chemin.add(new int[]{x, y});
        }

        String piece = plateau[y][x];
        int[] directions = {-1, 1};

//        Parcours les directions pour trouver les déplacements possibles.
        for (int dx : directions) {
            for (int dy : directions) {
                int nx = x + dx;
                int ny = y + dy;

//                Vérifie si le pion est bleu ou rouge.
                if (piece.equals(PION_BLEU) || piece.equals(PION_ROUGE)) {
//                    Vérifie si la destination est dans le plateau et si la destination est vide.
                    if (nx + dx >= 0 && nx + dx < TAILLE && ny + dy >= 0 && ny + dy < TAILLE && plateau[ny][nx].contains(couleurAdverse) && plateau[ny + dy][nx + dx].equals(CASE_NOIRE)) {
                        int[] newPos = new int[]{nx + dx, ny + dy};
//                        Si la nouvelle position n'est pas déjà visitée, on ajoute le déplacement à la liste des déplacements et on continue la recursivite.
                        if (!chemin.contains(newPos)) {
                            String[][] copiePlateau = Plateau.copierPlateau(plateau);
                            copiePlateau[ny + dy][nx + dx] = piece;
                            copiePlateau[y][x] = CASE_NOIRE;
                            copiePlateau[ny][nx] = CASE_NOIRE;
                            List<int[]> nouveauChemin = new ArrayList<>(chemin);
                            nouveauChemin.add(newPos);
                            deplacements.add(nouveauChemin);
                            trouverDeplacementsRecursifs(copiePlateau, nx + dx, ny + dy, couleur, couleurAdverse, nouveauChemin, deplacements);
                        }
                    }
//                Vérifie si c'est une dame et sa couleur.
                } else if (piece.equals(DAME_BLEUE) || piece.equals(DAME_ROUGE)) {
//                     Parcours le plateau dans la direction donnée.
                    while (nx >= 0 && nx < TAILLE && ny >= 0 && ny < TAILLE) {
//                        Si une pièce adverse est trouvée, on vérifie si la case suivante est vide.
                        if (plateau[ny][nx].contains(couleurAdverse)) {
                            int mx = nx + dx, my = ny + dy;
                            if (mx >= 0 && mx < TAILLE && my >= 0 && my < TAILLE && plateau[my][mx].equals(CASE_NOIRE)) {
                                int[] newPos = new int[]{mx, my};
//                                Si la nouvelle position n'est pas déjà visitée, on ajoute le déplacement à la liste des déplacements et on continue la recursivite.
                                if (!chemin.contains(newPos)) {
                                    String[][] copiePlateau = Plateau.copierPlateau(plateau);
                                    copiePlateau[my][mx] = piece;
                                    copiePlateau[y][x] = CASE_NOIRE;
                                    copiePlateau[ny][nx] = CASE_NOIRE;
                                    List<int[]> nouveauChemin = new ArrayList<>(chemin);
                                    nouveauChemin.add(newPos);
                                    deplacements.add(nouveauChemin);
                                    trouverDeplacementsRecursifs(copiePlateau, mx, my, couleur, couleurAdverse, nouveauChemin, deplacements);
                                }
                            }
                            break;
//                        Si une case non vide est trouvée, on arrête la recherche dans cette direction.
                        } else if (!plateau[ny][nx].equals(CASE_NOIRE)) {
                            break;
                        }
                        nx += dx;
                        ny += dy;
                    }
                }
            }
        }
    }
}