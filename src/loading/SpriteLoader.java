package loading;

import game.Consts;
import logic.ListChopper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class SpriteLoader {


    private static HashMap<String, BufferedImage> images = new HashMap<>();

    public static void loadAll() {
        File[] files = new File("images/").listFiles();
        ArrayList<File> allFiles = getAllFiles(files);

        int threadNum = Consts.cores;
        ArrayList<ArrayList<File>> threadFiles = ListChopper.chopIntoParts(allFiles, threadNum);

        ArrayList<LoadingThread> threads = new ArrayList<LoadingThread>();

        for (int i = 0; i < threadNum; i++) {
            LoadingThread t = new LoadingThread(threadFiles.get(i));
            threads.add(t);
            t.start();
        }


        for (int i = 0; i < threadNum; i++) {
            LoadingThread t = threads.get(i);

            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            images.putAll(t.getOutputImgs());
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

    public static BufferedImage getFromFilePath(String filepath) {
        filepath = filepath.replaceAll("/", "\\\\");

        return SpriteLoader.images.get(filepath);
    }
}

class LoadingThread extends Thread {

    ArrayList<File> files;
    HashMap<String, BufferedImage> outputImgs = new HashMap<>();

    public LoadingThread(ArrayList<File> files) {
        this.files = files;
    }

    @Override
    public void run() {
        for (File f: this.files) {
            try {
                BufferedImage img = ImageIO.read(f);
                outputImgs.put(f.getPath(), img);
            } catch (IOException e) {
                System.out.println("Loading Error");
            }
        }

    }

    public HashMap<String, BufferedImage> getOutputImgs() {
        return outputImgs;
    }
}
