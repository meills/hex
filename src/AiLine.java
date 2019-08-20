import java.util.*;

/**
 * A game mode where a human player plays against an AI that tries to complete a line across the board.
 */
public class AiLine {

    /**
     * Stores the colour of the AI.
     * True for blue, false for red.
     */
    public static boolean bluePlayer = false;

    public static HashSet<int[]> line;

    public static void setLine() {
        Random rand = new Random();
        int[] coords;
        line = new HashSet<>();

        int x = 1  + rand.nextInt(Board.BOARD_SIZE - 2);

        /**
         * Generates a random line.
         */
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            coords = new int[2];

            /**
             * the AI can be either red or blue.
             */
            if (bluePlayer) {
                coords[0] = i;
                coords[1] = x;
            }

            else {
                coords[0] = x;
                coords[1] = i;
            }

            line.add(coords);
        }
    }

    public static String makeMove() {

        Random rand = new Random();
        int index = rand.nextInt(line.size());


        if (Game.coordIndices != null) {
            aiMoveCheck(Game.coordIndices);
        }


        int[] coords = ((int[]) Arrays.asList(line.toArray()).get(index));

        // System.out.println("before move check: ");
        // for (int[] hex: line) {
        //     System.out.print(hex[0] + "," + hex[1] + "   ");
        // }


        // System.out.println("\nbefore remove coord: ");
        // for (int[] hex: line) {
        //     System.out.print(hex[0] + "," + hex[1] + "   ");
        // }

        removeCoord(coords);

        // System.out.println("\nafter remove coord: ");
        // for (int[] hex: line) {
        //     System.out.print(hex[0] + "," + hex[1] + "   ");
        // }

        //System.out.println("line array size: " + line.size() + "   chosen index: " + index);
        //System.out.println(line.get(index)[0] + " " + line.get(index)[1]);

        return ("(" + coords[0] + "," + coords[1] + ");");
    }

    /**
     * Checks if generated line coordinates are used by human player and modifies line move accordingly.
     * @param move - the indices of the current coordinate
     */
    public static void aiMoveCheck(int[] move) {
        LinkedHashSet<ArrayList<Integer>> altMoves = new LinkedHashSet<>();

        // debugLine();

        Iterator<int[]> it = line.iterator();
        while (it.hasNext()) {
            int[] coords = it.next();

            if (coords[0] == move[0] && coords[1] == move[1]) {
                it.remove();

                /**
                 * Gets the free neighbours of the hexagon.
                 */
                altMoves = Validator.getNeighbours(Board.FREE, move[0], move[1], new LinkedHashSet<>());
            }
        }

        /**
         * Checks for repeats and removes them.
         */
        for (ArrayList<Integer> neighbour: altMoves) {

            it = line.iterator();

            while (it.hasNext()) {

                int [] hexagon =  it.next();

                if (neighbour.get(0) == hexagon[0] && neighbour.get(1) == hexagon[1]) {
                    it.remove();
                }
            }
        }

        for (ArrayList<Integer> neighbour: altMoves) {
            int[] altCoords = new int[2];
            altCoords[0] = neighbour.get(0);
            altCoords[1] = neighbour.get(1);
            line.add(altCoords);
        }

        // debugLine();
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
