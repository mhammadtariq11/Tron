import com.mysql.cj.x.protobuf.MysqlxCrud;

import java.util.HashSet;
import java.util.Set;

public class Player {
    private String name;
    private String controlScheme;
    private int x, y;
    private String direction;
    private String color;
    private Set<String> trail;
    private boolean isMoving;
    private int score;

    public Player(String name, String controlScheme, int startX, int startY) {
        this.name = name;
        this.controlScheme = controlScheme;
        this.x = startX;
        this.y = startY;
        this.direction = "UP";
        this.color = "#FF0000"; // Default color
        this.trail = new HashSet<>();
        this.trail.add(x + "," + y);
        this.isMoving = false;
        this.score = 0;
    }

    public void move() {
        if (!isMoving) return;

        System.out.println(name + " moving " + direction);
        // Move player based on direction
        switch (direction) {
            case "UP":
                y--;
                break;
            case "DOWN":
                y++;
                break;
            case "LEFT":
                x--;
                break;
            case "RIGHT":
                x++;
                break;
        }
        trail.add(x + "," + y);
    }

    public void setDirection(String direction) {
        boolean validDirection = false;
        switch (direction) {
            case "UP":
            case "DOWN":
            case "LEFT":
            case "RIGHT":
                validDirection = true;
                break;
        }

        if (validDirection) {
            System.out.println(name + " changing direction to " + direction);
            this.direction = direction;
            this.isMoving = true; // Start moving the motor
        } else {
            System.out.println("Invalid direction: " + direction + " for " + name);
        }
    }

    public int getScore() {
        return score;  // Return current score
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Set<String> getTrail() {
        return trail;
    }

    public String getDirection() {
        return direction;
    }
}