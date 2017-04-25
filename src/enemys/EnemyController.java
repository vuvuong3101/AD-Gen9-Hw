package enemys;

import models.GameRect;
import players.Playerbullet;
import utils.Utils;
import views.ImageRenderer;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by admin on 4/13/17.
 */
public class EnemyController {
    private ImageRenderer imageRenderer;
    private GameRect gameRect;
    private int dx;
    private int dy;
    static boolean isStatusLeft;
    static boolean isStatusRight;
    private boolean shootEnable;
    private Movebehavior movebehavior;
   static  ArrayList<Enemybullet> enemybullets;

    public void setMovebehavior(Movebehavior movebehavior) {
        this.movebehavior = movebehavior;
    }

    public EnemyController(int x, int y, Image image) {
        gameRect = new GameRect(x , y, 50, 50);
        imageRenderer = new ImageRenderer(image);

        isStatusLeft = true;
        shootEnable = true;
        enemybullets = new ArrayList<>();

    }
    public void draw(Graphics graphics) {

       imageRenderer.render(graphics, gameRect);
    }
    public  void automove(){
        if (movebehavior != null) {
            movebehavior.move(gameRect);

        }
        if (gameRect.getY() > 700) {
            gameRect.setY(0);
        }
    }
    int coolDownTime;
    public  void autoshoot() {
        coolDownTime --;
        if (shootEnable) {
            Enemybullet enemy_bullet = new Enemybullet(
                    gameRect.getX() + gameRect.getWidth() / 2, gameRect.getY(), Utils.loadImage("res/enemy_bullet.png"));
            enemybullets.add(enemy_bullet);
            shootEnable = false;
            coolDownTime = 25;
        }
        if (coolDownTime == 0){
            shootEnable = true;
        }
    }
    public void draw_Ebullet(Graphics graphics) {
        for ( Enemybullet enemybullet : enemybullets) {
            enemybullet.draw(graphics);
        }
    }
    public  void updateEbullet() {
        for ( Enemybullet enemybullet : enemybullets) {
            enemybullet.update();
        }
    }
    public Rectangle getrect() {
        return (new Rectangle(gameRect.getX(), gameRect.getY(), 50, 50));
    }
    public  static  void remove(Enemybullet e) {
        enemybullets.remove(e);
    }

    public ArrayList<Enemybullet> getEnemybullets() {
        return enemybullets;
    }
}
