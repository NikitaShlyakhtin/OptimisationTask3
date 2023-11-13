import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        //Example 1
        int[] S = {10, 20, 30};
        int[][] C = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}
        };
        int[] D = {15, 15, 15, 15};

        //Example 2
//        int[] S = { 30, 40, 20 };
//        int[][] C = {
//                { 5, 8, 7, 4 },
//                { 2, 6, 9, 3 },
//                { 1, 7, 4, 2 }
//        };
//        int[] D = { 25, 20, 25, 20 };


        //Example 3
//        int[] S = { 50, 60, 40 };
//        int[][] C = { 
//                { 4, 6, 8, 5 }, 
//                { 7, 2, 5, 9 }, 
//                { 3, 1, 7, 4 } 
//        };
//        int[] D = { 30, 40, 30, 50 };

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
