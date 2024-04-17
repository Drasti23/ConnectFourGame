
import java.util.Scanner;
public class ConnectFour {
    public final int rows = 6;
    public final int column = 7;
    public char[][] board = new char[rows][column];
    public char currPlayer;
    public String player1;
    public String player2;
    public String current = "";
    public char AiPlayer;

    public ConnectFour() {
    }

    public ConnectFour(String p1, String p2) {
        player1 = p1;
        player2 = p2;
    }

    public void onePlayer(char color, String name) {
        currPlayer = color;
        AiPlayer = getOpponent();
        player1 = name;
    }

    public char getOpponent() {
        return (currPlayer == 'r') ? 'y' : 'r';
    }

    public void chooseColor(char color) {
        currPlayer = (color == 'r') ? 'r' : 'y';
        current = (current.equals(player1)) ? player2 : player1;
        System.out.println(player1 + " or " + currPlayer + " will go first.");
    }

    public void fillTheBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < column; j++) {
                board[i][j] = '-';
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < column; j++) {
                System.out.print("| " + board[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("  1   2   3   4   5   6   7 ");
    }

    public boolean isValidInput(int coll) {
        if (coll > 0 && coll <= column) return true;
        return false;
    }

    private boolean isGameOver() {
        return returnWinner(this.currPlayer) || returnWinner(this.currPlayer) || checkBoard();
    }

    public void takeTurn(int column) {
        for (int i = rows - 1; i >= 0; i--) {
            if (board[i][column - 1] == '-') {
                board[i][column - 1] = currPlayer;
                break;
            }
        }
    }

    public void switchPlayer() {

        if (currPlayer == 'r') {
            currPlayer = 'y';
        } else {
            currPlayer = 'r';
        }

        if (player1.equals(current)) {
            current = player2;
        } else {
            current = player1;
        }
    }

    public boolean checkBoard() {
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < column; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean returnWinner(char curr) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < column - 3; j++) {
                if (board[i][j] == curr && board[i][j + 1] == curr &&
                        board[i][j + 2] == curr &&
                        board[i][j + 3] == curr) {
                    return true;
                }
            }
        }

        for (int i = 0; i < rows - 3; i++) {
            for (int j = 0; j < column; j++) {
                if (board[i][j] == curr && board[i + 1][j] == curr &&
                        board[i + 2][j] == curr &&
                        board[i + 3][j] == curr) {
                    return true;
                }
            }
        }

        for (int i = 0; i < rows - 3; i++) {
            for (int j = 0; j < column - 3; j++) {
                if (board[i][j] == curr && board[i + 1][j + 1] == curr &&
                        board[i + 2][j + 2] == curr &&
                        board[i + 3][j + 3] == curr) {
                    return true;
                }
            }
        }
        for (int i = 3; i < rows; i++) {
            for (int j = 0; j < column - 3; j++) {
                if (board[i][j] == curr && board[i - 1][j + 1] == curr &&
                        board[i - 2][j + 2] == curr &&
                        board[i - 3][j + 3] == curr) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getAvailRow(int col) {
        for (int r = rows - 1; r >= 0; r--) {
            if (board[r][col] == '-') {
                return r;
            }
        }
        return -1;
    }

    public int evaluate() {
        char winner = checkWinner();
        if (winner == this.currPlayer) {
            return 10;
        } else if (winner == getOpponent()) {
            return -10;
        } else if (checkBoard()) {
            return 0;
        }
        return 0;
    }

    private char checkWinner() {

        if (returnWinner(this.currPlayer)) {
            return this.currPlayer;
        }
        if (returnWinner(getOpponent())) {
            return getOpponent();
        }
        return '\0';
    }

    // The minimax algorithm
    public int miniMax(char[][] board, int depth, int alpha, int beta, boolean maximizing) {
        char winner = checkWinner();
        if (depth == 0 || winner != '\0') {
            if (winner == this.currPlayer) {
                return 10;
            } else if (winner == getOpponent()) {
                return -10;
            } else {
                return 0;
            }
        }
        if (maximizing) {
            int value = Integer.MIN_VALUE;
            for (int c = 0; c < column; c++) {
                int r = getAvailRow(c);
                if (r != -1) {
                    board[r][c] = this.currPlayer;
                    value = Math.max(value, miniMax(board, depth - 1, alpha, beta, false));
                    board[r][c] = '-';
                    alpha = Math.max(alpha, value);
                    if (alpha >= beta) {
                        break;
                    }
                }
            }
            return value;
        } else {
            int value = Integer.MAX_VALUE;
            for (int c = 0; c < column; c++) {
                int r = getAvailRow(c);
                if (r != -1) {
                    board[r][c] = getOpponent();
                    value = Math.min(value, miniMax(board, depth - 1, alpha, beta, true));
                    board[r][c] = '-';
                    beta = Math.min(beta, value);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return value;
        }
    }

    // finding the best move
    public int BestMove(char[][] board) {
        int bestScore = -10;
        int bestMove = -1;
        for (int col = 0; col < column; col++) {
            if (isValidInput(col)) {
                int row = getAvailRow(col);
                board[row][col] = this.AiPlayer;
                int score = miniMax(board, 10, -10, 10, false);
                board[row][col] = '-';
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = col;
                }
            }
        }
        System.out.println("The score is " + bestScore);
        return bestMove;
    }
    }
