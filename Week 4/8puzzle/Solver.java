import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.Comparator;


class SearchNodeComparatator implements Comparator<SearchNode> {

    @Override
    public int compare(SearchNode o1, SearchNode o2) {
        if (o1.m_priority == o2.m_priority) {
            if (o1.m_board.hamming() > o2.m_board.hamming())
                return 1;
            else if (o1.m_board.hamming() == o2.m_board.hamming())
                return 0;
            return -1;
        }
        else if (o1.m_priority > o2.m_priority)
            return 1;
        else
            return -1;
    }
}

class SearchNode {
    final Board m_board;
    int m_move;
    int m_priority;
    SearchNode m_par;

    public SearchNode(Board b, int m, SearchNode par) {
        m_board = b;
        m_move = m;
        m_priority = b.manhattan() + m;
        m_par = par;
    }

}


public final class Solver {
    private final MinPQ<SearchNode> gameTree;
    private Stack<Board> solution;
    private boolean bSolvable;

    public Solver(
            Board initial)        // find a solution to the initial board (using the A* algorithm)
    {
        if (initial == null) {
            throw new java.lang.IllegalArgumentException("Invalid args");
        }
        gameTree = new MinPQ<>(new SearchNodeComparatator());
        solution = new Stack<>();
        bSolvable = false;
        SearchNode root = new SearchNode(initial, 0, null);
        SearchNode twinRoot = new SearchNode(initial.twin(), 0, null);
        gameTree.insert(root);
        gameTree.insert(twinRoot);

        while (!gameTree.isEmpty()) {
            SearchNode current = gameTree.delMin();
            if (current.m_board.isGoal()) {
                retracePath(current, initial);
                return;
            }

            for (Board b : current.m_board.neighbors()) {
                SearchNode neighbor = new SearchNode(b, current.m_move + 1, current);
                if (current.m_par != null && b.equals(current.m_par.m_board)) {
                    continue;
                }
                gameTree.insert(neighbor);
            }

        }
    }

    private void retracePath(SearchNode finalNode, Board initial) {
        SearchNode cur = finalNode;
        while (cur.m_par != null) {
            solution.push(cur.m_board);
            cur = cur.m_par;
        }
        if (cur.m_board.equals(initial)) {
            solution.push(cur.m_board);
            bSolvable = true;
        }

    }

    public boolean isSolvable()     // is the initial board solvable?
    {
        return bSolvable;
    }

    public int moves()      // min number of moves to solve initial board; -1 if unsolvable
    {
        if (solution.size() > 0 && isSolvable())
            return solution.size() - 1;
        return -1;
    }


    public Iterable<Board> solution()       // sequence of boards in a shortest solution; null if unsolvable
    {
        if (isSolvable())
            return solution;
        return null;
    }

    public static void main(String[] args)      // solve a slider puzzle (given below)
    {
        // int[][] blocks = { { 0, 1, 3 }, { 4, 2, 5 }, { 7, 8, 6 } };
        int[][] blocks = { { 1, 0 }, { 2, 3 } };
        Board b = new Board(blocks);
        Solver test = new Solver(b);
        if (test.isSolvable()) {
            System.out.println("Minimum number of moves = " + test.moves());
            for (Board sol : test.solution()) {
                System.out.println(sol);
            }
        }
        else {
            System.out.println("No solution possible." + test.moves());
        }
    }
}
