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

        Scanner input = new Scanner(System.in);
        int[] move;

        while (blueTurn) {
            System.out.println(Config.MAKE_MOVE);

            currentCoord = input.nextLine();

            if (!currentCoord.equals(Config.QUIT)) {
                move = parseCoor(currentCoord);
                System.out.println();
                Board.updateBoard(move);
            } else {
                blueTurn = false;
            }
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

                currentCoord = position;
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
                System.out.println(Config.MAKE_MOVE);

                currentCoord = input.nextLine(); // for printing in the server and client

                if (!currentCoord.equals(Config.QUIT)) {
                    move = parseCoor(currentCoord);
                    System.out.println();
                    Board.updateBoard(move);
                } else {
                    blueTurn = true;
                }
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
        public static int[] parseCoor (String input){
            String[] in;
            int[] coor = {-1, -1};
            int x = -1;
            int y = -1;

            /**
             * Checks that the input has the format:
             * "(" + number between 0 and 10 + ", " + number between 0 and 10 + ");"
             */
            if (input.matches("\\((\\d|[1][0])\\,\\s(\\d|[1][0])\\);")) {

                in = input.split(",");

                try {
                    x = Integer.parseInt(in[0].replaceAll("[\\s\\(\\);]+", ""));
                    y = Integer.parseInt(in[1].replaceAll("[\\s\\(\\);]+", ""));
                } catch (NumberFormatException e) {
                    System.out.println(Config.INVALID_MOVE);
                }
            }

            if ((x > 0 && x < Board.BOARD_SIZE) || (y > 0 && y < Board.BOARD_SIZE)) {
                coor[0] = x;
                coor[1] = y;
            }
            return coor;
        }
    }
