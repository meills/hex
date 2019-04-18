import java.util.Scanner;

/**
 * Contains methods for running the game in player vs player mode.
 */
public class PlayerVsPlayer {
    /**
     * Method that runs player vs player mode.
     */
    public static void runGame() {

        System.out.println("Player vs player mode selected.");
        System.out.println();

        while (!Game.gameComplete) {
            if (Game.blueTurn) {
                blueMove();
            } else {
                redMove();
            }
            Game.gameComplete = Validator.validateWin();
        }
    }


    /**
     * Calls for blue players move and updates game accordingly.
     */
    public static void blueMove() {
        Scanner input = new Scanner(System.in);
        int[] move;

        while (Game.blueTurn) {
            System.out.println("Blue player's turn.");
            Board.printBoard();
            System.out.println("Make your move (enter hex coordinates):");

            move = Game.parseCoor(input.nextLine());

            if (move[0] == -1) {
                break;
            }

            System.out.println();
            Board.updateBoard(move);
        }
    }

    /**
     * Calls for red players move and updates game accordingly.
     */
    public static void redMove() {
        Scanner input = new Scanner(System.in);
        int[] move;

        while (!Game.blueTurn) {
            System.out.println("Red player's turn.");
            Board.printBoard();
            System.out.println("Make your move (enter hex coordinates):");

            move = Game.parseCoor(input.nextLine());

            if (move[0] == -1) {
                break;
            }

            System.out.println();
            Board.updateBoard(move);
        }
    }
}
