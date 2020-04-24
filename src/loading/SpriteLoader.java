package loading;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SpriteLoader {


    private static HashMap<String, BufferedImage> images = new HashMap<>();

    public static void loadAll() {
        File[] files = new File("images/").listFiles();
        ArrayList<File> allFiles = getAllFiles(files);

        for (File f: allFiles) {
            try {
                BufferedImage img = ImageIO.read(f);
                images.put(f.getPath(), img);
            } catch (IOException e) {
                System.out.println("Loading Error");
            }
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
