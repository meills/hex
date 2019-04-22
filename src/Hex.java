import java.util.Scanner;

public class Hex {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("\nWelcome to Hex!\n");
        System.out.println("Enter 1 for local game, 2 for network.");

        String input = sc.nextLine();

        while (!input.equals("1") && !input.equals("2")) {
            System.out.println("Invalid input. Enter 1 for local game, 2 for network. Please re-enter choice.");
            input = sc.nextLine();
        }

        String mode;

        /**
         * Local game.
         */
        if (input.equals("1")) {
            mode = Mode.requestModeLocal(); //can be "1", "2", "3", "4"

            switch (mode) {

                case "1": {
                    Game.initGame();
                    Board.printBoard();

                    while (!Game.gameComplete) {
                        Game.playTurn();
                        Board.printBoard();
                    }

                    if (Game.blueTurn) {
                        System.out.println("Red wins!");
                    } else {
                        System.out.println("Blue wins!");
                    }

                    break;
                }

                case "2": {
                    AI.mode = mode;
                    Game.aiGame = true;
                    Game.initGame();
                    Board.printBoard();

                    while (!Game.gameComplete) {
                        Game.playTurn();
                        Board.printBoard();
                    }

                    if (Game.blueTurn) {
                        System.out.println("Red wins!");
                    } else {
                        System.out.println("Blue wins!");
                    }

                    break;
                }

                case "3": {
                    AI.mode = mode;
                    Game.aiGame = true;
                    AiLine.setLine();
                    Game.initGame();
                    Board.printBoard();

                    while (!Game.gameComplete) {
                        Game.playTurn();
                        Board.printBoard();
                    }

                    if (Game.blueTurn) {
                        System.out.println("Red wins!");
                    } else {
                        System.out.println("Blue wins!");
                    }

                    break;
                }

                case "4": {
                    AI.mode = mode;
                    Game.aiGame = true;
                    AiLineImproved.setLine();
                    Game.initGame();
                    Board.printBoard();

                    while (!Game.gameComplete) {
                        Game.playTurn();
                        Board.printBoard();
                    }

                    if (Game.blueTurn) {
                        System.out.println("Red wins!");
                    } else {
                        System.out.println("Blue wins!");
                    }
                }
            }
        }

        /**
         * Network game.
         */
        else {
            mode = Mode.requestModeNetwork(); //can be "1", "2", "3"

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
}
