import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] S = Input.readS();
        int[] D = Input.readD();
        int[][] C = Input.readC(S.length, D.length);

        TransportationProblem tp = new TransportationProblem(S, C, D);

        if (!tp.isBalanced()) {
            System.out.println("The problem is not balanced!");
            return;
        }

        tp.printInputParameterTable();

        System.out.println("--- North-West Corner Method ---");
        printSolutionVectors(tp.northWestCornerMethod());

        System.out.println("--- Vogel's Approximation Method ---");
        printSolutionVectors(tp.vogelsApproximationMethod());

        System.out.println("--- Russell's Approximation Method ---");
        printSolutionVectors(tp.russellsApproximationMethod());
    }

    public static void printSolutionVectors(int[][] solution) {
        for (int i = 0; i < solution.length; i++) {
            System.out.println("Vector X0_" + (i + 1) + ": " + Arrays.toString(solution[i]));
        }
        System.out.println();
    }
}
