import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Snake {
    public static final int BODYSIZE = 10;
    private final List<Rectangle> body = new ArrayList<>();

    public Snake() {
        this.getBody().add(new Rectangle(60, 250, Snake.BODYSIZE, Snake.BODYSIZE));
        this.getBody().add(new Rectangle(50, 250, Snake.BODYSIZE, Snake.BODYSIZE));
        this.getBody().add(new Rectangle(40, 250, Snake.BODYSIZE, Snake.BODYSIZE));
    }

    public void update(String direction) {
        List<Rectangle> copyBody = this.cloneBody();

        this.moveHead(direction);

        for (int i = 1; i < this.getBody().size(); i++) {
            this.getBody().get(i).y = copyBody.get(i-1).y;
            this.getBody().get(i).x = copyBody.get(i-1).x;
        }
    }

    public void draw(Graphics2D g2d) {

        for (Rectangle rectangle : this.getBody()) {
            g2d.setColor(Color.WHITE);
            g2d.fillRect(
                    rectangle.x,
                    rectangle.y,
                    rectangle.width,
                    rectangle.height
            );
        }
    }

    public void moveHead(String direction) {
        switch (direction) {
            case "UP":
                this.getBody().getFirst().y -= Snake.BODYSIZE;
                break;
            case "RIGHT":
                this.getBody().getFirst().x += Snake.BODYSIZE;
                break;
            case "DOWN":
                this.getBody().getFirst().y += Snake.BODYSIZE;
                break;
            case "LEFT":
                this.getBody().getFirst().x -= Snake.BODYSIZE;
                break;
        }
    }

    public List<Rectangle> cloneBody() {
        List<Rectangle> copyBody = new ArrayList<>();

        for (Rectangle rect : this.getBody()) {
            copyBody.add(new Rectangle(rect));
        }

        return copyBody;
    }

    public List<Rectangle> getBody() {
        return body;
    }

    public void setBody(List<Rectangle> body) {
        this.body.clear();
        this.body.addAll(body);
    }
}
