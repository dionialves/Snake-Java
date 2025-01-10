package main.java.com.dionialves.snakeJava.entities;

import main.java.com.dionialves.snakeJava.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/**
 * Representa a Snake do game
 *
 * <p>Esta classe possui a implementação da Snake.</p>
 *
 * @author Dioni Alves
 * @version 0.3.0
 * @since 2024-12-20
 */

public class Snake {
    // Atributo que define o tamanho do corpo da snake;
    private static final int BODY_SIZE_WIGHT = Game.CELLSIZE;
    private static final int BODY_SIZE_HEIGHT = Game.CELLSIZE;

    // Lista da classe SnakeSegment, customizada para representar cada segmento da Snake
    private final List<SnakeSegment> logicalSegments = new ArrayList<>();
    private final List<SnakeSegment> visualSegments = new ArrayList<>();

    // Sprite da snake em píxel art
    private final SnakeSprite spriteSheetPixel;
    private final BufferedImage snakeEye;
    private final BufferedImage snakeNose;

    // Atributo responsável por guardar a cor da snake
    private final Map<String, Integer> colorSnakeRGB = new HashMap<>();

    // Direção que a cabeça da snakeJava.entities.Snake está indo
    private String direction;

    public Snake(String direction) {
        // Inicialização da direção da snake
        this.setDirection(direction);
        // Iniciando a snake logica com as três primeiras posições
        this.getLogicalSegments().add(new SnakeSegment(140, 385, this.getDirection()));
        this.getLogicalSegments().add(new SnakeSegment(105, 385, this.getDirection()));
        this.getLogicalSegments().add(new SnakeSegment(70, 385, this.getDirection()));
        // iniciando a snake visual com as três primeiras posições
        this.getVisualSegments().add(new SnakeSegment(140, 385, this.getDirection()));
        this.getVisualSegments().add(new SnakeSegment(105, 385, this.getDirection()));
        this.getVisualSegments().add(new SnakeSegment(70, 385, this.getDirection()));

        // Carregamento da sprite
        String sprite = "/main/resources/images/sprites-snake-pixel.png";
        this.spriteSheetPixel = new SnakeSprite(sprite);
        this.snakeEye = this.getSpriteSheetPixel().getImageFromSprite(23, 40, 40,40);
        this.snakeNose = this.getSpriteSheetPixel().getImageFromSprite(50, 206, 10,90);
    }

    /**
     * Atualiza as posições da snake
     *
     * <p></>Aqui temos a atualização das posições da snake. Primeiramente movemos o corpo, ou seja, todas as partes menos a
     * cabeça, depois movimentamos a cabeça.</p>
     *
     * <p>Depois copiamos as posições da snake logica para a sake visual.</p>
     */

    // Método para atualizar a logica da Snake, primeiramente movimentando a cabeça, e depois construindo o restante
    // do corpo
    public void update() {
        // Update body
        this.updateBodyPosition();
        // Move cabeça da snake para frente, independente da direção
        this.moveHead();
        // Seta as coordenadas da snake nos retângulos, isso é importando para a lógica da verificação de colisões
        this.syncSnakeSegments(this.getLogicalSegments(), this.getVisualSegments());
    }

    /**
     * Desenha a snake na tela;
     *
     * <p>Neste método, centralizo todos os desenhos da snake. Primeiramente a snake visual, depois a snake logica,
     *  em seguida os olho e cauda</p>
     *
     * <p>Uma explicação sobre snake logicas e snake visual:
     * Snake logica será a base da movimentação da snake, se movimentando de setor em setor da matrix, isso é necessário
     * para termos uma snake base, onde iremos usar a mesma para nos movimentar pela matriz do jogo.</p>
     *
     * <p>Snake Visual é usada apenas para suavizar a movimentação, e sua atualização é de 1 píxel por vez, criando uma
     * sensação de fluides na movimentação.
     * </p>
     *
     * @param g2d recebido do paintComponent da classe GameEngine
     */
    public void draw(Graphics2D g2d) {
        // Depois desenha a snake visual
        this.drawSnake(g2d, this.getVisualSegments(), this.getVisualSegments().size());
        // Desenha primeiramente a snake logica
        this.drawSnake(g2d, this.getLogicalSegments(), this.getLogicalSegments().size()-1);

        // Desenha os Olhos da Snake
        this.drawEyes(g2d);
        // Desenha o nariz
        this.drawNose(g2d);
    }

