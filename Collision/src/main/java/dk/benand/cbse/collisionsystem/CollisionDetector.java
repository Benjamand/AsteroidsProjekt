package dk.benand.cbse.collisionsystem;

import dk.benand.cbse.common.asteroids.Asteroid;
import dk.benand.cbse.common.asteroids.IAsteroidSplitter;
import dk.benand.cbse.common.services.IPostEntityProcessingService;
import dk.benand.cbse.common.data.Entity;
import dk.benand.cbse.common.data.GameData;
import dk.benand.cbse.common.data.World;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.ServiceLoader;
import java.util.ArrayList;


public class CollisionDetector implements IPostEntityProcessingService {

    private final RestTemplate restTemplate;
    private final String pointServiceUrl = "http://localhost:8080/score";
    private IAsteroidSplitter asteroidSplitter;

    public CollisionDetector() {
        this.restTemplate = new RestTemplate();
        ServiceLoader<IAsteroidSplitter> loader = ServiceLoader.load(IAsteroidSplitter.class);
        loader.findFirst().ifPresent(s -> this.asteroidSplitter = s);
    }


    public void addPoints(Long points) {
        String url = pointServiceUrl + "?point=" + points;
        if (isApiAvailable(url)) {
            try {
                ResponseEntity<String> response = restTemplate.getForEntity(pointServiceUrl + "?point=" + points, String.class);
            } catch (Exception e) {
                System.err.println("Failed to add points: " + e.getMessage());
            }
        }
    }

    @Override
    public void process(GameData gameData, World world) {
        List<Entity> entities = new ArrayList<>(world.getEntities());

        for (int i = 0; i < entities.size(); i++) {
            Entity entity1 = entities.get(i);
            for (int j = i + 1; j < entities.size(); j++) {
                Entity entity2 = entities.get(j);

                if (collides(entity1, entity2)) {
                    if (entity1 instanceof Asteroid && asteroidSplitter != null) {
                        if (world.getEntities().contains(entity1)) {
                            asteroidSplitter.createSplitAsteroid(entity1, world);
                        }
                    }
                    if (entity2 instanceof Asteroid && asteroidSplitter != null) {
                        if (world.getEntities().contains(entity2)) {
                            asteroidSplitter.createSplitAsteroid(entity2, world);
                        }
                    }

                    world.removeEntity(entity1);
                    world.removeEntity(entity2);
                    addPoints(1L);
                }
            }
        }

    }

    public Boolean collides(Entity entity1, Entity entity2) {
        float dx = (float) entity1.getX() - (float) entity2.getX();
        float dy = (float) entity1.getY() - (float) entity2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < (entity1.getRadius() + entity2.getRadius());
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
