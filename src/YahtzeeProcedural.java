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
    }
}