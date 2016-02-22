import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int N;
    private int T;
    private double mean;
    private double stddev;
    private double confHi;
    private double confLo;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        this.N = N;
        this.T = T;
        runStats();
    }

    private void runStats() {
        // run tests
        double[] percolationAttemptCount = new double[this.T];

        // create random order to open boxes
        int[] openOrder = new int[this.N * this.N];
        // initialize
        for (int j = 0; j < openOrder.length; j++) {
            openOrder[j] = j;
        }
        for (int i = 0; i < this.T; i++) {
            Percolation p = new Percolation(this.N);
            // shuffle this
            StdRandom.shuffle(openOrder);
            int attemps = 0;
            boolean percolated = false;
            for (int item : openOrder) {
                attemps++;
                int row = item / this.N + 1;
                int column = item % this.N + 1;
                p.open(row, column);
                percolated = p.percolates();
                if (percolated) {
                    // StdOut.printf("Test %d percolated after %d attempts\n",
                    // i, attemps);
                    break;
                }
            }

            percolationAttemptCount[i] = (attemps / (double) (this.N * this.N));
        }

        // calculate stats
        this.mean = StdStats.mean(percolationAttemptCount);
        this.stddev = StdStats.stddev(percolationAttemptCount);

        // double standardError = this.stddev/Math.sqrt(this.N);
        // this.confHi = this.mean + (1.96)*standardError;
        // this.confLo = this.mean - (1.96)*standardError;
        this.confHi = this.mean + ((1.96 * this.stddev) / Math.sqrt(this.N));
        this.confLo = this.mean - ((1.96 * this.stddev) / Math.sqrt(this.N));
    }

    // sample mean of percolation threshold
    public double mean() {
        return this.mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return this.stddev;
    }

    // low end point of 95% confidence interval
    public double confidenceLo() {
        return this.confLo;
    }

    // high end point of 95% confidence interval
    public double confidenceHi() {
        return this.confHi;
    }

    // test client (optional)
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Args [N T]");
        }

        int n = 0;
        try {
            n = Integer.parseInt(args[0]);
        } catch (NumberFormatException nfe) {
            StdOut.print("N must be a positive integer");
        }

        int t = 0;
        try {
            t = Integer.parseInt(args[1]);
        } catch (NumberFormatException nfe) {
            StdOut.print("T must be a positive integer");
        }
        PercolationStats stats = new PercolationStats(n, t);
        // print output
        StdOut.printf("mean\t\t\t\t= %.16f\n", stats.mean());
        StdOut.printf("stddev\t\t\t\t= %.16f\n", stats.stddev());
        StdOut.printf("95%% confidence interval\t\t= %.16f, %.16f\n", 
                stats.confidenceLo(), stats.confidenceHi());
    }
}