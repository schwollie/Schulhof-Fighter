package graphics;

import game.Consts;
import game.GameObject;
import logic.AnimationType;
import logic.Dimension2D;
import logic.PlayerTypes;
import particle.ParticleType;

import java.io.File;

public abstract class AnimationLoader {

    public static Animation loadPlayerAnimation(PlayerTypes type, AnimationType animType, GameObject ref, boolean loopAnim, int priority) {
        AnimSpecs animSpec = getAnimSpecs(animType, type);
        String path = AnimationLoader.getPath(type, animSpec);

        File f = new File(path);
        if (!f.exists()) {
            System.out.println("File does not exist : " + path + " -> using default path instead");
            animSpec = getAnimSpecs(AnimationType.Default, type);
            path = AnimationLoader.getPath(type, animSpec);
        }

        animSpec.animType = animType;
        animSpec.loopAnim = loopAnim;
        animSpec.priority = priority;

        return Animation.loadAnim(path, animSpec, ref);
    }

    public static Animation loadParticleAnimation(ParticleType type, int picCount, float animationSpeed, Dimension2D size, GameObject ref, boolean loopAnim) {
        return loadAnimation(Consts.imageParticlesSrc, type.toString(), picCount, animationSpeed, size, ref, loopAnim, 1);
    }

    public static Animation loadAnimation(String path, String sheetName, int picCount, float animationSpeed, Dimension2D size, GameObject ref, boolean loopAnim, int priority) {
        AnimSpecs animSpec = new AnimSpecs(sheetName, picCount, animationSpeed, size);

        File f = new File(path);
        if (!f.exists()) {
            System.out.println("File does not exist : " + path + " -> TODO : using default path instead");
            //animSpec = getAnimSpecs(AnimationType.Default, type);
            //path = AnimationLoader.getPath(type, animSpec);
        }

        animSpec.loopAnim = loopAnim;
        animSpec.priority = priority;

        return Animation.loadAnim(path, animSpec, ref);
    }

    private static String getPath(PlayerTypes type, AnimSpecs a) {
        return Consts.imageSrc + type.name() + "/" + a.getAnimSheet();
    }


    private static AnimSpecs getAnimSpecs(AnimationType type, PlayerTypes pType) {
        AnimSpecs specs = null;
        switch (type) {
            case Default:
                specs = new AnimSpecs(Consts.defaultSheet, Consts.defaultPicCount, Consts.defaultAnimSpeed, getSpriteSize(pType));
                break;
            case Run:
                specs = new AnimSpecs(Consts.runSheet, Consts.runPicCount, Consts.runAnimSpeed, getSpriteSize(pType));
                break;
            case Jump:
                specs = new AnimSpecs(Consts.jumpSheet, Consts.jumpSheetPicCount, Consts.jumpAnimSpeed, getSpriteSize(pType));
                break;
            case Punch:
                specs = new AnimSpecs(Consts.punchSheet, Consts.punchSheetPicCount, Consts.punchAnimSpeed, getSpriteSize(pType));
                break;
            case Block:
                specs = new AnimSpecs(Consts.blockSheet, Consts.blockSheetPicCount, Consts.blockAnimSpeed, getSpriteSize(pType));
                break;
            case Kick:
                specs = new AnimSpecs(Consts.kickSheet, Consts.kickSheetPicCount, Consts.kickAnimSpeed, getSpriteSize(pType));
                break;
            case SpecialAttack:
                specs = new AnimSpecs(Consts.specialAttackSheet, Consts.specialAttackPicCount, Consts.specialAttackAnimSpeed, getSpriteSize(pType));
                break;
            default:
                throw new Error("NO SHEET FOUND: " + type);
        }
        return specs;
    }

    private static Dimension2D getSpriteSize(PlayerTypes type) {
        if (type == PlayerTypes.Hausperger) {
            return Consts.hauspergerCharacterSize;
        }
        throw new Error("NO SHEET FOUND: " + type);
    }

}

class AnimSpecs {

    private final float animSpeed;
    private final int animPicCount;
    private final String animSheet;
    private final Dimension2D imgDimensions;

    public boolean loopAnim = false;
    public int priority = 0;
    public AnimationType animType = AnimationType.Default;

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
