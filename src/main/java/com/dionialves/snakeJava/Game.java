package main.java.com.dionialves.snakeJava;

import main.java.com.dionialves.snakeJava.entities.Foods;
import main.java.com.dionialves.snakeJava.entities.Snake;
import main.java.com.dionialves.snakeJava.entities.SoundManager;

import java.awt.*;
import java.util.Random;

// Ajustes que devem ser feitos:

// Bugs
// 1: Ajustar a logica de colisão dos limites;
// 2: Ajustar a logica de colisão na própria snake
// 3: Ao adicionar um novo segmento, a direção desse novo segmento não segue a snake, causando um efeito visual não desejado
// 4: Melhorar a logica de sombreamento da snake
// 5: Ajustar o aumento do food, pois quando ele aumenta, continua tendo como referência o mesmo x e y, com isso
//    o aumento parte de um ponto, gostaria que aumentasse em todos os sentidos.
// 6: Quando existe um movimento repetitivo e rápido o som não sai direito

// Implementações
// 1: Desenhar uma cabeça para a snake
// 2: Ajustar o topo do game
// 3: Adicionar imagem do food

public class Game {
    // Atributos de controle do game
    public static final int WIGHT = 595;
    public static final int HEIGHT = 525;
    public static final int TOP = 105;
    public static final int LEFT = 35;
    public static final int CELLSIZE = 35;

    private boolean gameOver = false;
    public static String direction = "RIGHT";
    private final Random random = new Random();

    private int timer = 0;

    // Instancia das classes pertencentes ao projeto
    private final Snake snake = new Snake(direction);
    private final Foods food = new Foods(true, true, Game.CELLSIZE);

    public Game() {
        // Inicialização do food com coordenadas randômicas, posteriormente a isso, ele será chamado apenas se a snake
        // comer o food
        this.coordinatesOfFood();

        // Load sons do game
        SoundManager.loadSound("bite", "src/main/resources/sounds/bite.wav");
        SoundManager.loadSound("levelup", "src/main/resources/sounds/levelup.wav");
        SoundManager.loadSound("gameover", "src/main/resources/sounds/gameover.wav");
        SoundManager.loadSound("move", "src/main/resources/sounds/move.wav");
    }

    public void update() {

        if (!this.isGameOver()) {

            this.setTimer(this.getTimer() + 1);


            // Se snake comeu o food:
            // 1: Adiciona o som de mordida
            // 2: Gera uma nova posição para food
            // 3: Adiciona mais um retângulo a main.java.com.dionialves.snakeJava.entities.Snake
            if (this.isFoodEaten()) {
                SoundManager.playSound("bite");
                this.coordinatesOfFood();
                this.snake.addSegment();
            }

            if (this.getTimer() % 7 == 0) {
                // Atualiza as posições da main.java.com.dionialves.snakeJava.entities.Snake
                this.snake.update();

                int x = (int) this.snake.getLogicalSegments().getFirst().getX();
                int y = (int) this.snake.getLogicalSegments().getFirst().getY();

                if (x % Game.CELLSIZE == 0 && y % Game.CELLSIZE == 0) {
                    this.snake.changeDirection(Game.direction);

                }
            }

            this.snake.moveVisualSnake();

            // Verifica se existe alguma colisão
            if (this.hasCollision()) {
                // Caso sim:
                // Seta game over como true
                this.setGameOver(true);
                // emite som de game
                SoundManager.playSound("gameover");
            }



        }
    }

