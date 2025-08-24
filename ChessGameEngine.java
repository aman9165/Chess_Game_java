import java.util.*;

public class ChessGameEngine {
    private char[][] board;
    private boolean isWhiteTurn;
    private boolean[] castlingRights; // [whiteKingSide, whiteQueenSide, blackKingSide, blackQueenSide]
    private int[] enPassantSquare; // [row, col] of square where en passant capture is possible
    
    public ChessGameEngine() {
        initializeBoard();
        isWhiteTurn = true;
        castlingRights = new boolean[]{true, true, true, true};
        enPassantSquare = null;
    }
    
    private void initializeBoard() {
        board = new char[8][8];
        // Black pieces
        board[0] = new char[]{'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r'};
        Arrays.fill(board[1], 'p');
        // Empty squares
        for (int i = 2; i < 6; i++) {
            Arrays.fill(board[i], ' ');
        }
        // White pieces
        Arrays.fill(board[6], 'P');
        board[7] = new char[]{'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'};
    }

    public boolean makeMove(int startRow, int startCol, int endRow, int endCol) {
        if (!isValidMove(startRow, startCol, endRow, endCol)) {
            return false;
        }
        
        char piece = board[startRow][startCol];
        char capturedPiece = board[endRow][endCol];
        
        // Make the move
        board[endRow][endCol] = piece;
        board[startRow][startCol] = ' ';
        
        // Handle special moves
        handleSpecialMoves(piece, startRow, startCol, endRow, endCol);
        
        // Check if the move puts the current player in check
        if (isInCheck(isWhiteTurn)) {
            // Undo the move
            board[startRow][startCol] = piece;
            board[endRow][endCol] = capturedPiece;
            return false;
        }
        
        // Update game state
        isWhiteTurn = !isWhiteTurn;
        return true;
    }

    private boolean isValidMove(int startRow, int startCol, int endRow, int endCol) {
        if (!isValidPosition(startRow, startCol) || !isValidPosition(endRow, endCol)) {
            return false;
        }
        
        char piece = board[startRow][startCol];
        boolean isWhitePiece = Character.isUpperCase(piece);
        
        if (isWhiteTurn != isWhitePiece) {
            return false;
        }
        
        // TODO: implement piece-specific logic
        return true;
    }

    private String getPieceSymbol(char piece) {
        switch (piece) {
            case 'P': return "♙"; // White Pawn
            case 'R': return "♖";
            case 'N': return "♘";
            case 'B': return "♗";
            case 'Q': return "♕";
            case 'K': return "♔";
            case 'p': return "♟"; // Black Pawn
            case 'r': return "♜";
            case 'n': return "♞";
            case 'b': return "♝";
            case 'q': return "♛";
            case 'k': return "♚";
            default: return " ";
        }
    }

    private void handleSpecialMoves(char piece, int startRow, int startCol, int endRow, int endCol) {
        // Castling
        if (Character.toLowerCase(piece) == 'k' && Math.abs(endCol - startCol) == 2) {
            handleCastling(startRow, startCol, endRow, endCol);
        }
        // Promotion
        if (Character.toLowerCase(piece) == 'p' && (endRow == 0 || endRow == 7)) {
            handlePromotion(endRow, endCol);
        }
        // En passant
        if (Character.toLowerCase(piece) == 'p' && startCol != endCol && board[endRow][endCol] == ' ') {
            handleEnPassant(startRow, endRow, endCol);
        }
    }

    private boolean isInCheck(boolean isWhiteKing) {
        int[] kingPos = findKing(isWhiteKing);
        if (kingPos == null) return false;
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                char piece = board[i][j];
                if (piece != ' ' && isWhitePiece(piece) != isWhiteKing) {
                    if (isValidMove(i, j, kingPos[0], kingPos[1])) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    private boolean isWhitePiece(char piece) {
        return Character.isUpperCase(piece);
    }

    private void handleCastling(int startRow, int startCol, int endRow, int endCol) {
        // TODO
    }

    private void handlePromotion(int endRow, int endCol) {
        board[endRow][endCol] = 'Q'; // default promote to Queen
    }

    private void handleEnPassant(int startRow, int endRow, int endCol) {
        board[startRow][endCol] = ' ';
    }

    private int[] findKing(boolean isWhiteKing) {
        char kingChar = isWhiteKing ? 'K' : 'k';
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == kingChar) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    public char[][] getBoard() {
        return board;
    }

    public boolean isWhiteTurn() {
        return isWhiteTurn;
    }

    // Print the board with Unicode symbols
    public void printBoard() {
        for (int i = 0; i < 8; i++) {
            System.out.print((8 - i) + " ");
            for (int j = 0; j < 8; j++) {
                System.out.print(getPieceSymbol(board[i][j]) + " ");
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    public static void main(String[] args) {
        ChessGameEngine game = new ChessGameEngine();
        game.printBoard();
    }
}
