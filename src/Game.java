import java.util.Scanner;

public class Game {

    public static String currentCoord;

    public static boolean blueTurn;
    public static boolean gameComplete;

    public Game() {
        blueTurn = true;
        gameComplete = false;
        Board.initBoard();
    }

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
     * Signals blue player to make a move.
     */
    public static void blueMove() {

        Scanner input = new Scanner(System.in);
        int[] move;

        while (blueTurn) {
            System.out.println("Blue player's turn.");
            Board.printBoard();
            System.out.println("Make your move (enter hex coordinates):");

            currentCoord = input.nextLine(); // for printing in the server and client
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
            System.out.println("Red player's turn.");
            Board.printBoard();
            System.out.println("Make your move (enter hex coordinates):");

            currentCoord = input.nextLine(); // for printing in the server and client
            move = parseCoor(currentCoord);
            System.out.println();
            Board.updateBoard(move);
        }
    }

    public static String getMove() {
        return currentCoord;
    }

    /**
     * Parses user input into an int array so that the program can update the board accordingly.
     *
     * @param input - user input
     * @return - returns parse coordinates as an int array
     */
    public static int[] parseCoor(String input) {
        String[] in;
        int[] coor = new int[2];

        if (input.matches("\\d{1,2},\\s*\\d{1,2}")) {

            in = input.split(",");

            try {
                coor[0] = Integer.parseInt(in[0].replaceAll("\\s+", ""));
                coor[1] = Integer.parseInt(in[1].replaceAll("\\s+", ""));
            } catch (NumberFormatException e) {
                System.out.println("Invalid coordinates entered! Please ensure coordinates are numeric.");
            }

        } else {
            System.out.println("Invalid input! Please enter coordinates in the format '<left coordinate>, <right coordinate>'");
        }

        return coor;
    }
}
