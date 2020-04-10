package game;


import logic.Dimension2D;

import java.awt.*;

public abstract class Consts {

    public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int windowWidth = (int) screenSize.getWidth() / 2;
    public static final int windowHeight = (int) screenSize.getHeight() / 2;
    public static final boolean Fullscreen = false;
    public static final double ratio = (double) windowWidth / windowHeight;


    // main menu
    public static int bubblesAmount = 100;
    public static int bubblesConnectRadius = 150;

    // physics
    public static final double gravity = -9.81;
    public static final double linearDrag = 10;

    // image constants and file paths
    public static final String imageSrc = "images/";

    // animation sheet paths:
    public static final String defaultSheet = "defaultSheet.png";
    public static final int defaultPicCount = 1;
    public static final float defaultAnimSpeed = 1f; // in fps

    public static final String kickSheet = "kickSheet.png";
    public static final int kickSheetPicCount = 5;
    public static final float kickAnimSpeed = 15f; // in fps

    public static final String punchSheet = "punchSheet.png";
    public static final int punchSheetPicCount = 4;
    public static final float punchAnimSpeed = 12f; // in fps

    public static final String blockSheet = "blockSheet.png";
    public static final int blockSheetPicCount = 2;
    public static final float blockAnimSpeed = 7f; // in fps

    public static final String specialAttackSheet = "defaultSheet.png";
    public static final int specialAttackPicCount = 2;
    public static final float specialAttackAnimSpeed = 7f; // in fps

    public static final String runSheet = "runSheet.png";
    public static final int runPicCount = 8;
    public static final float runAnimSpeed = 8f; // in fps

    public static final String jumpSheet = "jumpSheet.png";
    public static final int jumpSheetPicCount = 1;
    public static final float jumpAnimSpeed = 1f; // in fps

    // default Character:
    public static final String defaultCharacter = "defaultCharacter/";
    public static final Dimension2D defaultCharacterSize = new Dimension2D(500, 500);

    // Hausperger
    public static final String Hausperger = "Hausperger/";
    public static final Dimension2D hauspergerCharacterSize = new Dimension2D(500, 500);

}
