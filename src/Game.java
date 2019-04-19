import java.util.Scanner;

public class Game {

    public static String currentCoord;
    
    public static int[] coordIndices;

    /**
     * By default, the game is player vs. player.
     * Updates for player vs. AI.
     */
    public static boolean aiGame = false;

    /**
     * By default, the human player starts.
     * Needs to be changed for networking.
     */
    public static boolean aiTurn = false;

    public static boolean blueTurn;
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

        /**
         * Player's move
         */
        if (!aiGame || !aiTurn) {

            Scanner input = new Scanner(System.in);

            while (blueTurn) {
                System.out.println(Config.MAKE_MOVE);

                currentCoord = input.nextLine();

                if (!currentCoord.equals(Config.QUIT)) {
                    coordIndices = parseCoor(currentCoord);
                    System.out.println();
                    Board.updateBoard(coordIndices);
                }
            }
        }

        /**
         * AI's move
         */
        else {

            if (AI.mode.equals("3")) {
                AiLine.aiMoveCheck(coordIndices);
            }

            System.out.println("AI's move:");
            currentCoord = AI.getCoords();
            System.out.println(currentCoord);
            coordIndices = parseCoor(currentCoord);
            System.out.println();
            Board.updateBoard(coordIndices);
        }

        blueTurn = false;
        aiTurn = !aiTurn;
    }

    /**
     * Used to update opponent's position.
     *
     * @param position - the opponent's position
     */
    public static void blueMove(String position) {

        int[] move;

        while (blueTurn) {

            currentCoord = position;
            move = parseCoor(currentCoord);
            System.out.println();
            Board.updateBoard(move);
        }

        if (aiGame) {
            aiTurn = true;
        }
    }

    /**
     * Signals red player to make a move.
     */
    public static void redMove() {

        int[] move;

        /**
         * Player's move
         */
        if (!aiGame || !aiTurn) {
            Scanner input = new Scanner(System.in);

            while (!blueTurn) {
                System.out.println(Config.MAKE_MOVE);

                currentCoord = input.nextLine(); // for printing in the server and client

                if (!currentCoord.equals(Config.QUIT)) {
                    move = parseCoor(currentCoord);
                    System.out.println();
                    Board.updateBoard(move);
                }
            }
        }

        /**
         * AI's move
         */
        else {

            if (AI.mode.equals("3")) {
                AiLine.aiMoveCheck(coordIndices);
            }

            System.out.println("AI's move:");
            currentCoord = AI.getCoords();
            System.out.println(currentCoord);
            move = parseCoor(currentCoord);
            System.out.println();
            Board.updateBoard(move);
        }

        blueTurn = true;
        aiTurn = !aiTurn;
    }

    /**
     * Used to update opponent's position.
     *
     * @param position - the opponent's position
     */
    public static void redMove(String position) {

        int[] move;

        while (!blueTurn) {

            currentCoord = position; // for printing in the server and client
            move = parseCoor(currentCoord);
            System.out.println();
            Board.updateBoard(move);
        }

        if (aiGame) {
            aiTurn = true;
        }
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
        if (input.matches("\\((\\d|[1][0]),(\\d|[1][0])\\);")) {

            in = input.split(",");

            try {
                coor[0] = Integer.parseInt(in[0].replaceAll("[\\s();]+", ""));
                coor[1] = Integer.parseInt(in[1].replaceAll("[\\s();]+", ""));
            } catch (NumberFormatException e) {
                System.out.println(Config.INVALID_MOVE);
            }

        } else {
            System.out.println(Config.INVALID_MOVE);
        }

        return coor;
    }
}
