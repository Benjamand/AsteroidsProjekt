package dk.benand.cbse.common.services;

import dk.benand.cbse.common.data.GameData;
import dk.benand.cbse.common.data.World;

/**
 *
 * @author jcs
 */
public interface IPostEntityProcessingService {

    void process(GameData gameData, World world);
}