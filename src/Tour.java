import java.util.Scanner;

public class Tour {
//    Les constantes suivantes sont utilisées pour les pièces et les couleurs.
    private static final int TAILLE = 10;


    /**
     * JcJ permet de lancer un jeu de dames en mode joueur contre joueur.
     */
    public static void JcJ() {
        Variables_Globales.tour = 1;

        Scanner scanner = new Scanner(System.in);
        String[][] plateau = new String[TAILLE][TAILLE];

//        Initialisation du plateau
        Plateau.initialiserPlateau(plateau);

//        Demande des pseudonymes des joueurs
        System.out.print("Entrez le pseudonyme du joueur 1 (Bleu): ");
        String pseudoJoueur1 = Variables_Globales.BLUE + scanner.nextLine() + Variables_Globales.RESET;
        System.out.print("Entrez le pseudonyme du joueur 2 (Rouge): ");
        String pseudoJoueur2 = Variables_Globales.RED + scanner.nextLine() + Variables_Globales.RESET;

//        Affichage du plateau
        Plateau.afficherPlateau(plateau);

//        Boucle de jeu
        do {
//            Affichage du tour
            System.out.println("Tour " + Variables_Globales.tour + " : " + (Variables_Globales.tour % 2 == 0 ? pseudoJoueur2 : pseudoJoueur1));
//            Couleur du joueur
            String couleurJoueur = (Variables_Globales.tour % 2 == 0) ? Variables_Globales.RED : Variables_Globales.BLUE;

//            Si le joueur peut manger, il doit manger
            if (Vérification.peutManger(plateau)) {
                Déplacement.trouverMeilleurDeplacement(plateau);
                Variables_Globales.tour++;
//            Sinon, le joueur doit déplacer une pièce
            } else {
                int x1, y1, x2, y2;
                int nbtour = 0;

//                Demande de la position de la pièce à déplacer
                do {
                    if (nbtour != 0) {
                        System.out.print("Ce n'est pas votre pièce. ");
                    }
                    System.out.print("Entrez la position de la pièce à déplacer (ex: A0): ");
                    String position1 = scanner.nextLine().toUpperCase();
                    x1 = (int) position1.charAt(0) - 'A';
                    y1 = position1.charAt(1) - '0';
                    nbtour++;
                } while (!plateau[y1][x1].contains(couleurJoueur));

//                Demande de la position de destination
                System.out.print("Entrez la position de destination (ex: B1): ");
                String position2 = scanner.nextLine().toUpperCase();
                x2 = (int) position2.charAt(0) - 'A';
                y2 = position2.charAt(1) - '0';

//                Si le déplacement est valide, on déplace la pièce
                if (Déplacement.deplacerPiece(plateau, x1, y1, x2, y2, true)) {
                    System.out.println("Déplacement réussi.");
                    Variables_Globales.tour++;
//                Sinon, on affiche un message d'erreur et on recommence le tour
                } else {
                    System.out.println("Déplacement invalide.");
                }
            }

//            Affichage du plateau
            Plateau.afficherPlateau(plateau);
//        Verifie si c'est la fin de la partie
        } while (Vérification.finPartie(plateau) == 0 && !Vérification.estBloquee(plateau));
        Plateau.afficherPlateau(plateau);
//        Affichage du gagnant
        if (!Vérification.estBloquee(plateau)) {
            System.out.println("Fin de la partie au tour " + (Variables_Globales.tour - 1) + ". Le joueur " + (Vérification.finPartie(plateau) == 1 ? pseudoJoueur2 : pseudoJoueur1) + " a gagné.\n");
        } else {
            System.out.println("Fin de la partie au tour " + (Variables_Globales.tour - 1) + ". La partie est bloquée. Le vainqueur est donc le joueur " + (Variables_Globales.tour % 2 == 0 ? pseudoJoueur2 : pseudoJoueur1) + ".\n");
        } System.out.println("---------------------------------------------------\n");
    }


