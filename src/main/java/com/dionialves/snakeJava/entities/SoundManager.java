package main.java.com.dionialves.snakeJava.entities;

import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.*;
import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class SoundManager {
    private static final Map<String, String> soundPaths = new HashMap<>();
    private static final BlockingQueue<String> soundQueue = new LinkedBlockingQueue<>();
    private static final Thread soundThread;

    static {
        // Thread para processar sons da fila
        soundThread = new Thread(() -> {
            while (true) {
                try {
                    String filePath = soundQueue.take(); // Aguarda som
                    playSoundInternal(filePath);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Reconfigura o estado de interrupção
                    break;
                }
            }
        });
        soundThread.setDaemon(true); // Permite encerrar quando o programa terminar
        soundThread.start();
    }

    /**
     * Carrega o caminho de um som associado a um nome.
     *
     * @param name      Nome do som.
     * @param filePath  Caminho absoluto do arquivo de som.
     */
    public static void loadSound(String name, String filePath) {
        soundPaths.put(name, filePath);
    }

    /**
     * Adiciona um som na fila para reprodução.
     *
     * @param name Nome do som previamente carregado.
     */
    public static void playSound(String name) {
        String filePath = soundPaths.get(name);
        if (filePath != null) {
            soundQueue.offer(filePath); // Usa `offer` para evitar exceções
        }
    }

    /**
     * Reproduz o som diretamente a partir do arquivo.
     *
     * @param filePath Caminho absoluto do arquivo de som.
     */
    private static void playSoundInternal(String filePath) {
        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filePath))) {
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            clip.drain(); // Aguarda até o som terminar
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Interrompe a thread de sons (opcional).
     */
    public static void shutdown() {
        soundThread.interrupt();
    }
}
