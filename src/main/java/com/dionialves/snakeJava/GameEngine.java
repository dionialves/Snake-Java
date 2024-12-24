package main.java.com.dionialves.snakeJava;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class GameEngine extends JPanel implements Runnable, KeyListener {
    public static final int WIDTH = 640;
    public static final int HEIGHT = 640;
    private final int fps = 240;

    private final Game game = new Game();

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
        //g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(new Color(20, 78, 11));
        g2d.fillRect(0, 0, getWidth(), getHeight());

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
            Game.direction = "RIGHT";
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            Game.direction = "LEFT";
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            Game.direction = "DOWN";
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            Game.direction = "UP";
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
