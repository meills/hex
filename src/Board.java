import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;

public class Board {

    /**
     * To change the colour of the terminal output.
     * <p>
     * https://dev.to/awwsmm/coloured-terminal-output-with-java-57l3
     */
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    private static final char FREE = '⬢';
    private static final char RED = 'r';
    private static final char BLUE = 'b';
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
            for (int space = 0; space < i; space++) {
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
        if (coor[0] >= 0 && coor[0] < BOARD_SIZE && coor[1] >= 0 && coor[1] < BOARD_SIZE) {

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

            /**
             * If there is a red piece on both sides, checks if there is a line connecting the sides.
             */
            if (edgesExist(RED)) {

                /**
                 * Keeps checking until a path is found or until the first column is finished.
                 */
                for (int i = 0; i < BOARD_SIZE; i++) {
                    if (board[i][0] == RED) {

                        /**
                         * Used to check which hexagons were visited to avoid infinite loops.
                         */
                        LinkedHashSet<ArrayList<Integer>> visited = new LinkedHashSet<>();

                        gameComplete = redConnectsToEdge(i, 0, visited);
                    }
                }
            }
        }

        /**
         * If it is the red player's turn, we check whether the blue player has won
         * before making a move.
         */
        else {

            /**
             * If there is a blue piece on both sides, checks if there is a line connecting the sides.
             */
            if (edgesExist(BLUE)) {

                /**
                 * Keeps checking until a path is found or until the first row is finished.
                 */
                for (int j = 0; j < BOARD_SIZE; j++) {
                    if (board[0][j] == BLUE) {

                        /**
                         * Used to check which hexagons were visited to avoid infinite loops.
                         */
                        LinkedHashSet<ArrayList<Integer>> visited = new LinkedHashSet<>();

                        /**
                         * j and 0 are in the opposite order from the red player,
                         * because we need the coordinates inversed.
                         */
                        gameComplete = blueConnectsToEdge(0, j, visited);
                    }
                }
            }
        }

        return gameComplete;
    }

    /**
     * Checks that there is at least one piece on each side.
     *
     * @param player - the character of the current player
     * @return - true if there is at least one piece on each edge, false otherwise
     */
    public static boolean edgesExist(char player) {

        boolean leftEdge = false;
        boolean rightEdge = false;

        /**
         * Checks edges for the red player.
         */
        if (player == RED) {

            /**
             * If the edges are found, does not continue iterating (efficiency).
             */
            for (int i = 0; i < BOARD_SIZE && (!leftEdge || !rightEdge); i++) {

                if (board[i][0] == player) {
                    leftEdge = true;
                }

                if (board[i][10] == player) {
                    rightEdge = true;
                }
            }
        }

        /**
         * Checks edges for the blue player.
         */
        else {
            for (int j = 0; j < BOARD_SIZE && (!leftEdge || !rightEdge); j++) {

                if (board[0][j] == player) {
                    leftEdge = true;
                }

                if (board[10][j] == player) {
                    rightEdge = true;
                }
            }
        }

        if (leftEdge && rightEdge) {
            return true;
        }

        return false;
    }

    /**
     * For each red hexagon around the current one, checks whether they complete a path to the edge.
     *
     * @param i       - i coord of the current hexagon
     * @param j       - j coord of the current hexagon
     * @param visited - the set of hexagons that were already visited
     * @return - true if a path connecting the edges has been found, false otherwise
     */
    public static boolean redConnectsToEdge(int i, int j, LinkedHashSet<ArrayList<Integer>> visited) {

        if (j == 10) {
            return true;
        }

        LinkedHashSet<ArrayList<Integer>> neighbours = redSetNeighbours(i, j, visited);

        boolean foundEdge = false;

        /**
         * Iterates through the ArrayLists containing the coordinates of the neighbours.
         */
        for (ArrayList<Integer> coords : neighbours) {

            visited.add(coords);

            if (redConnectsToEdge(coords.get(0), coords.get(1), visited)) {
                foundEdge = true;
            }
        }

        return foundEdge;
    }

    /**
     * For each blue hexagon around the current one, checks whether they complete a path to the edge.
     *
     * @param i       - i coord of the current hexagon
     * @param j       - j coord of the current hexagon
     * @param visited - the set of hexagons that were already visited
     * @return - true if a path connecting the edges has been found, false otherwise
     */
    public static boolean blueConnectsToEdge(int i, int j, LinkedHashSet<ArrayList<Integer>> visited) {

        if (i == 10) {
            return true;
        }

        LinkedHashSet<ArrayList<Integer>> neighbours = blueSetNeighbours(i, j, visited);

        boolean foundEdge = false;

        /**
         * Iterates through the ArrayLists containing the coordinates of the neighbours.
         */
        for (ArrayList<Integer> coords : neighbours) {

            visited.add(coords);

            if (blueConnectsToEdge(coords.get(0), coords.get(1), visited)) {
                foundEdge = true;
            }
        }

        return foundEdge;
    }

