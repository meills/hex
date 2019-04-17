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

    /**
     * Port number needs to be between 1025-65535.
     */
    static int portNumber = 8000;

    public static void main(String[] args) {

        /**
         * Used to find the user's IP address.
         */
        InetAddress IP;

        try {

            IP = InetAddress.getLocalHost();

            /**
             * Used by the user to see if they are the server or a client.
             */
            System.out.println("IP of my system is := " + IP.getHostAddress());

            hostName = IP.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        /**
         * Lets the user choose whether they are acting as a client or a server.
         */
        System.out.println("Type 1 for a server. Type 2 for a client.");
        String str = "";

        try {

            str = stdIn.readLine();

            while (!(str.equals("1") || str.equals("2"))) {
                System.out.println("Try again");
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
        if (str.equals("1")) {
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
            System.out.println("Please type in the IP Address that you want to compete against.");

            try {
                String ipAddress = getIPAddress();
                Client client = new Client(ipAddress, portNumber);
                client.communicate();
            } catch (IOException e) {
                e.printStackTrace();
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
            System.out.println("Not a valid IP address. Try again");
            ipAddress = getIPAddress();
        }

        return ipAddress;
    }
}
