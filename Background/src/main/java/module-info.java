import dk.benand.cbse.background.BackgroundPlugin;
import dk.benand.cbse.common.background.BackgroundSPI;

module Background {
     requires Common;
     requires CommonBackground;
     requires javafx.graphics;
     provides BackgroundSPI with BackgroundPlugin;
}