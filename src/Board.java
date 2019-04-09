public class Board {
    private static final char FREE = '#';
    private static final char RED = 'r';
    private static final char BLUE = 'b';
    private static final String BOARD_OFFSET = "  ";
    private static final String HEX_OFFSET = "   ";

    private static final int BOARD_SIZE = 11;


    private static char[][] board;

    public Board() {
        board = new char[BOARD_SIZE][BOARD_SIZE];

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = FREE;
            }
        }
    }

    public static void printBoard() {
        int index1 = 0;
        int index2 = 10;
        int offsetCount = 11;


        System.out.println("This is your Hex board: ");
        System.out.println();

        while (index1 <= 10) {
            System.out.print(BOARD_OFFSET + BOARD_OFFSET);

            for (int j = 0; j < offsetCount; j++) {
                System.out.print(BOARD_OFFSET);
            }


            System.out.print(index1 + BOARD_OFFSET);


            for (int i = 0; i <= index1; i++) {
                System.out.print(board[index1-i][i] + HEX_OFFSET);
            }


            System.out.print(index1);

            System.out.println();

            index1++;
            offsetCount--;
        }

        index1--;
        offsetCount+=2;

        while(index1 >= 0) {
            System.out.print(BOARD_OFFSET + BOARD_OFFSET + " " + BOARD_OFFSET);


            for (int m = 0; m < offsetCount; m++) {
                System.out.print(BOARD_OFFSET);
            }

            for (int k = 1; k <= index1; k++) {
                System.out.print(board[index2 - k + 1][k] + HEX_OFFSET);
            }
            System.out.println();

            index1--;
            offsetCount++;
        }

    }

}
