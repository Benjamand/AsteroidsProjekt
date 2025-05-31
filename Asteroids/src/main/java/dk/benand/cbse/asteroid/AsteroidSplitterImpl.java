package dk.benand.cbse.asteroid;

import dk.benand.cbse.common.asteroids.Asteroid;
import dk.benand.cbse.common.asteroids.IAsteroidSplitter;
import dk.benand.cbse.common.data.Entity;
import dk.benand.cbse.common.data.World;

import java.util.Random;

/**
 *
 * @author corfixen
 */
public class AsteroidSplitterImpl implements IAsteroidSplitter {

    @Override
    public void createSplitAsteroid(Entity original, World world) {
        System.out.println("AsteroidSplitterImpl createSplitAsteroid");
        if (!(original instanceof Asteroid)) {
            return;
        }

        Asteroid parentAsteroid = (Asteroid) original;

        int newLifeAmount = parentAsteroid.getLifeAmount() - 1;
        if (newLifeAmount <= 0) {
            return;
        }

        for (int i = 0; i < 2; i++) {
            Random rnd = new Random();
            Asteroid smallAsteroid = new Asteroid();

            smallAsteroid.setX(parentAsteroid.getX() + rnd.nextInt(-10, 10));
            smallAsteroid.setY(parentAsteroid.getY()  + rnd.nextInt(-10, 10));

            float newRadius = parentAsteroid.getRadius() * 0.6f;
            smallAsteroid.setRadius(newRadius);

            smallAsteroid.setPolygonCoordinates(
                    newRadius, -newRadius,
                    -newRadius, -newRadius,
                    -newRadius, newRadius,
                    newRadius, newRadius
            );

            float rotationOffset = rnd.nextInt(60) - 30;
            smallAsteroid.setRotation(parentAsteroid.getRotation() + rotationOffset);

            smallAsteroid.setLifeAmount(newLifeAmount);

            world.addEntity(smallAsteroid);
        }
    }

}
