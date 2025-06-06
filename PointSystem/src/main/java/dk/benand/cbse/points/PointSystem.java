package dk.benand.cbse.points;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class PointSystem {
     private Long totalScore = 0L;

     public PointSystem() {}

     public static void main(String[] args) {
          SpringApplication.run(PointSystem.class, args);
     }

     @GetMapping("/score")
     public Long calculateScore(@RequestParam(value = "point") Long point) {
          totalScore += point;
          return totalScore;
     }

     @GetMapping("/resetScore")
     public void resetScore() {
          totalScore = 0L;
     }


}


