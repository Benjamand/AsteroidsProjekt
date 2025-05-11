package dk.benand.cbse.opponent;

import dk.benand.cbse.common.services.IGamePluginService;

import dk.benand.cbse.common.data.Entity;
import dk.benand.cbse.common.data.GameData;
import dk.benand.cbse.common.data.World;

import java.util.Random;

public class OpponentPlugin implements IGamePluginService {


    private Entity enemy;

    public OpponentPlugin() {}

    @Override
    public void start(GameData gameData, World world) {
        // Add entities to the world
        enemy = createEnemyShip(gameData);
        world.addEntity(enemy);
    }

    private Entity createEnemyShip(GameData gameData) {

        Entity enemyShip = new Opponent();
        Random random = new Random();
        enemyShip.setPolygonCoordinates(-5,-5,10,0,-5,5);
        enemyShip.setX(random.nextInt(gameData.getDisplayHeight()));
        enemyShip.setY(random.nextInt(gameData.getDisplayWidth()));
        enemyShip.setRadius(8);
        enemyShip.setColor("RED");
        return enemyShip;
    }

        @Override
        public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(enemy);
    }

    }
