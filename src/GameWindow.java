import enemys.EnemyController;
import enemys.Enemybullet;
import enemys.HorzMovebehavior;
import enemys.Movebehavior;
import players.PlayerController;
import players.Playerbullet;
import utils.Utils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


/**
 * Created by admin on 4/13/17.
 */
public class GameWindow extends Frame{
    Image backgroundImage;

    private int screenW = 600;
    private int screenH = 700;
    boolean isUpPressed;
    boolean isDownPressed;
    boolean isLeftPressed;
    boolean isRightPressed;
    boolean isSpacePressed;

    PlayerController playerController;
    EnemyController enemyController;
    EnemyController enemyController2;
    BufferedImage backbufferedImage;
    Graphics backBufferedGapphics;

    ArrayList<EnemyController> enemyControllers;
    public GameWindow() {
        // Set Game Window
        setVisible(true);
        setSize(screenW, screenH);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);

            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    isLeftPressed = true;
                }else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    isRightPressed = true;
                }else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    isUpPressed = true;
                }else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    isDownPressed = true;
                }if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                    isSpacePressed = true;
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    isLeftPressed = false;
                }else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    isRightPressed =false;
                }else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    isUpPressed = false;
                }else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    isDownPressed = false;
                }if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                    isSpacePressed = false;
                }

            }
        });
        backbufferedImage = new BufferedImage(screenW, screenH, BufferedImage.TYPE_INT_ARGB);
        backBufferedGapphics = backbufferedImage.getGraphics();

        playerController = new PlayerController(370, 530, Utils.loadImage("res/plane2.png") );
        ////
        enemyControllers = new ArrayList<>();
        enemyController2 = new EnemyController(400, 100, Utils.loadImage("res/enemy_plane_yellow_1.png"));
        enemyControllers.add(enemyController2);

        for ( int x = 0; x < 600; x += 200) {
            enemyController = new EnemyController(x, 30, Utils.loadImage("res/enemy_plane_white_3.png"));
            if (x < 400) {
                enemyController.setMovebehavior(new HorzMovebehavior());
            }
            else {
                enemyController.setMovebehavior(new Movebehavior());
                enemyController2.setMovebehavior(new HorzMovebehavior());
            }
            enemyControllers.add(enemyController);

        }
        ArrayList<Playerbullet> playerbullets = playerController.getPlayerbullets();
        ArrayList<Enemybullet> enemybullets = enemyController.getEnemybullets();
        // load image
        backgroundImage = Utils.loadImage("res/background.png");

        // Game loop
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(17);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //// LOGIC
                    playerController.updateBullet();
                    for (EnemyController enemyController: enemyControllers) {
                        enemyController.automove();
                        enemyController2.automove();
                        enemyController.autoshoot();
                        enemyController2.autoshoot();
                    }
                    /// check collision
                    for (int i = 0 ; i < playerbullets.size() ; i ++ ) {
                        Playerbullet getbullet = playerbullets.get(i);
                        for (int j = 0; j < enemybullets.size(); j++) {
                            Enemybullet getebullet = enemybullets.get(j);
                            if (getbullet.getrect().intersects(getebullet.getrect())) {
                                System.out.println("Bum bum");
                                for (EnemyController enemyController: enemyControllers) {
                                    enemyController.remove(getebullet);
                                }
                            }
                            if (getebullet.getrect().intersects(playerController.getrect())) {
                                System.out.println("Dead");
                            }
                        }
                        for(int k = 0; k < enemyControllers.size(); k ++) {
                            EnemyController getenemycontroller = enemyControllers.get(k);

                            if (getbullet.getrect().intersects(getenemycontroller.getrect())) {

                                enemyControllers.remove(getenemycontroller);
                            }
                        }
                    }

                    // update vi tri playerController
                    playerController.processInput(isUpPressed, isDownPressed, isLeftPressed, isRightPressed, isSpacePressed);
                    enemyController.updateEbullet();
                    enemyController2.updateEbullet();
                    playerController.update();
                    repaint();
                }
            }
        });

        // repaint
        thread.start();
    }

    @Override
    public void update(Graphics g) {
        backBufferedGapphics.drawImage(backgroundImage, 0, 0 , screenW, screenH, null);
        playerController.draw(backBufferedGapphics);
        playerController.drawBullet(backBufferedGapphics);
        for ( EnemyController enemyController : enemyControllers) {
            enemyController.draw(backBufferedGapphics);
            enemyController2.draw(backBufferedGapphics);
            enemyController.draw_Ebullet(backBufferedGapphics);
        }

        g.drawImage(backbufferedImage, 0, 0, null);

    }
}
