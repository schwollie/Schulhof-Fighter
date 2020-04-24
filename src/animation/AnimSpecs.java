package animation;

import logic.Dimension2D;
import player.PlayerState;

public class AnimSpecs {

    private final float animSpeed;
    private final int animPicCount;
    private final String animSheet;
    private final Dimension2D imgDimensions;

    public boolean loopAnim = false;
    public int priority = 0;
    public PlayerState animType = PlayerState.Default;

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
