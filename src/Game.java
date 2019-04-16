import java.util.Scanner;

public class Game {

    public static String currentCoord;

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
    public static void makeMove() {
        if (!gameComplete) {
            if (blueTurn) {
                blueMove();
            } else {
                redMove();
            }
            gameComplete = Board.isGameComplete();
        }
    }

    /**
     * Used to update the board based on the opponent's move.
     *
     * @param position - the position chosen by the opponent
     */
    public static void makeMove(String position) {
        if (!gameComplete) {
            if (blueTurn) {
                blueMove(position);
            } else {
                redMove(position);
            }
            gameComplete = Board.isGameComplete();
        }
    }

    /**
     * Signals blue player to make a move.
     */
    public static void blueMove() {

        Scanner input = new Scanner(System.in);
        int[] move;

        while (blueTurn) {
            Board.printBoard();
            System.out.println("Make your move (enter hex coordinates):");

            currentCoord = input.nextLine(); // for printing in the server and client
            move = parseCoor(currentCoord);
            System.out.println();
            Board.updateBoard(move);
        }
    }

    /**
     * Used to update opponent's position.
     *
     * @param position - the opponent's position
     */
    public static void blueMove(String position) {

        int[] move;

        while (blueTurn) {

            currentCoord = position; // for printing in the server and client
            move = parseCoor(currentCoord);
            System.out.println();
            Board.updateBoard(move);
        }
    }

    /**
     * Signals red player to make a move.
     */
    public static void redMove() {

        Scanner input = new Scanner(System.in);
        int[] move;

        while (!blueTurn) {
            Board.printBoard();
            System.out.println("Make your move (enter hex coordinates):");

            currentCoord = input.nextLine(); // for printing in the server and client
            move = parseCoor(currentCoord);
            System.out.println();
            Board.updateBoard(move);
        }
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
    }

    /**
     * Parses user input into an int array so that the program can update the board accordingly.
     *
     * @param input - user input
     * @return - returns parse coordinates as an int array
     */
    public static int[] parseCoor(String input) {
        String[] in;
        int[] coor = {-1,-1};

        /**
         * Checks that the input has the format:
         * "(" + number between 0 and 10 + ", " + number between 0 and 10 + ");"
         */
        if (input.matches("\\((\\d|[1][0])\\,\\s(\\d|[1][0])\\);")) {

            in = input.split(",");

            try {
                coor[0] = Integer.parseInt(in[0].replaceAll("[\\s\\(\\);]+", ""));
                coor[1] = Integer.parseInt(in[1].replaceAll("[\\s\\(\\);]+", ""));
            } catch (NumberFormatException e) {
                System.out.println("Invalid coordinates entered! Please ensure coordinates are numeric.");
            }

        } else {
            System.out.println("Invalid input! Please enter coordinates in the format '(<left coordinate>, <right coordinate>);'");
        }

        return coor;
    }
}
