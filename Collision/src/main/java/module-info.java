import dk.benand.cbse.common.services.IPostEntityProcessingService;

module Collision {
     uses dk.benand.cbse.common.asteroids.IAsteroidSplitter;
     requires Common;
     requires CommonAsteroids;
     requires spring.web;
 requires spring.beans;
 exports dk.benand.cbse.collisionsystem;
     provides IPostEntityProcessingService with dk.benand.cbse.collisionsystem.CollisionDetector;
     opens dk.benand.cbse.collisionsystem to spring.core, spring.beans, spring.context;
}