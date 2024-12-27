package main.java.com.dionialves.snakeJava.entities;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ShadowGenerator {

    public static BufferedImage addShadowToImage(BufferedImage original, int offsetX, int offsetY, Color shadowColor) {
        // Obter as dimensões da imagem original
        int width = original.getWidth();
        int height = original.getHeight();

        // Criar uma nova imagem com a sombra
        BufferedImage shadowImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Criar Graphics2D para desenhar a sombra
        Graphics2D g2d = shadowImage.createGraphics();

        // Ativar suavização para uma sombra mais suave
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Definir a cor da sombra
        g2d.setColor(shadowColor);

        // Desenhar a sombra deslocada com base na imagem original (sem cor)
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if ((original.getRGB(x, y) >> 24) != 0) {  // Verificar se o pixel não é transparente
                    g2d.fillRect(x + offsetX, y + offsetY, 1, 1); // Deslocar a sombra
                }
            }
        }

        g2d.dispose();

        return shadowImage; // Retorna a imagem da sombra
    }

    public static BufferedImage addShadowToRectangle(int rectWidth, int rectHeight, int offsetX, int offsetY, Color shadowColor) {
        // Criar uma imagem que inclua espaço extra para a sombra
        int width = rectWidth + Math.abs(offsetX);
        int height = rectHeight + Math.abs(offsetY);

        BufferedImage shadowImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = shadowImage.createGraphics();

        // Configurar suavização (opcional)
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Desenhar o retângulo de sombra
        g2d.setColor(shadowColor);
        g2d.fillRect(offsetX, offsetY, rectWidth, rectHeight);

        g2d.dispose();
        return shadowImage;
    }

}
