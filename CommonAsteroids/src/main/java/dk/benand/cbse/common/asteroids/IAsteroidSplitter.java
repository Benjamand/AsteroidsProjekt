package dk.benand.cbse.common.asteroids;

import dk.benand.cbse.common.data.Entity;
import dk.benand.cbse.common.data.World;

/**
 *
 * @author corfixen
 */
public interface IAsteroidSplitter {
    void createSplitAsteroid(Entity e, World w);
}