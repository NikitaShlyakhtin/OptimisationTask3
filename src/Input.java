import java.util.Arrays;
import java.util.Scanner;

/**
 * The type Input.
 */
public class Input {
    /**
     * Read source.
     *
     * @return the int [ ]
     */
    public static int[] readS() {
        System.out.print("Provide S (space-separated): ");
        String rawInput = new Scanner(System.in).nextLine();

        return Arrays.stream(rawInput.split(" ")).mapToInt(Integer::parseInt).toArray();
    }

    /**
     * Read demand.
     *
     * @return the int [ ]
     */
    public static int[] readD() {
        System.out.print("Provide D (space-separated): ");
        String rawInput = new Scanner(System.in).nextLine();

        return Arrays.stream(rawInput.split(" ")).mapToInt(Integer::parseInt).toArray();
    }

    /**
     * Read cost.
     *
     * @param sSize the supply size
     * @param dSize the demand size
     * @return the int [ ] [ ]
     */
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
