package loading;

import animation.AnimSpecs;
import animation.Animation;
import game.Consts;
import gameobjects.GameObject;
import graphics.ImageSprite;
import logic.Dimension2D;
import logic.PlayerType;
import particle.ParticleType;
import player.PlayerState;

import java.io.File;

public abstract class AnimationLoader {

    public static Animation loadPlayerAnimation(PlayerType type, PlayerState animType, GameObject ref, boolean loopAnim, int priority) {
        AnimSpecs animSpec = getAnimSpecs(animType, type);
        String path = AnimationLoader.getPath(type, animSpec);

        File f = new File(path);
        if (!f.exists()) {
            System.err.println("File does not exist : " + path + " -> using default path instead");
            animSpec = getAnimSpecs(PlayerState.Default, type);
            path = AnimationLoader.getPath(type, animSpec);
        }

        animSpec.animType = animType;
        animSpec.loopAnim = loopAnim;
        animSpec.priority = priority;

        return Animation.loadAnim(path, animSpec, ref);
    }

    public static Animation loadParticleAnimation(ParticleType type, int picCount, float animationSpeed, Dimension2D size, GameObject ref, boolean loopAnim) {
        return loadAnimation(Consts.imageParticlesSrc + type.toString(), picCount, animationSpeed, size, ref, loopAnim, 1);
    }

    public static Animation loadAnimation(String path, int picCount, float animationSpeed, Dimension2D size, GameObject ref, boolean loopAnim, int priority) {
        AnimSpecs animSpec = new AnimSpecs(path, picCount, animationSpeed, size);

        File f = new File(path);
        if (!f.exists()) {
            System.err.println("File does not exist : " + path + " -> TODO : using default path instead");
        }

        animSpec.loopAnim = loopAnim;
        animSpec.priority = priority;

        return Animation.loadAnim(path, animSpec, ref);
    }

    public static Animation loadAnimation(ImageSprite[] animImages, float animationSpeed, GameObject ref, boolean loopAnim, int priority) {
        AnimSpecs animSpec = new AnimSpecs(null, 0, animationSpeed, null);

        animSpec.loopAnim = loopAnim;
        animSpec.priority = priority;

        return Animation.loadAnim(animImages, animSpec, ref);
    }

    private static String getPath(PlayerType type, AnimSpecs a) {
        return Consts.imageSrc + type.name() + "/" + a.getAnimSheet();
    }


    private static AnimSpecs getAnimSpecs(PlayerState type, PlayerType pType) {
        AnimSpecs specs;
        switch (type) {
            case Default:
                specs = new AnimSpecs(Consts.defaultSheet, Consts.defaultPicCount, Consts.defaultAnimSpeed, getSpriteSize(pType));
                break;
            case Walk:
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
            case SpecialAttack2:
                specs = new AnimSpecs(Consts.specialAttack2Sheet, Consts.specialAttack2PicCount, Consts.specialAttack2AnimSpeed, getSpriteSize(pType));
                break;
            case GotHit1:
                specs = new AnimSpecs(Consts.gotHit1Sheet, Consts.gotHit1PicCount, Consts.gotHit1AnimSpeed, getSpriteSize(pType));
                break;
            case GotHit2:
                specs = new AnimSpecs(Consts.gotHit2Sheet, Consts.gotHit2PicCount, Consts.gotHit2AnimSpeed, getSpriteSize(pType));
                break;
            case Death:
                specs = new AnimSpecs(Consts.defaultSheet, Consts.deathSheetCount, Consts.deathSheetSpeed, getSpriteSize(pType));
                break;
            default:
                throw new Error("NO SHEET FOUND: " + type);
        }
        return specs;
    }

    private static Dimension2D getSpriteSize(PlayerType type) {
        if (type == PlayerType.Hausperger) {
            return Consts.hauspergerCharacterSize;
        }
        throw new Error("NO SHEET FOUND: " + type);
    }

}

