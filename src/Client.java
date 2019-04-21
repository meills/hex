import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Random;

public class Client {

    public static Socket clientSocket;
    public static String hostName;
    public static int portNumber;
    public static BufferedReader in;
    public static BufferedReader stdIn;
    public static PrintWriter out;

    /**
     * Constructor for the client.
     */
    public Client(String name, int number) {
        hostName = name;
        portNumber = number;
    }

    public static void connect() throws NullPointerException {

        try {

            stdIn = new BufferedReader(new InputStreamReader(System.in));
            clientSocket = new Socket(hostName, portNumber);

            /**
             * Sets up the output.
             */
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            /**
             * Sets up the input.
             */
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            /**
             * Starts communication with the server.
             */
            out.println(Config.GREETING);
            System.out.println(Config.CLIENT + Config.GREETING);

            /**
             * Checks that the server is using the same protocols.
             */
            if (in.readLine().equals(Config.GREETING)) {
                System.out.println(Config.SERVER + Config.GREETING);

                /**
                 * Requests a new game. Server can respond "ready" or "reject".
                 */
                out.println(Config.GAME_REQUEST);
                System.out.println(Config.CLIENT + Config.GAME_REQUEST);

                if (in.readLine().equals(Config.ACCEPT)) {
                    System.out.println(Config.SERVER + Config.ACCEPT);

                    /**
                     * Initialises a new game.
                     */
                    Game.initGame();
                    Board.printBoard();

                    Random rand = new Random();

                    String serverMove;

                    /**
                     * Randomly chooses whether the client or the server starts.
                     */
                    int start = rand.nextInt(2);

                    /**
                     * If start is 0, the client starts.
                     * This only has to be done on the first round.
                     */
                    if (start == 0) {

                        /**
                         * If the client plays as an AI.
                         */
                        if (Game.aiGame) {
                            Game.aiTurn = true;
                            AI.setBlue();
                        }

                        /**
                         * Plays the client's move and prints the updated board.
                         */
                        Game.playTurn();
                        out.println(Game.currentCoord);

                        /**
                         * If the client quits.
                         */
                        if (Game.currentCoord.equals(Config.QUIT)) {
                            Game.gameComplete = true;
                        }

                        if (!Game.gameComplete) {
                            Board.printBoard();
                        }
                    }

                    /**
                     * If the server plays first.
                     */
                    else {
                        System.out.println(Config.CLIENT + Config.PASS);
                        out.println(Config.PASS);
                    }

                    while (!Game.gameComplete) {
                        serverMove = in.readLine();
                        System.out.println(Config.SERVER + serverMove);

                        /**
                         * If the server wants to quit or the client has won, the game is completed.
                         */
                        if (serverMove.equals(Config.QUIT) || serverMove.equals(Config.WIN_MESSAGE)) {
                            Game.gameComplete = true;
                            break;
                        }

                        /**
                         * Plays the server's move and prints the updated board.
                         */
                        Game.playTurn(serverMove);
                        Board.printBoard();

                        if (Game.gameComplete) {
                            System.out.println(Config.LOSE_MESSAGE);
                            out.println(Config.WIN_MESSAGE);
                            Game.gameComplete = true;
                            break;
                        }

                        /**
                         * Plays the client's move and prints the updated board.
                         */
                        Game.playTurn();
                        out.println(Game.currentCoord);

                        /**
                         * If the client wants to quit.
                         */
                        if (Game.currentCoord.equals(Config.QUIT)) {
                            Game.gameComplete = true;
                            break;
                        }

                        /**
                         * Only prints the board if the client did not quit.
                         */
                        Board.printBoard();

                        if (Game.gameComplete) {
                            System.out.println(Config.SERVER + in.readLine());
                        }
                    }
                }
            }

        } catch (ConnectException e) {

            System.out.println(Config.SERVER_UNAVAILABLE);

            try {
                stdIn.readLine();
                connect();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Connects the user to the server and starts the game.
     */
    public void communicate() {

        try {
            connect();
        } catch (NullPointerException e) {

            System.out.println(Config.LOST_CONNECTION);

            try {
                clientSocket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
