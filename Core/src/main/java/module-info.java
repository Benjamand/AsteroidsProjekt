module Core {
    requires Common;
    requires CommonBullet;
    requires CommonBackground;
    requires javafx.graphics;
    requires spring.boot.autoconfigure;
     requires spring.context;
     opens dk.benand.cbse.main to javafx.graphics;
    uses dk.benand.cbse.common.services.IGamePluginService;
    uses dk.benand.cbse.common.services.IEntityProcessingService;
    uses dk.benand.cbse.common.services.IPostEntityProcessingService;
    uses dk.benand.cbse.common.background.BackgroundSPI;
}


