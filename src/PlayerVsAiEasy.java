import java.util.Random;
import java.util.Scanner;

/**
 * A game mode where a human player plays against an AI that chooses completely random pieces on the Hex board.
 */
public class PlayerVsAiEasy {
    public static boolean humanTurn;
    public static boolean gameComplete;


    /**
     * Initialises play vs random ai mode.
     */
    public static void initGame() {
        humanTurn = true;
        gameComplete = false;
        Board.initBoard();
    }

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
        System.out.println("AI making its move...");
        Random random = new Random();
        int[] move = new int[2];

        while (!humanTurn) {
            move[0] = random.nextInt(11);
            move[1] = random.nextInt(11);
            Board.updateBoardRand(move);
        }
    }




}
