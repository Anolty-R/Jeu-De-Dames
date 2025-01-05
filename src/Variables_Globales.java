public class Variables_Globales {

    /*
     * Variables globales utilisées dans le programme.
     * Permet une utilisation plus simple et plus rapide des variables.
     * Permettent de définir les couleurs des pièces, les niveaux de l'IA, les cases du plateau, etc.
     */
    public static final String RESET = "\u001B[0m";
    public static final String BLUE = "\u001B[34m";
    public static final String DARK_BLUE = "\u001B[38;5;18m";
    public static final String RED = "\u001B[31m";
    public static final String DARK_RED = "\u001B[38;5;88m";
    public static final String BLACK = "\u001B[30m";

    public static final String CASE_BLANCHE = RESET + " ■ ";
    public static final String CASE_NOIRE = BLACK + " ■ " + RESET;
    public static final String PION_BLEU = BLUE + " ● " + RESET;
    public static final String DAME_BLEUE = BLUE + DARK_BLUE + " ● " + RESET;
    public static final String PION_ROUGE = RED + " ● " + RESET;
    public static final String DAME_ROUGE = RED + DARK_RED + " ● " + RESET;

    public static int Niveau_IA1 = 0;
    public static int Niveau_IA2 = 0;

    public static final int TAILLE = 10;
    public static int tour = 1;
}