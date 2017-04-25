package enemys;

import java.awt.*;

/**
 * Created by admin on 4/13/17.
 */
public class Enemybullet {
    private int x;
    private int y;
    private Image image;

    public Enemybullet(int x, int y, Image image) {
        this.x = x - image.getWidth(null)/2;
        this.y = y + image.getHeight(null);
        this.image = image;
    }
    public void draw(Graphics graphics) {
        graphics.drawImage(image, x, y, null);
    }
    public  void update(){
        this.y += 9;
    }
    public  Rectangle getrect() {
        return (new Rectangle(x, y, 32, 13));
    }
}
