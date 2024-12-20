import java.awt.*;

public class BodySnake extends Rectangle {
    private String direction;

    public BodySnake(int x, int y, int width, int height, String direction) {
        super(x, y, width, height);
        this.setDirection(direction);
    }

    public BodySnake(int width, int height, String direction) {
        super(width, height);
        this.setDirection(direction);
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
