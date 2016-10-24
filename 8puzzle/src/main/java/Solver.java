import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.*;

public class Solver {

    private class Node {
        private Integer moves;
        private Board board;
        private Node prev;

        private Node(Board board, int moves, Node prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
        }

        public String toString() {
            return String.format(
                    "--------------------\n" +
                    "Priority  = %d\n" +
                    "Moves     = %d\n" +
                    "Manhattan = %d\n%s"
                    ,moves + this.board.manhattan()
                    ,moves
                    ,this.board.manhattan()
                    ,this.board.toString()
            );
        }
    }

    private MinPQ<Node> minPQ;
    private MinPQ<Node> twinMinPQ;

    private Comparator<Node> hamming() {
        return new Comparator<Node>() {
            public int compare(Node o1, Node o2) {
                Integer h1 = o1.board.hamming() + o1.moves;
                Integer h2 = o2.board.hamming() + o2.moves;
                return h1.compareTo(h2);
            }
        };
    }

    private Comparator<Node> manhattan() {
        return new Comparator<Node>() {
            public int compare(Node o1, Node o2) {
                Integer h1 = o1.board.manhattan() + o1.moves;
                Integer h2 = o2.board.manhattan() + o2.moves;
                return h1.compareTo(h2);
            }
        };
    }

    private boolean _isSolvable;
    private int _moves;
    private Node ptr;

    /*
      *  First, insert the initial search node (the initial board, 0 moves, and a null previous search node) into a priority queue.
      *  Then, delete from the priority queue the search node with the minimum priority.
      * And insert onto the priority queue all neighboring search nodes
      * (those that can be reached in one move from the dequeued search node).
      * Repeat this procedure until the search node dequeued corresponds to a goal board.
     */
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new NullPointerException("Solver argument cannot be null.");

        _isSolvable = false;
        _moves = 0;

        minPQ = new MinPQ<Node>(manhattan());
        Node root = new Node(initial, 0, null);
        Set<String> explored = new HashSet<String>();
        minPQ.insert(root);
        explored.add(root.board.toString());
        // Twin
        Board twin = initial.twin();
        Node twinRoot = new Node(twin, 0, null);
        twinMinPQ = new MinPQ<Node>(manhattan());
        Set<String> twinExplorered = new HashSet<String>();
        twinMinPQ.insert(twinRoot);
        twinExplorered.add(twinRoot.board.toString());

        while(!minPQ.isEmpty()) {
            if (!minPQ.isEmpty()) {
                Node min = minPQ.delMin();
                _moves = min.moves;
                if (min.board.isGoal()) {
                    ptr = min;
                    _isSolvable = true;
                    break;
                }
//                StdOut.println(min);
//                StdOut.println(min.board.neighbors());
                for (Board neighbor : min.board.neighbors()) {
                    if (!explored.contains(neighbor.toString())) {
                        minPQ.insert(new Node(neighbor, min.moves + 1, min));
                        explored.add(neighbor.toString());
                    }
                }
            }

            // Twin
            if (!twinMinPQ.isEmpty()) {
                Node twinMin = twinMinPQ.delMin();
                if (twinMin.board.isGoal()) {
                    _moves = -1;
                    break;
                }
                for (Board neighbor : twinMin.board.neighbors()) {
                    if (!twinExplorered.contains(neighbor.toString())) {
                        twinMinPQ.insert(new Node(neighbor, twinMin.moves + 1, twinMin));
                        twinExplorered.add(neighbor.toString());
                    }
                }
            }
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return _isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return _moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (isSolvable()) {
            Stack<Board> stack = new Stack<Board>();
            Node prev = ptr;
            while (prev != null) {
                stack.push(prev.board);
                prev = prev.prev;
            }
            ArrayList<Board> solution = new ArrayList<Board>();
            while (!stack.isEmpty()) {
                solution.add(stack.pop());
            }
            return solution;
        } else {
            return null;
        }
    }

    public static void main(String[] args) {   // solve a slider puzzle (given below)

        // create initial board from file
        System.out.println(args[0]);
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}