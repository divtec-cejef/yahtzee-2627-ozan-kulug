import java.util.Random;
import java.util.Scanner;

public class YahtzeeProcedural {

    public static int lanceurDes(Random nbr) {
        return nbr.nextInt(6) + 1;
    }

    public static void afficherDes(int[] des) {
        for (int i = 0; i < des.length; i++) {
            System.out.println("{" + (i + 1) + ")" + des[i]);
        }
    }

    public static int[] relanceurDe(String saisie) {

        System.out.println("Saisir les dés que vous voulez relancer (1-5)");
        System.out.println("Séparer avec espace");
        System.out.println("Laissez vide ou mettez 0 si vous ne voulez pas relancer");

        String[] positions = saisie.split(" ");
        int[] relance = new int[positions.length];

        for (int i = 0; i < positions.length; i++) {
            int numero = Integer.parseInt(positions[i]);

            if (numero >= 1 && numero <= 5) {
                relance[i] = numero;
            }
        }

        return relance;
    }

    public static void main(String[] args) {
        Random nbr = new Random();
        int[] des = new int[5];

        for (int i = 0; i < des.length; i++) {

            des[i] = lanceurDes(nbr);
        }
        afficherDes(des);

        Scanner lecteur = new Scanner(System.in);

        for (int nombreLancer = 1; nombreLancer <= 3; nombreLancer++) {

        String saisie = lecteur.nextLine();

            if (saisie.equals("0")|| saisie.isEmpty()) {
                break;
            }

        int[] relance = relanceurDe(saisie);


            for (int i = 0; i < relance.length; i++) {
                int position = relance[i];

                des[position - 1] = lanceurDes(nbr);
            }

        afficherDes(des);
    }
}
}