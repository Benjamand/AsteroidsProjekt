package dk.benand.cbse.asteroid;

import dk.benand.cbse.common.asteroids.Asteroid;
import dk.benand.cbse.common.data.Entity;
import dk.benand.cbse.common.data.GameData;
import dk.benand.cbse.common.data.World;
import dk.benand.cbse.common.services.IGamePluginService;
import java.util.Random;

/**
 *
 * @author corfixen
 */
public class AsteroidPlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, World world) {
        Entity asteroid = createAsteroid(gameData);
        world.addEntity(asteroid);
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            world.removeEntity(asteroid);
        }
    }

    private Entity createAsteroid(GameData gameData) {
        Asteroid asteroid = new Asteroid();
        Random rnd = new Random();
        int size = rnd.nextInt(10) + 5;
        asteroid.setPolygonCoordinates(size, -size, -size, -size, -size, size, size, size);
        asteroid.setX(0);
        asteroid.setY(0);
        asteroid.setRadius(size);
        asteroid.setRotation(rnd.nextInt(90));
        asteroid.setLifeAmount(rnd.nextInt(4));

        return asteroid;
    }
}
