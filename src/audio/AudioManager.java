package audio;

import loading.AudioLoader;

public class AudioManager {

    private Track t1;
    private Track t2;

    public AudioManager() {
        AudioLoader.loadAll();

        t1 = new Track("soundtrack.WAV");
        t1.play();
        t1.setVolume(.5f);
    }

    public static void main(String[] args) {
        new AudioManager();
        while (true) {
        }
    }

}
