import java.util.Scanner;

public class Lanceur {

    /*
     * Méthode principale du programme qui permet de lancer le jeu de dames.
     * Permet de choisir un mode de jeu (JcJ, JcIA, IAcIA) et de lancer le jeu.
     * Si le mode de jeu comporte une IA, l'utilisateur peut choisir la difficulté de l'IA.
     */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choix = 0, IA1 = 0, IA2 = 0;

        System.out.println("\nBienvenue dans le jeu de dames ! \n");

        do {

            System.out.println("Choississez un mode de jeu : \n");

            System.out.println("1. Joueur contre joueur");
            System.out.println("2. Joueur contre IA");
            System.out.println("3. IA contre IA");
            System.out.println("4. Quitter");

            System.out.println("\nPS: Pour les modes avec une IA, vous devrez choisir la difficulté de l'IA (Facile, Moyen, Difficile)\n"); // TODO: Ajouter l'IA et les difficultés

            System.out.print("Entrez le numéro du mode de jeu : ");
            choix = scanner.nextInt();

            switch (choix) {
//                Laisse le choix du mode de jeu à l'utilisateur
                case 1:
                    System.out.println("\nBienvenue dans le mode JcJ !\n");
                    Tour.JcJ();
                    break;
                case 2:
                    System.out.println("\nBienvenue dans le mode JcIA !\n");
                    System.out.print("Choisissez la difficulté de l'IA: \n\n1. Facile\n2. Normale\n3. Difficile\n\nVotre choix: ");
                    Variables_Globales.Niveau_IA1 = scanner.nextInt(); // NiveauIA1
                    Tour.JcIA(Variables_Globales.Niveau_IA1);
                    // Tour.JcIA(NiveauIA);
                    break;
                case 3:
                    System.out.println("\nBienvenue dans le mode IAcIA !");
                    System.out.print("\nChoisissez la difficulté de l'IA 1: \n\n1. Facile\n2. Normale\n3. Difficile\n\nVotre choix: ");
                    IA1 = scanner.nextInt(); // NiveauIA1
                    System.out.print("\nChoisissez la difficulté de l'IA 2: \n\n1. Facile\n2. Normale\n3. Difficile\n\nVotre choix: ");
                    IA2 = scanner.nextInt(); // NiveauIA2
                    Tour.IAcIA(IA1, IA2);
                    break;
                case 4:
                    System.out.println("Merci d'avoir joué !");
                    break;
                default:
                    System.out.println("Choix invalide.");
                    break;
            }

        } while (choix != 4);
    }
}