    /**
     * Finds the red neighbours of the hexagon.
     *
     * @param i       - i coord of the current hexagon
     * @param j       - j coord of the current hexagon
     * @param visited - the set of hexagons that were already visited
     * @return - a set containing lists of the coords of the red neighbours that were not already visited
     */
    public static LinkedHashSet<ArrayList<Integer>> redSetNeighbours(int i, int j, LinkedHashSet<ArrayList<Integer>> visited) {

        LinkedHashSet<ArrayList<Integer>> neighbours = new LinkedHashSet<>();

        if (j + 1 <= 10 && board[i][j + 1] == RED && !visited.contains(new ArrayList<>(Arrays.asList(i, j + 1)))) {
            ArrayList<Integer> coords = new ArrayList<>(Arrays.asList(i, j + 1));
            neighbours.add(coords);
        }

        if (j + 1 <= 10 && i - 1 >= 0 && board[i - 1][j + 1] == RED && !visited.contains(new ArrayList<>(Arrays.asList(i - 1, j + 1)))) {
            ArrayList<Integer> coords = new ArrayList<>(Arrays.asList(i - 1, j + 1));
            neighbours.add(coords);
        }

        if (i + 1 <= 10 && board[i + 1][j] == RED && !visited.contains(new ArrayList<>(Arrays.asList(i + 1, j)))) {
            ArrayList<Integer> coords = new ArrayList<>(Arrays.asList(i + 1, j));
            neighbours.add(coords);
        }

        if (i - 1 >= 0 && board[i - 1][j] == RED && !visited.contains(new ArrayList<>(Arrays.asList(i - 1, j)))) {
            ArrayList<Integer> coords = new ArrayList<>(Arrays.asList(i - 1, j));
            neighbours.add(coords);
        }

        if (j - 1 >= 0 && i + 1 <= 10 && board[i + 1][j - 1] == RED && !visited.contains(new ArrayList<>(Arrays.asList(i + 1, j - 1)))) {
            ArrayList<Integer> coords = new ArrayList<>(Arrays.asList(i + 1, j - 1));
            neighbours.add(coords);
        }

        if (j - 1 >= 0 && board[i][j - 1] == RED && !visited.contains(new ArrayList<>(Arrays.asList(i, j - 1)))) {
            ArrayList<Integer> coords = new ArrayList<>(Arrays.asList(i, j - 1));
            neighbours.add(coords);
        }

        return neighbours;
    }

    /**
     * Finds the blue neighbours of the hexagon.
     *
     * @param i       - i coord of the current hexagon
     * @param j       - j coord of the current hexagon
     * @param visited - the set of hexagons that were already visited
     * @return - a set containing lists of the coords of the blue neighbours that were not already visited
     */
    public static LinkedHashSet<ArrayList<Integer>> blueSetNeighbours(int i, int j, LinkedHashSet<ArrayList<Integer>> visited) {

        LinkedHashSet<ArrayList<Integer>> neighbours = new LinkedHashSet<>();

        if (i + 1 <= 10 && board[i + 1][j] == BLUE && !visited.contains(new ArrayList<>(Arrays.asList(i + 1, j)))) {
            ArrayList<Integer> coords = new ArrayList<>(Arrays.asList(i + 1, j));
            neighbours.add(coords);
        }

        if (i + 1 <= 10 && j - 1 >= 0 && board[i + 1][j - 1] == BLUE && !visited.contains(new ArrayList<>(Arrays.asList(i + 1, j - 1)))) {
            ArrayList<Integer> coords = new ArrayList<>(Arrays.asList(i + 1, j - 1));
            neighbours.add(coords);
        }

        if (j + 1 <= 10 && board[i][j + 1] == BLUE && !visited.contains(new ArrayList<>(Arrays.asList(i, j + 1)))) {
            ArrayList<Integer> coords = new ArrayList<>(Arrays.asList(i, j + 1));
            neighbours.add(coords);
        }

        if (j - 1 >= 0 && board[i][j - 1] == BLUE && !visited.contains(new ArrayList<>(Arrays.asList(i, j - 1)))) {
            ArrayList<Integer> coords = new ArrayList<>(Arrays.asList(i, j - 1));
            neighbours.add(coords);
        }

        if (i - 1 >= 0 && board[i - 1][j] == BLUE && !visited.contains(new ArrayList<>(Arrays.asList(i - 1, j)))) {
            ArrayList<Integer> coords = new ArrayList<>(Arrays.asList(i - 1, j));
            neighbours.add(coords);
        }

        if (i - 1 >= 0 && j + 1 <= 10 && board[i - 1][j + 1] == BLUE && !visited.contains(new ArrayList<>(Arrays.asList(i - 1, j + 1)))) {
            ArrayList<Integer> coords = new ArrayList<>(Arrays.asList(i - 1, j + 1));
            neighbours.add(coords);
        }

        return neighbours;
    }
}
