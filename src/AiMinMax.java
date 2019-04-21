import java.util.HashSet;
import java.util.Random;

/**
 * This class is the AI that uses min max & alpha beta pruning.
 */
public class AiMinMax {
    private double alpha;
    private double beta;

    public static HashSet<int[]> line;

    /**
     * Main method created here to allow for debugging of individual class
     * can be deleted later
     */
    public static void main(String[] args) {

    }

    public static void setLine() {
        Random rand = new Random();
        int[] coords;
        int row;
        line = new HashSet<>();
        row = rand.nextInt(Board.BOARD_SIZE);

        //sets coords for edge piece to generate jagged line from


        /**
         * Generates a random line.
         */
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            coords = new int[2];
            coords[0] = row;
            coords[1] = i;
            line.add(coords);
        }
    }


}
