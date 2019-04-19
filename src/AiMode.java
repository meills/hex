/**
 * Parent class for all AI modes.
 */
public class AiMode {
    public static boolean humanTurn;
    public static boolean gameComplete;

    /**
     * Initialises play vs ai mode.
     */
    public static void initGame() {
        humanTurn = true;
        gameComplete = false;
        Board.initBoard();
    }

}
