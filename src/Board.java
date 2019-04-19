import java.lang.reflect.Array;
import java.util.*;

public class Board {
    private static final char FREE = '#';
    private static final char RED = 'r';
    private static final char BLUE = 'b';

    private static final String OFFSET_ONE = " ";
    private static final String OFFSET_TWO = "  ";
    private static final String OFFSET_THREE = "   ";

    private static final int BOARD_SIZE = 5;


    private static char[][] board;

    public static void initBoard() {
        board = new char[BOARD_SIZE][BOARD_SIZE];

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = FREE;
            }
        }
    }

    /*public static void debugBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; i < BOARD_SIZE; j++) {
                System.out.println(board[i][j]);
            }
        }
    }*/

    /**
     * Prints board
     */
    public static void printBoard() {
        System.out.println();

        for (int index = 0; index < BOARD_SIZE; index++) {
            System.out.print(" " + index);
        }
        System.out.println();

        for (int i = 0; i < BOARD_SIZE; i++) {

            for (int space = 0; space < i; space++) {
                if (space < 9) {
                    System.out.print(OFFSET_ONE);
                }
            }

            System.out.print(i + OFFSET_ONE);

            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(board[i][j] + OFFSET_ONE);
            }

            System.out.println();
        }

        System.out.println();
    }

    /**
     * Updates board according to specified coordinates.
     *
     * @param coor - coordinates on hex board
     */
    public static void updateBoard(int[] coor) {
        if (board[coor[0]][coor[1]] == FREE) {
            if (Game.blueTurn) {
                board[coor[0]][coor[1]] = BLUE;
                Game.blueTurn = false;
            } else if (!Game.blueTurn) {
                board[coor[0]][coor[1]] = RED;
                Game.blueTurn = true;
            }
        } else {
            System.out.println("Invalid move! Hex tile is already occupied.");
        }
    }


    /**
     * Method checks if game is complete and returns a boolean value.
     *
     * @return - boolean value denoting if game is complete
     */
    public static boolean isGameComplete() {
        boolean gameComplete = false;

        ArrayList<int[]> blueCoor = new ArrayList<>();
        ArrayList<int[]> redCoor = new ArrayList<>();
        int[] adjCoor = new HashSet<int[]>;

        // key - first coordinate in coordinate array
        // value - array list of coordinates
        HashMap<Integer, ArrayList<int[]>> rows;

        int[] coor;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                coor = new int[2];

                switch (board[i][j]) {
                    case 'b':
                        coor[0] = i;
                        coor[1] = j;
                        blueCoor.add(coor);
                        break;
                    case 'r':
                        coor[0] = i;
                        coor[1] = j;
                        redCoor.add(coor);
                        break;
                    default:
                        break;
                }
            }
        }


        //checks for blue winning


        rows = new HashMap<>();

        for (int i = 0; i < board.length; i++) {
            rows.put(i, new ArrayList<>());

            for (int[] bc : blueCoor) {
                if (bc[0] == i) {
                    rows.get(i).add(bc);
                }
            }
        }

        for (int i = 0; i < board.length; i++) {
            if (rows.get(i).size() != 0) {
                for (int[] bc : rows.get(i)) {
                    if (i > 0) {

                    }
                    adjCoor = genAdjCoor(bc, BLUE);
                }

            } else {
                gameComplete = false;
            }
        }


        // statements for debugging
        System.out.println("blue coords:");
        for(int[] cor:blueCoor) {
            System.out.println(cor[0] + " " + cor[1]);
        }
    }

    /**
     * Generates all coordinates of adjacent hex pieces.
     * @param coor
     * @return
     */
    public static int[] genAdjCoor(int[] coor, char c) {
        HashSet<int[]> adjCoors = new HashSet<>();

        int[] adjCoor = new int[2];
        adjCoor[0] = -1;

        if (coor[0] > 0 && board[coor[0] - 1][coor[1]] != FREE) {
            adjCoor = new int[2];
            adjCoor[0] = coor[0] - 1;
            adjCoor[1] =  coor[1];
            adjCoors.add(adjCoor);
        }

        if (coor[0] > 0 && coor[1] < BOARD_SIZE -1) {
            adjCoor = new int[2];
            adjCoor[0] = coor[0] - 1;
            adjCoor[1] =  coor[1]+1;
            adjCoors.add(adjCoor);
        }

        if (coor[1] < BOARD_SIZE - 1) {
            adjCoor = new int[2];
            adjCoor[0] = coor[0];
            adjCoor[1] =  coor[1] + 1;
            adjCoors.add(adjCoor);
        }

        if (coor[0] < BOARD_SIZE - 1) {
            adjCoor = new int[2];
            adjCoor[0] = coor[0] + 1;
            adjCoor[1] =  coor[1];
            adjCoors.add(adjCoor);
        }


        if (coor[0] < BOARD_SIZE - 1 && coor[1] > 0) {
            adjCoor = new int[2];
            adjCoor[0] = coor[0] + 1;
            adjCoor[1] =  coor[1] - 1;
            adjCoors.add(adjCoor);
        }

        if (coor[1] > 0) {
            adjCoor = new int[2];
            adjCoor[0] = coor[0];
            adjCoor[1] =  coor[1] - 1;
            adjCoors.add(adjCoor);
        }

        return adjCoor;
    }

    public static void offsetBoard() {
        System.out.print(OFFSET_THREE + OFFSET_THREE);
    }
}
