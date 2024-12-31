package main.java.com.dionialves.snakeJava.entities;

import main.java.com.dionialves.snakeJava.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

// Classe responsável pelo food do game!
public class Foods {
    private Rectangle body;
    private int normalSize;
    private int enlargedSize;
    private int currentSize;
    // Através dos atributos abaixo eu consigo manipular o tamanho da foods, possibilitando assim criar um efeito de
    // movimento, onde a food cresce e diminui de tempos em tempos
    private boolean isNormalSize = true;
    private boolean isAnimation = false;
    private boolean isShadow;
    // Atributos que carregam a imagem do food.
    // Futuramente preciso modificar a logica criando uma classe separada para gerenciar as sprites.
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
        // Por mais que o food seja representado visualmente por uma imagem, mantive na parte logica, com um retangulo
        // assim consigo saber de forma fácil quando ele se colide com a snake, que também e um retângulo. Faço isso
        // através do método "intersects".
        this.setBody(new Rectangle(this.getNormalSize(), this.getNormalSize()));

        // Esse bloco de código precisa ser reescrito quando eu for criar uma nova classe que irá gerenciar as sprites
        // do food
        try {
            InputStream inputFile = getClass().getResourceAsStream("/main/resources/images/sprites-foods.png");
            this.setSpriteSheet(ImageIO.read(inputFile));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Inicialização da imagem do food e da sombra do mesmo
        this.setFood(this.getSprite(18, 146, 140, 140));
        this.setShadowFood(
                ShadowGenerator.addShadowToImage(this.getFood(),
                        0,
                        1,
                        new Color(0,0,0, 30))
        );
        // Aqui dou start a animação
        if (this.isAnimation) {
            Timer timer = new Timer(750, e -> toggleSize());
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

        // Se normalSize for true, pega as informações do tamanho normal do food, caso contrário pega o tamanho largo
        // do food.
        // Essa logica pode ser melhorada
        if (isNormalSize) {
            this.setCurrentSize(this.getNormalSize());
        } else {
            x -= 2;
            y -= 2;
            this.setCurrentSize(this.getEnlargedSize());

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
