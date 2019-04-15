import java.io.*;
import java.net.*;

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

    /**
     * Connects the user to the server and starts the game.
     */
    public void communicate() {

        try {
            connect();
        } catch (NullPointerException e) {
            
            System.out.println("\n Lost connection with the server.");

            try {
                clientSocket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
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
            out.println("hello");
            System.out.println("Client: hello");

            /**
             * Checks that the server is using the same protocols.
             */
            if (in.readLine().equals("hello")) {
                System.out.println("Server: hello");

                /**
                 * Requests a new game. Server can respond "ready" or "reject".
                 */
                out.println("newgame");
                System.out.println("Client: newgame");

                if (in.readLine().equals("ready")) {
                    System.out.println("Server: ready");
                }

                /**
                 * Initialises a new game.
                 */
                Game.initGame();

                Board.printBoard();

                while (clientSocket.isConnected()) {
                    String serverMove = in.readLine();
                    System.out.println("Server: " + serverMove);
                    Game.makeMove(serverMove);
                    Game.makeMove();
                    Board.printBoard();
                    out.println(Game.currentCoord);
                }
            }

        } catch (ConnectException e) {
           
            System.out.println("There was no server available. Please enter to try again");

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
}
