package dk.benand.cbse.main;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main extends Application {

     private Game game;

     public static void main(String[] args) {
          launch(args);
     }

     @Override
     public void start(Stage window) {
          AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ModuleConfig.class);
          game = context.getBean(Game.class);
          game.start(window);
          game.render();
     }
}
