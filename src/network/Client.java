package network;

import java.io.*;
import java.net.*;

public class Client {

    Socket clientSocket;
    String hostName;
    int portNumber;
    BufferedReader in;
    BufferedReader stdIn;
    PrintWriter out;

    /**
     * Constructor for the client.
     */
    public Client(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
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

    public void connect() throws NullPointerException {

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

            if (in.readLine().equals("hello")) {
                System.out.println("Server says hello");
                out.println("hello");
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
