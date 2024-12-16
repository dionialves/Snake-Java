import java.awt.*;

public class Game {
    private final int wight = GameEngine.WIDTH - 40;
    private final int height = GameEngine.HEIGHT - 80;
    private final int top = 60;
    private final int left = 20;
    private final int bottom = 20;
    private boolean gameOver = false;
    private boolean isVisible = false;
    public static String DIRECTION = "RIGHT";

    private int timer = 0;

    private final Snake snake = new Snake();
    private final Snake copySnake = new Snake();
    private final Food food = new Food();

    private int x = 60;
    private int y = 300;

    public void update() {
        this.setTimer(this.getTimer() + 1);

        if (this.getTimer() % 10 == 0) {

            this.setVisible(!this.isVisible());

            if (!this.gameOver) {
                this.copySnake.setBody(this.snake.cloneBody());

                this.snake.update(Game.DIRECTION);
                this.setTimer(0);

                if (this.hasCollision()) {
                    this.setGameOver(true);
                    this.setVisible(false);
                    System.out.println("Collision Detected");
                }
            }
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        g2d.drawString("Score:", this.getLeft(), this.getTop()-20);

        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        g2d.drawString("0.000.0:", this.getLeft()+85, this.getTop()-20);

        // Desenha na tela os limites do retângulo principal do game
        g2d.setStroke(new BasicStroke(4f));
        g2d.drawRect(
                this.getLeft()-4,
                this.getTop()-4,
                this.getWight()+8,
                this.getHeight()+8
        );

        if (this.isGameOver()) {
            if (this.isVisible()) {
                this.copySnake.draw(g2d);
            }
        } else this.snake.draw(g2d);

    }

    public boolean hasCollision() {
        int x = this.snake.getBody().getFirst().x;
        int y = this.snake.getBody().getFirst().y;

        int leftBoundary = this.getLeft();
        int rightBoundary = this.getWight();
        int topBoundary = this.getTop();
        int bottomBoundary = this.getHeight() + this.getTop();

        // Verifica colisão com as extremidades
        if ((x - Snake.BODYSIZE) > rightBoundary) return true; // Direita
        if (x < leftBoundary) return true;                     // Esquerda
        if (y >= bottomBoundary) return true;                  // Abaixo
        if (y < topBoundary) return true;                      // Acima

        return false;
    }

    public int getTop() {
        return top;
    }

    public int getBottom() {
        return bottom;
    }

    public int getLeft() {
        return left;
    }

    public int getWight() {
        return wight;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
