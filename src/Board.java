public class Board {
    private static final char FREE = '#';
    private static final char RED = 'r';
    private static final char BLUE = 'b';

    private static final String OFFSET_ONE = " ";
    private static final String OFFSET_TWO = "  ";
    private static final String OFFSET_THREE = "   ";

    private static final int BOARD_SIZE = 11;


    private static char[][] board;

    public static void initBoard() {
        board = new char[BOARD_SIZE][BOARD_SIZE];

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = FREE;
            }
        }
    }

    public static void debugBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; i < BOARD_SIZE; j++) {
                System.out.println(board[i][j]);
            }
        }
    }

    public static void printBoard() {
        int coor = 0;
        int index1 = 0;
        int index2 = 10;
        int offsetCount = 11;



        System.out.println("This is your Hex board: ");
        System.out.println();

        for (int n = 0; n < offsetCount - 1; n++) {
            System.out.print(OFFSET_THREE);
        }

        System.out.print(coor + OFFSET_THREE + coor);
        System.out.println();

        coor++;

        while (index1 <= index2 -1) {
            System.out.print(OFFSET_THREE + OFFSET_THREE);


            for (int j = 0; j < offsetCount; j++) {
                System.out.print(OFFSET_TWO);
            }

            if (coor < 10) {
                System.out.print(coor + OFFSET_THREE);
            } else {
                System.out.print(coor + OFFSET_TWO);
            }



            for (int i = 0; i <= index1; i++) {
                if (i < 10) {
                    System.out.print(board[index1-i][i] + OFFSET_THREE);
                } else {
                    System.out.print(board[index1-i][i] + OFFSET_TWO);
                }
            }


            System.out.print(coor);

            System.out.println();

            coor++;
            index1++;
            offsetCount--;
        }

        offsetCount+=2;

        while(index1 >= 0) {
            System.out.print(OFFSET_THREE + OFFSET_THREE);


            for (int m = 0; m < offsetCount; m++) {
                System.out.print(OFFSET_TWO);
            }

            for (int k = 0; k <= index1; k++) {
                System.out.print(board[index2 - k][k] + OFFSET_THREE);
            }
            System.out.println();

            index1--;
            offsetCount++;
        }

        System.out.println();
    }

    public static void updateBoard(int[] coor) {
        if (board[coor[0]][coor[1]] == FREE) {
            if (Game.blueTurn) {
                board[coor[0]][coor[1]] = BLUE;
                Game.blueTurn = false;
            } else {
                board[coor[0]][coor[1]] = RED;
                Game.blueTurn = true;
            }
        } else {
            System.out.println("Invalid move! Hex tile is already occupied.");
        }
    }

}
