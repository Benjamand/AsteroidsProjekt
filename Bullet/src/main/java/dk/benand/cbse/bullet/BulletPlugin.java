package dk.benand.cbse.bullet;

import dk.benand.cbse.common.bullet.Bullet;
import dk.benand.cbse.common.data.Entity;
import dk.benand.cbse.common.data.GameData;
import dk.benand.cbse.common.data.World;
import dk.benand.cbse.common.services.IGamePluginService;

public class BulletPlugin implements IGamePluginService {

    private Entity bullet;

    @Override
    public void start(GameData gameData, World world) {

    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity e : world.getEntities()) {
            if (e.getClass() == Bullet.class) {
                world.removeEntity(e);
            }
        }
    }

}
