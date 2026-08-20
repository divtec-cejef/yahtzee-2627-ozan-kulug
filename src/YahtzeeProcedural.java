import java.util.Random;
public class YahtzeeProcedural {

    public static int lanceurDes(Random nbr) {
        return nbr.nextInt(6) + 1;
    }

    public static void afficherDes(int [] des){
        for (int i = 0; i < des.length; i++) {
            System.out.println("{" + (i + 1) +")" + des[i]);
        }
    }

    public static void main(String[] args) {

        Random nbr = new Random();
        int[] des = new int[5];

        for (int i = 0; i < des.length; i++) {

            des[i] = lanceurDes(nbr);
        }

        afficherDes(des);
    }
}