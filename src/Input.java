import java.util.Arrays;
import java.util.Scanner;

public class Input {
    public static int[] readS() {
        System.out.print("Provide S (space-separated): ");
        String rawInput = new Scanner(System.in).nextLine();

        return Arrays.stream(rawInput.split(" ")).mapToInt(Integer::parseInt).toArray();
    }

    public static int[] readD() {
        System.out.print("Provide D (space-separated): ");
        String rawInput = new Scanner(System.in).nextLine();

        return Arrays.stream(rawInput.split(" ")).mapToInt(Integer::parseInt).toArray();
    }

    public static int[][] readC(int sSize, int dSize) {
        System.out.println("Provide C (space-separated rows): ");
        int[][] C = new int[sSize][];

        for(int i = 0; i < sSize; i++) {
            System.out.print("(" + (i + 1) + "/" + sSize + "): ");
            String rawInput = new Scanner(System.in).nextLine();

            int[] row = Arrays.stream(rawInput.split(" ")).mapToInt(Integer::parseInt).toArray();

            if(row.length != dSize) {
                throw new IllegalArgumentException("Row size mismatch");
            }

            C[i] = row;
        }

        return C;
    }
}
