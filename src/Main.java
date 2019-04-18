import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {

    /**
     * A host is a computer that communicates with other hosts over the network.
     * A host can be a server or a client.
     * The hostName is the computer used by the player.
     */
    static String hostName;
    static String mode;

    /**
     * Port number needs to be between 1025-65535.
     */
    static int portNumber = 6660;

    public static void main(String[] args) {

        mode = Mode.requestMode();

        /**
         * Runs game according to specified mode.
         */
        switch (mode) {
            case Mode.PLAYER_PLAYER: {
                Game.initGame();
                PlayerVsPlayer.runGame();
                break;
            }
            case Mode.PLAYER_NETWORK: {
                /**
                 * Used to find the user's IP address.
                 */
                InetAddress IP;

                try {

                    IP = InetAddress.getLocalHost();

                    /**
                     * Used by the user to see if they are the server or a client.
                     */
                    System.out.println(Config.MY_IP + IP.getHostAddress());

                    hostName = IP.getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }

                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

                /**
                 * Lets the user choose whether they are acting as a client or a server.
                 */
                System.out.println(Config.CHOOSE_MESSAGE);
                String str = "";

                try {

                    str = stdIn.readLine();

                    while (!(str.equals(Config.SERVER_NO) || str.equals(Config.CLIENT_NO))) {
                        System.out.println(Config.TRY_MESSAGE);
                        str = stdIn.readLine();
                    }

                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                /**
                 * If the input string is "1", sets up the server.
                 */
                if (str.equals(Config.SERVER_NO)) {
                    Server server = new Server(hostName, portNumber);
                    server.communicate();
                }

                /**
                 * If the input string is "2", sets up the client.
                 */
                else {

                    /**
                     * Prompts the user to type in the IP address of the computer they are playing against.
                     * In this case, the user is the client and the opponent is the server.
                     */
                    System.out.println(Config.ADDRESS_MESSAGE);

                    try {
                        String ipAddress = getIPAddress();
                        Client client = new Client(ipAddress, portNumber);
                        client.communicate();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            case Mode.PLAYER_AI: {
                PlayerVsAiEasy.initGame();
                PlayerVsAiEasy.runGame();
                break;
            }
            default: {
                System.out.println("Game unable to run.");
                break;
            }

        }
    }

    /**
     * Gets the IP address of the server (in the case that the player is a client).
     *
     * @return - the IP address
     */
    public static String getIPAddress() throws IOException {

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String ipAddress = stdIn.readLine();
        String[] splitIP = ipAddress.split("\\.");

        boolean pass = true;

        /**
         * Checks that the IP address entered has the correct format.
         */
        if (splitIP.length == 4) {

            /**
             * Checks that all elements separated by "." are integers.
             * Otherwise, the input is not a valid IP address.
             */
            for (String element : splitIP) {
                try {
                    Integer.parseInt(element);
                } catch (NumberFormatException e) {
                    pass = false;
                } catch (NullPointerException e) {
                    pass = false;
                }
            }
        } else pass = false;

        /**
         * If the IP address is not valid, prompts the user to enter another IP address.
         */
        if (!pass) {
            System.out.println(Config.INVALID_ADDRESS);
            ipAddress = getIPAddress();
        }

        return ipAddress;
    }
}
