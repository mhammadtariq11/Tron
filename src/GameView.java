import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class GameView extends JFrame {
    private GameController controller;
    private GameModel model;

    public GameView(GameController controller, GameModel model) {
        this.controller = controller;
        this.model = model;

        System.out.println("Initializing GameView...");

        setTitle("Tron Light-Motorcycle Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                System.out.println("Key pressed: " + keyCode); // Debugging key presses

                switch (keyCode) {
                    case KeyEvent.VK_W:
                    case KeyEvent.VK_A:
                    case KeyEvent.VK_S:
                    case KeyEvent.VK_D:
                        controller.handleInput("Player1", keyCode);
                        break;

                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_RIGHT:
                        controller.handleInput("Player2", keyCode);
                        break;

                    default:
                        System.out.println("Unhandled key: " + keyCode);
                }
            }
        });

        setFocusable(true);
        requestFocusInWindow();

        // Collect player names and colors before making the window visible
        collectPlayerInfo();
    }

    private void collectPlayerInfo() {
        // Collect player names
        String player1Name = JOptionPane.showInputDialog(this, "Enter the name of Player 1:");
        Color player1Color = JColorChooser.showDialog(this, "Choose the light color of Player 1", Color.RED);
        String player2Name = JOptionPane.showInputDialog(this, "Enter the name of Player 2:");
        Color player2Color = JColorChooser.showDialog(this, "Choose the light color of Player 2", Color.BLUE);

        // Set player names and colors in the model
        model.getPlayers().get(0).setName(player1Name);
        model.getPlayers().get(0).setColor("#" + Integer.toHexString(player1Color.getRGB()).substring(2).toUpperCase());
        model.getPlayers().get(1).setName(player2Name);
        model.getPlayers().get(1).setColor("#" + Integer.toHexString(player2Color.getRGB()).substring(2).toUpperCase());

        // Make the window visible after collecting player info
        setVisible(true);
    }

    public void showMenu() {
        System.out.println("Showing menu...");
        // Display menu
        JOptionPane.showMessageDialog(this, "Welcome to Tron Light-Motorcycle Game!");
    }

    public void showGameOver() {
        System.out.println("Showing game over...");
        Player winner = model.getWinner();

        // Create a panel with buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JLabel message = new JLabel("Game Over! " + winner.getName() + " wins the game!", SwingConstants.CENTER);
        panel.add(message);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3)); // Adjusted for an extra button

        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current game window
                Main.startNewGame(); // Start a new game
            }
        });
        buttonPanel.add(restartButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exit the game
            }
        });
        buttonPanel.add(exitButton);

        // Add the Highscore button
        JButton highscoreButton = new JButton("Highscore");
        highscoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHighscores();
            }
        });
        buttonPanel.add(highscoreButton);

        panel.add(buttonPanel);

        // Display the panel in a dialog
        JOptionPane.showMessageDialog(this, panel, "Game Over", JOptionPane.PLAIN_MESSAGE);
    }
    private void showHighscores() {
        // Fetch the top 10 scores from the database
        List<String> topScores = DatabaseManager.getTopScores();

        // Display the scores in a dialog
        StringBuilder scores = new StringBuilder();
        for (String score : topScores) {
            scores.append(score).append("\n");
        }

        JOptionPane.showMessageDialog(this, scores.toString(), "Top 10 Highscores", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // Draw game state
        for (Player player : model.getPlayers()) {
            g.setColor(Color.decode(player.getColor())); // Change color based on player
            for (String pos : player.getTrail()) {
                String[] coords = pos.split(",");
                int x = Integer.parseInt(coords[0]);
                int y = Integer.parseInt(coords[1]);
                g.fillRect(x * 10, y * 10, 10, 10);
            }
        }
    }
}