package dk.benand.cbse.background;
import dk.benand.cbse.background.BackgroundPlugin;
import dk.benand.cbse.common.data.GameData;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BackgroundPluginTest {
     @BeforeAll
     static void initJavaFX() {
          new JFXPanel();
     }

     @Test
     void testGetBackgroundReturnsCorrectImageView() {
          GameData gameData = new GameData();
          gameData.setDisplayHeight(600);
          gameData.setDisplayWidth(800);

          BackgroundPlugin plugin = new BackgroundPlugin();

          ImageView bg = plugin.getBackground(gameData);

          assertNotNull(bg, "ImageView should not be null");
          assertEquals(800, bg.getFitWidth());
          assertEquals(600, bg.getFitHeight());
          assertTrue(bg.getImage().getUrl().contains("background.png"), "Image path should contain 'background.png'");
     }
}
