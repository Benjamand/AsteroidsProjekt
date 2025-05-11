package dk.benand.cbse.opponent;

import dk.benand.cbse.common.data.GameData;
import dk.benand.cbse.common.data.World;
import dk.benand.cbse.common.services.IEntityProcessingService;

public class OpponentSpawnerSystem implements IEntityProcessingService {
     private long lastSpawnTime = 0;
     private final long spawnInterval = 30000;

     @Override
     public void process(GameData gameData, World world) {
          long now = System.currentTimeMillis();
          if (now - lastSpawnTime > spawnInterval) {
               spawnAsteroid(gameData, world);
               lastSpawnTime = now;
          }
     }

     private void spawnAsteroid(GameData gameData, World world) {
          OpponentPlugin enemy = new OpponentPlugin();
          enemy.start(gameData, world);
     }
}
