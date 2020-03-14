package game;


import logic.Dimension2D;

import java.awt.*;

public abstract class Consts {

    public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int windowWidth = (int)screenSize.getWidth();
    public static final int windowHeight = (int)screenSize.getHeight();
    public static final boolean Fullscreen = true;
    public static final double ratio = (double) windowWidth/windowHeight;

    // physics
    public static final double gravity = 9.81;
    public static final double linearDrag = 10;

    // image constants and file paths
    public static final String imageSrc = "images/";

    // animation sheet paths:
    public static final String defaultSheet = "defaultSheet.png";
    public static final int defaultPicCount = 2;

    public static final String fightSheet = "defaultSheet.png";
    public static final int fightPicCount = 2;

    public static final String specialAttackSheet = "defaultSheet.png";
    public static final int specialAttackPicCount = 2;

    public static final String runSheet = "runSheet.png";
    public static final int runPicCount = 8;


    // default Character:
    public static final String defaultCharacter = "defaultCharacter/";
    public static final Dimension2D defaultCharacterSize = new Dimension2D(409, 409);

    // Hausperger
    public static final String Hausperger = "Hausperger/";
    public static final Dimension2D hauspergerCharacterSize = new Dimension2D(409, 409);

}
