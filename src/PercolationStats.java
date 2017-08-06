/******************************************************************************
 *  Compilation:  javac java PercolationStats.java
 *  Execution:    java java PercolationStats 200 100
 *  Dependencies: Percolation.java
 *
 *  This program takes as argument:
 *      - The number of experiment T to be performed
 *      - The size of the grid N
 *
 *  Algorithm
 *      - It initialize a N by N sites to blocked
 *      - Repeat T times
 *          - Until the system percolate
 *              - Choose a site (row i, column j) uniformly at random amonong all blocked sites
 *              - Open the site at row i, column j
 *      - The fraction of sites that are opened when the system percolates provides an estimate of the percolation threshold.
 *          - mean
 *          - stdev
 *          - confidenceLo
 *          - confidenceHi
 ******************************************************************************/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    // perform T independent experiments on an N-by-N grid

    private double[] data;
    private int N, T;
    public PercolationStats(int n, int experimentNum) {
        if (n <= 0 || experimentNum <= 0)
            throw new IllegalArgumentException("row index n out of bounds");
        N = n;
        T = experimentNum;
        data = new double[T];
        for (int t = 0; t < T; t++) {
            Percolation perc = new Percolation(N);
            double opened = 0.0;

            while (!perc.percolates()) {
                // Uniformely open a blocked site
                int i = StdRandom.uniform(N) + 1;
                int j = StdRandom.uniform(N) + 1;

                while (perc.isOpen(i, j)) {
                    i = StdRandom.uniform(N) + 1;
                    j = StdRandom.uniform(N) + 1;
                }

                perc.open(i, j);
                opened++;
            }
            data[t] = opened/(N*N);  // Fraction of sites that are opened
//            System.out.println(t + " " + data[t] + " " + opened + " " + N*N);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(data);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (T == 1)
            return Double.NaN;

        return StdStats.stddev(data);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96*stddev())/Math.sqrt(T);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96*stddev())/Math.sqrt(T);
    }

    // test client (described below)
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(N, T);
        System.out.printf("Mean                    = %f\n", stats.mean());
        System.out.printf("stddev                  = %f\n", stats.stddev());
        System.out.printf("95 confidence interval = %f, %f\n", stats.confidenceLo(), stats.confidenceHi());
    }
}