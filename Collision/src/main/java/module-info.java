import dk.benand.cbse.common.services.IPostEntityProcessingService;

module Collision {
    requires Common;
     requires spring.web;
     exports dk.benand.cbse.collisionsystem;
     provides IPostEntityProcessingService with dk.benand.cbse.collisionsystem.CollisionDetector;
     opens dk.benand.cbse.collisionsystem to spring.core, spring.beans, spring.context;
}