package dk.benand.cbse.common.services;

import dk.benand.cbse.common.data.GameData;
import dk.benand.cbse.common.data.World;

public interface IGamePluginService {

    void start(GameData gameData, World world);

    void stop(GameData gameData, World world);
}