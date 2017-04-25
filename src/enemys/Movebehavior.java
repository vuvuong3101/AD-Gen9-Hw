package enemys;
import enemys.EnemyController;
import models.GameRect;

/**
 * Created by admin on 4/25/17.
 */
public class Movebehavior {
    public  void move(GameRect gameRect) {
        if (EnemyController.isStatusLeft) {
            gameRect.move(-5,0);
            if (gameRect.getX() == 0) {
               EnemyController. isStatusLeft = false;
                EnemyController.isStatusRight = true;
            }
        }
        if (EnemyController.isStatusRight) {
            gameRect.move(5, 0);
            if (gameRect.getX() == 550) {
                EnemyController.isStatusRight = false;
                EnemyController.isStatusLeft = true;
            }
        }
    }
}
