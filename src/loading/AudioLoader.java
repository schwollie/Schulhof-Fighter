package loading;

import game.Consts;
import logic.ListChopper;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class AudioLoader {


    private static HashMap<String, Clip> music = new HashMap<>();

    public static void loadAll() {
        File[] files = new File("music/").listFiles();
        ArrayList<File> allFiles = getAllFiles(files);


        ArrayList<ArrayList<File>> threadFiles = ListChopper.chopIntoParts(allFiles, Consts.cores);
        int threadNum = threadFiles.size();

        ArrayList<LoadingThreadSound> threads = new ArrayList<LoadingThreadSound>();

        for (int i = 0; i < threadNum; i++) {
            LoadingThreadSound t = new LoadingThreadSound(threadFiles.get(i));
            threads.add(t);
            t.start();
        }


        for (int i = 0; i < threadNum; i++) {
            LoadingThreadSound t = threads.get(i);

            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            music.putAll(t.getOutputSound());
        }
    }

    public static ArrayList<File> getAllFiles(File[] dir) {
        ArrayList<File> files = new ArrayList<>();
        for (File file : dir) {
            if (file.isDirectory()) {
                files.addAll(getAllFiles(file.listFiles())); // Calls same method again.
            } else {
                files.add(file);
            }
        }

        return files;
    }

    public static Clip getFromFilePath(String filepath) {
        filepath = filepath.replaceAll("/", "\\\\");

        if (!music.containsKey(filepath)) { System.out.println("Could not load File"); }

        return AudioLoader.music.get(filepath);
    }
}

class LoadingThreadSound extends Thread {

    ArrayList<File> files;
    HashMap<String, Clip> outputSound = new HashMap<>();

    public LoadingThreadSound(ArrayList<File> files) {
        this.files = files;
    }

    @Override
    public void run() {
        for (File f: this.files) {
            try {
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(f);
                Clip clip = AudioSystem.getClip();
                clip.open(inputStream);
                outputSound.put(f.getPath(), clip);

            } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                e.printStackTrace();
                System.out.println("Loading Error");
            }
        }

    }

    public HashMap<String, Clip> getOutputSound() {
        return outputSound;
    }
}
