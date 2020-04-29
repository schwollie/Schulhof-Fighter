package audio;

import game.Consts;
import loading.AudioLoader;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Track {

    private Clip clip;
    private long position;
    private TrackState state;

    private float volume = .5f;

    public Track(String name) {
        clip = AudioLoader.getFromFilePath(Consts.defaultMusicPath + name);
        state = TrackState.PAUSED;
        applyVolume();
    }

    public void play() {
        if (state == TrackState.PAUSED) {
            clip.start();
            state = TrackState.PLAYED;
        }
    }

    public void pause() {
        if (state == TrackState.PLAYED) {
            position = clip.getMicrosecondPosition();
            clip.stop();
            state = TrackState.PAUSED;
        }
    }

    public void resume() {
        if (state == TrackState.PAUSED) {
            clip.close();
            clip.setMicrosecondPosition(position);
            play();
        }
    }

    public void restart() {
        clip.stop();
        position = 0;
        play();
    }

    public void stop() {
        clip.stop();
        clip.close();
        position = 0;
    }

    public void setVolume(float v) {
        v = lin2log(v);
        this.volume = v;

        this.volume = Math.min(Math.max(v, 0), 1);
        applyVolume();
    }

    private void applyVolume() {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain = (range * volume) + gainControl.getMinimum();

        gainControl.setValue(gain);
    }

    private float lin2log(float val) {
        //converts the volume in linear scale to log scale
        return (float) Math.log10((9*val+1));
    }
}
