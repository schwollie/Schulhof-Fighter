package graphics;

import game.Consts;
import logic.AnimationTypes;
import logic.Dimension2D;
import logic.PlayerTypes;
import player.Player;

import java.io.File;

public abstract class AnimationLoader {

    public static Animation loadAnimation(PlayerTypes type, AnimationTypes animType, Player player, boolean loopAnim, int priority) {
        AnimSpecs animSpec = getAnimSpecs(animType, type);
        String path = AnimationLoader.getPath(type, animSpec);

        File f = new File(path);
        if (!f.exists()) {
            System.out.println("File does not exist : " + path + " -> using default path instead");
            animSpec = getAnimSpecs(AnimationTypes.Default, type);
            path = AnimationLoader.getPath(type, animSpec);
        }

        animSpec.animType = animType;
        animSpec.loopAnim = loopAnim;
        animSpec.priority = priority;

        return Animation.loadAnim(path, animSpec, player);
    }

    private static String getPath(PlayerTypes type, AnimSpecs a) {
        return Consts.imageSrc + getTypePath(type) + a.getAnimSheet();
    }

    private static String getTypePath(PlayerTypes type) {
        if (type==PlayerTypes.Hausperger) {
            return Consts.Hausperger;
        }

        throw new Error("NO SHEET FOUND: " + type);
    }

    private static AnimSpecs getAnimSpecs(AnimationTypes type, PlayerTypes pType) {
        if (type==AnimationTypes.Default) {
            return new AnimSpecs(Consts.defaultSheet, Consts.defaultPicCount, Consts.defaultAnimSpeed, getSpriteSize(pType));
        }
        else if(type==AnimationTypes.Run) {
            return new AnimSpecs(Consts.runSheet, Consts.runPicCount, Consts.runAnimSpeed, getSpriteSize(pType));
        }
        else if(type==AnimationTypes.Jump) {
            return new AnimSpecs(Consts.jumpSheet, Consts.jumpSheetPicCount, Consts.jumpAnimSpeed, getSpriteSize(pType));
        }
        else if(type==AnimationTypes.Kick) {
            return new AnimSpecs(Consts.kickSheet, Consts.kickSheetPicCount, Consts.kickAnimSpeed, getSpriteSize(pType));
        }
        else if(type==AnimationTypes.Punch) {
            return new AnimSpecs(Consts.punchSheet, Consts.punchSheetPicCount, Consts.punchAnimSpeed, getSpriteSize(pType));
        }
        else if(type==AnimationTypes.Block) {
            return new AnimSpecs(Consts.blockSheet, Consts.blockSheetPicCount, Consts.blockAnimSpeed, getSpriteSize(pType));
        }
        else if (type==AnimationTypes.SpecialAttack) {
            return new AnimSpecs(Consts.specialAttackSheet, Consts.specialAttackPicCount, Consts.specialAttackAnimSpeed, getSpriteSize(pType));
        }
        throw new Error("NO SHEET FOUND: " + type);
    }

    private static Dimension2D getSpriteSize(PlayerTypes type) {
        if (type==PlayerTypes.Hausperger) {
            return Consts.hauspergerCharacterSize;
        }
        throw new Error("NO SHEET FOUND: " + type);
    }

}

class AnimSpecs{

    private float animSpeed;
    private int animPicCount;
    private String animSheet;
    private Dimension2D imgDimensions;

    public boolean loopAnim = false;
    public int priority = 0;
    public AnimationTypes animType = AnimationTypes.Default;

    public AnimSpecs(String animSheet, int animPicCount, float animSpeed, Dimension2D dim) {
        this.animPicCount = animPicCount;
        this.animSheet = animSheet;
        this.animSpeed = animSpeed;
        this.imgDimensions = dim;
    }

    public float getAnimSpeed() {
        return animSpeed;
    }

    public String getAnimSheet() {
        return animSheet;
    }

    public int getAnimPicCount() {
        return animPicCount;
    }

    public Dimension2D getImgDimensions() {
        return imgDimensions;
    }
}
