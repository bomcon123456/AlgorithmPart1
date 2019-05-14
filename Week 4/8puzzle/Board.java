/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */


import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;

public final class Board {
    private final int[][] tiles;
    private int zeroIndex;
    private final int size;
    private final int hamming;
    private final int manhattan;
    private final boolean bIsGoal;

    public Board(int[][] blocks)            // construct a tiles from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    {
        size = blocks.length;
        tiles = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (blocks[i][j] == 0) zeroIndex = i * size + j;
                tiles[i][j] = blocks[i][j];
            }
        }
        hamming = calculateHamming();
        manhattan = calculateManhattan();
        bIsGoal = calculateIsGoal();
    }

    private boolean calculateIsGoal() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] == 0) continue;
                if (!((tiles[i][j] - 1) == i * size + j)) {
                    return false;
                }
            }
        }
        return true;
    }

    private int calculateHamming() {
        int res = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] == 0) continue;
                int col = (tiles[i][j] - 1) % size;
                int row = (tiles[i][j] - 1) / size;
                if (!(i == row && j == col)) {
                    res++;
                }
            }
        }
        return res;
    }

    private int calculateManhattan() {
        int res = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] == 0) continue;
                int col = (tiles[i][j] - 1) % size;
                int row = (tiles[i][j] - 1) / size;
                if (!(i == row && j == col)) {
                    res += Math.abs(row - i);
                    res += Math.abs(col - j);
                }
            }
        }
        return res;
    }

    private void exch(int[][] tiles, int sCol, int sRow, int eCol, int eRow) {
        int temp = tiles[sCol][sRow];
        tiles[sCol][sRow] = tiles[eCol][eRow];
        tiles[eCol][eRow] = temp;
    }

    private boolean isInBound(int indexCol, int indexRow) {
        if (indexCol < size && indexCol >= 0 && indexRow < size && indexRow >= 0)
            return true;
        return false;
    }

    public int dimension()                 // tiles dimension n
    {
        return size;
    }

    public int hamming()                   // number of blocks out of place
    {
        return hamming;
    }

    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        return manhattan;
    }

    public boolean isGoal()                // is this tiles the goal tiles?
    {
        return bIsGoal;
    }

    public Board twin()                    // a tiles that is obtained by exchanging any pair of blocks
    {
        Board res = new Board(tiles);
        int swapS = zeroIndex, swapE = zeroIndex;
        while (swapS == zeroIndex)
            swapS = StdRandom.uniform(0, size * size);
        while (swapE == zeroIndex)
            swapE = StdRandom.uniform(0, size * size);
        int sCol = swapS % size;
        int sRow = swapS / size;
        int eCol = swapE % size;
        int eRow = swapE / size;
        exch(res.tiles, sCol, sRow, eCol, eRow);
        return res;
    }

    public boolean equals(Object y)        // does this tiles equal y?
    {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board b = (Board) y;
        if (size != b.size)
            return false;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] != b.tiles[i][j])
                    return false;
            }
        }
        return true;

    }

    public Iterable<Board> neighbors()     // all neighboring boards
    {
        Stack<Board> res = new Stack<>();
        int zeroRow = zeroIndex / size;
        int zeroCol = zeroIndex % size;
        final int[] delta = { 1, -1 };
        for (final int i : delta) {
            if (isInBound(zeroCol + i, zeroRow)) {
                Board in = new Board(tiles);
                in.exch(in.tiles, zeroCol, zeroRow, zeroCol + i, zeroRow);
                res.push(in);
            }
        }
        for (final int i : delta) {
            if (isInBound(zeroCol, zeroRow + i)) {
                Board in = new Board(tiles);
                in.exch(in.tiles, zeroCol, zeroRow, zeroCol, zeroRow + i);
                res.push(in);
            }
        }
        return res;
    }


    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(size + "\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) // unit tests (not graded)
    {
        int[][] blocks = { { 1, 2, 3 }, { 4, 0, 6 }, { 7, 8, 5 } };
        Board b = new Board(blocks);
        Iterable<Board> it = b.neighbors();
        for (Board a : it) {
            System.out.println(a);
        }

    }
}
