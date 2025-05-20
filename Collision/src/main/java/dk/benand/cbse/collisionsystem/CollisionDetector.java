package dk.benand.cbse.collisionsystem;

import dk.benand.cbse.common.services.IPostEntityProcessingService;
import dk.benand.cbse.common.data.Entity;
import dk.benand.cbse.common.data.GameData;
import dk.benand.cbse.common.data.World;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class CollisionDetector implements IPostEntityProcessingService {

    private final RestTemplate restTemplate;
    private final String pointServiceUrl = "http://localhost:8080/score";

    public CollisionDetector() {
        this.restTemplate = new RestTemplate();
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
        // two for loops for all entities in the world
        for (Entity entity1 : world.getEntities()) {
            for (Entity entity2 : world.getEntities()) {

                // if the two entities are identical, skip the iteration
                if (entity1.getID().equals(entity2.getID())) {
                    continue;
                }

                // CollisionDetection
                if (this.collides(entity1, entity2)) {
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
