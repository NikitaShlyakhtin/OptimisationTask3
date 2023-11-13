import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] S = {10, 20, 30};
        int[][] C = {
                {1, 2, 3, 4},
                {5, 6, 7, 8}, 
                {9, 10, 11, 12}
        };
        int[] D = {15, 15, 15, 15};

        TransportationProblem tp = new TransportationProblem(S, C, D);

        if (!tp.isBalanced()) {
            System.out.println("The problem is not balanced!");
            return;
        }

        tp.printInputParameterTable();

        System.out.println("\n--- North-West Corner Method ---\n");
        printSolutionVectors(tp.northWestCornerMethod());

        System.out.println("\n--- Vogel's Approximation Method ---\n");
        printSolutionVectors(tp.vogelsApproximationMethod());

        System.out.println("\n--- Russell's Approximation Method ---\n");
        printSolutionVectors(tp.russellsApproximationMethod());
    }

    public static void printSolutionVectors(int[][] solution) {
        for (int i = 0; i < solution.length; i++) {
            System.out.println("Vector X0_" + (i + 1) + ": " + Arrays.toString(solution[i]));
        }
        System.out.println();
    }
}