    /**
     * JcIA permet de lancer un jeu de dames en mode joueur contre IA.
     * @param niveau_IA1 le niveau de difficulté de l'IA.
     */
    public static void JcIA(int niveau_IA1) {

//        Initialisation des variables
        Variables_Globales.tour = 1;
        Variables_Globales.Niveau_IA1 = niveau_IA1;

        Scanner scanner = new Scanner(System.in);
        String[][] plateau = new String[TAILLE][TAILLE];

//        Initialisation du plateau
        Plateau.initialiserPlateau(plateau);

//        Demande du pseudonyme du joueur
        System.out.print("Entrez le pseudonyme du joueur 1 (Bleu): ");
        String pseudoJoueur1 = Variables_Globales.BLUE + scanner.nextLine() + Variables_Globales.RESET;
        String pseudoJoueur2 = Variables_Globales.RED + "IA Rouge" + Variables_Globales.RESET;

//        Boucle de jeu
        do {
            String couleurJoueur = (Variables_Globales.tour % 2 == 0) ? Variables_Globales.RED : Variables_Globales.BLUE;
//            Affichage du plateau
            System.out.println("\n");
            Plateau.afficherPlateau(plateau);

//            Affichage du tour
            System.out.println("Tour " + Variables_Globales.tour + " : " + (Variables_Globales.tour % 2 == 0 ? pseudoJoueur2 : pseudoJoueur1));

//            Si c'est le tour de l'IA, on appelle la méthode de l'IA correspondant au niveau de difficulté
            if (Variables_Globales.tour % 2 == 0) {
                switch (Variables_Globales.Niveau_IA1) {
                    case 1:
                        IA.IALvl1(plateau, Variables_Globales.RED);
                        Variables_Globales.tour++;
                        break;
                    case 2:
                        IA.IALvl2(plateau, Variables_Globales.RED);
                        Variables_Globales.tour++;
                        break;
                    case 3:
                        IA.IALvl3(plateau, Variables_Globales.RED);
                        Variables_Globales.tour++;
                        break;
                }
//                Si le joueur peut manger, il doit manger
            } else if (Vérification.peutManger(plateau)) {
                System.out.println("Vous pouvez manger un pion. Déplacement automatique.");
                Déplacement.trouverMeilleurDeplacement(plateau);
//            Sinon, le joueur doit déplacer une pièce
            } else {
                int x1, y1, x2, y2;
                int nbtour = 0;

//                Demande de la position de la pièce à déplacer
                do {
                    if (nbtour != 0) {
                        System.out.print("Ce n'est pas votre pièce. ");
                    }
                    System.out.print("Entrez la position de la pièce à déplacer (ex: A0): ");
                    String position1 = scanner.nextLine().toUpperCase();
                    x1 = (int) position1.charAt(0) - 'A';
                    y1 = position1.charAt(1) - '0';
                    nbtour++;
                } while (!plateau[y1][x1].contains(couleurJoueur));

//                Demande de la position de destination
                System.out.print("Entrez la position de destination (ex: B1): ");
                String position2 = scanner.nextLine().toUpperCase();
                x2 = (int) position2.charAt(0) - 'A';
                y2 = position2.charAt(1) - '0';

//                Si le déplacement est valide, on déplace la pièce
                if (Déplacement.deplacerPiece(plateau, x1, y1, x2, y2, true)) {
                    System.out.println("Déplacement réussi.");
                    Variables_Globales.tour++;
                } else {
                    System.out.println("Déplacement invalide.");
                }
            }

//            Vérification si la pièce est une dame (pour l'IA principalement)
            Vérification.estDame(plateau);

//            Affichage du plateau
            System.out.println("\n");
            Plateau.afficherPlateau(plateau);

//        Vérification si c'est la fin de la partie
        } while (Vérification.finPartie(plateau) == 0 && !Vérification.estBloquee(plateau));
        Plateau.afficherPlateau(plateau);
//        Affichage du gagnant
        if (!Vérification.estBloquee(plateau)) {
            System.out.println("Fin de la partie au tour " + (Variables_Globales.tour - 1) + ". Le joueur " + (Vérification.finPartie(plateau) == 1 ? pseudoJoueur2 : pseudoJoueur1) + " a gagné.\n");
        } else {
            System.out.println("Fin de la partie au tour " + (Variables_Globales.tour - 1) + ". La partie est bloquée. Le vainqueur est donc le joueur " + (Variables_Globales.tour % 2 == 0 ? pseudoJoueur2 : pseudoJoueur1) + ".\n");
        } System.out.println("---------------------------------------------------\n");
    }

