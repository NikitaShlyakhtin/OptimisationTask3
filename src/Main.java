import java.util.Arrays;

/**
 * The type Main.
 */
public class Main {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        // Read source, demand and cost
        int[] S = Input.readS();
        int[] D = Input.readD();
        int[][] C = Input.readC(S.length, D.length);

        TransportationProblem tp = new TransportationProblem(S, C, D); // initialize transportation problem

        // check if the problem is balanced
        if (!tp.isBalanced()) {
            System.out.println("The problem is not balanced!");
            return;
        }

        tp.printInputParameterTable();

        // Solve for each method
        System.out.println("--- North-West Corner Method ---");
        printSolutionVectors(tp.northWestCornerMethod());

        System.out.println("--- Vogel's Approximation Method ---");
        printSolutionVectors(tp.vogelsApproximationMethod());

        System.out.println("--- Russell's Approximation Method ---");
        printSolutionVectors(tp.russellsApproximationMethod());
    }

    /**
     * Print solution vectors.
     *
     * @param solution the solution
     */
    public static void printSolutionVectors(int[][] solution) {
        for (int i = 0; i < solution.length; i++) {
            System.out.println("Vector X0_" + (i + 1) + ": " + Arrays.toString(solution[i]));
        }
        System.out.println();
    }
}
