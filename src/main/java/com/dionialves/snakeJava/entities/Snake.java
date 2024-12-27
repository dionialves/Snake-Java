package main.java.com.dionialves.snakeJava.entities;

import main.java.com.dionialves.snakeJava.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

// Classe muito importante que possui os parâmetros da main.java.com.dionialves.snakeJava.entities.Snake
public class Snake {
    // Atributo que define o tamanho do corpo da snake, muito usado em todo o codigo;
    private final int bodySizeWight = Game.CELLSIZE;
    private final int bodySizeHeight = Game.CELLSIZE;

    // Lista de retângulos que compreendem a snake
    private final List<SnakeSegment> logicalSegments = new ArrayList<>();
    private final List<SnakeSegment> visualSegments = new ArrayList<>();


    // Direção que a cabeça da main.java.com.dionialves.snakeJava.entities.Snake está indo
    private String direction;

    public Snake(String direction) {

        this.setDirection(direction);
        // Desenho os 3 primeiras partes da main.java.com.dionialves.snakeJava.entities.Snake, pode observar que apenas a cabeça tem coordenadas
        // isso é necessário, pois todas as outras partes seguiram a cabeça.
        this.getLogicalSegments().add(new SnakeSegment(140, 385, this.getDirection()));
        this.getLogicalSegments().add(new SnakeSegment(105, 385, this.getDirection()));
        this.getLogicalSegments().add(new SnakeSegment(70, 385, this.getDirection()));

        this.getVisualSegments().add(new SnakeSegment(140, 385, this.getDirection()));
        this.getVisualSegments().add(new SnakeSegment(105, 385, this.getDirection()));
        this.getVisualSegments().add(new SnakeSegment(70, 385, this.getDirection()));
    }

    // Método para atualizar a logica da main.java.com.dionialves.snakeJava.entities.Snake, primeiramente movimentando a cabeça, e depois construindo o restante
    // do corpo
    public void update() {
        // Move cabeça da snake para frente, independente da direção
        this.moveHead();
        // Seta as coordenadas da snake nos retângulos, isso é importando para a logica da verificação de colisões
        this.updateVisualSegments();


    }

    // Desenha a main.java.com.dionialves.snakeJava.entities.Snake na tela
    public void draw(Graphics2D g2d) {

        // Depois desenha a snake visual
        this.drawSnake(g2d, this.getVisualSegments(), this.getVisualSegments().size(), true);
        // Desenha primeiramente a snake logica
        this.drawSnake(g2d, this.getLogicalSegments(), this.getLogicalSegments().size()-1, false);
    }

    private void drawSnake(Graphics2D g2d, List<SnakeSegment> snakeSegment, int size, boolean isShadow) {
        List<String> directions = Arrays.asList("LEFT", "RIGHT");
        int r = 78;
        int g = 123;
        int b = 244;

        for (int i = 0; i < size; i++) {

            // Aqui é uma tentativa de melhorar a logica do sombreamento, impedindo que o segmento 1 (no caso ao segundo)
            // aparece a sombra quando ele estiver descendo.
            // preciso melhorar e muito essa logica
            if (isShadow && !(i == 1 && snakeSegment.get(i).getDirection().equals("DOWN"))) {

                BufferedImage ShadowSegment = ShadowGenerator.addShadowToRectangle(
                        this.getBodySizeWight(),
                        this.getBodySizeHeight(),
                        0,
                        5,
                        new Color(0, 0, 0, 30)
                );

                g2d.drawImage(
                        ShadowSegment,
                        (int) snakeSegment.get(i).getX(),
                        (int) snakeSegment.get(i).getY(),
                        null);
            }

            g2d.setColor(new Color(r, g, b));
            // Desenho do segmento
            g2d.fillRect(
                    (int) snakeSegment.get(i).getX(),
                    (int) snakeSegment.get(i).getY(),
                    this.getBodySizeWight(),
                    this.getBodySizeHeight()
            );

            r -= 6;
            g -= 6;
            b -= 9;
            r = Math.max(0, Math.min(255, r));
            g = Math.max(0, Math.min(255, g));
            b = Math.max(0, Math.min(255, b));

        }
    }

    public void moveVisualSnake() {
        for (int i = 0; i < this.getVisualSegments().size(); i ++) {
            SnakeSegment currentSegment = this.getVisualSegments().get(i);
            String direction;

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
                    currentSegment.setY(y-5);
                    break;
                case "RIGHT":
                    currentSegment.setX(x+5);
                    break;
                case "DOWN":
                    currentSegment.setY(y+5);
                    break;
                case "LEFT":
                    currentSegment.setX(x-5 );
                    break;
            }
        }
    }

    // Método responsável por movimentar a cabeça da snake, movimetando um 1 posição no quadro dependendo da direção
    // escolhida pelo usuário. Caso não seja trocado a direção ela segue em frente, dependendo da direção.
    // Após a movimentação, adiciona a posição no atributo segments. Pode-se observar que é adicionado a posição no
    // índice 0. Essa lista de posições também tem um limite, que seria o tamanho de bodySizeWight * o tamanho do corpo da
    // snake.
    public void moveHead() {
        int x = (int) this.getLogicalSegments().getFirst().getX();
        int y = (int) this.getLogicalSegments().getFirst().getY();
        int segmentSize = 40;

        // Atualizar os segmentos seguintes
        for (int i = this.getLogicalSegments().size()-1; i > 0; i--) {

            SnakeSegment current = this.getLogicalSegments().get(i);
            SnakeSegment previous = this.getLogicalSegments().get(i - 1);

            current.setX((int)previous.getX());
            current.setY((int)previous.getY());
            current.setDirection(previous.getDirection());

        }

        // Atualiza a posição da cabeça
        this.getLogicalSegments().getFirst().setDirection(this.getDirection());
        switch (this.getDirection()) {
            case "UP":
                this.getLogicalSegments().getFirst().setY(y-35);
                break;
            case "RIGHT":
                this.getLogicalSegments().getFirst().setX(x+35);
                break;
            case "DOWN":
                this.getLogicalSegments().getFirst().setY(y+35);
                break;
            case "LEFT":
                this.getLogicalSegments().getFirst().setX(x-35);
                break;
        }




    }

    public void updateVisualSegments() {
        for (int i = 0; i < this.getLogicalSegments().size(); i++) {
            SnakeSegment logicalSnake = this.getLogicalSegments().get(i);
            SnakeSegment visualSnake = this.getVisualSegments().get(i);

            visualSnake.setX((int) logicalSnake.getX());
            visualSnake.setY((int) logicalSnake.getY());
            visualSnake.setDirection(logicalSnake.getDirection());

        }
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

    public void addSegment() {
        int x = (int) this.getLogicalSegments().getLast().getX();
        int y = (int) this.getLogicalSegments().getLast().getY();

        this.getLogicalSegments().addLast(new SnakeSegment(x, y, ""));
        this.getVisualSegments().addLast(new SnakeSegment(x, y, ""));
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

    public List<SnakeSegment> getLogicalSegments() {
        return this.logicalSegments;
    }

    public int getBodySizeWight() {
        return bodySizeWight;
    }

    public int getBodySizeHeight() {
        return bodySizeHeight;
    }

    public List<SnakeSegment> getVisualSegments() {
        return visualSegments;
    }
}
