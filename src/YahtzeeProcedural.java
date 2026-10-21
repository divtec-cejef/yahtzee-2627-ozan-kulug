import java.util.Random;
import java.util.Scanner;

public class YahtzeeProcedural {
    static int NOMBRE_RELANCE_PLUS = 2;

    static final String[] CATEGORIES = {
            "Yahtzee", "Grande Suite", "Petite Suite", "Full House",
            "Carre", "Brelan", "Deux Paires", "Paire"
    };
    static final int NOMBRE_CATEGORIES = CATEGORIES.length;

    public static int lanceurDes(Random nbr) {
        return nbr.nextInt(6) + 1;
    }

    public static void afficherDes(int[] des) {
        for (int i = 0; i < des.length; i++) {
            System.out.println("{" + (i + 1) + "}" + des[i]);
        }
    }

    public static void relanceurDe(int[] positions, int[] des, Random nbr) {
        for (int position : positions) {
            des[position] = lanceurDes(nbr);
        }
    }

    public static int[] demandeRelance(Scanner lecteur) {
        System.out.println("Quels dés relancer ? (positions séparées par un espace, vide ou 0 pour arrêter)");
        String saisie = lecteur.nextLine();
        if (saisie.isEmpty() || saisie.equals("0")) {
            return new int[0];
        }
        String[] positions = saisie.split(" ");

        int[] indices = new int[positions.length];
        for (int i = 0; i < positions.length; i++) {
            int numero = Integer.parseInt(positions[i]) - 1;

            if (numero >= 0 && numero <= 4) {
                indices[i] = numero;
            } else {
                return new int[0];
            }
        }
        return indices;
    }

    public static int[] compterOccurrences(int[] des ){
        int[] occurrences = new int[6];
        for (int de : des) {
            occurrences[de - 1]++;
        }
        return occurrences;
    }

    public static boolean contientExacte(int[] occurrences, int nombre) {
        for (int occurrence : occurrences) {
            if (occurrence == nombre) {
                return true;
            }
        }
        return false;
    }
    public static boolean contientAuMoins(int[] occurrences, int nombre){
        for (int occurrence : occurrences) {
            if (occurrence >= nombre) {
                return true;
            }
        }
        return false;
    }

    public static boolean estUnePaire(int [] occurrences){
        return contientAuMoins(occurrences, 2);
    }

    public static boolean estBrelan(int[] occurrences){
        return contientAuMoins(occurrences, 3);
    }

    public static boolean estCarre(int[] occurrences){
        return contientAuMoins(occurrences, 4);
    }

    public static boolean estYahtzee(int[] occurrences){
        return contientExacte(occurrences, 5);
    }

    public static boolean deuxPaires(int[] occurrences){
        int compteur = 0;
        for (int occurrence : occurrences) {
            if (occurrence >= 2) {
                compteur++;
            }
        }
        return compteur == 2;
    }

    public static boolean estFullHouse(int[] occurrences){
        return contientExacte(occurrences, 2) && contientExacte(occurrences, 3);
    }

    public static boolean estPetiteSuite(int[] occurrences){
        return (occurrences[0] > 0 && occurrences[1] > 0 && occurrences[2] > 0 && occurrences[3] > 0)
                || (occurrences[1] > 0 && occurrences[2] > 0 && occurrences[3] > 0 && occurrences[4] > 0)
                || (occurrences[2] > 0 && occurrences[3] > 0 && occurrences[4] > 0 && occurrences[5] > 0);
    }

    public static boolean estGrandeSuite(int[] occurrences) {
        return (occurrences[0] > 0 && occurrences[1] > 0 && occurrences[2] > 0 && occurrences[3] > 0 && occurrences[4] > 0)
                || (occurrences[1] > 0 && occurrences[2] > 0 && occurrences[3] > 0 && occurrences[4] > 0 && occurrences[5] > 0);
    }

    public static int sommeDes(int[] des ){
        int somme = 0;
        for (int de : des) {
            somme += de;
        }
        return somme;
    }

    public static int scoreRepetition(int[] occurrences, int nombre) {
        for (int i = 0; i < occurrences.length; i++) {
            if (occurrences[i] >= nombre) {
                return (i + 1) * nombre;
            }
        }
        return 0;
    }

    public static String nomCategorie(int index){
        return CATEGORIES[index - 1];
    }

    public static int calculerScore (String categorie, int[] occurrences) {
        return switch (categorie) {
            case "Yahtzee" -> estYahtzee(occurrences) ? 50 : 0;
            case "Grande Suite" -> estGrandeSuite(occurrences) ? 40 : 0;
            case "Petite Suite" -> estPetiteSuite(occurrences) ? 30 : 0;
            case "Full House" -> estFullHouse(occurrences) ? 25 : 0;
            case "Carre" -> estCarre(occurrences) ? scoreRepetition(occurrences, 4) : 0;
            case "Brelan" -> estBrelan(occurrences) ? scoreRepetition(occurrences, 3) : 0;
            case "Deux Paires" -> deuxPaires(occurrences) ? 10 : 0;
            case "Paire" -> estUnePaire(occurrences) ? 5 : 0;
            default -> 0;
        };
    }

    public static void afficherDisponibles(String utilisees, int[] occurrences) {
        System.out.println("Combinaisons disponibles :");
        for (int i = 1; i <= NOMBRE_CATEGORIES; i++) {
            if (!estUtilisee(utilisees, i)) {
                System.out.println(i + ") " + nomCategorie(i) + " : " + calculerScore(nomCategorie(i), occurrences));
            }
        }
    }

    public static boolean estUtilisee(String utilisees, int numero) {
        return utilisees.contains(String.valueOf(numero));
    }

    public static int choisirCombinaison(int[] occurrences, String utilisees, Scanner lecteur){
        int choix = 0;
        while (choix < 1 || choix > NOMBRE_CATEGORIES || estUtilisee(utilisees, choix)) {
            System.out.println("Choisissez une combinaison disponible :");
            try {
                choix = Integer.parseInt(lecteur.nextLine().trim());
            } catch (NumberFormatException e) {
                choix = 0;
            }
        }
        return choix;
    }

    public static void main(String[] args) {
        Random nbr = new Random();
        Scanner lecteur = new Scanner(System.in);
        String utilisees = "";

        int total = 0;
        for (int manche = 1; manche <= 5; manche++) {
            int[] des = new int[5];


            for (int i = 0; i < des.length; i++) {
                des[i] = lanceurDes(nbr);
            }
            afficherDes(des);


            for (int nombreLancer = 1;
                 nombreLancer <= NOMBRE_RELANCE_PLUS;
                 nombreLancer++) {
                int[] indices = demandeRelance(lecteur);

                if (indices.length == 0) {
                    break;
                }

                relanceurDe(indices, des, nbr);

                afficherDes(des);
            }
            int[] occurrences = compterOccurrences(des);

            afficherDisponibles(utilisees, occurrences);

            int choix = choisirCombinaison(occurrences, utilisees, lecteur);
            int score = calculerScore(nomCategorie(choix), occurrences);
            utilisees += choix;

            System.out.println("\nScore obtenu pour " + nomCategorie(choix) + " : " + score);

            total += score;
            System.out.println ("Score total : " + total + "\n");

        }
        System.out.println("Score final : " + total);
    }
}