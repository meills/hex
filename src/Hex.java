import java.util.Scanner;

public class Hex {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println(Config.MODE);

        String input = sc.nextLine();

        while (!input.equals("1") && !input.equals("2")) {
            System.out.println(Config.INVALID_MODE);
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
