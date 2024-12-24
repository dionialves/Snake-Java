package main.java.com.dionialves.snakeJava.entities;

import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.*;
import java.io.File;

public class SoundManager {
    private static final Map<String, Clip> sounds = new HashMap<>();

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

    public static void playSound(String name) {
        Clip clip = sounds.get(name);
        if (clip != null) {
            clip.setFramePosition(0); // Reposiciona no início
            clip.start();
        }
    }
}
