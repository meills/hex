import java.util.*;

/**
 * A game mode where a human player plays against an AI that tries to complete a line across the board.
 */
public class AiLine {

    public static HashSet<int[]> line;

    public static void setLine() {
        Random rand = new Random();
        int[] coords;
        int row;
        line = new HashSet<>();
        row = rand.nextInt(Board.BOARD_SIZE);

        /**
         * Generates a random line.
         */
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            coords = new int[2];
            coords[0] = row;
            coords[1] = i;
            line.add(coords);
        }
    }

    public static String makeMove() {
        Random rand = new Random();
        int index;

        index = rand.nextInt(line.size());
        int[] coords = ((int[]) Arrays.asList(line.toArray()).get(index));
        
        removeCoord(coords);

        //System.out.println("line array size: " + line.size() + "   chosen index: " + index);
        //System.out.println(line.get(index)[0] + " " + line.get(index)[1]);
        return ("(" + coords[0] + "," + coords[1] + ");");
    }

    /**
     * Checks if generated line coordinates are used by human player and modifies line move accordingly.
     * @param move - the indices of the current coordinate
     */
    public static void aiMoveCheck(int[] move) {
        List<Object> altMoves = new ArrayList<>();

        // debugLine();

        Iterator<int[]> it = line.iterator();
        while (it.hasNext()) {
            int[] coords = it.next();

            if (coords[0] == move[0] && coords[1] == move[1]) {
                it.remove();
                altMoves = genAltMoves(move);
            }
        }

        it = line.iterator();

        // checks for any repeats and removes them
        while (it.hasNext()) {
            int[] lineCoords = it.next();
            int[] altCoords;

            for (Object altMove: altMoves) {
                altCoords = (int[]) altMove;
                if (altCoords[0] == lineCoords[0] && altCoords[1] == lineCoords[1]) {
                    it.remove();
                }
            }
        }

        for (Object altMove: altMoves) {
            int[] altCoords = (int[]) altMove;
            line.add(altCoords);
        }

        // debugLine();
    }

    /**
     * Adds alternate coordinates when one of the line's tiles are occupied by the opponent.
     * @param move - human player's move
     * @return - List of alternate moves
     */
    public static List<Object> genAltMoves(int[] move) {
        HashSet<int[]> neighbours = new HashSet<>();
        int[] neighbour;

        if (move[0] > 0) {
            neighbour = new int[2];
            neighbour[0] = move[0] - 1;
            neighbour[1] =  move[1];

            if (Board.board[neighbour[0]][neighbour[1]] == Board.FREE) {
                neighbours.add(neighbour);
            }
        }

        if (move[0] > 0 && move[1] < Board.BOARD_SIZE -1) {
            neighbour = new int[2];
            neighbour[0] = move[0] - 1;
            neighbour[1] =  move[1] + 1;
            neighbours.add(neighbour);

            if (Board.board[neighbour[0]][neighbour[1]] == Board.FREE) {
                neighbours.add(neighbour);
            }
        }

        if (move[1] < Board.BOARD_SIZE - 1) {
            neighbour = new int[2];
            neighbour[0] = move[0];
            neighbour[1] =  move[1] + 1;
            neighbours.add(neighbour);
            if (Board.board[neighbour[0]][neighbour[1]] == Board.FREE) {
                neighbours.add(neighbour);
            }
        }

        if (move[0] < Board.BOARD_SIZE - 1) {
            neighbour = new int[2];
            neighbour[0] = move[0] + 1;
            neighbour[1] =  move[1];
            neighbours.add(neighbour);
            if (Board.board[neighbour[0]][neighbour[1]] == Board.FREE) {
                neighbours.add(neighbour);
            }
        }


        if (move[0] < Board.BOARD_SIZE - 1 && move[1] > 0) {
            neighbour = new int[2];
            neighbour[0] = move[0] + 1;
            neighbour[1] =  move[1] - 1;
            neighbours.add(neighbour);
            if (Board.board[neighbour[0]][neighbour[1]] == Board.FREE) {
                neighbours.add(neighbour);
            }
        }

        if (move[1] > 0) {
            neighbour = new int[2];
            neighbour[0] = move[0];
            neighbour[1] =  move[1] - 1;
            neighbours.add(neighbour);
            if (Board.board[neighbour[0]][neighbour[1]] == Board.FREE) {
                neighbours.add(neighbour);
            }
        }
        return Arrays.asList(neighbours.toArray());
    }

    /**
     * Method to check all the coordinates of pieces to form a line.
     */
    public static void debugLine() {
        System.out.print("line coords: ");

        for (int[] coords: line) {
            System.out.print("(" + coords[0] + ", " + coords[1] + ") ");
        }

        System.out.println();
    }

    public static void removeCoord(int[] move) {
        Iterator<int[]> it = line.iterator();
        int[] lineCoor;

        while (it.hasNext()) {
            lineCoor = it.next();
            if (lineCoor[0] == move[0] && lineCoor[1] == move[1]) {
                it.remove();
            }
        }
    }
}
