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
        System.out.println("AsteroidPlugin stop");
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            (new AsteroidSplitterImpl()).createSplitAsteroid(asteroid, world);
            world.removeEntity(asteroid);
        }
    }

    private Entity createAsteroid(GameData gameData) {
        Asteroid asteroid = new Asteroid();
        Random rnd = new Random();
        int size = rnd.nextInt(10) + 5;
        asteroid.setPolygonCoordinates(size, -size, -size, -size, -size, size, size, size);
        asteroid.setX(rnd.nextFloat() * gameData.getDisplayWidth());
        asteroid.setY(rnd.nextFloat() * gameData.getDisplayHeight());
        asteroid.setRadius(size);
        asteroid.setRotation(rnd.nextInt(90));
        asteroid.setLifeAmount(rnd.nextInt(3) + 1);

        return asteroid;
    }
}
