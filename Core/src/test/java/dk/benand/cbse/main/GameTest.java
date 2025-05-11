package dk.benand.cbse.main;

import dk.benand.cbse.common.data.Entity;
import dk.benand.cbse.common.data.GameData;
import dk.benand.cbse.common.data.World;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

public class GameTest {
     @BeforeClass
     public static void initJFX() throws InterruptedException {
          CountDownLatch latch = new CountDownLatch(1);
          Platform.startup(latch::countDown);
          latch.await();
     }

     @Test
     public void testDrawAddsAndUpdatesPolygons() {
          GameData gameData = mock(GameData.class);
          World world = new World();

          Entity entity = new Entity();
          entity.setPolygonCoordinates(0.0, 0.0, 10.0, 0.0, 10.0, 10.0, 0.0, 10.0);
          entity.setX(50);
          entity.setY(75);
          entity.setRotation(30);
          entity.setColor("RED");
          world.addEntity(entity);

          Game game = new Game(gameData, world, List.of(), List.of(), List.of(), List.of());

          Platform.runLater(() -> {
               game.start(new Stage());
               game.draw();

               Polygon polygon = game.polygons.get(entity);
               assertNotNull(polygon);
               assertEquals(50, polygon.getTranslateX(), 0.01);
               assertEquals(75, polygon.getTranslateY(), 0.01);
               assertEquals(30, polygon.getRotate(), 0.01);
               assertEquals(Color.RED, polygon.getFill());
          });
     }
}
