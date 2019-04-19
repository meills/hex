import java.util.Random;
import java.util.Scanner;

/**
 * A game mode where a human player plays against an AI that chooses completely random pieces on the Hex board.
 */
public class AiRandom extends AiMode{

    /**
     * Runs player vs random ai mode.
     */
    public static void runGame() {

        System.out.println("Player vs AI mode selected!");
        System.out.println();

        while (!gameComplete) {
            if (humanTurn) {
                humanMove();
            } else {
                aiMove();
            }

            gameComplete = Validator.validateWin();
        }

    }

    /**
     * Signals human move and updates board accordingly.
     */
    public static void humanMove() {
        Scanner input = new Scanner(System.in);
        int[] move;

        while (humanTurn) {
            System.out.println("Your turn.");
            Board.printBoard();
            System.out.println("Make your move (enter hex coordinates):");

            move = Game.parseCoor(input.nextLine());

            if (move[0] == -1) {
                System.out.println(Config.INVALID_MOVE);
                System.out.println();
                break;
            }

            System.out.println();
            Board.updateBoardRand(move);
        }
    }

    /**
     * Carry out AI's move and updates board accordingly.
     */
    public static void aiMove() {
        System.out.println(Config.AI_MOVE);
        System.out.println();
        Random random = new Random();
        int[] move = new int[2];

        while (!humanTurn) {
            move[0] = random.nextInt(Board.BOARD_SIZE);
            move[1] = random.nextInt(Board.BOARD_SIZE);
            Board.updateBoardRand(move);
        }
    }
}
