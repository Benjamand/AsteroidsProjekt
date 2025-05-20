package dk.benand.cbse.main;

import dk.benand.cbse.common.background.BackgroundSPI;
import dk.benand.cbse.common.data.GameData;
import dk.benand.cbse.common.data.GameKeys;
import dk.benand.cbse.common.data.World;
import dk.benand.cbse.common.services.IEntityProcessingService;
import dk.benand.cbse.common.services.IGamePluginService;
import dk.benand.cbse.common.services.IPostEntityProcessingService;
import dk.benand.cbse.common.data.Entity;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.lang.module.Configuration;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Game {

    private final GameData gameData;
    private final World world;
    private final List<IGamePluginService> gamePluginServices;
    private final List<IEntityProcessingService> entityProcessingServices;
    private final List<IPostEntityProcessingService> postEntityProcessingServices;
    private final List<BackgroundSPI> backgroundSpis;
    private final RestTemplate restTemplate;
    private final String pointServiceUrl = "http://localhost:8080/score";
    private Label scoreLabel = new Label("0");

    final Map<Entity, Polygon> polygons = new ConcurrentHashMap<>();
    private final Pane gameWindow = new Pane();

    public Game(GameData gameData,
                World world,
                List<IGamePluginService> gamePluginServices,
                List<IEntityProcessingService> entityProcessingServices,
                List<IPostEntityProcessingService> postEntityProcessingServices,
                List<BackgroundSPI> backgroundSpis) {
        this.gameData = gameData;
        this.world = world;
        this.gamePluginServices = gamePluginServices;
        this.entityProcessingServices = entityProcessingServices;
        this.postEntityProcessingServices = postEntityProcessingServices;
        this.backgroundSpis = backgroundSpis;
        this.restTemplate = new RestTemplate();
    }

    public void start(Stage window) {
        gameWindow.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        Scene scene = new Scene(gameWindow);


        for (BackgroundSPI backgroundSPI : backgroundSpis) {
            ImageView background = backgroundSPI.getBackground(gameData);
            gameWindow.getChildren().add(background);
        }

        Label scoreTextLabel = new Label("Score:");
        scoreTextLabel.setFont(new Font("Arial", 20));
        scoreTextLabel.setTextFill(Color.RED);
        scoreTextLabel.setTranslateX(10);
        scoreTextLabel.setTranslateY(10);
        gameWindow.getChildren().add(scoreTextLabel);
        ;
        scoreLabel.setFont(new Font("Arial", 20));
        scoreLabel.setTextFill(Color.RED);
        scoreLabel.setTranslateX(100);
        scoreLabel.setTranslateY(10);
        gameWindow.getChildren().add(scoreLabel);

        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.A)) {
                gameData.getKeys().setKey(GameKeys.LEFT, true);
            }
            if (event.getCode().equals(KeyCode.D)) {
                gameData.getKeys().setKey(GameKeys.RIGHT, true);
            }
            if (event.getCode().equals(KeyCode.W)) {
                gameData.getKeys().setKey(GameKeys.UP, true);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                gameData.getKeys().setKey(GameKeys.SPACE, true);
            }
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.A)) {
                gameData.getKeys().setKey(GameKeys.LEFT, false);
            }
            if (event.getCode().equals(KeyCode.D)) {
                gameData.getKeys().setKey(GameKeys.RIGHT, false);
            }
            if (event.getCode().equals(KeyCode.W)) {
                gameData.getKeys().setKey(GameKeys.UP, false);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                gameData.getKeys().setKey(GameKeys.SPACE, false);
            }

        });

        // Start plugins
        for (IGamePluginService plugin : gamePluginServices) {
            plugin.start(gameData, world);
        }

        for (Entity entity : world.getEntities()) {

            Polygon polygon = new Polygon(entity.getPolygonCoordinates());
            if (entity.getColor() != null) {
                polygon.setFill(Color.valueOf(entity.getColor()));
            }
            polygons.put(entity, polygon);
            gameWindow.getChildren().add(polygon);
        }
        String resetScoreUrl = "http://localhost:8080/resetScore";
        if (isApiAvailable(resetScoreUrl)) {
            restTemplate.getForEntity(resetScoreUrl, String.class);
        }
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    String url = pointServiceUrl + "?point=0";
                    if (isApiAvailable(url)) {
                        try {
                            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
                            Long score = Long.valueOf(response.getBody());
                            scoreLabel.setText(String.valueOf(score));
                        } catch (Exception e) {
                            System.err.println("Failed to get score: " + e.getMessage());
                        }
                    }
                }
                ));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        window.setScene(scene);
        window.setTitle("ASTEROIDS");
        window.show();
    }

    public void render() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                draw();
                gameData.getKeys().update();
            }
        }.start();
    }

    private void update() {

        for (IEntityProcessingService entityProcessor : entityProcessingServices) {
            entityProcessor.process(gameData, world);
        }
        for (IPostEntityProcessingService postProcessor : postEntityProcessingServices) {
            postProcessor.process(gameData, world);
        }

    }

    void draw() {
        for (Entity polygonEntity : polygons.keySet()) {
            if (!world.getEntities().contains(polygonEntity)) {
                Polygon removedPolygon = polygons.get(polygonEntity);
                polygons.remove(polygonEntity);
                gameWindow.getChildren().remove(removedPolygon);
            }
        }

        for (Entity entity : world.getEntities()) {
            Polygon polygon = polygons.get(entity);
            if (polygon == null) {
                polygon = new Polygon(entity.getPolygonCoordinates());
                if (entity.getColor() != null) {
                    polygon.setFill(Color.valueOf(entity.getColor()));
                }
                polygons.put(entity, polygon);
                gameWindow.getChildren().add(polygon);
            }
            polygon.setTranslateX(entity.getX());
            polygon.setTranslateY(entity.getY());
            polygon.setRotate(entity.getRotation());
        }
    }

    private boolean isApiAvailable(String url) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            System.err.println("API check failed: " + url + " - " + e.getMessage());
            return false;
        }
    }
}
