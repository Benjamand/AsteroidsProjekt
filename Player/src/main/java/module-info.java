import dk.benand.cbse.common.services.IEntityProcessingService;
import dk.benand.cbse.common.services.IGamePluginService;

module Player {
     requires Common;
     requires CommonBullet;
     uses dk.benand.cbse.common.bullet.BulletSPI;
     provides IGamePluginService with dk.benand.cbse.player.PlayerPlugin;
     provides IEntityProcessingService with dk.benand.cbse.player.PlayerControlSystem;

}