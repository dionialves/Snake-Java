import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.*;
import java.awt.image.BufferedImage;
import java.util.List;


// Classe muito importante que possui os parâmetros da Snake
public class Snake {
    // Atributo que define o tamanho do corpo da snake, muito usado em todo o codigo;
    public static final int BODYSIZE = 40;
    // Lista de retângulos que compreendem a snake
    private final List<BodySnake> body = new ArrayList<>();
    // Lista de posições, uso ela para desenhas e snake e dar uma sensação de suavidade na movimentação da snake
    private final List<PositionSnake> positions = new ArrayList<>();
    // Direção que a cabeça da Snake está indo
    private String direction;
    // Lista contendo a direção de cada

    private BufferedImage headImage;
    private List<BufferedImage> bodyImages = new ArrayList<>();
    private BufferedImage tailImage;

    public Snake(String direction) {
        // Desenho os 3 primeiras partes da Snake, pode observar que apenas a cabeça tem coordenadas
        // isso é necessário, pois todas as outras partes seguiram a cabeça.
        this.getBody().add(new BodySnake(120, 280, Snake.BODYSIZE, Snake.BODYSIZE, "RIGHT"));
        this.getBody().add(new BodySnake(120, 280,  Snake.BODYSIZE, Snake.BODYSIZE, "RIGHT"));

        // Adiciono a coordenada da cabeça na lista de posições
        this.getPositions().addFirst(new PositionSnake(this.getBody().getFirst().x, this.getBody().getFirst().y, direction));
        // Defino a direção inicial da Snake
        this.setDirection(direction);
        // Seta a imagem inicial da cabeça
        this.setImageHead(this.getDirection());
    }

    // Método para atualizar a logica da Snake, primeiramente movimentando a cabeça, e depois construindo o restante
    // do corpo
    public void update() {
        // Move cabeça da snake para frente, independente da direção
        this.moveHead();
        // Seta as coordenadas da snake nos retângulos, isso é importando para a logica da verificação de colisões
        this.buildSnake();
        // Muda imagem do corpo caso tenha uma alteração na direção
        this.changeDirectionBody();
        // Atualiza a imagem do rabo
        this.setImageTail(this.getPositions().getLast().getDirection());
    }

    // Desenha a Snake na tela
    public void draw(Graphics2D g2d) {
        List<String> directions = Arrays.asList("UP", "DOWN", "RIGHT", "LEFT");

        for (int i = 1; i < this.getBody().size()-1; i++) {

            for (int p = i*40; p < this.getPositions().size(); p++) {

                if (!this.getPositions().isEmpty()) {
                    //if (directions.contains(this.getBody().get(i).getDirection())) {
                        BufferedImage bodyImage = this.setImageBody(this.getBody().get(i).getDirection());

                        g2d.drawImage(
                                bodyImage,
                                this.getPositions().get(p).getX(),
                                this.getPositions().get(p).getY(),
                                Snake.BODYSIZE,
                                Snake.BODYSIZE,
                                null
                        );
                   // }
                }

            }
        }

        g2d.drawImage(
                getHeadImage(),
                this.getPositions().getFirst().getX(),
                this.getPositions().getFirst().getY(),
                Snake.BODYSIZE,
                Snake.BODYSIZE,
                null
        );

        g2d.drawImage(
                getTailImage(),
                this.getPositions().getLast().getX(),
                this.getPositions().getLast().getY(),
                Snake.BODYSIZE,
                Snake.BODYSIZE,
                null
        );


    }

