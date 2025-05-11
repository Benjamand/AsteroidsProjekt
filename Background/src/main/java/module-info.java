import dk.benand.cbse.background.BackgroundPlugin;
import dk.benand.cbse.common.background.BackgroundSPI;

module Background {
     requires Common;
     requires CommonBackground;
     requires javafx.graphics;
     requires javafx.swing;
     provides BackgroundSPI with BackgroundPlugin;
}