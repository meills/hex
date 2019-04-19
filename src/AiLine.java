import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Random;

/**
 * A game mode where a human player plays against an AI that tries to complete a line across the board.
 */
public class AiLine extends AiMode{
    public static ArrayList<int[]> line;

    /**
     * This method overrides AiMode's initGame() method.
     * Coordinates of a line across the board is generated as a guideline for the AI.
     * @Override
     */
    public static void initGame() {
        AiMode.initGame();
        Random rand = new Random();
        int[] coords = new int[2];

        Board.initBoard();
        line = new ArrayList<>();
        coords[0] = rand.nextInt(Board.BOARD_SIZE);

        /**
         * Generates a random line.
         */
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            coords[1] = i;
            line.add(coords);
        }

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
        /**
         * Signals human move and updates board accordingly.
         */

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
        Random rand = new Random();
        int index;

        index = rand.nextInt(line.size());
        System.out.println(line.size() + " " + index);

        //System.out.println(line.get(index)[0] + " " + line.get(index)[1]);
        //System.out.println(index);
        Board.updateBoardLine(line.get(index));
    }
}