    public void draw(Graphics2D g2d) {



        // Define uma matrix para desenhar o quadrante do game, com tamanho de cada posição o valor de CELLSIZE
        int[][] matrix = new int[Game.HEIGHT/ Game.CELLSIZE][Game.WIGHT/ Game.CELLSIZE];
        // Loop para desenhar esse quatro, que ficará em formato xadrez!
        for (int l = 0; l < matrix.length; l++) {
            for (int c = 0; c < matrix[0].length; c++) {
                if (l % 2 == 0) {
                    if (c % 2 == 0) {
                        g2d.setColor(new Color(161, 208, 73) );
                    } else {
                        g2d.setColor(new Color(169, 214, 81) );
                    }
                } else {
                    if (c % 2 == 0) {
                        g2d.setColor(new Color(169, 214, 81) );
                    } else {
                        g2d.setColor(new Color(161, 208, 73) );
                    }
                }
                g2d.fillRect(
                        (Game.LEFT + (Game.CELLSIZE * c)),
                        (Game.TOP + (Game.CELLSIZE * l)),
                        Game.CELLSIZE,
                        Game.CELLSIZE);
            }
        }

        // Desenha a main.java.com.dionialves.snakeJava.entities.Snake
        this.snake.draw(g2d);
        // Desenha o Food
        this.food.draw(g2d);


    }

    // método responsável por avaliar se a snake comeu o food. Usando o método intersects, que verifica se um retànfulo
    // sobrepôs outro!
    public boolean isFoodEaten() {
        return this.snake.getVisualSegments().getFirst().intersects(this.food.getBody());
    }

    // Método responsável por verificar se ouve uma colisão, ele verificas as colições nos limites do quadro e também
    // se a main.java.com.dionialves.snakeJava.entities.Snake colidiu com ela mesma
    public boolean hasCollision() {
        // Verificação se colidiu com as extremidades
        double headX = this.snake.getVisualSegments().getFirst().getX();
        double headY = this.snake.getVisualSegments().getFirst().getY();

        if (headX > Game.WIGHT) return true;                    // Direita
        if (headX < Game.LEFT) return true;                     // Esquerda
        if (headY > Game.HEIGHT + Game.CELLSIZE * 2 ) return true;                   // Abaixo
        if (headY < Game.TOP) return true;                      // Acima

        // Verificação se a snake colidiu com ela mesma
        // Nesse for não checamos as 4 primeiras posições, pois é impossível ter uma colisão nessas posições
        for (int i = 4; i < this.snake.getLogicalSegments().size(); i++) {
            if (this.snake.getLogicalSegments().getFirst().intersects(this.snake.getLogicalSegments().get(i))) {
                return true;
            }
        }
        return false;
    }

    // Gera posições randômicas, dentro do quadro do game, mas usando uma logica para gerar certas de 40 em 40, para
    // ficar dentro de cada posição da matrix.
    public int[] randomCoordinatesOfFood() {
        int x = random.nextInt(((Game.WIGHT / 35) - (Game.LEFT / 35)) + 1) * 35 + Game.LEFT / 35 * 35;
        int y = random.nextInt(((Game.HEIGHT / 35) - (Game.TOP / 35)) + 1) * 35 + Game.TOP / 35 * 35;

        return new int[]{x, y};
    }

    // Método para validar se as coordenadas passadas não então no mesmo trajeto na main.java.com.dionialves.snakeJava.entities.Snake, garantindo que não será criado
    // nenhum food em cima da snake
    public boolean validateFoodPosition(int x, int y) {
        boolean positionValidate = true;

        for (int i = 0; i < this.snake.getLogicalSegments().size(); i++) {
            if (this.snake.getLogicalSegments().get(i).getX() == x && this.snake.getLogicalSegments().get(i).getY() == y) {
                positionValidate = false;
            }
        }
        return positionValidate;
    }

    // Método que é responsável por definir as coordenadas válidas, usando randomCoordinatesOfFood(),
    // e validateFoodPosition(). Usando um loop while, com condição true, só irá sair do loop quando criar uma posição
    // valida.
    public void coordinatesOfFood() {

        while (true) {
            int[] coordinates = this.randomCoordinatesOfFood();
            int x = coordinates[0];
            int y = coordinates[1];

            // Se entrar nessa condição, significa que se encontrou uma posição valida, então seta a nova posição no
            // food e sai do loop;
            if (this.validateFoodPosition(x, y)) {
                this.food.getBody().setLocation(x, y);
                break;
            }
        }
    }

    public Snake getSnake() {
        return this.snake;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }
}
