import dk.benand.cbse.asteroid.AsteroidSplitterImpl;
import dk.benand.cbse.common.asteroids.IAsteroidSplitter;
import dk.benand.cbse.common.services.IEntityProcessingService;
import dk.benand.cbse.common.services.IGamePluginService;

module Asteroid {
    requires Common;
    requires CommonAsteroids;
    provides IGamePluginService with dk.benand.cbse.asteroid.AsteroidPlugin;
    provides IEntityProcessingService with dk.benand.cbse.asteroid.AsteroidProcessor, dk.benand.cbse.asteroid.AsteroidSpawner;
    provides IAsteroidSplitter with dk.benand.cbse.asteroid.AsteroidSplitterImpl;
}