public abstract class Config {

    public static final String MY_IP = "IP of my system is : ";
    public static final String SERVER_NO = "1";
    public static final String CLIENT_NO = "2";
    public static final String CHOOSE_MESSAGE = "Type " + SERVER_NO + " for a server. Type " + CLIENT_NO +" for a client.";
    public static final String TRY_MESSAGE = "Try again.";
    public static final String ADDRESS_MESSAGE = "Please type in the IP Address that you want to compete against.";
    public static final String INVALID_ADDRESS = "Not a valid IP address. Try again";

    public static final String CLIENT = "Client: ";
    public static final String SERVER = "Server: ";

    public static final String CLIENT_FOUND = "Client found.";
    public static final String WAITING = "\nWaiting for client...";

    public static final String GREETING= "hello";
    public static final String GAME_REQUEST = "newgame";
    public static final String ACCEPT = "ready";
    public static final String REJECT = "reject";

    public static final String PASS = "pass";
    public static final String QUIT = "quit";
    public static final String WIN_MESSAGE = "you-win; bye";
    public static final String LOSE_MESSAGE = "Game lost.";

    public static final String SERVER_UNAVAILABLE = "There was no server available. Please enter to try again";
    public static final String LOST_CONNECTION = "\nLost connection with the server.";

    public static final String MAKE_MOVE = "\nMake your move (enter hex coordinates):";
    public static final String INVALID_MOVE = "\nInvalid input! Please enter coordinates in the format " +
            "\"(<number between 0 and " + (Board.BOARD_SIZE - 1) +">,<number between 0 and " + (Board.BOARD_SIZE - 1) + ">);\"";
    public static final String TILE_OCCUPIED = "\nInvalid move! Hex tile is already occupied.";
}
