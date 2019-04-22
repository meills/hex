import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Mode {

    private static String mode;

    private static String requestMode(String modes) {
        boolean validMode = false;

        System.out.println(modes);

        System.out.println(Config.CHOOSE_MODE);
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        try {
            mode = stdIn.readLine().replaceAll("\\s+", "");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (mode.matches("[1-4]")) {
            validMode = true;
        }

        while (!validMode) {
            System.out.println(Config.INVALID_MODE_DIGIT);
            System.out.println();

            System.out.println(Config.CHOOSE_MODE);
            stdIn = new BufferedReader(new InputStreamReader(System.in));
            System.out.println();

            try {
                mode = stdIn.readLine().replaceAll("\\s+", "");
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (mode.matches("[1-4]")) {
                validMode = true;
            }
        }

        return mode;
    }

    /**
     * Prompts for user's preferred game mode.
     * String - number of preferred game mode.
     */
    public static String requestModeLocal() {
        return requestMode(Config.LOCAL_MODES);
    }

    public static String requestModeNetwork() {
        return requestMode(Config.NETWORK_MODES);
    }

    public static void playLocalMode(String mode) {
        switch (mode) {

            case "1": {
                Game.initGame();
                Board.printBoard();

                playGame();

                break;
            }

            case "2": {
                AI.mode = mode;
                Game.aiGame = true;
                Game.initGame();
                Board.printBoard();

                playGame();

                break;
            }

            case "3": {
                AI.mode = mode;
                Game.aiGame = true;
                AiLine.setLine();
                Game.initGame();
                Board.printBoard();

                playGame();

                break;
            }

            case "4": {
                AI.mode = mode;
                Game.aiGame = true;
                AiLineImproved.setLine();
                Game.initGame();
                Board.printBoard();

                playGame();
            }
        }
    }

    private static void playGame() {
        while (!Game.gameComplete) {
            Game.playTurn();
            Board.printBoard();
        }

        if (Game.blueTurn) {
            System.out.println(Config.RED_WIN);
        } else {
            System.out.println(Config.BLUE_WIN);
        }
    }

    public static void playNetworkMode(String mode) {

        Game.networkGame = true;

        switch (mode) {

            case "1": {
                Networking.connect();
                break;
            }

            case "2": {
                AI.mode = mode;
                Game.aiGame = true;
                Networking.connect();
                break;
            }

            case "3": {
                AI.mode = mode;
                Game.aiGame = true;
                AiLine.setLine();
                Networking.connect();
                break;
            }

            case "4": {
                AI.mode = mode;
                Game.aiGame = true;
                AiLineImproved.setLine();
                Networking.connect();
            }
        }
    }
}
