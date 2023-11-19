import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * The type Transportation problem test.
 */
public class TransportationProblemTest {
    /**
     * The Supply.
     */
    int[] S;
    /**
     * The Cost.
     */
    int[][] C;
    /**
     * The Demand.
     */
    int[] D;

    /**
     * Init.
     */
    @Before
    public void init() {
        S = new int[]{7, 9, 18};
        C = new int[][]{
                {19, 30, 50, 10},
                {70, 30, 40, 60},
                {40, 8, 70, 20}
        };
        D = new int[]{5, 8, 7, 14};
    }

    /**
     * Test North-west corner method.
     */
    @Test
    public void testNorthWestCornerMethod() {
        TransportationProblem tp = new TransportationProblem(S, C, D);
        Assert.assertTrue(tp.isBalanced());
        Assert.assertArrayEquals(new int[][] {{5, 2, 0, 0}, {0, 6, 3, 0}, {0, 0, 4, 14}}, tp.northWestCornerMethod());
    }

    /**
     * Test Vogel's approximation method.
     */
    @Test
    public void testVogelsApproximationMethod() {
        TransportationProblem tp = new TransportationProblem(S, C, D);
        Assert.assertTrue(tp.isBalanced());
        Assert.assertArrayEquals(new int[][] {{5, 0, 0, 2}, {0, 0, 7, 2}, {0, 8, 0, 10}}, tp.vogelsApproximationMethod());
    }

    /**
     * Test Russell's approximation method.
     */
    @Test
    public void testRussellsApproximationMethod() {
        TransportationProblem tp = new TransportationProblem(S, C, D);
        Assert.assertTrue(tp.isBalanced());
        Assert.assertArrayEquals(new int[][] {{5, 2, 0, 0}, {0, 2, 7, 0}, {0, 4, 0, 14}}, tp.russellsApproximationMethod());
    }

    /**
     * Test on unbalanced.
     */
    @Test
    public void testUnbalanced() {
        int[] S = {5, 5};
        int[][] C = {
                {1, 2, 3},
                {5, 6, 7},
        };
        int[] D = {3, 3, 3};
        TransportationProblem tp = new TransportationProblem(S, C, D);
        Assert.assertFalse(tp.isBalanced());
    }
}
