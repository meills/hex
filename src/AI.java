/**
 * Parent class for all AI modes.
 */
public class AI {

    /**
     * "2" for random
     * "3" for line
     */
    public static String mode;

    public static String getCoords() {
        if (mode.equals("2")) {
            return AiRandom.makeMove();
        }
        return AiLine.makeMove();
    }
}
