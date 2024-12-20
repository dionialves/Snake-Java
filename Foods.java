import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

// Classe responsável pelo food do game!
public class Foods {
    private BufferedImage foodImage;
    private Rectangle foodRectangle;
    private int size;
    private Image fruitImage;

    public Foods() {
        // Tamanho da fruta
        this.setSize(Snake.BODYSIZE);
        // Inicialização do retângulo que vai ser usado como gestão de posição para controle das colisões
        this.setFoodRectangle(new Rectangle(this.getSize(), this.getSize()));
        // Inicialização da fruta
        try {
            this.setFoodImage(ImageIO.read(new File("assets/graphics/apple.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método principal da classe que desenha o food na tela.
    public void draw(Graphics2D g2d) {

        g2d.drawImage(
                getFoodImage(),
                this.getFoodRectangle().x,
                this.getFoodRectangle().y,
                this.getSize(),
                this.getSize(),
                null
        );
    }

    public BufferedImage getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(BufferedImage foodImage) {
        this.foodImage = foodImage;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Rectangle getFoodRectangle() {
        return foodRectangle;
    }

    public void setFoodRectangle(Rectangle foodRectangle) {
        this.foodRectangle = foodRectangle;
    }
}
