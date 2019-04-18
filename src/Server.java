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
            System.out.println(Config.WAITING);

            /**
             * ".accept()" waits for an incoming client to connect to the server on the specified port.
             */
            clientSocket = serverSocket.accept();
            System.out.println(Config.CLIENT_FOUND);

            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            stdIn = new BufferedReader(new InputStreamReader(System.in));

            if (in.readLine().equals(Config.GREETING)) {
                System.out.println(Config.CLIENT + Config.GREETING);
                out.println(Config.GREETING);
                System.out.println(Config.SERVER + Config.GREETING);

                if (in.readLine().equals(Config.GAME_REQUEST)) {

                    System.out.println(Config.CLIENT + Config.GAME_REQUEST);

                    out.println(Config.ACCEPT);
                    System.out.println(Config.SERVER + Config.ACCEPT);

                    /**
                     * Initialises a new game.
                     */
                    Game.initGame();
                    Board.printBoard();

                    String clientMove;

                    while (!Game.gameComplete) {
                        clientMove = in.readLine();
                        System.out.println(Config.CLIENT + clientMove);

                        if (!clientMove.equals(Config.PASS)) {

                            if (clientMove.equals(Config.QUIT) || clientMove.equals(Config.WIN_MESSAGE)) {
                                clientSocket.close();
                                Game.gameComplete = true;
                                break;
                            }

                            Game.playTurn(clientMove);
                            Board.printBoard();

                            if (Game.gameComplete) {
                                System.out.println(Config.LOSE_MESSAGE);
                                out.println(Config.WIN_MESSAGE);
                                clientSocket.close();
                                Game.gameComplete = true;
                                break;
                            }
                        }

                        Game.playTurn();
                        out.println(Game.currentCoord);

                        if (Game.currentCoord.equals(Config.QUIT)) {
                            clientSocket.close();
                            Game.gameComplete = true;
                            break;
                        }

                        Board.printBoard();

                        if (Game.gameComplete) {
                            System.out.println(Config.CLIENT + in.readLine());
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

                /**
                 * After a game is finished, the server is waiting for another client to play.
                 */
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
