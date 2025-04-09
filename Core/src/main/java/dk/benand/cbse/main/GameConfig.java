package dk.benand.cbse.main;

import dk.benand.cbse.common.data.GameData;
import dk.benand.cbse.common.data.GameKeys;
import dk.benand.cbse.common.data.World;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class GameConfig {

     @Bean
     public GameData gameData() {
          return new GameData();
     }

     @Bean
     public World world() {
          return new World();
     }

}
