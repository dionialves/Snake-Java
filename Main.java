import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        GameEngine game = new GameEngine();

        JFrame window = new JFrame("Snake Java");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        window.add(game);
        window.pack();

        window.setVisible(true);
        window.setLocationRelativeTo(null);

        // Adicionando tudo isso a uma Thread
        new Thread(game).start();
    }
}