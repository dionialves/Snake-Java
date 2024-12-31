package main.java.com.dionialves.snakeJava.entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

// Essa classe é responsável por gerenciar as imagens da snake, ela recebe a sprite completa e tem métodos para retornar
// exatamente a imagem que preciso. Também tem um método para rotacionar a imagem.
public class SnakeSprite {
    private BufferedImage sprite;

    public SnakeSprite(String filePath) {
        try {
            this.setSprite(ImageIO.read(new File(filePath)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para retornar a imagem que preciso da sprite
    public BufferedImage getImageFromSprite(int x, int y, int width, int height) {
        return this.getSprite().getSubimage(x, y, width, height);
    }

    // Método para rotacionar a imagem
    //
    public void drawRotatedImage(Graphics2D g2d, BufferedImage image, double angle, int x, int y, int width, int height) {

        // Calcula o centro da imagem
        int centerX = image.getWidth() / 2;
        int centerY = image.getHeight() / 2;
        // Salva o estado original do Graphics2D
        AffineTransform originalTransform = g2d.getTransform();
        // Translada para a posição de desenho
        g2d.translate(x, y);
        // Aplica a rotação no centro da imagem
        g2d.rotate(Math.toRadians(angle), centerX, centerY);
        // Desenha a imagem
        g2d.drawImage(image, 0, 0, width, height, null);
        // Restaura o estado original
        g2d.setTransform(originalTransform);
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }
}
