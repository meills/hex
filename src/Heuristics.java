/**
 * Heuristics used to calculate score for min max.
 */
public class Heuristics {

    /**
     * This heuristic looks at how many choices the option has.
     * The more "neighbours" it has the better.
     */
    private double choices;

    /**
     * Heuristic that looks at how close the piece is to other pieces on the board.
     */
    private double proximity;

    private double space;

    /**
     * Calculates score for choices heuristic.
     * @param coords - coordinates of piece on the board being evaluated;
     * @return - score for choices heuristic
     */
    public static double calcChoices(int[] coords) {
        double score = 0;

        if (coords[0] > 0 && Board.board[coords[0] - 1][coords[1]] == Board.FREE) {
            score++;
        }

        if (coords[0] > 0 && coords[1] < Board.BOARD_SIZE - 1
                && Board.board[coords[0] - 1][ coords[1] + 1] == Board.FREE) {
            score++;
        }

        if (coords[1] < Board.BOARD_SIZE - 1 && Board.board[coords[0]][coords[1] +1] == Board.FREE) {
            score++;
        }

        if (coords[0] < Board.BOARD_SIZE - 1 && Board.board[coords[0] + 1][coords[1]] == Board.FREE) {
            score++;
        }


        if (coords[0] < Board.BOARD_SIZE - 1 && coords[1] > 0
                && Board.board[coords[0] + 1][coords[1] - 1] == Board.FREE) {
            score++;
        }

        if (coords[1] > 0 && Board.board[coords[0]][coords[1] - 1] == Board.FREE) {
            score++;
        }

        return score;
    }

    public static double calcProximity(int[] coords) {
        double score = 0;

        char piece = Board.board[coords[0]][coords[1]];

        return score;
    }

}
