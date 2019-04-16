import java.util.*;

public class Board {

    private static final char FREE = '⬢';
    private static final char RED = 'r';
    private static final char BLUE = 'b';

    /**
     * To change the colour of the terminal output.
     * 
     * https://dev.to/awwsmm/coloured-terminal-output-with-java-57l3
     */
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";

    private static final int BOARD_SIZE = 11;

    private static char[][] board;

    /**
     * Initialises the board with all positions unoccupied.
     */
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
     * Prints the current state of the board.
     */
    public static void printBoard() {

        System.out.println();

        /**
         * Prints the blue coordinates on top.
         */
        for (int index = 0; index < BOARD_SIZE; index++) {
            System.out.print(ANSI_BLUE + " " + index);
        }
        System.out.println(ANSI_RESET);

        for (int i = 0; i < BOARD_SIZE; i++) {

            /**
             * Prints the empty spaces at the beginning of each line.
             */
            for (int space = 0; space < i; space ++) {
                if (space < 9) {
                    System.out.print(" ");
                }
            }

            /**
             * Prints the red coordinates on the left.
             */
            System.out.print(ANSI_RED + i + " ");
            System.out.print(ANSI_RESET);

            /**
             * Positions are stored as '⬢', 'r', 'b'.
             * The board is printed as hexagons of different colours.
             */
            for (int j = 0; j < BOARD_SIZE; j++) {

                /**
                 * Prints the red hexagons.
                 */
                if (board[i][j] == 'r') {
                    System.out.print(ANSI_RED + FREE + " ");
                } 
                
                /**
                 * Prints the blue hexagons.
                 */
                else if (board[i][j] == 'b') {
                    System.out.print(ANSI_BLUE + FREE + " ");
                } 
                
                /**
                 * Prints the empty spaces.
                 */
                else {
                    System.out.print(ANSI_RESET + FREE + " ");
                }
            }

            /**
             * Prints the red coordinates on the right.
             */
            System.out.println(ANSI_RED + " " + i);
        }

        /**
         * Prints the empty spaces and the blue coordinates on the bottom.
         */
        System.out.print(String.format("%1$" + (BOARD_SIZE + 1) + "s", ""));
        for (int index = 0; index < BOARD_SIZE; index++) {
            System.out.print(ANSI_BLUE + " " + index);
        }
        System.out.println(ANSI_RESET + "\n");
    }

    /**
     * Updates the board based on the selected position.
     * 
     * @param coor - the coordinates of the position
     */
    public static void updateBoard(int[] coor) {

        /**
         * Checks that the coordinates are in range before updating the board.
         */
        if (coor[0] >= 0 && coor[0]< BOARD_SIZE && coor[1] >= 0 && coor[1]< BOARD_SIZE) {

            /**
             * Checks that the position is free before updating the board.
             * Updates the turn if the move is valid.
             */
            if (board[coor[0]][coor[1]] == FREE) {
                if (Game.blueTurn) {
                    board[coor[0]][coor[1]] = BLUE;
                    Game.blueTurn = false;
                } else {
                    board[coor[0]][coor[1]] = RED;
                    Game.blueTurn = true;
                }
            } else {
                System.out.println("Invalid move! Hex tile is already occupied.");
            }
        }
    }

    // Needs to be completed.
    public static boolean isGameComplete() {

        boolean gameComplete = false;

        /**
         * If it is the blue player's turn, we check whether the red player has won
         * before making a move.
         */
        if (Game.blueTurn) {

            boolean leftEdge = false;
            boolean rightEdge = false;

            /**
             * Checks that there is at least one red piece on each side.
             */
            for (int i = 0; i < BOARD_SIZE; i++) {

                if (board[i][0] == 'r') {
                    leftEdge = true;
                }

                if (board[i][10] == 'r') {
                    rightEdge = true;
                }
            }

            /**
             * If there is a red piece on both sides, checks if there is a line connecting the sides.
             */
            if (leftEdge && rightEdge) {

            }
        }

        /**
         * If it is the red player's turn, we check whether the blue player has won
         * before making a move.
         */
        else {

        }

        return gameComplete;
    }
}
