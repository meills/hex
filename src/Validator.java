import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Scanner;

public abstract class Validator {

    /**
     * Checks whether the current player has won.
     *
     * @return - true if the game is finished, false otherwise
     */
    public static boolean validateWin() {

        boolean gameComplete = false;

        /**
         * If it is the blue player's turn, we check whether the red player has won
         * before making a move.
         */
        if (Game.blueTurn) {

            /**
             * If there is a red piece on both sides, checks if there is a line connecting the sides.
             */
            if (edgesExist(Board.RED)) {

                /**
                 * Keeps checking until a path is found or until the first column is finished.
                 */
                for (int i = 0; i < Board.BOARD_SIZE; i++) {
                    if (Board.board[i][0] == Board.RED) {

                        /**
                         * Used to check which hexagons were visited to avoid infinite loops.
                         */
                        LinkedHashSet<ArrayList<Integer>> visited = new LinkedHashSet<>();
                        ArrayList<Integer> current = new ArrayList<>(Arrays.asList(i, 0));
                        visited.add(current);

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
            if (edgesExist(Board.BLUE)) {

                /**
                 * Keeps checking until a path is found or until the first row is finished.
                 */
                for (int j = 0; j < Board.BOARD_SIZE; j++) {
                    if (Board.board[0][j] == Board.BLUE) {

                        /**
                         * Used to check which hexagons were visited to avoid infinite loops.
                         */
                        LinkedHashSet<ArrayList<Integer>> visited = new LinkedHashSet<>();
                        ArrayList<Integer> current = new ArrayList<>(Arrays.asList(0, j));
                        visited.add(current);

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
        if (player == Board.RED) {

            /**
             * If the edges are found, does not continue iterating (efficiency).
             */
            for (int i = 0; i < Board.BOARD_SIZE && (!leftEdge || !rightEdge); i++) {

                if (Board.board[i][0] == player) {
                    leftEdge = true;
                }

                if (Board.board[i][Board.BOARD_SIZE - 1] == player) {
                    rightEdge = true;
                }
            }
        }

        /**
         * Checks edges for the blue player.
         */
        else {
            for (int j = 0; j < Board.BOARD_SIZE && (!leftEdge || !rightEdge); j++) {

                if (Board.board[0][j] == player) {
                    leftEdge = true;
                }

                if (Board.board[Board.BOARD_SIZE - 1][j] == player) {
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
     * @param i - i coord of the current hexagon
     * @param j - j coord of the current hexagon
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
        for (ArrayList<Integer> coords: neighbours) {

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
     * @param i - i coord of the current hexagon
     * @param j - j coord of the current hexagon
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
        for (ArrayList<Integer> coords: neighbours) {

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
     * @param i - i coord of the current hexagon
     * @param j - j coord of the current hexagon
     * @param visited - the set of hexagons that were already visited
     * @return - a set containing lists of the coords of the red neighbours that were not already visited
     */
    public static LinkedHashSet<ArrayList<Integer>> redSetNeighbours(int i, int j, LinkedHashSet<ArrayList<Integer>> visited) {

        LinkedHashSet<ArrayList<Integer>> neighbours = new LinkedHashSet<>();

        if (j + 1 <= 10 && Board.board[i][j + 1] == Board.RED && !visited.contains(new ArrayList<>(Arrays.asList(i, j + 1)))) {
            ArrayList<Integer> coords = new ArrayList<>(Arrays.asList(i, j + 1));
            neighbours.add(coords);
        }

        if (j + 1 <= 10 && i - 1 >= 0 && Board.board[i - 1][j + 1] == Board.RED && !visited.contains(new ArrayList<>(Arrays.asList(i - 1, j + 1)))) {
            ArrayList<Integer> coords = new ArrayList<>(Arrays.asList(i - 1, j + 1));
            neighbours.add(coords);
        }

        if (i + 1 <= 10 && Board.board[i + 1][j] == Board.RED && !visited.contains(new ArrayList<>(Arrays.asList(i + 1, j)))) {
            ArrayList<Integer> coords = new ArrayList<>(Arrays.asList(i + 1, j));
            neighbours.add(coords);
        }

        if (i - 1 >= 0 && Board.board[i - 1][j] == Board.RED && !visited.contains(new ArrayList<>(Arrays.asList(i - 1, j)))) {
            ArrayList<Integer> coords = new ArrayList<>(Arrays.asList(i - 1, j));
            neighbours.add(coords);
        }

        if (j - 1 >= 0 && i + 1 <= 10 && Board.board[i + 1][j - 1] == Board.RED && !visited.contains(new ArrayList<>(Arrays.asList(i + 1, j - 1)))) {
            ArrayList<Integer> coords = new ArrayList<>(Arrays.asList(i + 1, j - 1));
            neighbours.add(coords);
        }

        if (j - 1 >= 0 && Board.board[i][j - 1] == Board.RED && !visited.contains(new ArrayList<>(Arrays.asList(i, j - 1)))) {
            ArrayList<Integer> coords = new ArrayList<>(Arrays.asList(i, j - 1));
            neighbours.add(coords);
        }

        return neighbours;
    }

    /**
     * Finds the blue neighbours of the hexagon.
     *
     * @param i - i coord of the current hexagon
     * @param j - j coord of the current hexagon
     * @param visited - the set of hexagons that were already visited
     * @return - a set containing lists of the coords of the blue neighbours that were not already visited
     */
    public static LinkedHashSet<ArrayList<Integer>> blueSetNeighbours(int i, int j, LinkedHashSet<ArrayList<Integer>> visited) {

        LinkedHashSet<ArrayList<Integer>> neighbours = new LinkedHashSet<>();

        if (i + 1 <= 10 && Board.board[i + 1][j] == Board.BLUE && !visited.contains(new ArrayList<>(Arrays.asList(i + 1, j)))) {
            ArrayList<Integer> coords = new ArrayList<>(Arrays.asList(i + 1, j));
            neighbours.add(coords);
        }

        if (i + 1 <= 10 && j - 1 >= 0 && Board.board[i + 1][j - 1] == Board.BLUE && !visited.contains(new ArrayList<>(Arrays.asList(i + 1, j - 1)))) {
            ArrayList<Integer> coords = new ArrayList<>(Arrays.asList(i + 1, j - 1));
            neighbours.add(coords);
        }

        if (j + 1 <= 10 && Board.board[i][j + 1] == Board.BLUE && !visited.contains(new ArrayList<>(Arrays.asList(i, j + 1)))) {
            ArrayList<Integer> coords = new ArrayList<>(Arrays.asList(i, j + 1));
            neighbours.add(coords);
        }

        if (j - 1 >= 0 && Board.board[i][j - 1] == Board.BLUE && !visited.contains(new ArrayList<>(Arrays.asList(i, j - 1)))) {
            ArrayList<Integer> coords = new ArrayList<>(Arrays.asList(i, j - 1));
            neighbours.add(coords);
        }

        if (i - 1 >= 0 && Board.board[i - 1][j] == Board.BLUE && !visited.contains(new ArrayList<>(Arrays.asList(i - 1, j)))) {
            ArrayList<Integer> coords = new ArrayList<>(Arrays.asList(i - 1, j));
            neighbours.add(coords);
        }

        if (i - 1 >= 0 && j + 1 <= 10 && Board.board[i - 1][j + 1] == Board.BLUE && !visited.contains(new ArrayList<>(Arrays.asList(i - 1, j + 1)))) {
            ArrayList<Integer> coords = new ArrayList<>(Arrays.asList(i - 1, j + 1));
            neighbours.add(coords);
        }

        return neighbours;
    }
}
