package dk.benand.cbse.common.bullet;

import dk.benand.cbse.common.data.Entity;
import dk.benand.cbse.common.data.GameData;

/**
 *
 * @author corfixen
 */
public interface BulletSPI {
    Entity createBullet(Entity e, GameData gameData);
}