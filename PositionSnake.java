public class PositionSnake {
    private final int x;
    private final int y;
    private final String direction;

    public PositionSnake(int x, int y, String description) {
        this.x = x;
        this.y = y;
        this.direction = description;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getDirection() {
        return direction;
    }
}