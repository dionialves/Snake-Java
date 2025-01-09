package main.java.com.dionialves.snakeJava;

import main.java.com.dionialves.snakeJava.entities.Foods;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;

public class GameEngine extends JPanel implements Runnable, KeyListener {
    public static final int WIDTH = 665;
    public static final int HEIGHT = 660;
    private final int fps = 30;

    private final Game game = new Game();
    private final Foods food = new Foods(false, false, 35);

    public GameEngine() {
        this.addKeyListener(this);

        this.setPreferredSize(new Dimension(GameEngine.WIDTH, GameEngine.HEIGHT));
        this.setFocusable(true);
        this.setLayout(null);
        this.requestFocusInWindow();
    }

    // Class update, onde serão atualizados a lógica do jogo
    public void update() {
        this.game.update();
    }

    // Herança da classe paintComponent, responsável por desenhar os objetos na tela
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);

        // Nesse trecho de código, faço um cast da classe Graphics para Graphics2D
        // crio um retângulo na cor preta para definir todo o backgroud.
        // e chamo o método dram da classe PainelGame, dentro desse método irei desenhar
        // tudo que a tela irá apresentar.
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(new Color(87, 137, 52));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(new Color(74, 116, 44));
        g2d.fillRect(0, 0, getWidth(), 70);

        // Desenho do food no top da pagina, como imagem contador dos pontos
        this.getFood().getBody().setLocation(35, 15);
        this.getFood().draw(g2d);


        // Desenha os pontos obtidos no game, cada food que a snake comer será um ponto
        g2d.setColor(new Color(255,255,255));
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        // Para definição do ponto eu pego o tamanho da Snake e diminuo 3, que são as posições iniciais
        g2d.drawString(Integer.toString(
                this.game.getSnake().getLogicalSegments().size()-3),
                80,
                42
        );

        this.game.draw(g2d);
    }

    // Usando a implementação da interface Runnable, herdei o mêtodo run que será
    // responsável pela quantidade de frames na tela.
    // a logica utilizada não foi pensada por mim e sim pelo canal
    // RyiSnow link do youtube: https://www.youtube.com/@RyiSnow
    @Override
    public void run() {

        double drawInterval = 1000000000/getFps();
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (true) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                this.update();
                this.repaint();
                delta--;
            }
        }
    }

    public int getFps() {
        return fps;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            this.game.addNewDirection("RIGHT");
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            this.game.addNewDirection("LEFT");
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            this.game.addNewDirection("DOWN");
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            this.game.addNewDirection("UP");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    public Foods getFood() {
        return food;
    }
}