    /**
     * IAcIA permet de lancer un jeu de dames en mode IA contre IA.
     * @param niveau_IA1 le niveau de difficulté de l'IA 1.
     * @param niveau_IA2 le niveau de difficulté de l'IA 2.
     */
    public static void IAcIA(int niveau_IA1, int niveau_IA2) {
//        Initialisation des variables
        Variables_Globales.tour = 1;
        Variables_Globales.Niveau_IA1 = niveau_IA1;
        Variables_Globales.Niveau_IA2 = niveau_IA2;

        Scanner scanner = new Scanner(System.in);
        String[][] plateau = new String[TAILLE][TAILLE];

//        Initialisation du plateau
        Plateau.initialiserPlateau(plateau);

//        Donne le pseudonyme des joueurs
        String pseudoJoueur1 = Variables_Globales.BLUE + "IA Bleue" + Variables_Globales.RESET;
        String pseudoJoueur2 = Variables_Globales.RED + "IA Rouge" + Variables_Globales.RESET;

        do {
//             Affichage du plateau
            System.out.println("\n");
            Plateau.afficherPlateau(plateau);

//            Affichage du tour
            System.out.print("Tour " + Variables_Globales.tour + " : " + (Variables_Globales.tour % 2 == 0 ? pseudoJoueur2 : pseudoJoueur1));
            scanner.nextLine();
//            Si c'est le tour de l'IA1, on appelle la méthode de l'IA correspondant au niveau de difficulté
            if (Variables_Globales.tour % 2 == 0) {
                switch (Variables_Globales.Niveau_IA1) {
                    case 1:
                        IA.IALvl1(plateau, Variables_Globales.RED);
                        break;
                    case 2:
                        IA.IALvl2(plateau, Variables_Globales.RED);
                        break;
                    case 3:
                        IA.IALvl3(plateau, Variables_Globales.RED);
                        break;
                }
//            Sinon, on appelle la méthode de l'IA correspondant au niveau de difficulté
            } else {
                switch (Variables_Globales.Niveau_IA2) {
                    case 1:
                        IA.IALvl1(plateau, Variables_Globales.BLUE);
                        break;
                    case 2:
                        IA.IALvl2(plateau, Variables_Globales.BLUE);
                        break;
                    case 3:
                        IA.IALvl3(plateau, Variables_Globales.BLUE);
                        break;
                }
            }

//            On ajoute un tour
            Variables_Globales.tour++;

//            Vérification si la pièce est une dame
            Vérification.estDame(plateau);

//            Verifie si c'est la fin de la partie
        } while (Vérification.finPartie(plateau) == 0 && !Vérification.estBloquee(plateau));
//        Affichage du plateau
        Plateau.afficherPlateau(plateau);
//        Affichage du gagnant
        if (!Vérification.estBloquee(plateau)) {
            System.out.println("Fin de la partie au tour " + (Variables_Globales.tour - 1) + ". Le joueur " + (Vérification.finPartie(plateau) == 1 ? pseudoJoueur2 : pseudoJoueur1) + " a gagné.\n");
        } else {
            System.out.println("Fin de la partie au tour " + (Variables_Globales.tour - 1) + ". La partie est bloquée. Le vainqueur est donc le joueur " + (Variables_Globales.tour % 2 == 0 ? pseudoJoueur2 : pseudoJoueur1) + ".\n");
        } System.out.println("---------------------------------------------------\n");
    }
}