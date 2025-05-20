module PointSystem {
     requires javafx.graphics;
     requires spring.context;
     requires spring.core;
     requires spring.beans;
     requires spring.boot.autoconfigure;
     requires spring.web;
     requires spring.boot;
     exports dk.benand.cbse.points to spring.web;
     opens dk.benand.cbse.points to javafx.graphics, spring.core, spring.beans, spring.context;
}