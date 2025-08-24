// Main.java
public class Main {
    public static void main(String[] args) {
        ChessGameEngine game = new ChessGameEngine();

        // Example move: white pawn from (6,0) to (4,0)
        if (game.makeMove(6, 0, 4, 0)) {
            System.out.println("Move successful!");
        } else {
            System.out.println("Invalid move!");
        }

        // Print board
        char[][] board = game.getBoard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}

