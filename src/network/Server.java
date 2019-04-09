package network;

import java.io.*;
import java.net.*;

public class Server {

    ServerSocket serverSocket = null;
    Socket clientSocket = null;
    String hostName;
    int portNumber;
    BufferedReader in;
    BufferedReader stdIn;
    PrintWriter out;

    /**
     * Constructir for the server.
     * 
     * @param hostName - the name that identifies the computer on the network (server name)
     * @param portNumber - the identifier of the endpoint of the communication
     */
    public Server(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
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

    public void connect() {

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

            /**
             * Starts communication with the client.
             */
            out.println("hello");

            /**
             * Checks that the client is using the same protocols.
             */
            if (in.readLine().equals("hello")) {
                System.out.println("Client says hello");
            }

        } catch (socketException e) {
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
