package dk.benand.cbse.asteroid;

import dk.benand.cbse.common.asteroids.Asteroid;
import dk.benand.cbse.common.data.GameData;
import dk.benand.cbse.common.data.World;
import dk.benand.cbse.common.services.IEntityProcessingService;

import java.util.Random;

public class AsteroidSpawner implements IEntityProcessingService {

     private long lastSpawnTime = 0;
     private final long spawnInterval = 500; // 4 seconds (in milliseconds)

     @Override
     public void process(GameData gameData, World world) {
          long now = System.currentTimeMillis();
          if (now - lastSpawnTime > spawnInterval) {
               spawnAsteroid(gameData, world);
               lastSpawnTime = now;
          }
     }

     private void spawnAsteroid(GameData gameData, World world) {
          Random rnd = new Random();
          Asteroid asteroid = new Asteroid();
          int size = rnd.nextInt(10) + 5;

          asteroid.setPolygonCoordinates(size, -size, -size, -size, -size, size, size, size);

          int edge = rnd.nextInt(4);
          float x = 0;
          float y = 0;

          switch (edge) {
               case 0:
                    x = rnd.nextFloat() * gameData.getDisplayWidth();
                    y = 0;
                    break;
               case 1:
                    x = rnd.nextFloat() * gameData.getDisplayWidth();
                    y = gameData.getDisplayHeight();
                    break;
               case 2:
                    x = 0;
                    y = rnd.nextFloat() * gameData.getDisplayHeight();
                    break;
               case 3:
                    x = gameData.getDisplayWidth();
                    y = rnd.nextFloat() * gameData.getDisplayHeight();
                    break;
          }

          asteroid.setX(x);
          asteroid.setY(y);
          asteroid.setRadius(size);
          asteroid.setRotation(rnd.nextInt(360));
          asteroid.setLifeAmount(rnd.nextInt(4) + 1);

          world.addEntity(asteroid);
     }
}
