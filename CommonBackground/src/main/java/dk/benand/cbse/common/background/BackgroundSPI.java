package dk.benand.cbse.common.background;

import java.net.URL;
import dk.benand.cbse.common.data.GameData;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;

public interface BackgroundSPI {
     ImageView getBackground(GameData gameData);
}
