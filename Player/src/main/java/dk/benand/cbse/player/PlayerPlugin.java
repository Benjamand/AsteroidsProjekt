package dk.benand.cbse.player;

import dk.benand.cbse.common.data.Entity;
import dk.benand.cbse.common.data.GameData;
import dk.benand.cbse.common.data.World;
import dk.benand.cbse.common.services.IGamePluginService;
public class PlayerPlugin implements IGamePluginService {

     private Entity player;

     public PlayerPlugin() {
     }

     @Override
     public void start(GameData gameData, World world) {

          // Add entities to the world
          player = createPlayerShip(gameData);
          world.addEntity(player);
     }

     private Entity createPlayerShip(GameData gameData) {

          Entity playerShip = new Player();
          playerShip.setPolygonCoordinates(-5,-5,10,0,-5,5);
          playerShip.setX(gameData.getDisplayHeight()/2);
          playerShip.setY(gameData.getDisplayWidth()/2);
          playerShip.setRadius(8);
          return playerShip;
     }

     @Override
     public void stop(GameData gameData, World world) {
          System.out.println("Player ship stopped");
          // Remove entities
          world.removeEntity(player);
     }

}
