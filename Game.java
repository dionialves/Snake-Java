import java.awt.*;
import java.util.Random;

public class Game {
    public static final int WIGHT = GameEngine.WIDTH -  Snake.BODYSIZE *2;
    public static final int HEIGHT = GameEngine.HEIGHT - (Snake.BODYSIZE * 2);
    public static final int TOP = 40;
    public static final int LEFT = 40;
    private final int bottom = 20;
    private boolean gameOver = false;
    private boolean isVisible = false;
    public static String direction = "RIGHT";
    private final Random random = new Random();

    private int timer = 0;
    private int timerIsMov = 0;
    int count = 0;

    private final Snake snake = new Snake(direction);
    private final Snake copySnake = new Snake(direction);
    private final Foods food = new Foods();

    private int x = 60;
    private int y = 300;

    public Game() {
        this.coordinatesOfFood();
    }

    public void update() {
        this.setTimer(this.getTimer() + 1);

        if (this.isFoodEaten()) {
            this.coordinatesOfFood();
            this.snake.addBody();
        }

        if (!this.gameOver) {
            this.snake.update();
            if (this.hasCollision()) {
                this.setGameOver(true);
                this.setVisible(false);
                System.out.println("Collision Detected");
            }
        }


 /*
        for (int i = 0; i < this.snake.getBody().size(); i++) {
            System.out.println(this.snake.getBody().get(i));
        }

        for (int i = 0; i < this.snake.getPositions().size(); i++) {
            System.out.println(
                    "size: " + i + "| x: " +
                            this.snake.getPositions().get(i)[0] +
                            ", y: " + this.snake.getPositions().get(i)[1] +
                            ", Direção: " + this.snake.getPositions().get(i)[2]);
        }

     */

        if (this.getTimer() % 10 == 0) {
            // Timer para a movimentação da cabeça
            this.setTimerIsMov(this.getTimerIsMov() + 1);
            this.setVisible(!this.isVisible());

            if (!this.gameOver) {

                if (getTimerIsMov() == 4) {
                    this.snake.changeDirection(Game.direction);
                    this.setTimerIsMov(0);
                }


                this.setTimer(0);


            }
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        g2d.drawString("Score:", Game.LEFT, Game.TOP-10);

        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        g2d.drawString("0.000.0:", Game.LEFT+85, Game.TOP-10);

        // Desenha na tela os limites do retângulo principal do game
        g2d.setColor(new Color(31, 133, 4) );

        g2d.fillRect(
                Game.LEFT,
                Game.TOP,
                Game.WIGHT,
                Game.HEIGHT
        );

        int[][] matrix = new int[Game.HEIGHT/Snake.BODYSIZE][Game.WIGHT/Snake.BODYSIZE];

        for (int l = 0; l < matrix.length; l++) {
            for (int c = 0; c < matrix[0].length; c++) {


                if (l % 2 == 0) {
                    if (c % 2 == 0) {
                        g2d.setColor(new Color(27, 117, 5) );
                    } else {
                        g2d.setColor(new Color(31, 133, 4) );
                    }
                } else {
                    if (c % 2 == 0) {
                        g2d.setColor(new Color(31, 133, 4) );
                    } else {
                        g2d.setColor(new Color(27, 117, 5) );
                    }
                }


                g2d.fillRect(
                        (Game.LEFT + (Snake.BODYSIZE * c)),
                        (Game.TOP + (Snake.BODYSIZE * l)),
                        Snake.BODYSIZE,
                        Snake.BODYSIZE);
            }
        }


        if (this.isGameOver()) {
            if (this.isVisible()) {
                this.snake.draw(g2d);
            }
        } else this.snake.draw(g2d);

        this.food.draw(g2d);

    }

    public boolean isFoodEaten() {
        if (this.snake.getBody().getFirst().intersects(this.food.getBody())) {
            return true;
        }
        return false;
    }

    public boolean hasCollision() {
        int headX = this.snake.getPositions().getFirst()[0];
        int headY = this.snake.getPositions().getFirst()[1];

        // Verifica colisão com as extremidades
        if (headX > Game.WIGHT) return true;                    // Direita
        if (headX < Game.LEFT) return true;                     // Esquerda
        if (headY > Game.HEIGHT) return true;                   // Abaixo
        if (headY < Game.TOP) return true;                      // Acima

        // Nesse for não checamos as 4 primeiras posições, pois é impossível ter uma colisão nessas posições
        for (int i = 4; i < this.snake.getBody().size(); i++) {
            System.out.println(this.snake.getBody().get(i));


            if (this.snake.getBody().getFirst().intersects(this.snake.getBody().get(i))) {
                return true;
            }
        }
        return false;
    }

    public int[] randomCoordinatesOfFood() {
        int x = random.nextInt(((Game.WIGHT / 40) - (Game.LEFT / 40)) + 1) * 40 + Game.LEFT / 40 * 40;
        int y = random.nextInt(((Game.HEIGHT / 40) - (Game.TOP / 40)) + 1) * 40 + Game.TOP / 40 * 40;

        return new int[]{x, y};
    }

    public boolean validateFoodPosition(int x, int y) {
        boolean positionValidate = true;

        for (int i = 0; i < this.snake.getPositions().size(); i++) {
            if (this.snake.getPositions().get(i)[0] == x && this.snake.getPositions().get(i)[1] == y) {
                positionValidate = false;
            }
        }
        return positionValidate;
    }

    public void coordinatesOfFood() {

        while (true) {
            int[] coordinates = this.randomCoordinatesOfFood();
            int x = coordinates[0];
            int y = coordinates[1];

            if (this.validateFoodPosition(x, y)) {
                this.food.getBody().setLocation(x, y);
                break;
            }
        }
    }

    public int getBottom() {
        return bottom;
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

    public int getTimerIsMov() {
        return timerIsMov;
    }

    public void setTimerIsMov(int timerIsMov) {
        this.timerIsMov = timerIsMov;
    }
}
