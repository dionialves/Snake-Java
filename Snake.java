import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// Classe muito importante que possui os parâmetros da Snake
public class Snake {
    // Atributo que define o tamanho do corpo da snake, muito usado em todo o codigo;
    public static final int BODYSIZE = 40;
    // Lista de retângulos que compreendem a snake
    private final List<Rectangle> body = new ArrayList<>();
    // Lista de posições, uso ela para desenhas e snake e dar uma sensação de suavidade na movimentação da snake
    private final List<int[]> positions = new ArrayList<>();
    // Direção que a cabeça da Snake está indo
    private String direction;

    public Snake(String direction) {
        // Desenho os 3 primeiras partes da Snake, pode observar que apenas a cabeça tem coordenadas
        // isso é necessário, pois todas as outras partes seguiram a cabeça.
        this.getBody().add(new Rectangle(120, 280, Snake.BODYSIZE, Snake.BODYSIZE));
        this.getBody().add(new Rectangle( Snake.BODYSIZE, Snake.BODYSIZE));
        this.getBody().add(new Rectangle( Snake.BODYSIZE, Snake.BODYSIZE));

        // Adiciono a coordenada da cabeça na lista de posições
        this.getPositions().addFirst(new int[]{this.getBody().getFirst().x, this.getBody().getFirst().y});
        // Defino a direção inicial da Snake
        this.setDirection(direction);
    }

    // Método para atualizar a logica da Snake, primeiramente movimentando a cabeça, e depois construindo o restante
    // do corpo
    public void update() {
        // Move cabeça da snake para frente, independente da direção
        this.moveHead();
        // Seta as coordenadas da snake nos retângulos, isso é importando para a logica da verificação de colisões
        this.buildSnake();
    }

    // Desenha a Snake na tela
    public void draw(Graphics2D g2d) {
        for (int i = 0; i < this.getPositions().size(); i++) {
            g2d.setColor(new Color(255, 255, 255));
            g2d.fillRect(
                    this.getPositions().get(i)[0],
                    this.getPositions().get(i)[1],
                    Snake.BODYSIZE,
                    Snake.BODYSIZE
            );
        }
    }

    // Método responsável por movimentar a cabeça da snake, movimetando um 1 posição no quadro dependendo da direção
    // escolhida pelo usuário. Caso não seja trocado a direção ela segue em frente, dependendo da direção.
    // Após a movimentação, adiciona a posição no atributo positions. Pode-se observar que é adicionado a posição no
    // índice 0. Essa lista de posições também tem um limite, que seria o tamanho de BODYSIZE * o tamanho do corpo da
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
        this.getPositions().addFirst(new int[]{this.getBody().getFirst().x, this.getBody().getFirst().y});

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
        this.getBody().add(new Rectangle( Snake.BODYSIZE, Snake.BODYSIZE));
    }

    public List<Rectangle> getBody() {
        return body;
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
