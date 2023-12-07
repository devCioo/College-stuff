/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gameobject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import main.GamePanel;
import other.Animation;

/**
 *
 * @author Dawid
 */
public class Enemy 
{
    private int x;
    private int y;
    private int width;
    private int height;
    private int currentHealth;
    private int maxHealth;
    
    private Animation animation;
    private BufferedImage[] sprites;
    
    public ArrayList<EnemyBullet> bullets;
    private long lastShotTime;
    
    public Player player;
    
    public Enemy()
    {
        x = 269;
        y = 194;
        width = 64;
        height = 64;
        maxHealth = currentHealth = 25;
        
        bullets = new ArrayList<>();
        
        try
        {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Resources/enemy.png"));
            sprites = new BufferedImage[6];
            for (int i = 0; i < 6; i++)
            {
                sprites[i] = spritesheet.getSubimage(i * 64, 0, width, height);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(100);
        animation.start();
        
        lastShotTime = System.currentTimeMillis();
    }
    
    public void update()
    {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime >= 1000)
        {
            fire(player);
            lastShotTime = currentTime;
        }
        
        for (int i = 0; i < bullets.size(); i++)
        {
            EnemyBullet bullet = bullets.get(i);
            bullet.update();
            if (bullet.getX() < 0 || bullet.getY() < 0 ||  bullet.getX() > GamePanel.WIDTH || bullet.getY() > GamePanel.HEIGHT)
            {
                bullets.remove(i);
                i--;
            }
        }
    }
    
    public void draw(Graphics2D g)
    {
        g.drawImage(animation.getImage(), (int)x, (int)y, null);
        
        for (Bullet bullet : bullets)
        {
            bullet.draw(g);
        }
    }
    
    public void fire(Player player)
    {
        double angle = Math.atan2(player.getY() - y, player.getX() - x);
        EnemyBullet bullet = new EnemyBullet(x, y + 32, Color.RED, angle);
        bullets.add(bullet);
    }
    
    public Rectangle getBounds()
    {
        return new Rectangle(x, y, width, height);
    }
    
    public void takeDamage()
    {
        currentHealth -= 1;
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