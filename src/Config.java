public abstract class Config {

    public static final String MY_IP = "\nIP of my system is : ";
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

    public static final String PASS = "pass";
    public static final String QUIT = "quit";
    public static final String WIN_MESSAGE = "you-win; bye";
    public static final String LOSE_MESSAGE = "Game lost.";

    public static final String SERVER_UNAVAILABLE = "There was no server available. Please enter to try again";
    public static final String LOST_CONNECTION = "\nLost connection with the server.";

    public static final String MAKE_MOVE = "Make your move (enter hex coordinates):";
    public static final String INVALID_MOVE = "\nInvalid input! Please enter coordinates in the format " +
            "\"(<number between 0 and " + (Board.BOARD_SIZE - 1) +">,<number between 0 and " + (Board.BOARD_SIZE - 1) + ">);\"";
    public static final String TILE_OCCUPIED = "\nInvalid move! Hex tile is already occupied.";

    public static final String MODE = "\nWelcome to Hex!\nEnter 1 for local game, 2 for network.";
    public static final String INVALID_MODE = "\"Invalid input. Enter 1 for local game, 2 for network. Please re-enter choice.\"";
    public static final String INVALID_MODE_DIGIT = "Invalid mode entered! Please enter a digit from 1-4.";
    public static final String CHOOSE_MODE = "Choose your game mode:";

    public static final String BLUE = "Blue Player - ";
    public static final String RED = "Red Player - ";
    public static final String AI_MOVE = "AI's move: ";

    public static final String BLUE_WIN = "Blue wins!";
    public static final String RED_WIN = "Red wins!";

    public static final String LOCAL_MODES = "\nGame modes:\n1 - Player vs Player\n2 - Player vs AI (Random)\n3 - Player vs AI (Line)\n4 - Player vs AI (Improved Line)\n";
    public static final String NETWORK_MODES = "\nGame modes:\n1 - Play as human player\n2 - Play as random AI\n3 - Play as line AI\n4 - Play as improved line AI\n";
}
