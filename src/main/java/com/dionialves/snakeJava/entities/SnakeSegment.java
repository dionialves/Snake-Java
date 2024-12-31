package main.java.com.dionialves.snakeJava.entities;

import main.java.com.dionialves.snakeJava.Game;

import java.awt.*;

// Criei essa classa para gerenciar os seguimentos da snake, pois preciso que cada seguimento seja um retângulo, pois
// uso o retângulo para algumas validações, como colisão, mas também preciso que cada seguimento tenha uma direção.
// Melhor opção foi criar uma classe separada
public class SnakeSegment extends Rectangle {
    private String direction;

    public SnakeSegment(int x, int y, String direction) {
        this.x = x;
        this.y = y;
        this.width = Game.CELLSIZE;
        this.height = Game.CELLSIZE;
        this.direction = direction;
    }

    public void setX(int x) { this.x = x; }

    public void setY(int y) { this.y = y; }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
