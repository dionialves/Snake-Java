package main.java.com.dionialves.snakeJava.entities;

import main.java.com.dionialves.snakeJava.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

// Classe responsável pelo food do game!
public class Foods {
    private Rectangle body;
    private int normalSize;
    private int enlargedSize;
    private int currentSize;

    private boolean isNormalSize = true;
    private boolean isAnimation = false;
    private boolean isShadow;
    private BufferedImage spriteSheet;
    private BufferedImage food;
    private BufferedImage shadowFood;


    public Foods(boolean isAnimation, boolean isShadow, int size) {
        // Inicializações
        this.setNormalSize(size);
        this.setEnlargedSize(size + 4);
        this.setCurrentSize(size);

        this.setAnimation(isAnimation);
        this.setShadow(isShadow);

        this.setBody(new Rectangle(this.getNormalSize(), this.getNormalSize()));

        try {
            this.setSpriteSheet(ImageIO.read(new File("src/main/resources/images/sprites-foods.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.setFood(this.getSprite(18, 146, 140, 140));
        this.setShadowFood(
                ShadowGenerator.addShadowToImage(this.getFood(),
                        0,
                        1,
                        new Color(0,0,0, 30))
        );

        if (this.isAnimation) {
            Timer timer = new Timer(1000, e -> toggleSize());
            timer.start();
        }
    }

    // Método principal da classe que desenha o food na tela.
    public void draw(Graphics2D g2d) {
        // Inicialização de algumas variáveis para deixar o código mais legível
        int x = this.getBody().x;
        int y = this.getBody().y;
        int width = this.getCurrentSize();
        int height = this.getCurrentSize();

        if (!isNormalSize) {
            x -= 2;
            y -= 2;
            this.setCurrentSize(this.getEnlargedSize());
        } else {
            this.setCurrentSize(this.getNormalSize());
        }
        if (this.isShadow()) {
            g2d.drawImage(
                    this.getShadowFood(),
                    x,
                    y+5,
                    width,
                    height,
                    null
            );
        }
        g2d.drawImage(
                this.getFood(),
                x,
                y,
                width,
                height,
                null
        );
    }

    public BufferedImage getSprite(int x, int y, int width, int height) {
        return this.getSpriteSheet().getSubimage(x, y, width, height);
    }

    public static BufferedImage addShadow(BufferedImage original, int offsetX, int offsetY, Color shadowColor) {
        // Dimensões do novo canvas para incluir a sombra
        int width = original.getWidth() + Math.abs(offsetX);
        int height = original.getHeight() + Math.abs(offsetY);

        // Criar imagem com espaço extra para a sombra
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = result.createGraphics();

        // Desenhar a sombra
        g2d.setColor(shadowColor);
        g2d.drawImage(original, offsetX, offsetY, shadowColor, null);

        // Desenhar a imagem original por cima
        g2d.drawImage(original, 0, 0, null);

        g2d.dispose();
        return result;
    }

    // Alterna entre os tamanhos normal e ampliado
    private void toggleSize() {
        this.setNormalSize(!this.isNormalSize());

        // Faz a alteração do currentSize
        this.setCurrentSize(this.isNormalSize() ? this.getNormalSize() : this.getEnlargedSize());
    }

    public Rectangle getBody() {
        return body;
    }

    public void setBody(Rectangle body) {
        this.body = body;
    }

    public int getNormalSize() {
        return normalSize;
    }

    public void setNormalSize(int normalSize) {
        this.normalSize = normalSize;
    }

    public BufferedImage getSpriteSheet() {
        return spriteSheet;
    }

    public void setSpriteSheet(BufferedImage spriteSheet) {
        this.spriteSheet = spriteSheet;
    }

    public BufferedImage getFood() {
        return food;
    }

    public void setFood(BufferedImage food) {
        this.food = food;
    }

    public BufferedImage getShadowFood() {
        return shadowFood;
    }

    public void setShadowFood(BufferedImage shadowFood) {
        this.shadowFood = shadowFood;
    }

    public boolean isNormalSize() {
        return isNormalSize;
    }

    public void setNormalSize(boolean normalSize) {
        isNormalSize = normalSize;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    public int getEnlargedSize() {
        return enlargedSize;
    }

    public void setEnlargedSize(int enlargedSize) {
        this.enlargedSize = enlargedSize;
    }

    public boolean isAnimation() {
        return isAnimation;
    }

    public void setAnimation(boolean animation) {
        isAnimation = animation;
    }

    public boolean isShadow() {
        return isShadow;
    }

    public void setShadow(boolean shadow) {
        isShadow = shadow;
    }
}
