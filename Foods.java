import java.awt.*;

// Classe responsável pelo food do game!
public class Foods {
    private Rectangle body;
    private int size;

    // Método principal da classe que desenha o food na tela.
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.red);
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
