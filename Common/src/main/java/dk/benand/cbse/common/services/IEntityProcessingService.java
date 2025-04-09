package dk.benand.cbse.common.services;

import dk.benand.cbse.common.data.GameData;
import dk.benand.cbse.common.data.World;

public interface IEntityProcessingService {

    /**
     *
     *
     *
     * @param gameData
     * @param world
     * @throws
     */
    void process(GameData gameData, World world);
}