package physics;

import game.GameObject;

public interface CollissionListener {

    void onCollision(Collider c1, Collider c2);

}
