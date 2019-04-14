import java.util.*;

public class Board {
    private static final char FREE = '#';
    private static final char RED = 'r';
    private static final char BLUE = 'b';

    private static final String OFFSET_ONE = " ";
    private static final String OFFSET_TWO = "  ";
    private static final String OFFSET_THREE = "   ";

    private static final int BOARD_SIZE = 4;


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
        int coor = 0;
        int index1 = 0;
        int index2 = BOARD_SIZE-1;
        int index3 = 0;
        int offsetCount = BOARD_SIZE;

        System.out.println("This is your Hex board: ");
        System.out.println();

        offsetBoard();
        for (int n = 0; n <= BOARD_SIZE; n++) {
            System.out.print(OFFSET_TWO);
        }

        System.out.print(coor + OFFSET_THREE + coor);
        System.out.println();

        coor++;

        while (index1 <= index2 - 1) {
            offsetBoard();

            for (int j = 0; j < offsetCount; j++) {
                System.out.print(OFFSET_TWO);
            }

            if (coor < 10) {
                System.out.print(coor + OFFSET_THREE);
            } else {
                System.out.print(coor + OFFSET_TWO);
            }


            for (int i = 0; i <= index1; i++) {
                if (i < 10) {
                    System.out.print(board[index1-i][i] + OFFSET_THREE);
                } else {
                    System.out.print(board[index1-i][i] + OFFSET_TWO);
                }
            }

            System.out.print(coor);
            System.out.println();

            coor++;
            index1++;
            offsetCount--;
        }

        offsetCount++;

        while(index1 >= 0) {
            offsetBoard();

            for (int m = 0; m <= offsetCount; m++) {
                System.out.print(OFFSET_TWO);
            }

            for (int k = 0; k <= index1; k++) {
                System.out.print(board[index2 - k][index3 + k] + OFFSET_THREE);
            }
            System.out.println();

            index1--;
            index3++;
            offsetCount++;
        }

        System.out.println();
    }

    /**
     * Updates board according to specified coordinates.
     * @param coor - coordinates on hex board
     */
    public static void updateBoard(int[] coor) {
        if (board[coor[0]][coor[1]] == FREE) {
            if (Game.blueTurn) {
                board[coor[0]][coor[1]] = BLUE;
                Game.blueTurn = false;
            } else  if (!Game.blueTurn) {
                board[coor[0]][coor[1]] = RED;
                Game.blueTurn = true;
            }
        } else {
            System.out.println("Invalid move! Hex tile is already occupied.");
        }
    }


    /**
     * Method checks if game is complete and returns a boolean value.
     * @return - boolean value denoting if game is complete
     */
    public static boolean isGameComplete() {
        boolean gameComplete = false;

        ArrayList<int[]> blueCoor = new ArrayList<>();
        ArrayList<int[]> redCoor = new ArrayList<>();

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

        for (int[] cor: blueCoor) {
            if (cor[1] == 0) {

            }
        }

        // statements for debugging
        System.out.println("blue coords:");
        for (int[] cor: blueCoor) {
            System.out.println(cor[0] + " " + cor[1]);
        }

        System.out.println("red coords:");
        for (int[] co: redCoor) {
            System.out.println(co[0] + " " + co[1]);
        }

        return gameComplete;
    }



    public static void offsetBoard() {
        System.out.print(OFFSET_THREE + OFFSET_THREE);
    }
}
