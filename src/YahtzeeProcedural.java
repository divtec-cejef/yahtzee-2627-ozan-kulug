import java.util.Random;
public class YahtzeeProcedural {

    public static int[] lanceurDes(){

        Random lanceurDes = new Random();
        int[] des = new int[5];

        for (int i = 0; i < 5; i++) {

            //On fait 6+1 sinon on aura 0
            des[i] = lanceurDes.nextInt(6) + 1;
        }
        return des;
    }

    public static void main(String[] args) {

        int[] des = lanceurDes();

        for (int i = 0; i < 5; i++) {
            System.out.println("{" + (i + 1) +")" + des[i]);
        }
    }
}