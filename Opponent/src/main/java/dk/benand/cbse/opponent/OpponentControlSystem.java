package dk.benand.cbse.opponent;

import dk.benand.cbse.common.bullet.BulletSPI;
import dk.benand.cbse.common.data.Entity;
import dk.benand.cbse.common.data.GameData;
import dk.benand.cbse.common.data.World;
import dk.benand.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class OpponentControlSystem implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Opponent.class)) {

            double rand = Math.random();
            if (rand <= 0.25) {
                enemy.setRotation(enemy.getRotation() - 5);
            }
            if (rand <= 0.5 && rand > 0.25) {
                enemy.setRotation(enemy.getRotation() + 5);
            }
            if (rand <= 0.75 && rand > 0.5) {
                double changeX = Math.cos(Math.toRadians(enemy.getRotation()));
                double changeY = Math.sin(Math.toRadians(enemy.getRotation()));
                enemy.setX(enemy.getX() + changeX);
                enemy.setY(enemy.getY() + changeY);
            }
            if(rand > 0.75) {
                getBulletSPIs().stream().findFirst().ifPresent(
                        spi -> {world.addEntity(spi.createBullet(enemy, gameData));}
                );
            }

            if (enemy.getX() < 0) {
                enemy.setX(1);
            }

            if (enemy.getX() > gameData.getDisplayWidth()) {
                enemy.setX(gameData.getDisplayWidth()-1);
            }

            if (enemy.getY() < 0) {
                enemy.setY(1);
            }

            if (enemy.getY() > gameData.getDisplayHeight()) {
                enemy.setY(gameData.getDisplayHeight()-1);
            }


        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
