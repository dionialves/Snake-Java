import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Snake {
    public static final int BODYSIZE = 40;
    public static final int NEWSIZE = 40;
    private final List<Rectangle> body = new ArrayList<>();
    private String direction;
    private final List<int[]> positions = new ArrayList<>();

    public Snake(String direction) {
        this.getBody().add(new Rectangle(120, 280, Snake.NEWSIZE, Snake.NEWSIZE));
        this.getBody().add(new Rectangle( Snake.NEWSIZE, Snake.NEWSIZE));
        this.getBody().add(new Rectangle( Snake.NEWSIZE, Snake.NEWSIZE));

        this.getPositions().addFirst(new int[]{this.getBody().getFirst().x, this.getBody().getFirst().y});

        this.setDirection(direction);
    }

    public void update() {
        // Move cabeça da snake para frente, independente da direção
        this.moveHead();
        // Seta as coordenadas da snake nos retângulos, isso é importando para a logica da verificação de colisões
        this.buildSnake();
    }

    public void draw(Graphics2D g2d) {

        for (int i = 0; i < this.getPositions().size(); i++) {

            int color = (int) (113 - (1+(i*0.05)));
            if (color < 0) {
                color = 0;
            }
            g2d.setColor(new Color(255, 255, 255));


            g2d.fillRect(
                    this.getPositions().get(i)[0],
                    this.getPositions().get(i)[1],
                    Snake.NEWSIZE,
                    Snake.NEWSIZE
            );


        }
    }

    public void moveHead() {
        int dire = 0;
        switch (this.getDirection()) {
            case "UP":
                this.getBody().getFirst().y -= 1;
                dire = 0;
                break;
            case "RIGHT":
                this.getBody().getFirst().x += 1;
                dire = 1;
                break;
            case "DOWN":
                this.getBody().getFirst().y += 1;
                dire = 2;
                break;
            case "LEFT":
                this.getBody().getFirst().x -= 1;
                dire = 3;
                break;
        }
        this.getPositions().addFirst(new int[]{this.getBody().getFirst().x, this.getBody().getFirst().y, dire});

        if (this.getPositions().size() > (this.getBody().size() * Snake.BODYSIZE) - Snake.BODYSIZE) {
            this.getPositions().removeLast();
        }
    }

    public void buildSnake() {
        // A construção da snake se refere a setar as coordenadas x e y das posições visuais que não diferentes das
        // posições do corpo da snake, visualmente eu tenho um retângulo a cada 1pixel, mas os retângulos reais são de
        // 40pixel em 40pixel. Isso é feito para dar uma sensação de continuidade na snake, visualmente falando.

        if ((this.getPositions().size() / (this.getBody().size() - 1)) >= Snake.BODYSIZE)  {
            for (int i = 1; i < this.getBody().size(); i++) {

                // Aqui pegamos as coordenadas de 40 em 40 da lista total de coordenadas, para definir logicamente
                // cada parte do corda da snake, nesse for a primeira posição fica de fora, pois é a cabeça.
                // observe que foi colocado -1 na formula: i * Snake.BODYSIZE)-1. Isso força a pegar a posição anterior
                // aos 40, ou seja, pega a posição 39, 79, 119 que são as posições reais dos retangulos na lista
                this.getBody().get(i).x = this.getPositions().get((i * Snake.BODYSIZE)-1)[0];
                this.getBody().get(i).y = this.getPositions().get((i * Snake.BODYSIZE)-1)[1];

            }
        }
    }

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

    public void addBody() {
        this.getBody().add(new Rectangle( Snake.NEWSIZE, Snake.NEWSIZE));
    }

    public List<Rectangle> cloneBody() {
        List<Rectangle> copyBody = new ArrayList<>();

        for (Rectangle rect : this.getBody()) {
            copyBody.add(new Rectangle(rect));
        }

        return copyBody;
    }

    public List<Rectangle> getBody() {
        return body;
    }

    public void setBody(List<Rectangle> body) {
        this.body.clear();
        this.body.addAll(body);
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public List<int[]> getPositions() {
        return this.positions;
    }
}
