import dk.benand.cbse.common.bullet.BulletSPI;
import dk.benand.cbse.common.services.IEntityProcessingService;
import dk.benand.cbse.common.services.IGamePluginService;

module Bullet {
    requires Common;
    requires CommonBullet;
    provides IGamePluginService with dk.benand.cbse.bullet.BulletPlugin;
    provides BulletSPI with dk.benand.cbse.bullet.BulletControlSystem;
    provides IEntityProcessingService with dk.benand.cbse.bullet.BulletControlSystem;

    exports dk.benand.cbse.bullet;
}