    /**
     * Desenha a snake na tela;
     *
     * <p>Responsável por desenha a snake, tanto logica como visual</>
     *
     * @param g2d recebido do draw desta mesma classe.
     * @param snakeSegment recebe o tipo de snake, logica ou visual
     * @param size recebe o tamanho da snake
     *
     */
    private void drawSnake(Graphics2D g2d, List<SnakeSegment> snakeSegment, int size) {
        this.setColorSnakeRGB(78, 123, 244);

        for (int i = 0; i < size; i++) {

            g2d.setColor(new Color(
                    this.getColorSnakeRGB().get("r"),
                    this.getColorSnakeRGB().get("g"),
                    this.getColorSnakeRGB().get("b")
            ));
            // Desenho do segmento
            g2d.fillRect(
                    (int) snakeSegment.get(i).getX(),
                    (int) snakeSegment.get(i).getY(),
                    BODY_SIZE_WIGHT,
                    BODY_SIZE_HEIGHT
            );

            this.darkenColorForSegment();
        }
    }

    /**
     * Desenha os olhos da Snake
     *
     * <p>Responsável por desenhar os olhos na snake, e também a movimentação dos olhos, dependêndo da direção que
     * a snake estiver</p>
     *
     * @param g2d recebido do draw desta mesma classe.
     *
     */
    private void drawEyes(Graphics2D g2d) {

        Object[] positionsEyes = this.generationPositionEyes();

        this.getSpriteSheetPixel().drawRotatedImage(
                g2d,
                this.getSnakeEye(),
                (int) positionsEyes[0],
                (int) positionsEyes[1],
                (int) positionsEyes[2],
                15,
                15
        );
        this.getSpriteSheetPixel().drawRotatedImage(
                g2d,
                this.getSnakeEye(),
                (int) positionsEyes[0],
                (int) positionsEyes[3],
                (int) positionsEyes[4],
                15,
                15
        );
    }

    /**
     * Desenha o nariz da snake
     *
     * <p>Responsável por desenhar o nariz da snake, e também sua movimentação, dependêndo da direção que
     * a snake estiver</p>
     *
     * @param g2d recebido do draw desta mesma classe.
     *
     */
    private void drawNose(Graphics2D g2d) {

        Object[] infoNose = this.generationPositionNose();
        this.getSpriteSheetPixel().drawRotatedImage(
                g2d,
                this.getSnakeNose(),
                (int) infoNose[0],
                (int) infoNose[1],
                (int) infoNose[2],
                3,
                25
        );
    }

    /**
     * Responsável por gerar as coordenadas dos olhos da snake
     */
    private Object[] generationPositionEyes() {
        SnakeSegment first = this.getVisualSegments().getFirst();

        int angle = 0;
        int xEyeRight = 0;
        int yEyeRight = 0;
        int xEyeLeft = 0;
        int yEyeLeft = 0;

        switch (this.getDirection()) {
            case "RIGHT":
                xEyeRight  = (int) first.getX();
                yEyeRight  = (int) first.getY();
                xEyeLeft  = (int) first.getX();
                yEyeLeft = (int) first.getY() + 19;
                break;
            case "LEFT":
                angle = 180;

                xEyeRight  = (int) first.getX()-5;
                yEyeRight  = (int) first.getY()-5;
                xEyeLeft  = (int) first.getX() -5;
                yEyeLeft = (int) first.getY() -24;
                break;
            case "UP":
                angle = 270;

                xEyeRight  = (int) first.getX();
                yEyeRight  = (int) first.getY()-5;
                xEyeLeft  = (int) first.getX() + 19;
                yEyeLeft = (int) first.getY()-5;
                break;
            case "DOWN":
                angle = 90;

                xEyeRight  = (int) first.getX()-5;
                yEyeRight  = (int) first.getY();
                xEyeLeft  = (int) first.getX() - 24;
                yEyeLeft = (int) first.getY();
                break;
        }

        return new Object[]{angle, xEyeRight, yEyeRight, xEyeLeft, yEyeLeft};
    }

