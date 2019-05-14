/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;

import java.util.HashMap;
import java.util.Map;


public final class Solver {
    private final MinPQ<Board> gameTree;

    public Solver(
            Board initial)        // find a solution to the initial board (using the A* algorithm)
    {
        Map<Board, Integer> move = new HashMap<>();
        Map<Board, Integer> parent = new HashMap<>();
        gameTree = new MinPQ<>();
        
    }

    public boolean isSolvable()     // is the initial board solvable?
    {

    }

    public int moves()      // min number of moves to solve initial board; -1 if unsolvable
    {

    }

    public Iterable<Board> solution()       // sequence of boards in a shortest solution; null if unsolvable
    {

    }

    public static void main(String[] args)      // solve a slider puzzle (given below)
    {

    }
}
