import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;

public class Board {
    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    private final int[][] data;
    private final int N;

    public Board(int[][] blocks) {
        data = blocks;
        N = data[0].length;
    }

    // board dimension N
    public int dimension() {
        return N;
    }

    // number of blocks out of place
    public int hamming() {
        int dist = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
//                System.out.printf("%d <->d %d : %d\n", i, j, to1D(i, j));
                if (data[i][j] != to1D(i, j) && to1D(i, j) != N*N)
                    dist += 1;
            }
        }
        return dist;
    }

    private int to1D(int i, int j) {
        return 1 + i * N + j;
    }

    private int getX(int j) {
        if (j == 0) { return 0; }
        return (j-1) / N;
    }

    private int getY(int j) {
        if (j == 0) { return 0; }
        return  (j-1) % N;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int[] distances = new int[N*N];
        int dist;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                dist = 0;
                if (data[i][j] != 0) {
                    dist += Math.abs(i - getX(data[i][j]));
                    dist += Math.abs(j - getY(data[i][j]));
                    distances[data[i][j] - 1] = dist;
                }
            }
        }

        dist = 0;
        for (int i : distances) {
            dist += i;
        }
        return dist;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (data[i][j] != 0 && data[i][j] != to1D(i, j))
                    return false;
            }
        }
        return true;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int[][] blocks = deepCopy(data);
        int first = generateIdx(0);
        int second = generateIdx(first);
        swap(blocks, first, second);
        return new Board(blocks);
    }

    private int generateIdx(int other){
        int rand = StdRandom.uniform(N * N);
//        System.out.printf("(%d) = (%d, %d)\n", rand, getX(rand), getY(rand));
        while (data[getX(rand)][getY(rand)] == 0 || data[getX(rand)][getY(rand)] == data[getX(other)][getY(other)]) {
            rand = StdRandom.uniform(N*N);
        }
        return rand;
    }

    // swap board at element i and j
    private void swap(int[][] board, int i, int j) {
        int temp = board[getX(i)][getY(i)];
        board[getX(i)][getY(i)] = board[getX(j)][getY(j)];
        board[getX(j)][getY(j)] = temp;
    }

    // does this board equal y?
    /* TODO:
        * this is the bottleneck.
        * for optimization use 1D char[]`
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        Board that = (Board) other;
        if (this.N != that.N) return false;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.data[i][j] != that.data[i][j])
                    return false;
            }
        }
        return true;
    }

    private int[][] deepCopy(int[][] data) {
        int _new[][] = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                _new[i][j] = data[i][j];
            }
        }
        return _new;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> list = new ArrayList<Board>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if(data[i][j] == 0) {
                    if (i > 0) { // Top
                        int[][] blocks = deepCopy(data);
                        swap(blocks, to1D(i, j), to1D(i-1, j));
                        list.add(new Board(blocks));
                    }
                    if (i < N-1) {  // bottom
                        int[][] blocks = deepCopy(data);
                        swap(blocks, to1D(i, j), to1D(i+1, j));
                        list.add(new Board(blocks));
                    }
                    if (j > 0) {  // left
                        int[][] blocks = deepCopy(data);
                        swap(blocks, to1D(i, j), to1D(i, j-1));
                        list.add(new Board(blocks));
                    }
                    if (j < N-1) {  // right
                        int[][] blocks = deepCopy(data);
                        swap(blocks, to1D(i, j), to1D(i, j+1));
                        list.add(new Board(blocks));
                    }
                }
            }
        }
        return list;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", data[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        String filename = "src/main/resources/puzzle11.txt";
        In in = new In(filename);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        Board board = new Board(blocks);

    }


}