    /**
     * Responsável por gerar as coordenadas do nariz da snake
     */
    private Object[] generationPositionNose() {
        SnakeSegment first = this.getVisualSegments().getFirst();
        int angle = 0;
        int x = 0;
        int y = 0;

        switch (this.getDirection()) {
            case "RIGHT":
                x  = (int) first.getX() + 25;
                y  = (int) first.getY() + 5;
                break;
            case "LEFT":
                x  = (int) first.getX() + 5;
                y  = (int) first.getY() + 5;
                break;
            case "UP":
                angle = 90;

                x  = (int) first.getX() - 20;
                y  = (int) first.getY() - 35;
                break;
            case "DOWN":
                angle = 90;

                x  = (int) first.getX() - 20;
                y  = (int) first.getY() - 15;
                break;
        }

        return new Object[]{angle, x, y};
    }

    /**
     * Reduz o tom da cor da snake
     *
     * <p>Este método altera o tom da cor da snake para baixo, ou seja, a cada execução ele fica mais escuro</p>
     *
     */
    private void darkenColorForSegment() {

        int r = this.getColorSnakeRGB().get("r");
        int g = this.getColorSnakeRGB().get("g");
        int b = this.getColorSnakeRGB().get("b");

        r -= 6;
        g -= 6;
        b -= 9;
        r = Math.max(0, Math.min(255, r));
        g = Math.max(0, Math.min(255, g));
        b = Math.max(0, Math.min(255, b));

        this.setColorSnakeRGB(r, g, b);
    }

    /**
     * Faz a movimentação da Snake Visual
     *
     * <p>Na movimentação da Snake temos a snake Visual, que faz uma movimentação mais suave avançando sempre 1px.
     * Este método é responsável por fazer a movimentação de cada seguimento, 1px por vez, na direção que o segmento tem
     * </p>
     *
     */
    public void moveVisualSnake() {
        for (int i = 0; i < this.getVisualSegments().size(); i ++) {
            SnakeSegment currentSegment = this.getVisualSegments().get(i);
            String direction;
            int speed = 1;

            int x = (int) currentSegment.getX();
            int y = (int) currentSegment.getY();

            if (i == 0) {
                direction = this.getDirection();
            } else {
                SnakeSegment previousSegment = this.getVisualSegments().get(i-1);
                direction = previousSegment.getDirection();
            }

            switch (direction) {
                case "UP":
                    currentSegment.setY(y-speed);
                    break;
                case "RIGHT":
                    currentSegment.setX(x+speed);
                    break;
                case "DOWN":
                    currentSegment.setY(y+speed);
                    break;
                case "LEFT":
                    currentSegment.setX(x-speed);
                    break;
            }
        }
    }

    /**
     * Faz a movimentação da cabeça da snake logica
     *
     * <p> Primeiramente é atualizado a direção da cabeça da snake, depois movimenta a cabeça da cobra, na direção
     * definida para a snake. O tamanho dessa movimentação segue o espaçamento da matrix do jogo.</p>
     *
     */
    private void moveHead() {
        int x = (int) this.getLogicalSegments().getFirst().getX();
        int y = (int) this.getLogicalSegments().getFirst().getY();

        // Atualiza a posição da cabeça
        this.getLogicalSegments().getFirst().setDirection(this.getDirection());
        switch (this.getDirection()) {
            case "UP":
                this.getLogicalSegments().getFirst().setY(y-Game.CELLSIZE);
                break;
            case "RIGHT":
                this.getLogicalSegments().getFirst().setX(x+Game.CELLSIZE);
                break;
            case "DOWN":
                this.getLogicalSegments().getFirst().setY(y+Game.CELLSIZE);
                break;
            case "LEFT":
                this.getLogicalSegments().getFirst().setX(x-Game.CELLSIZE);
                break;
        }
    }

