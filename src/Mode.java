import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Mode {
    public static final String PLAYER_PLAYER = "1";
    public static final String PLAYER_NETWORK = "2";
    public static final String PLAYER_AI_RAND = "3";
    public static final String PLAYER_AI_LINE = "4";


    private static String mode;

   /**
    * Prompts for user's preferred game mode.
    * String - number of preferred game mode.
    */
    public static String requestModeLocal() {
        boolean validMode = false;

        System.out.println("Game modes:");
        System.out.println("1 - Player vs Player");
        System.out.println("2 - Player vs AI (Random)");
        System.out.println("3 - Player vs AI (Line)");
        //need to add ai vs ai
        System.out.println();

        System.out.println("Choose your game mode:");
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        try {
            mode = stdIn.readLine().replaceAll("\\s+", "");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (mode.matches("[1-3]")) {
            validMode = true;
        }

        while (!validMode) {
            System.out.println("Invalid mode entered! Please enter a digit from 1-4.");
            System.out.println();

            System.out.println("Choose your game mode:");
            stdIn = new BufferedReader(new InputStreamReader(System.in));
            System.out.println();

            try {
                mode = stdIn.readLine().replaceAll("\\s+", "");
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (mode.matches("[1-3]")) {
                validMode = true;
            }
        }

        return mode;
    }

    public static String requestModeNetwork() {
        boolean validMode = false;

        System.out.println("Game modes:");
        System.out.println("1 - Play as human player");
        System.out.println("2 - Play as random AI");
        System.out.println("3 - Play as line AI");
        System.out.println();

        System.out.println("Choose your game mode:");
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        try {
            mode = stdIn.readLine().replaceAll("\\s+", "");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (mode.matches("[1-3]")) {
            validMode = true;
        }

        while (!validMode) {
            System.out.println("Invalid mode entered! Please enter a digit from 1-4.");
            System.out.println();

            System.out.println("Choose your game mode:");
            stdIn = new BufferedReader(new InputStreamReader(System.in));
            System.out.println();

            try {
                mode = stdIn.readLine().replaceAll("\\s+", "");
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (mode.matches("[1-3]")) {
                validMode = true;
            }
        }

        return mode;
    }
}
