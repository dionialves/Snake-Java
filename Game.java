import java.awt.*;
import java.sql.Time;
import java.util.Random;

public class Game {
    // Atributos de controle do game
    public static final int WIGHT = GameEngine.WIDTH -  Snake.BODYSIZE *2;
    public static final int HEIGHT = GameEngine.HEIGHT - (Snake.BODYSIZE * 2);
    public static final int TOP = 40;
    public static final int LEFT = 40;
    private boolean gameOver = false;
    public static String direction = "RIGHT";
    private final Random random = new Random();

    private int timer = 0;
    private int timerIsMov = 0;

    // Instancia das classes pertencentes ao projeto
    private final Snake snake = new Snake(direction);
    private final Foods food = new Foods();

    public Game() {
        // Inicialização do food com coordenadas randômicas, posteriormente a isso, ele será chamado apenas se a snake
        // comer o food
        this.coordinatesOfFood();
    }

    public void update() {
        // Controle de tempo para execução do loop da game
        this.setTimer(this.getTimer() + 1);
        if (!this.isGameOver()) {
            // Atualiza as posições da Snake
            this.snake.update();
            // Verifica se existe alguma colisão
            if (this.hasCollision()) {
                // Caso sim:
                // Seta game over como true
                this.setGameOver(true);
            }
            // Se snake comeu o food:
            // 1: Gera uma nova posição para food
            // 2: Adiciona mais um retângulo a Snake
            if (this.isFoodEaten()) {
                this.coordinatesOfFood();
                this.snake.addBody();
            }

            // Aqui entra a movimentação da Snake, a cada 10 frames do game, entra para a contabilização da contador de
            // movimento, o contador de movimento se limita a 4, então a cada 40 posições, temos a movimentação.
            // Isso se faz necessário para ter tempo de capturar o movimento do teclado, mas só executar na posição
            // correta, respeitando o quadro do game
            if (this.getTimer() % 10 == 0) {
                // Timer para a movimentação da cabeça
                this.setTimerIsMov(this.getTimerIsMov() + 1);
                // Após 4 times de movimentação, que representam 40 frames, a Snake se movimenta, chamando o método
                // changeDirection
                if (getTimerIsMov() == 4) {
                    this.snake.changeDirection(Game.direction);
                    // zera o timer de movimento
                    this.setTimerIsMov(0);
                }
                // zera o timer do loop frame
                this.setTimer(0);
            }
        }
    }

    public void draw(Graphics2D g2d) {
        // Desenho do food no top da pagina, como imagem contador dos pontos
        g2d.setColor(Color.red);
        g2d.fillOval( Game.LEFT, Game.TOP-32, 25, 25);

        // Desenha os pontos obtidos no game, cada food que a snake comer será um ponto
        g2d.setColor(new Color(227, 185, 31));
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        // Para definição do ponto eu pego o tamanho da Snake e diminuo 3, que são as posições iniciais
        g2d.drawString(Integer.toString(this.snake.getBody().size()-3), Game.LEFT+35, Game.TOP-13);

        // Define uma matrix para desenhar o quadrante do game, com tamanho de cada posição o valor de Snake.BODYSIZE
        int[][] matrix = new int[Game.HEIGHT/Snake.BODYSIZE][Game.WIGHT/Snake.BODYSIZE];
        // Loop para desenhar esse quatro, que ficará em formato xadrez!
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
        // Desenha a Snake
        this.snake.draw(g2d);
        // Desenha o Food
        this.food.draw(g2d);
    }

    // método responsável por avaliar se a snake comeu o food. Usando o método intersects, que verifica se um retànfulo
    // sobrepôs outro!
    public boolean isFoodEaten() {
        if (this.snake.getBody().getFirst().intersects(this.food.getBody())) {
            return true;
        }
        return false;
    }

    // Método responsável por verificar se ouve uma colisão, ele verificas as colições nos limites do quadro e também
    // se a Snake colidiu com ela mesma
    public boolean hasCollision() {
        // Verificação se colidiu com as extremidades
        int headX = this.snake.getPositions().getFirst()[0];
        int headY = this.snake.getPositions().getFirst()[1];

        if (headX > Game.WIGHT) return true;                    // Direita
        if (headX < Game.LEFT) return true;                     // Esquerda
        if (headY > Game.HEIGHT) return true;                   // Abaixo
        if (headY < Game.TOP) return true;                      // Acima

        // Verificação se a snake colidiu com ela mesma
        // Nesse for não checamos as 4 primeiras posições, pois é impossível ter uma colisão nessas posições
        for (int i = 4; i < this.snake.getBody().size(); i++) {
            if (this.snake.getBody().getFirst().intersects(this.snake.getBody().get(i))) {
                return true;
            }
        }
        return false;
    }

    // Gera posições randômicas, dentro do quadro do game, mas usando uma logica para gerar certas de 40 em 40, para
    // ficar dentro de cada posição da matrix.
    public int[] randomCoordinatesOfFood() {
        int x = random.nextInt(((Game.WIGHT / 40) - (Game.LEFT / 40)) + 1) * 40 + Game.LEFT / 40 * 40;
        int y = random.nextInt(((Game.HEIGHT / 40) - (Game.TOP / 40)) + 1) * 40 + Game.TOP / 40 * 40;

        return new int[]{x, y};
    }

    // Método para validar se as coordenadas passadas não então no mesmo trajeto na Snake, garantindo que não será criado
    // nenhum food em cima da snake
    public boolean validateFoodPosition(int x, int y) {
        boolean positionValidate = true;

        for (int i = 0; i < this.snake.getPositions().size(); i++) {
            if (this.snake.getPositions().get(i)[0] == x && this.snake.getPositions().get(i)[1] == y) {
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

    public int getTimerIsMov() {
        return timerIsMov;
    }

    public void setTimerIsMov(int timerIsMov) {
        this.timerIsMov = timerIsMov;
    }
}
