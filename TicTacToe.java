
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToe extends JFrame {

    private JButton[][] buttons = new JButton[3][3];
    private char[][] board = new char[3][3];

    private char currentPlayer = 'X';
    private boolean gameOver = false;

    private JLabel status;
    private JPanel boardPanel;

    private Color background = new Color(25, 25, 40);
    private Color cellColor = new Color(42, 42, 65);
    private Color xColor = new Color(0, 220, 255);
    private Color oColor = new Color(255, 90, 140);

    public TicTacToe() {

        setTitle("Animated Tic Tac Toe");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        getContentPane().setBackground(background);
        setLayout(new BorderLayout(10, 10));

        status = new JLabel("Player X's Turn", JLabel.CENTER);
        status.setFont(new Font("Arial", Font.BOLD, 25));
        status.setForeground(Color.WHITE);
        status.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        add(status, BorderLayout.NORTH);

        boardPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        boardPanel.setBackground(background);
        boardPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                final int row = i;
                final int col = j;

                buttons[i][j] = new JButton("");

                buttons[i][j].setFont(
                    new Font("Arial", Font.BOLD, 65)
                );

                buttons[i][j].setBackground(cellColor);
                buttons[i][j].setForeground(Color.WHITE);
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].setBorder(
                    BorderFactory.createLineBorder(
                        new Color(75, 75, 100), 2
                    )
                );

                buttons[i][j].addActionListener(e ->
                    makeMove(row, col)
                );

                boardPanel.add(buttons[i][j]);
            }
        }

        add(boardPanel, BorderLayout.CENTER);

        JButton restart = new JButton("RESTART GAME");
        restart.setFont(new Font("Arial", Font.BOLD, 18));
        restart.setForeground(Color.WHITE);
        restart.setBackground(new Color(90, 70, 200));
        restart.setFocusPainted(false);

        restart.addActionListener(e -> resetGame());

        JPanel bottom = new JPanel();
        bottom.setBackground(background);
        bottom.setBorder(
            BorderFactory.createEmptyBorder(5, 20, 20, 20)
        );
        bottom.add(restart);

        add(bottom, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void makeMove(int row, int col) {

        if (gameOver || board[row][col] != '\0') {
            return;
        }

        board[row][col] = currentPlayer;

        JButton button = buttons[row][col];

        button.setText(currentPlayer == 'X' ? "X" : "O");

        button.setForeground(
            currentPlayer == 'X' ? xColor : oColor
        );

        animateButton(button);

        if (checkWinner()) {
            gameOver = true;
            status.setText("Player " + currentPlayer + " Wins!");
            status.setForeground(Color.GREEN);
            highlightWinner();
            return;
        }

        if (isDraw()) {
            gameOver = true;
            status.setText("It's a Draw!");
            status.setForeground(Color.YELLOW);
            return;
        }

        currentPlayer = currentPlayer == 'X' ? 'O' : 'X';

        status.setText("Player " + currentPlayer + "'s Turn");
        status.setForeground(Color.WHITE);
    }

    // Symbol animation
    private void animateButton(JButton button) {

        Timer timer = new Timer(15, null);
        final int[] size = {10};

        timer.addActionListener(e -> {

            size[0] += 5;

            button.setFont(
                new Font("Arial", Font.BOLD, size[0])
            );

            if (size[0] >= 65) {
                timer.stop();
            }
        });

        timer.start();
    }

    // Check winner
    private boolean checkWinner() {

        for (int i = 0; i < 3; i++) {

            if (board[i][0] != '\0' &&
                board[i][0] == board[i][1] &&
                board[i][1] == board[i][2]) {
                return true;
            }

            if (board[0][i] != '\0' &&
                board[0][i] == board[1][i] &&
                board[1][i] == board[2][i]) {
                return true;
            }
        }

        if (board[0][0] != '\0' &&
            board[0][0] == board[1][1] &&
            board[1][1] == board[2][2]) {
            return true;
        }

        if (board[0][2] != '\0' &&
            board[0][2] == board[1][1] &&
            board[1][1] == board[2][0]) {
            return true;
        }

        return false;
    }

    // Highlight winning cells
    private void highlightWinner() {

        Color winColor = new Color(40, 180, 100);

        for (int i = 0; i < 3; i++) {

            if (board[i][0] != '\0' &&
                board[i][0] == board[i][1] &&
                board[i][1] == board[i][2]) {

                for (int j = 0; j < 3; j++) {
                    buttons[i][j].setBackground(winColor);
                }
                return;
            }

            if (board[0][i] != '\0' &&
                board[0][i] == board[1][i] &&
                board[1][i] == board[2][i]) {

                for (int j = 0; j < 3; j++) {
                    buttons[j][i].setBackground(winColor);
                }
                return;
            }
        }

        if (board[0][0] != '\0' &&
            board[0][0] == board[1][1] &&
            board[1][1] == board[2][2]) {

            for (int i = 0; i < 3; i++) {
                buttons[i][i].setBackground(winColor);
            }
            return;
        }

        if (board[0][2] != '\0' &&
            board[0][2] == board[1][1] &&
            board[1][1] == board[2][0]) {

            for (int i = 0; i < 3; i++) {
                buttons[i][2-i].setBackground(winColor);
            }
        }
    }

    // Check draw
    private boolean isDraw() {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                if (board[i][j] == '\0') {
                    return false;
                }
            }
        }

        return true;
    }

    // Restart game
    private void resetGame() {

        currentPlayer = 'X';
        gameOver = false;

        status.setText("Player X's Turn");
        status.setForeground(Color.WHITE);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                board[i][j] = '\0';

                buttons[i][j].setText("");
                buttons[i][j].setBackground(cellColor);
                buttons[i][j].setFont(
                    new Font("Arial", Font.BOLD, 65)
                );
            }
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new TicTacToe();
        });
    }
}
