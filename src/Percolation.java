import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    private WeightedQuickUnionUF wqu;
    private boolean[] squaresOpen;

    // create N-by-N grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be > 0");
        }
        this.n = n;

        // wqu is +2 for the endpoints, up is n^2, down is n^2 + 1
        this.wqu = new WeightedQuickUnionUF(this.n * this.n + 2);
        this.squaresOpen = new boolean[this.n * this.n];
    }

    private int convertToArrayIndex(int i, int j) {
        int index = ((i - 1) * this.n) + (j - 1);
        return index;
    }

    /**
     * Verifies that i and j are referencing valid elements
     * 
     * @param i
     * @param j
     * @throws IndexOutOfBoundsException
     *             if i or j reference invalid elements
     */
    private void validate(int i, int j) {
        // validate input
        if (i < 1 || i > this.n || j < 1 || j > this.n) {
            throw new IndexOutOfBoundsException();
        }
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        validate(i, j);
        if (!isOpen(i, j)) {
            int index = convertToArrayIndex(i, j);

            // connect up if exists and open
            if (i > 1) { // does up exist?
                int upIndex = convertToArrayIndex(i - 1, j);
                connectIfOpen(index, upIndex);
            } else {
                // connect to top item in wqu
                this.wqu.union(index, this.squaresOpen.length); // connect
                                                                // them
            }

            // connect down if exists and open
            if (i < this.n) {
                int downIndex = convertToArrayIndex(i + 1, j);
                connectIfOpen(index, downIndex);
            } else {
                // connect to bottom item in wqu
                this.wqu.union(index, this.squaresOpen.length + 1); // connect
                                                                    // them
            }

            // connect left if exists and opens
            if (j > 1) { // does up exist?
                int leftIndex = convertToArrayIndex(i, j - 1);
                connectIfOpen(index, leftIndex);
            }

            // connect right if exists and open
            if (j < this.n) {
                int rightIndex = convertToArrayIndex(i, j + 1);
                connectIfOpen(index, rightIndex);
            }

            // final mark it as open
            this.squaresOpen[index] = true;
        }
    }

    private void connectIfOpen(int index, int otherIndex) {
        if (this.squaresOpen[otherIndex]) { // is it open
            this.wqu.union(index, otherIndex); // connect them
            // StdOut.println("Joining " + index + " to " + otherIndex);
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        validate(i, j);
        int index = convertToArrayIndex(i, j);
        boolean isOpen = squaresOpen[index];
        return isOpen;
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        validate(i, j);
        int index = convertToArrayIndex(i, j);
        boolean full = this.wqu.connected(index, this.squaresOpen.length);
        return full;
    }

    // does the system percolate?
    public boolean percolates() {
        boolean connected = this.wqu.connected(
                this.squaresOpen.length, this.squaresOpen.length + 1);
        return connected;
    }

    // test client (optional)
    public static void main(String[] args) {
        int N = 10;
        Percolation p = new Percolation(N);

        // create random order to open boxes
        int[] openOrder = new int[N * N];
        // initialize
        for (int i = 0; i < openOrder.length; i++) {
            openOrder[i] = i;
        }
        StdRandom.shuffle(openOrder);

        for (int i = 0; i < openOrder.length; i++) {
            int item = openOrder[i];
            int row = item / N + 1;
            int column = item % N + 1;
            // StdOut.println("Test " + i + " for row=" + row + ",column=" +
            // column);
            p.open(row, column);
            boolean percolated = p.percolates();
            StdOut.println("Percolates = " + percolated);
            if (percolated) {
                break;
            } else {
                StdOut.println();
            }
        }
    }
}
