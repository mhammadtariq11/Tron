package test.java.com.tron;
import main.java.com.tron.GameController;
import main.java.com.tron.GameModel;
import org.junit.jupiter.api.*;

import main.java.com.tron.Player;
import java.awt.event.KeyEvent;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UnitTestingTest {

    @Test
    void testPlayerMove() {
        Player player = new Player("TestPlayer", "WASD", 10, 10);

        player.setDirection("UP");
        player.move();
        assertEquals(9, player.getY(), "Player should move up by 1.");

        player.setDirection("DOWN");
        player.move();
        assertEquals(10, player.getY(), "Player should move down by 1.");

        player.setDirection("LEFT");
        player.move();
        assertEquals(9, player.getX(), "Player should move left by 1.");

        player.setDirection("RIGHT");
        player.move();
        assertEquals(10, player.getX(), "Player should move right by 1.");
    }

    @Test
    void testPlayerTrail() {
        Player player = new Player("TestPlayer", "WASD", 5, 5);

        player.setDirection("UP");
        player.move();
        player.move();
        assertTrue(player.getTrail().contains("5,3"), "Player trail should contain the updated position.");
        assertTrue(player.getTrail().contains("5,4"), "Player trail should track intermediate steps.");
    }

    @Test
    void testGameModelCollisions() {
        GameModel model = new GameModel();
        List<Player> players = model.getPlayers();

        // Simulate a boundary collision for Player 1
        Player player1 = players.get(0);
        player1.setDirection("LEFT");
        for (int i = 0; i < 15; i++) {
            player1.move();
        }
        model.update();
        assertTrue(model.isGameOver(), "Game should end when a player collides with the boundary.");
        assertEquals(players.get(1), model.getWinner(), "Player 2 should be the winner.");
    }

    @Test
    void testGameControllerHandleInput() {
        GameController controller = new GameController();
        controller.startGame();

        Player player1 = controller.model.getPlayers().get(0);
        controller.handleInput("Player1", KeyEvent.VK_W);
        assertEquals("UP", player1.getDirection(), "Player 1 should change direction to UP.");
    }
}
