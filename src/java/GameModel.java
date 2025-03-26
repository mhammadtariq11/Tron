package java;
import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private List<Player> players;
    private boolean isGameOver;
    private Player winner;

    public GameModel() {
        System.out.println("Initializing GameModel...");
        players = new ArrayList<>();
        players.add(new Player("Player 1", "WASD", 10, 10));
        players.add(new Player("Player 2", "ARROWS", 30, 30));
        isGameOver = false;
        winner = null;
    }

    public void update() {
        System.out.println("Updating game state...");
        // Update game state
        for (Player player : players) {
            player.move();
        }
        checkCollisions();
    }

    public void processInput(String input) {
        System.out.println("Processing input: " + input);
        // Process player input
        for (Player player : players) {
            player.setDirection(input);
        }
    }

    private void checkCollisions() {
        System.out.println("Checking for collisions...");
        // Check for collisions
        for (Player player : players) {
            // Check boundary collisions
            if (player.getX() < 0 || player.getX() >= 80 || player.getY() < 0 || player.getY() >= 60) {
                isGameOver = true;
                winner = players.get((players.indexOf(player) + 1) % 2);
                return;
            }
            // Check light trace collisions
            for (Player otherPlayer : players) {
                if (player != otherPlayer && otherPlayer.getTrail().contains(player.getX() + "," + player.getY())) {
                    isGameOver = true;
                    winner = otherPlayer;
                    return;
                }
            }
        }
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public Player getWinner() {
        return winner;
    }

    public List<Player> getPlayers() {
        return players;
    }
}