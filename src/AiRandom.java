import java.util.Random;

/**
 * A game mode where a human player plays against an AI that chooses completely random pieces on the Hex board.
 */
public class AiRandom {

    /**
     * Carry out AI's move and updates board accordingly.
     */
    public static String makeMove() {

        int[] move = chooseCoord();

        while(Board.board[move[0]][move[1]] != Board.FREE) {
            move = chooseCoord();
        }

        return ("(" + move[0] + "," + move[1] + ");");
    }

    private static int[] chooseCoord() {
        Random random = new Random();
        int[] move = new int[2];
        move[0] = random.nextInt(Board.BOARD_SIZE);
        move[1] = random.nextInt(Board.BOARD_SIZE);
        return move;
    }
}
