package main;

import javax.sound.sampled.*;
import java.net.URL;
import javax.sound.sampled.LineEvent;

public class Sound {
    Clip musicClip;
    URL[] url = new URL[10];

    public Sound() {
        url [0] = getClass().getResource("/Tetris.wav");
        url [1] = getClass().getResource("/delete line.wav");
        url [2] = getClass().getResource("/game over.wav");
        url [3] = getClass().getResource("/rotation.wav");
        url [4] = getClass().getResource("/touch floor.wav");
    }
    public void play(int i, boolean music) {

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url[i]);
            Clip clip = AudioSystem.getClip();

            if (music) {
                musicClip = clip;
            }

            clip.open(audioInputStream);
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });
            audioInputStream.close();
            clip.start();
        } catch (Exception _){

        }
    }

    public void loop() {
        musicClip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop() {
        if (musicClip != null) {
            musicClip.stop();
            musicClip.close();
        }
    }
}
