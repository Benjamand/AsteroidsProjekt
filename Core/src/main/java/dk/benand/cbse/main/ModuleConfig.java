package dk.benand.cbse.main;

import dk.benand.cbse.common.data.GameData;
import dk.benand.cbse.common.data.World;
import dk.benand.cbse.common.services.IEntityProcessingService;
import dk.benand.cbse.common.services.IGamePluginService;
import dk.benand.cbse.common.services.IPostEntityProcessingService;
import dk.benand.cbse.common.background.BackgroundSPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@ComponentScan
@Configuration
public class ModuleConfig {

     public ModuleConfig() {}
     @Bean
     public GameData gameData() {
          return new GameData();
     }

     @Bean
     public World world() {
          return new World();
     }

     @Bean
     public List<IGamePluginService> gamePluginServices() {
          return ServiceLoader.load(IGamePluginService.class).stream()
                  .map(ServiceLoader.Provider::get)
                  .collect(toList());
     }

     @Bean
     public List<IEntityProcessingService> entityProcessingServiceList() {
          return ServiceLoader.load(IEntityProcessingService.class).stream()
                  .map(ServiceLoader.Provider::get)
                  .collect(toList());
     }

     @Bean
     public List<IPostEntityProcessingService> postEntityProcessingServices() {
          return ServiceLoader.load(IPostEntityProcessingService.class).stream()
                  .map(ServiceLoader.Provider::get)
                  .collect(toList());
     }

     @Bean
     public List<BackgroundSPI> backgroundSpis() {
          return ServiceLoader.load(BackgroundSPI.class).stream()
                  .map(ServiceLoader.Provider::get)
                  .collect(Collectors.toList());
     }

     @Bean
     public Game game(GameData gameData,
                      World world,
                      List<IGamePluginService> gamePluginServices,
                      List<IEntityProcessingService> entityProcessingServices,
                      List<IPostEntityProcessingService> postEntityProcessingServices,
                      List<BackgroundSPI> backgroundSpis) {
          return new Game(gameData, world, gamePluginServices, entityProcessingServices, postEntityProcessingServices, backgroundSpis);
     }



}
