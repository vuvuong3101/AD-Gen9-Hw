package players;

import models.GameRect;
import utils.Utils;
import views.ImageRenderer;
import players.Playerbullet;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by admin on 4/13/17.
 */
public class PlayerController {
    private GameRect gameRect;
    private Image image;
    private int dx;
    private  int dy;
    private int screenW = 600;
    private int screenH = 700;
    private boolean shootEnable;
    private ImageRenderer imageRenderer;
    static ArrayList<Playerbullet> playerbullets;

    public PlayerController(int x, int y, Image image) {
        gameRect =  new GameRect(x, y, 70, 56);
        imageRenderer = new ImageRenderer(image);
        this.image = image;
        shootEnable = true;
        playerbullets = new ArrayList<>();
    }
    public  GameRect getGameRect() {
        return gameRect;
    }
    public  void updateBullet() {
        for (Playerbullet bullet: playerbullets) {
            bullet.update();
        }
    }
    public  void drawBullet(Graphics graphics){
        for (Playerbullet bullet: playerbullets) {
            bullet.draw(graphics);
        }
    }

    public void draw(Graphics graphics) {

       imageRenderer.render(graphics, gameRect);
    }

    public  void processInput(boolean isUpPressed,
                              boolean isDownPressed,
                              boolean isLeftPressed,
                              boolean isRightPressed,
                              boolean isSpacePressed){
        dx = 0;
        dy = 0;
        if (isUpPressed) {
            if (gameRect.getY() > 20) {
                dy -= 10;
            }
        }
        if (isDownPressed) {
            if (gameRect.getY() <= screenH - gameRect.getHeight()) {
                dy += 10;
            }
        }
        if (isLeftPressed) {
            if (gameRect.getX() >= 0) {
                dx -= 10;
            }
        }
        if (isRightPressed) {
            if (gameRect.getX() <= screenW - gameRect.getWidth()) {
                dx += 10;
            }
        }
        if (isSpacePressed) {
            if (shootEnable) {
                Playerbullet bullet = new Playerbullet(gameRect.getX() + gameRect.getWidth() / 2, gameRect.getY(), Utils.loadImage("res/bullet.png"));
                playerbullets.add(bullet);
                shootEnable = false;
                coolDownTime = 15;
            }

        }

    }
    int coolDownTime;
    public  void update() {
         gameRect.move(dx,dy);
        if  (!shootEnable) {
            coolDownTime --;
            if (coolDownTime == 0) {
                shootEnable = true;
            }
        }
    }
    public Rectangle getrect() {
        return (new Rectangle(gameRect.getX(), gameRect.getY(), 50, 70));
    }
    public  void  remove(Playerbullet e) {
        remove(e);
    }
    public ArrayList<Playerbullet> getPlayerbullets() {
        return playerbullets;
    }
}
