import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Mode {
    public static final String PLAYER_PLAYER = "1";
    public static final String PLAYER_NETWORK = "2";
    public static final String PLAYER_AI = "3";

    private static String mode;

   /**
    * Prompts for user's preferred game mode.
    * String - number of preferred game mode.
    */
    public static String requestMode() {
        boolean validMode = false;


        System.out.println("Welcome to Hex!");
        System.out.println();
        System.out.println("Game modes:");
        System.out.println("1 - Player vs Player");
        System.out.println("2 - Player vs Network Player");
        System.out.println("3 - Player vs AI");
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
            System.out.println("Invalid mode entered! Please enter '1', '2' or '3'.");
            System.out.println();

            System.out.println("Choose your game mode:");
            stdIn = new BufferedReader(new InputStreamReader(System.in));

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
