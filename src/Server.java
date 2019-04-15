import java.io.*;
import java.net.*;

public class Server {

    public static ServerSocket serverSocket = null;
    public static Socket clientSocket = null;
    public static String hostName;
    public static int portNumber;
    public static BufferedReader in;
    public static BufferedReader stdIn;
    public static PrintWriter out;

    /**
     * To check whether a new game can be initialised.
     */
    private static boolean gameInProgress = false;

    /**
     * Constructir for the server.
     * 
     * @param hostName - the name that identifies the computer on the network (server name)
     * @param portNumber - the identifier of the endpoint of the communication
     */
    public Server(String name, int number) {
        hostName = name;
        portNumber = number;
    }

    /**
     * Connects the user to the client and strts the game.
     */
    public void communicate() {

        try {
            connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void connect() {

        try {

            serverSocket = new ServerSocket(portNumber);
            System.out.println("Waiting for client...");

            /**
             * ".accept()" waits for an incoming client to connect to the server on the specified port.
             */
            clientSocket = serverSocket.accept();
            System.out.println("Client found.");

            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            stdIn = new BufferedReader(new InputStreamReader(System.in));

            if (in.readLine().equals("hello")) {
                System.out.println("Client: hello");
                out.println("hello");
                System.out.println("Server: hello");

                /**
                 * Server should respond "ready" or "reject".
                 */
                if (in.readLine().equals("newgame")) {

                    System.out.println("Client: newgame");

                    if (!gameInProgress) {
                        out.println("ready");
                        System.out.println("Server: ready");

                        /**
                         * Initialises a new game.
                         */
                        Game.initGame();

                        gameInProgress = true;

                        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                        // need to adjust it
                        // to switch between the server and the client at each turn
                        // right now, the client doesn't do anything, all happens in the server
                        while (!Game.gameComplete) {
                            Game.makeMove();
                            out.println(Game.currentCoord);
                            System.out.println("Server: " + Game.currentCoord);
                        }

                        if (Game.gameComplete) {
                            out.println("you-win; bye");
                            System.out.println("you-win; bye");
                            serverSocket.close();
                        }
                    }

                    // Haven't checked yet that this one works as expected.
                    else {
                        out.println("reject");
                        serverSocket.close();
                    }
                }
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
