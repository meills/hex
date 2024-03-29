import java.util.Scanner;

public class Game {

    public static String currentCoord;

    public static int[] coordIndices;

    /**
     * By default, the game is player vs. player.
     */
    public static boolean aiGame = false;

    public static boolean aiTurn = false;

    public static boolean networkGame = false;

    /**
     * The blue player starts first by default.
     */
    public static boolean blueTurn;

    /**
     * Stores the state of the game - finished (true) or ongoing (false).
     */
    public static boolean gameComplete;

    /**
     * Initialises a new game.
     */
    public static void initGame() {
        blueTurn = true;
        gameComplete = false;
        Board.initBoard();
    }

    /**
     * Used to make the current move for the current player.
     */
    public static void playTurn() {
        if (!gameComplete) {
            if (blueTurn) {
                blueMove();
            } else {
                redMove();
            }
            gameComplete = Validator.validateWin();
        }
    }

    /**
     * Used to update the board based on the opponent's move.
     *
     * @param position - the position chosen by the opponent
     */
    public static void playTurn(String position) {
        if (!gameComplete) {
            if (blueTurn) {
                blueMove(position);
            } else {
                redMove(position);
            }
            gameComplete = Validator.validateWin();
        }
    }

    /**
     * Signals blue player to make a move.
     */
    public static void blueMove() {

        if (!aiTurn) {
            Scanner input = new Scanner(System.in);

            while (blueTurn) {
                System.out.print(Config.BLUE);

                System.out.println(Config.MAKE_MOVE);

                currentCoord = input.nextLine();

                if (!currentCoord.equals(Config.QUIT)) {
                    coordIndices = parseCoor(currentCoord);
                    System.out.println();
                    Board.updateBoard(coordIndices);
                } else {
                    if (networkGame) {
                        blueTurn = false;
                    } else {
                        System.out.println(Config.INVALID_MOVE);
                    }
                }
            }

            if (aiGame) {
                aiTurn = true;
            }
        } else {

            while (blueTurn) {
                aiMove();
            }

            aiTurn = false;
        }
    }

    /**
     * Used to update opponent's position.
     *
     * @param position - the opponent's position
     */
    public static void blueMove(String position) {
        opponentMove(position);
    }

    /**
     * Signals red player to make a move.
     */
    public static void redMove() {

        if (!aiTurn) {
            Scanner input = new Scanner(System.in);

            while (!blueTurn) {
                System.out.print(Config.RED);

                System.out.println(Config.MAKE_MOVE);

                currentCoord = input.nextLine(); // for printing in the server and client

                if (!currentCoord.equals(Config.QUIT)) {
                    coordIndices = parseCoor(currentCoord);
                    System.out.println();
                    Board.updateBoard(coordIndices);
                } else {
                    if (networkGame) {
                        blueTurn = true;
                    } else {
                        System.out.println(Config.INVALID_MOVE);
                    }
                }
            }

            if (aiGame) {
                aiTurn = true;
            }
        } else {

            while (!blueTurn) {
                aiMove();
            }

            aiTurn = false;
        }
    }

    /**
     * Used to update opponent's position.
     *
     * @param position - the opponent's position
     */
    public static void redMove(String position) {
        opponentMove(position);
    }

    /**
     * The move made by the opponent.
     */
    private static void opponentMove(String position) {
        currentCoord = position;
        coordIndices = parseCoor(currentCoord);
        System.out.println();
        Board.updateBoard(coordIndices);

        if (aiGame) {
            aiTurn = true;
        }
    }

    /**
     * The move made by the AI player.
     */
    public static void aiMove() {
        System.out.println(Config.AI_MOVE);
        currentCoord = AI.getCoords();
        System.out.println(currentCoord);
        coordIndices = parseCoor(currentCoord);
        System.out.println();
        Board.updateBoard(coordIndices);
    }

    /**
     * Parses user input into an int array so that the program can update the board accordingly.
     *
     * @param input - user input
     * @return - returns parse coordinates as an int array
     */
    public static int[] parseCoor(String input) {
        String[] in;
        int[] coor = {-1, -1};

        /**
         * Checks that the input has the format:
         * "(" + number between 0 and 10 + "," + number between 0 and 10 + ");"
         */
        if (input.matches("\\((\\d|[1][0])\\,(\\d|[1][0])\\);")) {

            in = input.split(",");

            try {
                coor[0] = Integer.parseInt(in[0].replaceAll("[\\s\\(\\);]+", ""));
                coor[1] = Integer.parseInt(in[1].replaceAll("[\\s\\(\\);]+", ""));
            } catch (NumberFormatException e) {
                System.out.println(Config.INVALID_MOVE);
            }

        } else {
            System.out.println(Config.INVALID_MOVE);
        }

        return coor;
    }
}