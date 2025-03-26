package java;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

public class GameController {
    public GameModel model;
    private GameView view;
    private Timer timer;

    public GameController() {
        System.out.println("Initializing GameController...");
        this.model = new GameModel();
        this.view = new GameView(this, model);
        this.timer = new Timer();
    }

    public void startGame() {
        System.out.println("Starting game...");
        view.showMenu();
        startTimer();
    }

    public void handleInput(String player, int keyCode) {
        Player currentPlayer = player.equals("Player1") ? model.getPlayers().get(0) : model.getPlayers().get(1);

        switch (keyCode) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                currentPlayer.setDirection("UP");
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                currentPlayer.setDirection("DOWN");
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                currentPlayer.setDirection("LEFT");
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                currentPlayer.setDirection("RIGHT");
                break;
            default:
                System.out.println("Unhandled key code for " + player);
        }
    }

    public void updateGame() {
        System.out.println("Updating game...");
        model.update();
        view.repaint();
        if (model.isGameOver()) {
            Player winner = model.getWinner();
            if (winner != null) {
                // Increment the winner's score by 1
                DatabaseManager.saveScore(winner.getName(), 1);  // Increment by 1 each time a player wins
            }
            timer.cancel();
            view.showGameOver();
        }
    }


    private void startTimer() {
        System.out.println("Starting timer...");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateGame();
            }
        }, 0, 100); // Update game every 100 milliseconds
    }
}

