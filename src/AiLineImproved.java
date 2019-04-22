import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

/**
 * AI that tries to create a jagged line across the board
 */
public class AiLineImproved extends AI {

    /**
     * Stores the colour of the AI.
     * True for blue, false for red.
     */
    public static boolean bluePlayer = false;

    public static HashSet<int[]> line;

    /**
     * Variable that signals if opponent has occupied one of the pieces on the jagged line.
     */
    private static boolean opponentThreat;
    private static int[] moveAgainstOpp;

    /**
     * Main method used for debugging.
     */
    /*public static void main(String[] args) throws IOException{
        Board.initBoard();
        setLine();

        debugLine();
        int[] testMove = new int[2];

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        testMove[0] = Integer.parseInt(br.readLine());

        br = new BufferedReader(new InputStreamReader(System.in));
        testMove[1] =  Integer.parseInt(br.readLine());

        aiMoveCheck(testMove);
    }*/
    public static void setLine() {
        Random rand = new Random();
        opponentThreat = false;
        int[] coords;
        int x;

        boolean changeRow = false;

        line = new HashSet<>();

        x = Board.BOARD_SIZE / 2 + rand.nextInt(Board.BOARD_SIZE / 2);

        /**
         * Generates a random jagged line.
         */
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            coords = new int[2];

            if (changeRow) {
                x--;
            }

            /**
             * the AI can be either red or blue.
             */
            if (bluePlayer) {
                coords[0] = i;
                coords[1] = x;
            } else {
                coords[0] = x;
                coords[1] = i;
            }

            line.add(coords);

            /**
             * Ensures that a jagged line is created and that alternate coordinates are taken from a different row.
             */
            changeRow = i % 2 != 0;
        }

        //debugLine();
    }

    public static String makeMove() {
        int[] coords;

        //debugLine();

        //System.out.println(Game.coordIndices);
        if (Game.coordIndices != null) {
            aiMoveCheck(Game.coordIndices);
        }

        //debugLine();

        //System.out.println(opponentThreat);
        if (opponentThreat && moveAgainstOpp[0] != -1) {
            coords = moveAgainstOpp;
        } else {
            Random rand = new Random();
            int index;
            index = rand.nextInt(line.size());
            coords = ((int[]) Arrays.asList(line.toArray()).get(index));
        }


        removeCoord(coords);

        //System.out.println("line array size: " + line.size() + "   chosen index: " + index);
        //System.out.println(line.get(index)[0] + " " + line.get(index)[1]);
        return ("(" + coords[0] + "," + coords[1] + ");");
    }

    /**
     * Checks if generated line coordinates are used by human player and modifies line move accordingly.
     *
     * @param move - the indices of the current coordinate
     */
    public static void aiMoveCheck(int[] move) {
        int[] altMove = {-1, -1};
        opponentThreat = false;

        if (lineContains(move[0], move[1])) {
            removeCoord(move);
            altMove = genAltMove(move);
            //System.out.println("alt move: " + altMove[0] + ", " + altMove[1]);
            moveAgainstOpp = altMove;
            opponentThreat = true;
        }


        /*it = line.iterator();
        // checks for any repeats and removes them
        // not sure if this is necessary for this part, needs testing
        while (it.hasNext()) {
            int[] lineCoords = it.next();
            int[] altCoords;
            if (lineContains(altMove[0], altMove[1])) {
                it.remove();
            }
        }*/
        if (altMove[0] != -1 && altMove[1] != -1) {
            line.add(altMove);
        }
    }


    /**
     * Generates alternate move and signals program to make the alternate move next.
     *
     * @param move
     * @return
     */
    public static int[] genAltMove(int[] move) {
        HashSet<int[]> neighbours = new HashSet<>();
        int[] neighbour;
        int[] altMove = {-1, -1};

        if (move[0] > 0) {
            neighbour = new int[2];
            neighbour[0] = move[0] - 1;
            neighbour[1] = move[1];

            if (Board.board[neighbour[0]][neighbour[1]] == Board.FREE) {
                neighbours.add(neighbour);
            }
        }

        if (move[0] > 0 && move[1] < Board.BOARD_SIZE - 1) {
            neighbour = new int[2];
            neighbour[0] = move[0] - 1;
            neighbour[1] = move[1] + 1;

            if (Board.board[neighbour[0]][neighbour[1]] == Board.FREE) {
                neighbours.add(neighbour);
            }
        }

        if (move[1] < Board.BOARD_SIZE - 1) {
            neighbour = new int[2];
            neighbour[0] = move[0];
            neighbour[1] = move[1] + 1;

            if (Board.board[neighbour[0]][neighbour[1]] == Board.FREE) {
                neighbours.add(neighbour);
            }
        }

        if (move[0] < Board.BOARD_SIZE - 1) {
            neighbour = new int[2];
            neighbour[0] = move[0] + 1;
            neighbour[1] = move[1];

            if (Board.board[neighbour[0]][neighbour[1]] == Board.FREE) {
                neighbours.add(neighbour);
            }
        }


        if (move[0] < Board.BOARD_SIZE - 1 && move[1] > 0) {
            neighbour = new int[2];
            neighbour[0] = move[0] + 1;
            neighbour[1] = move[1] - 1;

            if (Board.board[neighbour[0]][neighbour[1]] == Board.FREE) {
                neighbours.add(neighbour);
            }
        }

        if (move[1] > 0) {
            neighbour = new int[2];
            neighbour[0] = move[0];
            neighbour[1] = move[1] - 1;

            if (Board.board[neighbour[0]][neighbour[1]] == Board.FREE) {
                neighbours.add(neighbour);
            }
        }

        //System.out.println("move: " + move[0] + ", " + move[1]);

        // for debugging
        System.out.print("neighbours:");
        for (int[] set : neighbours) {
            System.out.print("(" + set[0] + " " + set[1] + ") ");
        }
        System.out.println();


        for (int[] n : neighbours) {
            if (lineContains(n[0], n[1] - 1) && lineContains(n[0] - 1, n[1] + 1)) {
                //System.out.println("adj present");
                altMove[0] = n[0]++;
                altMove[1] = n[1];
                break;
            } else if (lineContains(n[0] + 1, n[1] - 1) && lineContains(n[0], n[1] + 1)) {
                //System.out.println("adj present");
                altMove[0] = n[0]--;
                altMove[1] = n[1];
                break;
            } else {
                line.add(n);
            }

        }

        //System.out.println("alt move: " + altMove[0] + ", " + altMove[1]);
        return altMove;
    }


    public static void debugLine() {
        System.out.print("line coords: ");

        for (int[] coords : line) {
            System.out.print("(" + coords[0] + ", " + coords[1] + ") ");
        }
        System.out.println();
    }


    /**
     * A method that checks if the line set contains coordinates.
     *
     * @param x - row coordinate
     * @param y - column coordinate
     * @return -  if line set contains coordinates
     */
    public static boolean lineContains(int x, int y) {
        Iterator<int[]> it = line.iterator();
        int[] lineCoor;

        while (it.hasNext()) {
            lineCoor = it.next();
            //System.out.println("line coor: " + lineCoor[0] + ", " + lineCoor[1]);
            //System.out.println("move: " + x + ". " + y);
            if (lineCoor[0] == x && lineCoor[1] == y) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes coordinate from line set.
     *
     * @param move
     */
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
