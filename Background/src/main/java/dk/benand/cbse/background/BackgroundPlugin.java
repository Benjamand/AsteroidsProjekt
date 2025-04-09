package dk.benand.cbse.background;

import dk.benand.cbse.common.background.BackgroundSPI;
import dk.benand.cbse.common.data.GameData;
import dk.benand.cbse.common.data.World;
import dk.benand.cbse.common.services.IGamePluginService;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BackgroundPlugin implements IGamePluginService, BackgroundSPI {


     ImageView view;
     @Override
     public void start(GameData gameData, World world) {

     }

     @Override
     public void stop(GameData gameData, World world) {

     }

     @Override
     public ImageView getBackground(GameData gameData) {

               view = new ImageView(new Image(this.getClass().getResource("/images/background.png").toString()));

          view.setFitHeight(gameData.getDisplayHeight());
          view.setFitWidth(gameData.getDisplayWidth());
          return view;
     }
}
