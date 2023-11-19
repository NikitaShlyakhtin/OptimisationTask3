import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * The type Transportation problem.
 */
public class TransportationProblem {
    private int[] S;
    private int[][] C;
    private int[] D;

    /**
     * Instantiates a new Transportation problem.
     *
     * @param S the source
     * @param C the cost
     * @param D the demand
     */
    public TransportationProblem(int[] S, int[][] C, int[] D) {
        this.S = S;
        this.C = C;
        this.D = D;
    }

    /**
     * Is balanced boolean.
     *
     * @return the boolean
     */
    public boolean isBalanced() {
        int totalSupply = Arrays.stream(S).sum();
        int totalDemand = Arrays.stream(D).sum();
        return totalSupply == totalDemand; // if sum of supply and demand is equal, then the problem is balanced
    }

    /**
     * Print input parameter table.
     */
    public void printInputParameterTable() {
        System.out.println("\n--- Input Parameter Table ---");

        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C[i].length; j++) {
                System.out.printf("%4d ", C[i][j]);
            }
            System.out.println("| " + S[i]);
        }

        for (int i = 0; i < D.length; i++) {
            System.out.print("---- ");
        }
        System.out.println();

        for (int i = 0; i < D.length; i++) {
            System.out.printf("%4d ", D[i]);
        }
        System.out.println();
        System.out.println();
    }

    /**
     * North-west corner method.
     *
     * It begins by starting at the top-left (north-west) corner of a transportation matrix
     * and iteratively allocates shipments based on the available supply and demand at each
     * point. By moving from the north-west corner to the south-east corner, this method
     * efficiently provides an initial feasible solution.
     *
     * @return the int [ ] [ ]
     */
    public int[][] northWestCornerMethod() {
        int i = 0, j = 0;
        int[][] allocation = new int[S.length][D.length];
        int[] supply = Arrays.copyOf(S, S.length);
        int[] demand = Arrays.copyOf(D, D.length);

        while (i < supply.length && j < demand.length) {
            int min = Math.min(supply[i], demand[j]);
            allocation[i][j] = min;
            supply[i] -= min;
            demand[j] -= min;

            if (supply[i] == 0 && i < supply.length - 1) {
                i++;
            } else if (demand[j] == 0 && j < demand.length - 1) {
                j++;
            } else {
                i++;
                j++;
            }
        }

        return allocation;
    }

    /**
     * Vogel's approximation method.
     *
     * Unlike simpler methods like the North-West Corner, Vogel's approximation method (VAM)
     * considers both the costs of shipping and the differences between the lowest costs in
     * each row and column of the transportation matrix. By prioritizing the allocation of
     * goods to the most cost-sensitive cells, VAM aims to create an initial feasible solution
     * that is often closer to the optimal solution compared to more basic methods.
     *
     * @return the int [ ] [ ]
     */
    public int[][] vogelsApproximationMethod() {
        int[] S_copy = Arrays.copyOf(S, S.length);
        int[] D_copy = Arrays.copyOf(D, D.length);

        // Copying C 2D array to C_copy 2D array
        int[][] C_copy = new int[C.length][C[0].length];
        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C[i].length; j++) {
                C_copy[i][j] = C[i][j];
            }
        }

        int numSources = S.length;
        int numDestinations = D.length;
        int[][] result = new int[numSources][numDestinations];

        while (!isFullyAllocated()) {

            int[] maxPenaltyIndex = findMaxPenaltyIndex();
            int i = maxPenaltyIndex[0]; // row
            int j = maxPenaltyIndex[1]; // column

            if (j == -1) { // If max penalty is in row

                final int I = i;
                int minCost = Arrays.stream(C[i]).min().getAsInt();
                int minCostIndex = IntStream.range(0, C[i].length)
                        .filter(k -> C[I][k] == minCost)
                        .findFirst()
                        .getAsInt();

                int quantity = Math.min(S[i], D[minCostIndex]);
                result[i][minCostIndex] = quantity;

                S[i] -= quantity;
                D[minCostIndex] -= quantity;

                if (S[i] == 0) {
                    for (int k = 0; k < numDestinations; k++) {
                        C[i][k] = Integer.MAX_VALUE;
                    }
                }

                if (D[minCostIndex] == 0) {
                    for (int k = 0; k < numSources; k++) {
                        C[k][minCostIndex] = Integer.MAX_VALUE;
                    }
                }
            } else if (i == -1) { // If max penalty is in column
                int minCost = Integer.MAX_VALUE;
                for (int k = 0; k < S.length; k++) {
                    if (C[k][j] < minCost) {
                        minCost = C[k][j];
                    }
                }

                final int MinCost = minCost;
                int minCostIndex = IntStream.range(0, S.length)
                        .filter(k -> C[k][j] == MinCost)
                        .findFirst()
                        .getAsInt();

                int quantity = Math.min(S[minCostIndex], D[j]);
                result[minCostIndex][j] = quantity;

                S[minCostIndex] -= quantity;
                D[j] -= quantity;

                if (S[minCostIndex] == 0) {
                    for (int k = 0; k < numDestinations; k++) {
                        C[minCostIndex][k] = Integer.MAX_VALUE;
                    }
                }

                if (D[j] == 0) {
                    for (int k = 0; k < numSources; k++) {
                        C[k][j] = Integer.MAX_VALUE;
                    }
                }
            }
        }

        D = D_copy;
        S = S_copy;

        for (int i = 0; i < C_copy.length; i++) {
            for (int j = 0; j < C_copy[i].length; j++) {
                C[i][j] = C_copy[i][j];
            }
        }

        return result;
    }

    // Same check for balance of problem
    private boolean isFullyAllocated() {
        return Arrays.stream(S).sum() == 0 && Arrays.stream(D).sum() == 0;
    }

    private int[] findMaxPenaltyIndex() {
        int maxPenaltyI = -1;
        int maxPenaltyJ = -1;
        int maxPenaltyIndexI = -1;
        int maxPenaltyIndexJ = -1;

        // Check rows
        for (int i = 0; i < S.length; i++) {
            if (S[i] == 0) {
                continue;
            }

            int[] row = Arrays.copyOf(C[i], C[i].length);
            Arrays.sort(row);

            int penalty = row[1] - row[0];

            if (penalty > maxPenaltyI) {
                maxPenaltyI = penalty;
                maxPenaltyIndexI = i;
            }
        }

        // Check columns
        for (int j = 0; j < D.length; j++) {
            if (D[j] == 0) {
                continue;
            }

            int[] column = new int[C.length];
            for (int i = 0; i < C.length; i++) {
                column[i] = C[i][j];
            }

            Arrays.sort(column);

            int penalty = column[1] - column[0];

            if (penalty > maxPenaltyJ) {
                maxPenaltyJ = penalty;
                maxPenaltyIndexJ = j;
            }
        }

        if (maxPenaltyI >= maxPenaltyJ) {
            maxPenaltyIndexJ = -1;
        } else {
            maxPenaltyIndexI = -1;
        }

        return new int[] { maxPenaltyIndexI, maxPenaltyIndexJ };
    }

    /**
     * Russell's approximation method.
     *
     * This method begins with the identification of a basic feasible solution by
     * allocating shipments based on specific criteria. Russell's method then proceeds
     * to check for optimality conditions and iteratively improves the solution until
     * an optimal result is achieved. Though not always guaranteeing the best solution,
     * Russell's approximation method provides a solid starting point for addressing
     * transportation problems efficiently.
     *
     * @return the int [ ] [ ]
     */
    public int[][] russellsApproximationMethod() {
        int numSources = S.length;
        int numDestinations = D.length;
        int[][] result = new int[numSources][numDestinations];

        while (!isFullyAllocated()) {
            int[] maxOpportunityCostIndex = findMaxOpportunityCostIndex();
            int i = maxOpportunityCostIndex[0];
            int j = maxOpportunityCostIndex[1];
            int quantity = Math.min(S[i], D[j]);
            result[i][j] = quantity;

            S[i] -= quantity;
            D[j] -= quantity;

            if (S[i] == 0) {
                for (int k = 0; k < numDestinations; k++) {
                    C[i][k] = Integer.MIN_VALUE;
                }
            }

            if (D[j] == 0) {
                for (int k = 0; k < numSources; k++) {
                    C[k][j] = Integer.MIN_VALUE;
                }
            }
        }

        return result;
    }

    private int[] findMaxOpportunityCostIndex() {
        int minOpportunityCost = Integer.MAX_VALUE;
        int maxOpportunityCostIndexI = -1;
        int maxOpportunityCostIndexJ = -1;

        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C[i].length; j++) {
                if (S[i] > 0 && D[j] > 0) {
                    int opportunityCost = C[i][j] - findMinInRowAndColumn(i, j);
                    if (opportunityCost < minOpportunityCost) {
                        minOpportunityCost = opportunityCost;
                        maxOpportunityCostIndexI = i;
                        maxOpportunityCostIndexJ = j;
                    }
                }
            }
        }

        return new int[] { maxOpportunityCostIndexI, maxOpportunityCostIndexJ };
    }

    private int findMinInRowAndColumn(int row, int column) {
        int maxInRow = Arrays.stream(C[row]).max().getAsInt();
        int maxInColumn = IntStream.range(0, C.length).map(i -> C[i][column]).max().getAsInt();
        return maxInRow + maxInColumn;
    }

}
