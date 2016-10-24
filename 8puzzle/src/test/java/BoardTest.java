import edu.princeton.cs.algs4.In;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by nando on 6/14/16.
 */
public class BoardTest extends TestCase {

    private Board board;
    private Board loadPuzzle(String filename) {
        In in = new In(filename);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                blocks[i][j] = in.readInt();
            }

        return new Board(blocks);
    }

    @Before
    public void before() {
        System.out.println("Before");
        String filename = "src/main/resources/puzzle3x3-32.txt";
        board = loadPuzzle(filename);
    }


    @Test
    public void testHamming() throws Exception {
        String filename = "src/main/resources/puzzle3x3-32.txt";
        board = loadPuzzle(filename);
        assertEquals(5, board.hamming());
    }


    @Test
    public void testHamming2() throws  Exception {
        int[][] data = {{1,  2,  7}, {6,  4,  0},  {5,  8,  3}};
        board = new Board(data);
        assertEquals(5, board.hamming());
    }


    @Test
    public void testHamming3() throws Exception {
        int[][] data = {{1,  2}, {3, 0}};
        board = new Board(data);
        assertEquals(0, board.hamming());
    }

    @Test
    public void testManhattan() throws Exception {
        String filename = "src/main/resources/puzzle3x3-32.txt";
        board = loadPuzzle(filename);
        assertEquals(10, board.manhattan());
    }


    @Test
    public void testIsGoal_False() throws Exception {
        String filename = "src/main/resources/puzzle3x3-32.txt";
        board = loadPuzzle(filename);
        assertEquals(false, board.isGoal());
    }

    @Test
    public void testIsGoal_True() throws Exception {
        String filename = "src/main/resources/puzzle4x4-00.txt";
        board = loadPuzzle(filename);
        assertEquals(true, board.isGoal());
    }


    @Test
    public void testEquals_False() throws Exception {
        board = loadPuzzle("src/main/resources/puzzle3x3-32.txt");
        Board board1 = loadPuzzle("src/main/resources/puzzle3x3-12.txt");
        assertEquals(false, board.equals(board1));
    }

    @Test
    public void testEquals_True() throws Exception {
        String filename = "src/main/resources/puzzle3x3-32.txt";
        board = loadPuzzle(filename);
        Board board1 = loadPuzzle(filename);
        assertEquals(true, board.equals(board1));
    }

    @Test
    public void testTwin() throws Exception {
//        String filename = "src/main/resources/puzzle04.txt";
//        board = loadPuzzle(filename);
        int[][] data = {{3, 0},{2, 1}};
        board = new Board(data);
        System.out.println(board);
        System.out.println(board.twin());
    }

    @Test
    public void testNeighbors_All() throws Exception {
        board = loadPuzzle("src/main/resources/puzzle3x3-32.txt");
        final int[][] top = {{8,  0,  3}, {4, 1, 2},{7, 6, 5}};
        final int [][] bottom = {{8, 1, 3}, {4, 6, 2}, {7, 0, 5}};
        final int [][] left = {{8, 1, 3},{0, 4, 2}, {7, 6, 5}};
        final int [][] right = {{8, 1, 3}, {4, 2, 0}, {7, 6, 5}};
        ArrayList<Board> neighbors = com.google.common.collect.Lists.newArrayList(board.neighbors());
        ArrayList<Board> expected = com.google.common.collect.Lists.newArrayList(
            new Board(top),
            new Board(bottom),
            new Board(left),
            new Board(right)
        );
        assertEquals(expected, neighbors);
    }

    @Test
    public void testNeighbors_NoTop() throws Exception {
        final int[][] top = {{8,  0,  3}, {4, 1, 2},{7, 6, 5}};
        final int [][] bottom = {{8,  1,  3}, {4, 0, 2},{7, 6, 5}};
        final int [][] left = {{0,  8,  3}, {4, 1, 2},{7, 6, 5}};
        final int [][] right ={{8,  3,  0}, {4, 1, 2},{7, 6, 5}};
        board = new Board(top);
        ArrayList<Board> neighbors = com.google.common.collect.Lists.newArrayList(board.neighbors());
        ArrayList<Board> expected = com.google.common.collect.Lists.newArrayList(
                new Board(bottom),
                new Board(left),
                new Board(right)
        );
        assertEquals(expected, neighbors);
    }

    @Test
    public void testNeighbors_NoBottom() throws Exception {
        final int[][] top = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        final int [][] bottom = {{8, 1, 3}, {4, 6, 2}, {7, 0, 5}};
        final int [][] left = {{8, 1, 3}, {4, 6, 2}, {0, 7, 5}};
        final int [][] right = {{8, 1, 3}, {4, 6, 2}, {7, 5, 0}};
        board = new Board(bottom);
        ArrayList<Board> neighbors = com.google.common.collect.Lists.newArrayList(board.neighbors());
        ArrayList<Board> expected = com.google.common.collect.Lists.newArrayList(
                new Board(top),
                new Board(left),
                new Board(right)
        );
        assertEquals(expected, neighbors);
    }

    @Test
    public void testNeighbors_NoRightAndBottom() throws Exception {
        final int[][] top = {{8, 1, 3}, {4, 6, 0}, {7, 5, 2}};
        final int [][] left = {{8, 1, 3}, {4, 6, 2}, {7, 0, 5}};
        final int [][] right = {{8, 1, 3}, {4, 6, 2}, {7, 5, 0}};
        board = new Board(right);
        ArrayList<Board> neighbors = com.google.common.collect.Lists.newArrayList(board.neighbors());
        ArrayList<Board> expected = com.google.common.collect.Lists.newArrayList(
                new Board(top),
                new Board(left)
        );
        assertEquals(expected, neighbors);
    }

    @Test
    public void testNeighbors_NoLeft() throws Exception {
        final int[][] bottom = {{4, 1, 3}, {0, 6, 2}, {7, 8, 5}};
        final int [][] left = {{0, 1, 3}, {4, 6, 2}, {7, 8, 5}};
        final int [][] right = {{1, 0, 3}, {4, 6, 2}, {7, 8, 5}};
        board = new Board(left);
        ArrayList<Board> neighbors = com.google.common.collect.Lists.newArrayList(board.neighbors());
        ArrayList<Board> expected = com.google.common.collect.Lists.newArrayList(
                new Board(bottom),
                new Board(right)
        );
        assertEquals(expected, neighbors);
    }

    @Test
    public void testDimension() throws Exception {
        String filename = "src/main/resources/puzzle3x3-32.txt";
        board = loadPuzzle(filename);
        assertEquals(3, board.dimension());
    }
}