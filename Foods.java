import java.awt.*;
import java.util.Random;

public class Foods {
    private Rectangle body;
    private int size;

    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.fillOval(this.body.x, this.body.y, this.size, this.size);

    }

    public Foods() {
        this.setSize(Snake.BODYSIZE);
        this.setBody(new Rectangle(this.getSize(), this.getSize()));
    }

    public Rectangle getBody() {
        return body;
    }

    public void setBody(Rectangle body) {
        this.body = body;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
