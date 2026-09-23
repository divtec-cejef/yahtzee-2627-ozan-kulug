import java.util.Random;
import java.util.Scanner;

public class YahtzeeProcedural {
    static int NOMBRE_RELANCE_PLUS = 2;

    public static int lanceurDes(Random nbr) {
        return nbr.nextInt(6) + 1;
    }

    public static void afficherDes(int[] des) {
        for (int i = 0; i < des.length; i++) {
            System.out.println("{" + (i + 1) + ")" + des[i]);
        }
    }

    public static void relanceurDe(int[] positions, int[] des, Random nbr) {
        for (int i = 0; i < positions.length; i++) {
            des[positions[i]] = lanceurDes(nbr);
        }
    }

    public static int[] demandeRelance(Scanner lecteur) {

        System.out.println("""
                Saisir les dés que vous voulez relancer (1-5)
                Séparer avec espace
                Laissez vide ou mettez 0 si vous ne voulez pas relancer""");

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

        for(int i = 0; i < des.length; i++){
            occurrences[des[i] - 1]++;
        }
        return occurrences;
    }

    public static boolean contientExacte(int[] occurrences, int nombre) {

        for (int i = 0; i < occurrences.length;i ++){
            if(occurrences[i] == nombre){
                return true;
            }
        }return false;
    }
    public static boolean contientAuMoins(int[] occurrences, int nombre){
        for (int i = 0; i < occurrences.length; i++) {
            if (occurrences[i] >= nombre) {
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
        for(int i = 0; i < occurrences.length; i++){
            if (occurrences[i] >= 2) {
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
        for (int i = 0; i < des.length; i++) {
            somme += des[i];
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
    public static void calculerScore(int[] des, int[] occurrences) {
        String[] categories = {
                "Yahtzee", "Grande Suite", "Petite Suite", "Full House",
                "Carre", "Brelan", "Deux Paires", "Paire"
        };

        for (String categorie : categories) {

            int score = 0;

            switch (categorie) {
                case "Yahtzee" -> score = estYahtzee(occurrences) ? 50 : 0;
                case "Grande Suite" -> score = estGrandeSuite(occurrences) ? 40 : 0;
                case "Petite Suite" -> score = estPetiteSuite(occurrences) ? 30 : 0;
                case "Full House" -> score = estFullHouse(occurrences) ? 25 : 0;
                case "Carre" -> score = estCarre(occurrences) ? scoreRepetition(occurrences, 4) : 0;
                case "Brelan" -> score = estBrelan(occurrences) ? scoreRepetition(occurrences, 3) : 0;
                case "Deux Paires" -> score = deuxPaires(occurrences) ? 10 : 0;
                case "Paire" -> score = estUnePaire(occurrences) ? 5 : 0;
            }
            System.out.println(categorie + " : " + score);

        }
    }


    public static void main(String[] args) {
        Random nbr = new Random();
        int[] des = new int[5];

        for (int i = 0; i < des.length; i++) {
            des[i] = lanceurDes(nbr);
        }
        afficherDes(des);

        Scanner lecteur = new Scanner(System.in);

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
        calculerScore(des, occurrences);

    }
}