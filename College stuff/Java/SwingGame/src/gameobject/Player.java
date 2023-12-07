/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gameobject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import main.GamePanel;
import other.Animation;

/**
 *
 * @author Dawid
 */
public class Player {
    private int x;
    private int y;
    private int width;
    private int height;
    private int speed;

    private boolean left;
    private boolean right;
    private boolean jumping;
    private boolean falling;
    private boolean isFiringAnimationPlaying;
    private boolean canShoot;
    
    private boolean firing;
    public ArrayList<PlayerBullet> bullets;
    private int actionBeforeFiring;
    private Clip clip;

    private double dy;
    private double gravity;
    private int currentHealth;
    private int maxHealth;
    
    private Animation animation;
    private int currentAction;
    private ArrayList<BufferedImage[]> sprites;
    private final int[] numOfFrames = {6, 6, 8, 6};
    private static final int IDLE = 0;
    private static final int WALKING = 1;
    private static final int JUMPING = 2;
    private static final int FIRING = 3;

    public Player() 
    {
        x = 100;
        y = 100;
        width = 16;
        height = 32;
        speed = 5;
        currentHealth = maxHealth = 5;

        left = false;
        right = false;
        jumping = false;
        falling = true;
        firing = false;
        isFiringAnimationPlaying = false;
        canShoot = true;
        gravity = 0.5;
        
        bullets = new ArrayList<>();
        
        try
        {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Resources/player.png"));
            
            sprites = new ArrayList<BufferedImage[]>();
            for (int i = 0; i < 4; i++)
            {
                BufferedImage[] bi = new BufferedImage[numOfFrames[i]];
                for (int j = 0; j < numOfFrames[i]; j++)
                {
                    bi[j] = spritesheet.getSubimage(j * width, i * height, width, height);
                }
                
                sprites.add(bi);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        animation = new Animation();
        currentAction = IDLE;
        animation.setFrames(sprites.get(IDLE));
        animation.setDelay(100);
    }

    public void update() 
    {
        if (left) 
        {
            x -= speed;
        }
        if (right) 
        {
            x += speed;
        }
        if (x <= 0)
        {
            x = 0;
        }
        else if (x >= GamePanel.WIDTH - width)
        {
            x = GamePanel.WIDTH - width;
        }
        if (jumping && !falling) 
        {
            dy = -10;
            falling = true;
        }
        if (falling) 
        {
            dy += gravity;
            y += dy;
        }
        if (y >= GamePanel.HEIGHT - height)
        {
            y = GamePanel.HEIGHT - height;
            falling = false;
        }
        if (firing && !isFiringAnimationPlaying)
        {
            fire();
            canShoot = false;
        }
             
        if (!isFiringAnimationPlaying)
        {
            if (left || right)
            {
                setCurrentAction(WALKING, 100);
            }
            else if (jumping && falling)
            {
                setCurrentAction(JUMPING, 133);
            }
            else
            {
                setCurrentAction(IDLE, 100);
            }
        }
        
        for (int i = 0; i < bullets.size(); i++)
        {
            PlayerBullet bullet = bullets.get(i);
            bullet.update();
            if (bullet.getX() > GamePanel.WIDTH)
            {
                bullets.remove(i);
                i--;
            }
        }
    }

    public void draw(Graphics2D g) 
    {
        g.drawImage(animation.getImage(), (int)x, (int)y, null);
        
        for (PlayerBullet bullet : bullets)
        {
            bullet.draw(g);
        }
    }

    public void keyPressed(int keyCode) 
    {
        if (keyCode == KeyEvent.VK_LEFT) 
        {
            left = true;
        }
        if (keyCode == KeyEvent.VK_RIGHT) 
        {
            right = true;
        }
        if (keyCode == KeyEvent.VK_SPACE) 
        {
            jumping = true;
        }
        if (keyCode == KeyEvent.VK_F) 
        {
            firing = true;
            actionBeforeFiring = currentAction;
            setCurrentAction(FIRING, 100);
        }
    }

    public void keyReleased(int keyCode) 
    {
        if (keyCode == KeyEvent.VK_LEFT) 
        {
            left = false;
        }
        if (keyCode == KeyEvent.VK_RIGHT) 
        {
            right = false;
        }
        if (keyCode == KeyEvent.VK_SPACE) 
        {
            jumping = false;
            falling = true;
        }
        if (keyCode == KeyEvent.VK_F) 
        {
            firing = false;
            isFiringAnimationPlaying = false;
            canShoot = true;
        }
    }
    
    private void setCurrentAction(int action, int delay)
    {
        if (currentAction != action)
        {
            currentAction = action;
            animation.setFrames(sprites.get(action));
            animation.setDelay(delay);
            animation.start();
        }
    }
    
    public void fire()
    {
        PlayerBullet bullet = new PlayerBullet(x + width, y + height / 2, Color.YELLOW);
        bullets.add(bullet);
        setCurrentAction(FIRING, 100);
        isFiringAnimationPlaying = true;
        playBulletSound();
    }
    
    public Rectangle getBounds()
    {
        return new Rectangle(x, y, width, height);
    }
    
    public void takeDamage()
    {
        currentHealth -= 1;
    }
    
    public void playBulletSound()
    {
        try
        {
            URL soundURL = getClass().getResource("/Resources/shot.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getMaxHealth() 
    {
        return maxHealth;
    }

    public int getCurrentHealth() 
    {
        return currentHealth;
    }
}