package main.java.com.dionialves.snakeJava.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.sound.sampled.*;
import java.io.File;

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
        // Permite encerrar quando o programa terminar
        soundThread.setDaemon(true);
        soundThread.start();
    }

    // Recebe caminho do arquivo de som e associa a um nome
    public static void loadSound(String name, String filePath) {
        soundPaths.put(name, filePath);
    }

    // Adiciona o som a fila
    public static void playSound(String name) {
        String filePath = soundPaths.get(name);
        if (filePath != null) {
            soundQueue.offer(filePath); // Usa `offer` para evitar exceções
        }
    }

    // Reproduz o som
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

    // Interrompe a thread de sons (opcional)
    public static void shutdown() {
        soundThread.interrupt();
    }
}
