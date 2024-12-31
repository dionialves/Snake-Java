package main.java.com.dionialves.snakeJava.entities;

import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.*;
import java.io.File;

// Classe responsável por gerenciar os audios do game
public class SoundManager {
    private static final Map<String, Clip> sounds = new HashMap<>();

    // Aqui temos o load dos arquivos
    public static void loadSound(String name, String filePath) {
        try {
            File file = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            sounds.put(name, clip);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Aqui temos o player do audio
    public static void playSound(String name) {
        Clip clip = sounds.get(name);
        if (clip != null) {
            clip.setFramePosition(0); // Reposiciona no início
            clip.start();
        }
    }
}
