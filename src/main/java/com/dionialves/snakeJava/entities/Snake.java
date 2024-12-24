package main.java.com.dionialves.snakeJava.entities;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Classe muito importante que possui os parâmetros da main.java.com.dionialves.snakeJava.entities.Snake
public class Snake {
    // Atributo que define o tamanho do corpo da snake, muito usado em todo o codigo;
    private final int bodySizeWight = 40;
    private final int bodySizeHeight = 40;

    // Lista de retângulos que compreendem a snake
    private final List<Rectangle> body = new ArrayList<>();
    // Lista de posições, uso ela para desenhas e snake e dar uma sensação de suavidade na movimentação da snake
    private final List<PositionSnake> positions = new ArrayList<>();
    // Direção que a cabeça da main.java.com.dionialves.snakeJava.entities.Snake está indo
    private String direction;

    public Snake(String direction) {
        // Desenho os 3 primeiras partes da main.java.com.dionialves.snakeJava.entities.Snake, pode observar que apenas a cabeça tem coordenadas
        // isso é necessário, pois todas as outras partes seguiram a cabeça.
        this.getBody().add(new Rectangle(120, 280, this.getBodySizeWight(), this.getBodySizeHeight()));
        this.getBody().add(new Rectangle( this.getBodySizeWight(), this.getBodySizeHeight()));
        this.getBody().add(new Rectangle( this.getBodySizeWight(), this.getBodySizeHeight()));

        this.setDirection(direction);

        for (int i = 0; i < 80; i++) {
            this.getPositions().add(
                    new PositionSnake(
                            this.getBody().getFirst().x - i,
                            this.getBody().getFirst().y,
                            this.getDirection()
                    )
            );
        }
    }

    // Método para atualizar a logica da main.java.com.dionialves.snakeJava.entities.Snake, primeiramente movimentando a cabeça, e depois construindo o restante
    // do corpo
    public void update() {
        // Move cabeça da snake para frente, independente da direção
        this.moveHead();
        // Seta as coordenadas da snake nos retângulos, isso é importando para a logica da verificação de colisões
        this.buildSnake();
    }

    // Desenha a main.java.com.dionialves.snakeJava.entities.Snake na tela
    public void draw(Graphics2D g2d) {
        for (int i = 0; i < this.getPositions().size(); i++) {

            g2d.setColor(new Color(255, 255, 255));

            RoundRectangle2D.Double roundRect;
            roundRect = new RoundRectangle2D.Double(
                    this.getPositions().get(i).getX(),
                    this.getPositions().get(i).getY(),
                    this.getBodySizeWight(),
                    this.getBodySizeHeight(),
                    100,
                    100);

            g2d.fill(roundRect);
        }
    }


    // Método responsável por movimentar a cabeça da snake, movimetando um 1 posição no quadro dependendo da direção
    // escolhida pelo usuário. Caso não seja trocado a direção ela segue em frente, dependendo da direção.
    // Após a movimentação, adiciona a posição no atributo positions. Pode-se observar que é adicionado a posição no
    // índice 0. Essa lista de posições também tem um limite, que seria o tamanho de bodySizeWight * o tamanho do corpo da
    // snake.
    public void moveHead() {
        switch (this.getDirection()) {
            case "UP":
                this.getBody().getFirst().y -= 1;
                break;
            case "RIGHT":
                this.getBody().getFirst().x += 1;
                break;
            case "DOWN":
                this.getBody().getFirst().y += 1;
                break;
            case "LEFT":
                this.getBody().getFirst().x -= 1;
                break;
        }
        this.getPositions().addFirst(
                new PositionSnake(
                        (int) this.getBody().getFirst().getX(),
                        (int) this.getBody().getFirst().getY(),
                        this.getDirection()
                )
        );

        if (this.getPositions().size() > (this.getBody().size() * this.getBodySizeWight()) - this.getBodySizeWight()) {
            this.getPositions().removeLast();
        }
    }

    public void buildSnake() {

    }

    // Nesse método altera a direção da snake, foi construído para não permitir que a snake faça um retorno de 180 graus
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

    // Adiciona um novo retângulo ao corpo da snake.
    public void addBody() {
        this.getBody().add(new Rectangle(this.getBodySizeWight(), this.getBodySizeHeight()));
    }

    public List<Rectangle> getBody() {
        return body;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        if (!Objects.equals(this.direction, direction)) {
            this.direction = direction;
            SoundManager.playSound("move");
        }
    }

    public List<PositionSnake> getPositions() {
        return this.positions;
    }

    public int getBodySizeWight() {
        return bodySizeWight;
    }

    public int getBodySizeHeight() {
        return bodySizeHeight;
    }
}
