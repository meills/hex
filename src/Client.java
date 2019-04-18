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
                }

                /**
                 * Initialises a new game.
                 */
                Game.initGame();
                Board.printBoard();

                Random rand = new Random();

                String serverMove;

                int start = rand.nextInt(2);

                if (start == 0) {
                    Game.playTurn();
                    out.println(Game.currentCoord);

                    if (Game.currentCoord.equals(Config.QUIT)) {
                        Game.gameComplete = true;
                    }

                    if (!Game.gameComplete) {
                        Board.printBoard();
                    }
                } else {
                    System.out.println(Config.CLIENT + Config.PASS);
                    out.println(Config.PASS);
                }

                while (!Game.gameComplete) {
                    serverMove = in.readLine();
                    System.out.println(Config.SERVER + serverMove);

                    if (serverMove.equals(Config.QUIT) || serverMove.equals(Config.WIN_MESSAGE)) {
                        Game.gameComplete = true;
                        break;
                    }

                    Game.playTurn(serverMove);
                    Board.printBoard();

                    if (Game.gameComplete) {
                        System.out.println(Config.LOSE_MESSAGE);
                        out.println(Config.WIN_MESSAGE);
                        Game.gameComplete = true;
                        break;
                    }

                    Game.playTurn();
                    out.println(Game.currentCoord);

                    if (Game.currentCoord.equals(Config.QUIT)) {
                        Game.gameComplete = true;
                        break;
                    }

                    Board.printBoard();

                    if (Game.gameComplete) {
                        System.out.println(Config.SERVER + in.readLine());
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
