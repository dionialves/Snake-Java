package main.java.com.dionialves.snakeJava.entities;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

/**
 * Representa o alimento no jogo Snake, com suporte a animações de tamanho e sombra.
 */
public class Foods {
    private final Color SHADOW_COLOR = new Color(0, 0, 0, 30);

    private Rectangle body;
    private boolean isAnimation = false;
    private boolean hasShadow;

    private int normalSize;
    private int enlargedSize;
    private int currentSize;
    private boolean isNormalSize = true;

    private BufferedImage foodImage;
    private BufferedImage shadowImage;

    /**
     * Construtor para inicializar o alimento.
     *
     * @param isAnimation  Indica se o alimento tem animação de tamanho.
     * @param hasShadow   Indica se o alimento exibe uma sombra.
     * @param size        Tamanho inicial do alimento.
     */
    public Foods(boolean isAnimation, boolean hasShadow, int size) {
        this.setNormalSize(size);
        this.setEnlargedSize(size + 4);
        this.setCurrentSize(size);
        this.setAnimation(isAnimation);
        this.setShadow(hasShadow);

        this.setBody(new Rectangle(size, size));

        // Load das imagens do food, no caso sprite e a sombra
        this.loadImages();

        // Aqui dou start a animação
        if (this.isAnimation()) {
            this.startAnimation();
        }
    }

    /**
     * Desenha o alimento no painel.
     *
     * @param g2d Contexto gráfico.
     */
    public void draw(Graphics2D g2d) {
        int SHADOW_OFFSET_Y = 5;

        // Calcula as coordenadas com base no estado atual, ajustando o x e y em 2pixel. Isso é necessário para
        // corrigir a posição do desenho, dependendo do estado da imagem.
        int x = this.getBody().x - (this.isNormalSize() ? 0 : 2);
        int y = this.getBody().y - (this.isNormalSize() ? 0 : 2);
        this.setCurrentSize(this.isNormalSize() ? this.getNormalSize() : this.getEnlargedSize());

        if (this.isShadow()) {
            g2d.drawImage(
                    this.getShadowFood(),
                    x,
                    y + SHADOW_OFFSET_Y,
                    this.getCurrentSize(),
                    this.getCurrentSize(),
                    null
            );
        }
        g2d.drawImage(
                this.getFoodImage(),
                x,
                y,
                this.getCurrentSize(),
                this.getCurrentSize(),
                null
        );
    }

    /**
     * Carrega as imagens para o alimento e sua sombra.
     */
    public void loadImages() {
        try {
            InputStream inputFile = getClass().getResourceAsStream("/main/resources/images/sprites-foods.png");

            if (inputFile == null) {
                throw new IllegalArgumentException("Resource not found: /main/resources/images/sprites-foods.png");
            }
            BufferedImage spriteSheet = ImageIO.read(inputFile);
            this.setFoodImage(spriteSheet.getSubimage(18, 146, 140, 140));
            this.setShadowFood(
                    ShadowGenerator.addShadowToImage(this.getFoodImage(),
                            0,
                            1,
                            SHADOW_COLOR)
            );
        } catch (Exception e) {
            throw new RuntimeException("Error loading images", e);
        }
    }

    /**
     * Inicia a animação do Food
     */
    private void startAnimation() {
        int ANIMATION_DELAY = 750;

        if (this.isAnimation) {
            Timer timer = new Timer(ANIMATION_DELAY, _ -> toggleSize());
            timer.start();
        }
    }

    /**
     * Alterna entre os tamanhos, normal e ampliado
     */
    private void toggleSize() {
        this.setNormalSize(!this.isNormalSize());
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

    public BufferedImage getFoodImage() {
        return this.foodImage;
    }

    public void setFoodImage(BufferedImage food) {
        this.foodImage = food;
    }

    public BufferedImage getShadowFood() {
        return shadowImage;
    }

    public void setShadowFood(BufferedImage shadowImage) {
        this.shadowImage = shadowImage;
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
        return hasShadow;
    }

    public void setShadow(boolean shadow) {
        hasShadow = shadow;
    }
}
