import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

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
     * @param name   - the name that identifies the computer on the network (server name)
     * @param number - the identifier of the endpoint of the communication
     */
    public Server(String name, int number) {
        hostName = name;
        portNumber = number;
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

                        String clientMove = in.readLine();

                        /**
                         * If the server starts first.
                         */
                        if (clientMove.equals("pass")) {

                            System.out.println("Client: pass");

                            while (!Game.gameComplete) {
                                Game.makeMove();
                                Board.printBoard();
                                out.println(Game.currentCoord);

                                clientMove = in.readLine();
                                System.out.println("Client: " + clientMove);

                                /**
                                 * If the server wins.
                                 */
                                if (clientMove.equals("you-win; bye")) {
                                    Game.gameComplete = true;
                                } else {
                                    Game.makeMove(clientMove);

                                    if (Game.gameComplete) {
                                        System.out.println("Server: you-win; bye");
                                        out.println("you-win; bye");
                                    }
                                }
                            }
                        }

                        /**
                         * If the client starts first.
                         */
                        else {

                            while (!Game.gameComplete) {
                                System.out.println("Client: " + clientMove);
                                Game.makeMove(clientMove);

                                /**
                                 * If the game is complete, the client wins.
                                 */
                                if (Game.gameComplete) {
                                    System.out.println("Server: you-win; bye");
                                    out.println("you-win; bye");
                                } else {

                                    Game.makeMove();
                                    Board.printBoard();
                                    out.println(Game.currentCoord);

                                    clientMove = in.readLine();

                                    /**
                                     * If the server wins.
                                     */
                                    if (clientMove.equals("you-win; bye")) {
                                        System.out.println("Client: " + clientMove);
                                        Game.gameComplete = true;
                                    }
                                }
                            }
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
}
