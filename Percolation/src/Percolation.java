/******************************************************************************
 *  Compilation:  javac Percolation.java
 *  Execution:    java Percolation N
 *
 *  This program takes the number of ...
 *  Doc: http://algs4.cs.princeton.edu/lectures/15UnionFind-2x2.pdf
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    // create N-by-N grid, with all sites blocked

    private boolean[] openned;
    private int N;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF fullness;
    private int virtualTop;
    private int virtualBottom;

    public Percolation(int N) {

        if (N <= 0)
            throw new IllegalArgumentException();

        this.N = N;
        uf = new WeightedQuickUnionUF(N*N+2);
        fullness = new WeightedQuickUnionUF(N*N+1);

        // All site closed by default
        openned = new boolean[N*N];
        for (int i = 0; i < N*N; i++) {
            openned[i] = false;
        }

        virtualTop = N*N;
        virtualBottom = N*N+1;
    }

    private int xyTo1D(int i, int j) {
        validateIndex(i, j);
        return i * N - 1 - (N - j) % N;
    }

    private boolean isValid(int i, int j) {
        return i >= 1 && i <= N && j >= 1 && j <= N;
    }

    private void validateIndex(int i, int j) {
        if (!isValid(i, j))
            throw new IndexOutOfBoundsException("Index is (" + i + ", " + j + ") not between 0 and " + N);
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        validateIndex(i, j);

        if (!isOpen(i, j)) {

            if (i == 1) {
                uf.union(xyTo1D(i, j), virtualTop);
                fullness.union(xyTo1D(i, j), virtualTop);
            }

            if (i == N) {
                uf.union(xyTo1D(i, j), virtualBottom);
            }

            openned[xyTo1D(i, j)] = true;

            if (isValid(i-1, j) && isOpen(i - 1, j)) {                     // Connect Top grid
                uf.union(xyTo1D(i, j), xyTo1D(i - 1, j));
                fullness.union(xyTo1D(i, j), xyTo1D(i - 1, j));
            }
            if (isValid(i+1, j) && isOpen(i+1, j)) {                      // Connect bottom grid
                uf.union(xyTo1D(i, j), xyTo1D(i + 1, j));
                fullness.union(xyTo1D(i, j), xyTo1D(i + 1, j));
            }
            if (isValid(i, j-1) && isOpen(i, j-1)) {                     // Connect Left grid
                uf.union(xyTo1D(i, j), xyTo1D(i, j - 1));
                fullness.union(xyTo1D(i, j), xyTo1D(i, j - 1));
            }
            if (isValid(i, j+1) && isOpen(i, j+1)) {                      // Connect Right grid
                uf.union(xyTo1D(i, j), xyTo1D(i, j + 1));
                fullness.union(xyTo1D(i, j), xyTo1D(i, j + 1));
            }
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        validateIndex(i, j);
        return openned[xyTo1D(i, j)];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        validateIndex(i, j);
        return fullness.connected(xyTo1D(i, j), virtualTop);
//        return uf.connected(xyTo1D(i, j), virtualTop);
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(virtualTop, virtualBottom);
    }

    public static void main(String[] args) {
        In in = new In(args[0]);      // input file
        int N = in.readInt();         // N-by-N percolation system

        // repeatedly read in sites to open and draw resulting system
        Percolation perc = new Percolation(N);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
    }
}