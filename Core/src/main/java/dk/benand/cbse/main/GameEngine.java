package dk.benand.cbse.main;

import dk.benand.cbse.common.data.GameData;
import dk.benand.cbse.common.data.World;

import dk.benand.cbse.common.services.IEntityProcessingService;
import dk.benand.cbse.common.services.IGamePluginService;
import dk.benand.cbse.common.services.IPostEntityProcessingService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GameEngine {
     private final GameData gameData;
     private final World world;
     private final List<IGamePluginService> pluginServices;
     private final List<IEntityProcessingService> entityProcessingServices;
     private final List<IPostEntityProcessingService> postEntityProcessingServices;

     public GameEngine(
             GameData gameData,
             World world,
             List<IGamePluginService> pluginServices,
             List<IEntityProcessingService> entityProcessingServices,
             List<IPostEntityProcessingService> postEntityProcessingServices) {
          this.gameData = gameData;
          this.world = world;
          this.pluginServices = pluginServices;
          this.entityProcessingServices = entityProcessingServices;
          this.postEntityProcessingServices = postEntityProcessingServices;
     }

     public void init() {
          for (IGamePluginService plugin : pluginServices) {
               plugin.start(gameData, world);
          }
     }

     public void update() {
          for (IEntityProcessingService entityProcessor : entityProcessingServices) {
               entityProcessor.process(gameData, world);
          }
          for (IPostEntityProcessingService postProcessor : postEntityProcessingServices) {
               postProcessor.process(gameData, world);
          }
          gameData.getKeys().update();
     }
}
