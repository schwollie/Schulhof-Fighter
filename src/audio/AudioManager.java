package audio;

import game.Consts;
import loading.AudioLoader;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioManager {

    private Track t1;
    private Track t2;

    public AudioManager() {
        AudioLoader.loadAll();

        t1 = new Track("test.wav");
        t1.play();
        t1.setVolume(.5f);
    }

    public static void main(String[] args) {
        new AudioManager();
        while (true) {
        }
    }

}
