package main.java.com.dionialves.snakeJava.entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

// Classe responsável pelo food do game!
public class Foods {
    private Rectangle body;
    private int size;
    private BufferedImage sprite;

    public Foods(int size) {
        this.setSize(size);
        this.setBody(new Rectangle(this.getSize(), this.getSize()));

        try {
            this.sprite = ImageIO.read(new File("src/main/resources/images/food.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Método principal da classe que desenha o food na tela.
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.red);
        g2d.fillOval(this.body.x, this.body.y, this.size, this.size);

        //g2d.drawImage(this.getSprite(), this.body.x, this.body.y, null);
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

    public BufferedImage getSprite() {
        return sprite;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }
}
