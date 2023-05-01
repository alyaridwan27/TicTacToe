import javax.swing.*;
import java.awt.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;


public class TicTacToeGUI {
    private JFrame frame;
    private JPanel boardPanel;
    private JLabel messageLabel;
    private JButton[][] board;
    private boolean playerX;
    private boolean gameOver;

    public TicTacToeGUI() {
        frame = new JFrame("Tic Tac Toe");
        boardPanel = new JPanel();
        messageLabel = new JLabel();
        board = new JButton[3][3];
        playerX = true;
        gameOver = false;

        // Set up the game board
        boardPanel.setLayout(new GridLayout(3, 3));
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton button = new JButton("");
                button.setFont(new Font("Arial", Font.PLAIN, 80));
                button.addActionListener(new ButtonListener(row, col));
                board[row][col] = button;
                boardPanel.add(button);
            }
        }

        // Set up the message label
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        messageLabel.setText("Player X's turn");

        // Add components to the frame
        frame.setLayout(new BorderLayout());
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(messageLabel, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    private void checkForWin() {
        // Check for horizontal wins
        for (int row = 0; row < 3; row++) {
            if (!board[row][0].getText().equals("") &&
                    board[row][0].getText().equals(board[row][1].getText()) &&
                    board[row][1].getText().equals(board[row][2].getText())) {
                gameOver = true;
            }
        }

        // Check for vertical wins
        for (int col = 0; col < 3; col++) {
            if (!board[0][col].getText().equals("") &&
                    board[0][col].getText().equals(board[1][col].getText()) &&
                    board[1][col].getText().equals(board[2][col].getText())) {
                gameOver = true;
            }
        }

        // Check for diagonal wins
        if (!board[0][0].getText().equals("") &&
                board[0][0].getText().equals(board[1][1].getText()) &&
                board[1][1].getText().equals(board[2][2].getText())) {
            gameOver = true;
        }
        if (!board[0][2].getText().equals("") &&
                board[0][2].getText().equals(board[1][1].getText()) &&
                board[1][1].getText().equals(board[2][0].getText())) {
            gameOver = true;
        }

        // Display the winner or a tie game
        if (gameOver) {
            String winner = playerX ? "O" : "X";
            messageLabel.setText("Player " + winner + " wins!");
        } else if (isBoardFull()) {
            messageLabel.setText("Tie game");
        } else {
            // Switch to the other player's turn
            playerX = !playerX;
            String player = playerX ? "X" : "O";
            messageLabel.setText("Player " + player + "'s turn");
        }
    }

    private boolean isBoardFull() {
        for (int row = 0;     row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    private class ButtonListener implements ActionListener {
        private int row;
        private int col;

        public ButtonListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public void actionPerformed(ActionEvent e) {
            // Don't do anything if the game is over or the button has already been clicked
            if (gameOver || !((JButton)e.getSource()).getText().equals("")) {
                return;
            }

            // Mark the button with the current player's symbol and check for a win
            String symbol = playerX ? "X" : "O";
            ((JButton)e.getSource()).setText(symbol);
            checkForWin();
        }
    }

    public static void main(String[] args) {
        new TicTacToeGUI();
    }
