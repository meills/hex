import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {

    public static ServerSocket serverSocket;
    public static Socket clientSocket;
    public static String hostName;
    public static int portNumber;
    public static BufferedReader in;
    public static BufferedReader stdIn;
    public static PrintWriter out;

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
            System.out.println("\n Waiting for client...");

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

                    out.println("ready");
                    System.out.println("Server: ready");

                    /**
                     * Initialises a new game.
                     */
                    Game.initGame();

                    String clientMove = in.readLine();

                    /**
                     * If the server starts first.
                     */
                    if (clientMove.equals("pass")) {

                        System.out.println("Client: " + clientMove);

                        while (!Game.gameComplete) {
                            Game.makeMove();
                            out.println(Game.currentCoord);

                            if (!Game.currentCoord.equals("quit")) {
                                Board.printBoard();

                                clientMove = in.readLine();
                                System.out.println("Client: " + clientMove);

                                /**
                                 * If the client quits.
                                 */
                                if (clientMove.equals("quit")) {
                                    clientSocket.close();
                                    Game.gameComplete = true;
                                    break;
                                }

                                /**
                                 * If the server wins.
                                 */
                                if (clientMove.equals("you-win; bye")) {
                                    serverSocket.close();
                                    Game.gameComplete = true;
                                    break;
                                } else {
                                    Game.makeMove(clientMove);

                                    if (Game.gameComplete) {
                                        Board.printBoard();
                                        System.out.println("you-win; bye");
                                        out.println("you-win; bye");
                                        clientSocket.close();
                                        Game.gameComplete = true;
                                        break;
                                    }
                                }
                            } else {
                                clientSocket.close();
                                Game.gameComplete = true;
                                break;
                            }
                        }
                    }

                    /**
                     * If the client starts first.
                     */
                    else {

                        while (!Game.gameComplete) {
                            System.out.println("Client: " + clientMove);

                            /**
                             * If the client quits.
                             */
                            if (clientMove.equals("quit")) {
                                clientSocket.close();
                                Game.gameComplete = true;
                                break;
                            }

                            Game.makeMove(clientMove);

                            /**
                             * If the game is complete, the client wins.
                             */
                            if (Game.gameComplete) {
                                System.out.println("you-win; bye");
                                out.println("you-win; bye");
                                clientSocket.close();
                                Game.gameComplete = true;
                                break;
                            } else {

                                Game.makeMove();
                                out.println(Game.currentCoord);

                                if (!Game.currentCoord.equals("quit")) {
                                    Board.printBoard();

                                    clientMove = in.readLine();

                                    /**
                                     * If the server wins.
                                     */
                                    if (clientMove.equals("you-win; bye")) {
                                        Board.printBoard();
                                        System.out.println("Client: " + clientMove);
                                        clientSocket.close();
                                        Game.gameComplete = true;
                                        break;
                                    }
                                } else {
                                    clientSocket.close();
                                    Game.gameComplete = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                Game.gameComplete = false;
                serverSocket.close();
                connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Connects the user to the client and starts the game.
     */
    public void communicate() {

        try {
            connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}