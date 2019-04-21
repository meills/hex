/**
 * Parent class for all AI modes.
 */
public class AI {

    /**
     * "2" for random
     * "3" for line
     * "4" for improved line
     */
    public static String mode;

    public static String getCoords() {
        if (mode.equals("2")) {
            return AiRandom.makeMove();
        }
        if (mode.equals("3")) {
            return AiLine.makeMove();
        }
        return AiLineImproved.makeMove();
    }

    public static void setBlue() {

        switch (mode) {
            case "3": {
                AiLine.bluePlayer = true;
                AiLine.setLine();
                break;
            }

            case "4": {
                AiLineImproved.bluePlayer = true;
                AiLineImproved.setLine();
            }
        }
    }
}