    /**
    * Atualiza a posição e direção dos segmentos corporais da cobra.
    *
    * <p>Este método percorre os segmentos do corpo da cobra, começando no último segmento,
    * e atualiza a posição e direção de cada segmento para corresponder ao segmento que o precede.
    * A cabeça da cobra não foi modificada, pois É atualizada separadamente.
    * Isso garante que o corpo da cobra siga o movimento da cabeça de forma lógica.</p>
    *
    */
    private void updateBodyPosition() {
        // Atualizar os segmentos seguintes
        for (int i = this.getLogicalSegments().size()-1; i > 0; i--) {
            SnakeSegment current = this.getLogicalSegments().get(i);
            SnakeSegment previous = this.getLogicalSegments().get(i - 1);

            current.setX((int)previous.getX());
            current.setY((int)previous.getY());
            current.setDirection(previous.getDirection());
        }
    }

    /**
     * Sincroniza as informações da snake visual com as da snake logica
     *
     * <p>Essa atualização ocorre a cada ciclo de frames, ou seja, quando a snake atinge um ponto de movimento,
     * que é definido, na condição "this.getTimer() % Game.CELLSIZE == 0". Isso é necessário principalmente para
     * atualizar a direção de cada seguimento.</p>
     *
     * <p>Esse método não pode ser confundido com this.moveVisualSnake(), são parecidos mais com funções diferentes</p>
     *
     */
    private void syncSnakeSegments(List<SnakeSegment> listSnakeOrigin, List<SnakeSegment> listSnakeDestination) {
        for (int i = 0; i < listSnakeOrigin.size(); i++) {
            SnakeSegment origin = listSnakeOrigin.get(i);
            SnakeSegment destination = listSnakeDestination.get(i);

            destination.setX((int) origin.getX());
            destination.setY((int) origin.getY());
            destination.setDirection(origin.getDirection());
        }
    }

    /**
     * Atualiza a direção da snake
     *
     * <p>Esse método atualiza a direção da snake, esse atributo é usado para atualizar a direção da cabeça da snake,
     * quando existe um ponto de movimentação. Também é validado se a nova direção é valida, pois eu não posso mudar
     * a direção da snake de uma para down e vice e versa, e nem de right para left e vice e versa</p>
     *
     * @param direction nova direção da snake.
     *
     */
    public void changeDirection(String direction) {
        switch (direction) {
            case "UP":
                if (!this.getDirection().equals("DOWN")) {
                    this.setDirection("UP");
                }
                break;
            case "RIGHT":
                if (!this.getDirection().equals("LEFT")) {
                    this.setDirection("RIGHT");
                }
                break;
            case "DOWN":
                if (!this.getDirection().equals("UP")) {
                    this.setDirection("DOWN");
                }
                break;
            case "LEFT":
                if (!this.getDirection().equals("RIGHT")) {
                    this.setDirection("LEFT");
                }
                break;
        }
    }

    /**
     * Adiciona um segmento novo ao final da snake
     *
     * <p>Esse método pe chamado quando a snake come uma fruta, então é adicionado um novo segmento ao final da lista
     * de segmentos lógicos e visuais. Pode ser observado que o novo segmento é gerado em uma posição não visível na
     * tela, isso é necessário para não gerar nenhuma deformidade na snake. Posteriormente esse segmento será
     * atualizado pelo updateBodyPosition no caso da snake logica e syncVisualSegmentsWithLogicalSegments no caso
     * da snake visual</p>
     *
     */
    public void addSegment() {
        this.getLogicalSegments().addLast(new SnakeSegment(-100, -100, ""));
        this.getVisualSegments().addLast(new SnakeSegment(-100, -100, ""));
    }

    // Getters e Setters
    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        if (!Objects.equals(this.direction, direction)) {
            this.direction = direction;
            SoundManager.playSound("move");
        }
    }

    public List<SnakeSegment> getLogicalSegments() {
        return this.logicalSegments;
    }

    public List<SnakeSegment> getVisualSegments() {
        return visualSegments;
    }

    public SnakeSprite getSpriteSheetPixel() {
        return spriteSheetPixel;
    }

    public BufferedImage getSnakeEye() {
        return snakeEye;
    }

    public BufferedImage getSnakeNose() {
        return snakeNose;
    }

    public Map<String, Integer> getColorSnakeRGB() {
        return colorSnakeRGB;
    }

    private void setColorSnakeRGB(int r, int g, int b) {
            this.getColorSnakeRGB().put("r", r);
            this.getColorSnakeRGB().put("g", g);
            this.getColorSnakeRGB().put("b", b);
    }
}
