package main.java.com.dionialves.snakeJava.entities;

public class PositionSnake {
    private double x;
    private double y;
    private String direction;

    public PositionSnake(int x, int y, String direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
