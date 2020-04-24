package audio;

import game.Consts;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioManager {
    private final File file;
    private Clip clip;
    private long position;
    private State state;
    private AudioInputStream inputStream;

    public AudioManager(String name) {
        file = new File(Consts.defaultMusicPath + name);
        loadClip();
        state = State.PAUSED;
    }

    public void play() {
        if (state == State.PAUSED) {
            clip.start();
            state = State.PLAYED;
        }
    }

    public void pause() {
        if (state == State.PLAYED) {
            position = clip.getMicrosecondPosition();
            clip.stop();
            state = State.PAUSED;
        }
    }

    public void resume() {
        if (state == State.PAUSED) {
            clip.close();
            loadClip();
            clip.setMicrosecondPosition(position);
            play();
        }
    }

    public void restart() {
        clip.stop();
        clip.close();
        loadClip();
        position = 0;
        play();
    }

    public void stop() {
        clip.stop();
        clip.close();
        position = 0;
    }

    public void loadClip() {
        try {
            inputStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(inputStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private enum State {
        PAUSED, PLAYED
    }

}
