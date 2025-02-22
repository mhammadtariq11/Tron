public class Main {
    public static void main(String[] args) {
        System.out.println("Starting the game...");
        startNewGame();
    }
    public static void startNewGame() {
        GameController controller = new GameController();
        controller.startGame();
    }
}