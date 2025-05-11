
import dk.benand.cbse.common.services.IEntityProcessingService;
import dk.benand.cbse.common.services.IGamePluginService;
import dk.benand.cbse.opponent.OpponentControlSystem;
import dk.benand.cbse.opponent.OpponentPlugin;
import dk.benand.cbse.opponent.OpponentSpawnerSystem;

module Opponent {
    requires Common;
    requires CommonBullet;   
    uses dk.benand.cbse.common.bullet.BulletSPI;
    provides IGamePluginService with OpponentPlugin;
    provides IEntityProcessingService with OpponentControlSystem, OpponentSpawnerSystem;
}
