/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

public final class Board {
    private final int[][] tiles;
    private final int size;
    private int manhattan;

    public Board(int[][] blocks)            // construct a tiles from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    {
        size = blocks.length;
        tiles = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tiles[i][j] = blocks[i][j];
            }
        }
        manhattan = calculateManhattan();
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

    private void exch(int[][] other, int sCol, int sRow, int eCol, int eRow) {
        int temp = other[sRow][sCol];
        other[sRow][sCol] = other[eRow][eCol];
        other[eRow][eCol] = temp;
        manhattan = calculateManhattan();
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
        int res = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] == 0) {
                    continue;
                }
                int col = (tiles[i][j] - 1) % size;
                int row = (tiles[i][j] - 1) / size;
                if (!(i == row && j == col)) {
                    res++;
                }
            }
        }
        return res;
    }

    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        return manhattan;
    }

    public boolean isGoal()                // is this tiles the goal tiles?
    {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] == 0) {
                    if (i != size - 1 || j != size - 1) {
                        return false;
                    }
                    continue;
                }
                if (!((tiles[i][j] - 1) == i * size + j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public Board twin()                    // a tiles that is obtained by exchanging any pair of blocks
    {
        int[][] newTiles = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                newTiles[i][j] = tiles[i][j];
            }
        }
        int startR, startC, endR, endC;
        if (newTiles[0][0] != 0 && newTiles[0][1] != 0) {
            startR = 0;
            startC = 0;
            endR = 0;
            endC = 1;
        }
        else {
            startR = 1;
            startC = 0;
            endR = 1;
            endC = 1;
        }
        int temp = newTiles[startR][startC];
        newTiles[startR][startC] = newTiles[endR][endC];
        newTiles[endR][endC] = temp;
        return new Board(newTiles);
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
        int zeroRow = -1;
        int zeroCol = -1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                if (tiles[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                }
        }
        final int[] delta = { 1, -1 };
        for (final int i : delta) {
            if (isInBound(zeroCol + i, zeroRow)) {
                Board in = new Board(tiles);
                in.exch(in.tiles, zeroCol, zeroRow, zeroCol + i, zeroRow);
                res.push(in);
            }
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
        for (String filename : args) {
            // read in the board specified in the filename
            In in = new In(filename);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            // solve the slider puzzle
            Board initial = new Board(tiles);
            int size = 0;
            System.out.println("//////////////////");
            System.out.println(initial);
            System.out.println("//////////////////");
            for (Board b : initial.neighbors()) {
                System.out.println("Neighbors:");
                System.out.println(b);
                size++;
            }
            System.out.println("Total neighbors:" + size);
            System.out.println("//////////////////");
            System.out.println("Twin1");
            System.out.println(initial.twin());
            System.out.println("Twin2");
            System.out.println(initial.twin());
            System.out.println("Twin3");
            System.out.println(initial.twin());
            System.out.println("Twin4");
            System.out.println(initial.twin());

        }
    }
}