    // Método responsável por movimentar a cabeça da snake, movimetando um 1 posição no quadro dependendo da direção
    // escolhida pelo usuário. Caso não seja trocado a direção ela segue em frente, dependendo da direção.
    // Após a movimentação, adiciona a posição no atributo positions. Pode-se observar que é adicionado a posição no
    // índice 0. Essa lista de posições também tem um limite, que seria o tamanho de BODYSIZE * o tamanho do corpo da
    // snake.
    public void moveHead() {
        // 0: UP
        // 1: RIGHT
        // 2: DOWN
        // 3: LEFT

        String direction = "";
        switch (this.getDirection()) {
            case "UP":
                this.getBody().getFirst().y -= 1;
                direction = "UP";
                break;
            case "RIGHT":
                this.getBody().getFirst().x += 1;
                direction = "RIGHT";
                break;
            case "DOWN":
                this.getBody().getFirst().y += 1;
                direction = "DOWN";
                break;
            case "LEFT":
                this.getBody().getFirst().x -= 1;
                direction = "LEFT";
                break;
        }
        this.getPositions().addFirst(new PositionSnake(this.getBody().getFirst().x, this.getBody().getFirst().y, direction));

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

                PositionSnake rectangle = this.getPositions().get((i * Snake.BODYSIZE)-1);

                this.getBody().get(i).setLocation(
                        rectangle.getX(),
                        rectangle.getY()
                );

                this.getBody().get(i).setDirection(rectangle.getDirection());
            }
        }
    }

    // Nesse método altera a direção da snake, foi construído para não permitir que a snake faça um retorno de 180 graus
    public void changeDirection(String direction) {
        switch (direction) {
            case "UP":
                if (!this.getDirection().equals("DOWN")) {
                    this.setDirection("UP");
                    this.setImageHead("UP");
                }
                break;
            case "RIGHT":
                if (!this.getDirection().equals("LEFT")) {
                    this.setDirection("RIGHT");
                    this.setImageHead("RIGHT");
                }
                break;
            case "DOWN":
                if (!this.getDirection().equals("UP")) {
                    this.setDirection("DOWN");
                    this.setImageHead("DOWN");
                }
                break;
            case "LEFT":
                if (!this.getDirection().equals("RIGHT")) {
                    this.setDirection("LEFT");
                    this.setImageHead("LEFT");
                }
                break;
        }
    }

    // Responsável por modificar a imagem caso tenha uma alterações na rota
    private void changeDirectionBody() {
        for (int i = 0; i < this.getBody().size()-1; i++) {
            BodySnake currentBody = this.getBody().get(i);
            BodySnake nextBody = this.getBody().get(i+1);


            if (currentBody.getDirection().equals("UP")) {
                if (nextBody.getDirection().equals("RIGHT")) {
                    if (i == 0) {
                        this.getBody().getFirst().y -= 20;
                    }
                    currentBody.setDirection("TOP-RIGHT");
                } else if (nextBody.getDirection().equals("LEFT")) {
                    if (i == 0) {
                        this.getBody().getFirst().y -= 10;
                    }
                    currentBody.setDirection("TOP-RIGHT");
                }
            } else if (currentBody.getDirection().equals("DOWN")) {
                if (nextBody.getDirection().equals("RIGHT")) {
                    if (i == 0) {
                        this.getBody().getFirst().y += 10;
                    }
                    currentBody.setDirection("DOWN-RIGHT");
                } else if (nextBody.getDirection().equals("LEFT")) {
                    if (i == 0) {
                        this.getBody().getFirst().y += 10;
                    }
                    currentBody.setDirection("DOWN-LEFT");
                }
            } else if (currentBody.getDirection().equals("LEFT")) {
                if (nextBody.getDirection().equals("DOWN")) {
                    if (i == 0) {
                        this.getBody().getFirst().x -= 10;
                    }
                    currentBody.setDirection("DOWN-LEFT");
                } else if (nextBody.getDirection().equals("UP")) {
                    if (i == 0) {
                        this.getBody().getFirst().x -= 10;
                    }
                    currentBody.setDirection("UP-LEFT");
                }
            } else if (currentBody.getDirection().equals("RIGHT")) {
                if (nextBody.getDirection().equals("DOWN")) {
                    if (i == 0) {
                        this.getBody().getFirst().x += 10;
                    }
                    currentBody.setDirection("DOWN-RIGHT");
                } else if (nextBody.getDirection().equals("UP")) {
                    if (i == 0) {
                        this.getBody().getFirst().x += 10;
                    }
                    currentBody.setDirection("UP-RIGHT");
                }
            }
        }
    }

    private void setImageHead(String direction) {
        Map<String, String> dictionary = new HashMap<>();

        dictionary.put("UP", "assets/graphics/head_up.png");
        dictionary.put("RIGHT", "assets/graphics/head_right.png");
        dictionary.put("DOWN", "assets/graphics/head_down.png");
        dictionary.put("LEFT", "assets/graphics/head_left.png");

        try {
            this.setHeadImage(ImageIO.read(new File(dictionary.get(direction))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setImageTail(String direction) {
        Map<String, String> dictionary = new HashMap<>();

        dictionary.put("UP", "assets/graphics/tail_down.png");
        dictionary.put("RIGHT", "assets/graphics/tail_left.png");
        dictionary.put("DOWN", "assets/graphics/tail_up.png");
        dictionary.put("LEFT", "assets/graphics/tail_right.png");

        try {
            this.setTailImage(ImageIO.read(new File(dictionary.get(direction))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BufferedImage setImageBody(String direction) {
        Map<String, String> dictionary = new HashMap<>();

        dictionary.put("UP", "assets/graphics/body_vertical.png");
        dictionary.put("RIGHT", "assets/graphics/body_horizontal.png");
        dictionary.put("DOWN", "assets/graphics/body_vertical.png");
        dictionary.put("LEFT", "assets/graphics/body_horizontal.png");
        dictionary.put("UP-RIGHT", "assets/graphics/body_upright.png");
        dictionary.put("UP-LEFT", "assets/graphics/body_upleft.png");
        dictionary.put("DOWN-RIGHT", "assets/graphics/body_downright.png");
        dictionary.put("DOWN-LEFT", "assets/graphics/body_downleft.png");

        try {
            return ImageIO.read(new File(dictionary.get(direction)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Adiciona um novo retângulo ao corpo da snake.
    public void addBody(String direction) {
        this.getBody().add(new BodySnake( Snake.BODYSIZE, Snake.BODYSIZE, direction));
    }

    public List<BodySnake> getBody() {
        return body;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {

        if (!Objects.equals(this.direction, direction)) {
            // Chama o som ao mudar a posição da Snake
            this.direction = direction;
            // Atualiza a direção da cabeça
            this.getBody().getFirst().setDirection(direction);
            SoundManager.playSound("move");
        }
    }

    public List<PositionSnake> getPositions() {
        return this.positions;
    }

    public BufferedImage getHeadImage() {
        return headImage;
    }

    public void setHeadImage(BufferedImage headImage) {
        this.headImage = headImage;
    }

    public BufferedImage getTailImage() {
        return tailImage;
    }

    public void setTailImage(BufferedImage tailImage) {
        this.tailImage = tailImage;
    }
}
