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

                Random rand = new Random();

                int start = rand.nextInt(2);

                /**
                 * If the server starts first.
                 */
                if (start == 0) {

                    out.println("pass");
                    System.out.println("pass");

                    while (!Game.gameComplete) {
                        String serverMove = in.readLine();
                        System.out.println("Server: " + serverMove);

                        /**
                         * If the server quits.
                         */
                        if (serverMove.equals("quit")) {
                            clientSocket.close();
                            Game.gameComplete = true;
                            break;
                        }

                        /**
                         * If the client wins.
                         */
                        else if (serverMove.equals("you-win; bye")) {
                            System.out.println("bye");
                            out.println("bye");
                            Game.gameComplete = true;
                            clientSocket.close();
                        }

                        else {

                            Game.makeMove(serverMove);

                            if (Game.gameComplete) {
                                Board.printBoard();
                                System.out.println("you-win; bye");
                                out.println("you-win; bye");
                                serverMove = in.readLine();
                                System.out.println("Server: " + serverMove);
                            } else {
                                Game.makeMove();
                                out.println(Game.currentCoord);

                                if (!Game.currentCoord.equals("quit")) {
                                    Board.printBoard();
                                } else {
                                    Game.gameComplete = true;
                                    clientSocket.close();
                                }
                            }
                        }
                    }
                }

                /**
                 * If the client starts first.
                 */
                else {

                    while (!Game.gameComplete) {
                        Game.makeMove();
                        out.println(Game.currentCoord);

                        if (!Game.currentCoord.equals("quit")) {

                            Board.printBoard();

                            String serverMove = in.readLine();
                            System.out.println("Server: " + serverMove);

                            /**
                             * If the server quits.
                             */
                            if (serverMove.equals("quit")) {
                                clientSocket.close();
                                Game.gameComplete = true;
                                break;
                            }


                            /**
                             * If the client wins.
                             */
                            else if (serverMove.equals("you-win; bye")) {
                                System.out.println("bye");
                                out.println("bye");
                                Game.gameComplete = true;
                                clientSocket.close();
                            }

                            else {
                                Game.makeMove(serverMove);

                                if (Game.gameComplete) {
                                    Board.printBoard();
                                    System.out.println("you-win; bye");
                                    out.println("you-win; bye");
                                    serverMove = in.readLine();
                                    System.out.println("Server: " + serverMove);
                                    clientSocket.close();
                                }
                            }
                        } else {
                            Game.gameComplete = true;
                            clientSocket.close();
                        }
                    }
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
}
