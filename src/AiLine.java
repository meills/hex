import java.util.*;

/**
 * A game mode where a human player plays against an AI that tries to complete a line across the board.
 */
public class AiLine extends AiMode{
    public static HashSet<int[]> line;

    /**
     * This method overrides AiMode's initGame() method.
     * Coordinates of a line across the board is generated as a guideline for the AI.
     * @Override
     */
    public static void initGame() {
        AiMode.initGame();
        Random rand = new Random();
        int[] coords;
        int row;

        Board.initBoard();
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

    /**
     * Runs player vs random ai mode.
     */
    public static void runGame() {

        System.out.println("Player vs AI mode selected!");
        System.out.println();

        while (!gameComplete) {
            if (humanTurn) {
                humanMove();
            } else {
                aiMove();
            }

            gameComplete = Validator.validateWin();
        }
    }

    /**
     * Signals human move and updates board accordingly.
     */
    public static void humanMove() {
        /**
         * Signals human move and updates board accordingly.
         */

        Scanner input = new Scanner(System.in);
        int[] move;

        while (humanTurn) {
            System.out.println("Your turn.");
            Board.printBoard();
            System.out.println("Make your move (enter hex coordinates):");

            move = Game.parseCoor(input.nextLine());


            if (move[0] == -1) {
                System.out.println(Config.INVALID_MOVE);
                System.out.println();
                break;
            }

            System.out.println();
            Board.updateBoardLine(move);
        }
    }

    /**
     * Carry out AI's move and updates board accordingly.
     */
    public static void aiMove() {
        System.out.println(Config.AI_MOVE);
        System.out.println();

        Random rand = new Random();
        int index;

        index = rand.nextInt(line.size());
        //System.out.println("line array size: " + line.size() + "   chosen index: " + index);
        //System.out.println(line.get(index)[0] + " " + line.get(index)[1]);
        Board.updateBoardLine((int[]) Arrays.asList(line.toArray()).get(index));
    }

    /**
     * Checks if generated line coordinates are used by human player and modifies line move accordingly.
     * @param move - human player's move
     */
    public static void aiMoveCheck(int[] move) {
        List<Object> altMoves = new ArrayList<>();

        System.out.println("human move: " + move[0] + " " + move[1]);
        debugLine();

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


        debugLine();
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
        return (List<Object>) Arrays.asList(neighbours.toArray());
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
