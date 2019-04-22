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
            Mode.playLocalMode(mode);
        }

        /**
         * Network game.
         */
        else {
            mode = Mode.requestModeNetwork(); //can be "1", "2", "3"
            Mode.playNetworkMode(mode);
        }
    }
}
