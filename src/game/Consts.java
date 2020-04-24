package game;


import logic.Dimension2D;

import java.awt.*;

public abstract class Consts {

    // System
    public static int cores = Runtime.getRuntime().availableProcessors();

    // Window
    public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int windowWidth = (int) (screenSize.getWidth() / 2);
    public static final int windowHeight = (int) (screenSize.getHeight() / 2);
    public static final boolean Fullscreen = false;
    public static final double ratio = (double) windowWidth / windowHeight;

    // main menu
    public static int bubblesAmount = 100;
    public static int bubblesConnectRadius = 150;

    // physics
    public static final double gravity = -9.81;
    public static final double linearDrag = 10;

    // image constants and file paths
    public static final String imageSrc = "images/", imageParticlesSrc = imageSrc + "particles/";

    // animation sheet paths:
    public static final String defaultSheet = "defaultSheet.png";
    public static final int defaultPicCount = 1;
    public static final float defaultAnimSpeed = 1f; // in fps

    public static final String runSheet = "runSheet.png";
    public static final int runPicCount = 8;
    public static final float runAnimSpeed = 8f; // in fps

    public static final String jumpSheet = "jumpSheet.png";
    public static final int jumpSheetPicCount = 1;
    public static final float jumpAnimSpeed = 1f; // in fps

    public static final String kickSheet = "kickSheet.png";
    public static final int kickSheetPicCount = 5;
    public static final float kickAnimSpeed = 15f; // in fps

    public static final String punchSheet = "punchSheet.png";
    public static final int punchSheetPicCount = 4;
    public static final float punchAnimSpeed = 12f; // in fps

    public static final String blockSheet = "blockSheet.png";
    public static final int blockSheetPicCount = 2;
    public static final float blockAnimSpeed = 7f; // in fps

    public static final String specialAttackSheet = "specialSheet.png";
    public static final int specialAttackPicCount = 3;
    public static final float specialAttackAnimSpeed = 5f; // in fps

    public static final String specialAttack2Sheet = "specialSheet2.png";
    public static final int specialAttack2PicCount = 3;
    public static final float specialAttack2AnimSpeed = 5f; // in fps

    public static final String gotHit1Sheet = "gotHit1.png";
    public static final int gotHit1PicCount = 1;
    public static final float gotHit1AnimSpeed = 4f; // in fps

    public static final String gotHit2Sheet = "gotHit2.png";
    public static final int gotHit2PicCount = 1;
    public static final float gotHit2AnimSpeed = 4f; // in fps

    public static final String deathSheet = "deathSheet.png";
    public static final int deathSheetCount = 2;
    public static final float deathSheetSpeed = 2f; // in fps

    // default Character:
    public static final String defaultCharacter = "defaultCharacter/";
    public static final Dimension2D defaultCharacterSize = new Dimension2D(500, 500);

    // Hausperger
    public static final Dimension2D hauspergerCharacterSize = new Dimension2D(500, 500);

    // -- Particles --
    // Punch
    public static final String punch = "runSheet.png";
    public static final int punchParticlePicCount = 1;
    public static final float punchParticleAnimSpeed = 1f; // in fps
    public static final float punchParticleLivingTime = 50f; // in fps
    public static final Dimension2D punchParticleSize = new Dimension2D(500, 500);
}
