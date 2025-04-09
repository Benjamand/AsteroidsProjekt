import dk.benand.cbse.common.services.IPostEntityProcessingService;

module Collision {
    requires Common;
    provides IPostEntityProcessingService with dk.benand.cbse.collisionsystem.CollisionDetector;
}