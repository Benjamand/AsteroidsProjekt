module Core {
    requires Common;
    requires CommonBackground;
    requires javafx.graphics;
     requires spring.context;
     requires spring.core;
     requires spring.beans;
     opens dk.benand.cbse.main to javafx.graphics, spring.core, spring.beans, spring.context;
    uses dk.benand.cbse.common.services.IGamePluginService;
    uses dk.benand.cbse.common.services.IEntityProcessingService;
    uses dk.benand.cbse.common.services.IPostEntityProcessingService;
    uses dk.benand.cbse.common.background.BackgroundSPI;
}


