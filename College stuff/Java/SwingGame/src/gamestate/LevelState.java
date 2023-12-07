/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamestate;

import gameobject.Enemy;
import gameobject.EnemyBullet;
import gameobject.Player;
import gameobject.PlayerBullet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import other.Background;
import other.BackgroundMusic;
import other.HealthBar;

/**
 *
 * @author Dawid
 */
public class LevelState extends GameState
{
    private Background bg;
    private BackgroundMusic music;
    
    private Player player;
    private Enemy enemy; 
    private HealthBar playerHealthBar;
    private HealthBar enemyHealthBar;
    
    private String[] options = {"Menu", "Quit"};
    private int currentChoice;
    
    private boolean gameEnded;
    private boolean playerWon;
    private String endGameMessage;
    
    public LevelState(GameStateManager gsm)
    {
        this.gsm = gsm;
        
        try
        {
            bg = new Background("/Resources/gamebg.jpg", 1);
            bg.setVector(0, 0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        music = new BackgroundMusic("/Resources/levelst.wav");
        init();
    }

    public void init()
    {
        player = new Player();
        enemy = new Enemy();
        enemy.player = player;
        gameEnded = false;
        playerWon = false;
        endGameMessage = "";
        
        playerHealthBar = new HealthBar(20, 20, 50, 10, player.getMaxHealth(), Color.GREEN);
        enemyHealthBar = new HealthBar(200, 20, 50, 10, enemy.getMaxHealth(), Color.GREEN);
    }

    public void update()
    {
        bg.update();
        if (!gameEnded)
        {
            music.play();
        }
        if (player != null)
        {
            player.update();
            playerHealthBar.setCurrentHealth(player.getCurrentHealth());
        }
        if (enemy != null)
        {
            enemy.update();
            enemyHealthBar.setCurrentHealth(enemy.getCurrentHealth());
        }
        if (player != null && enemy != null)
        {
            checkEnemyCollision();
            checkPlayerCollision();
        }
        checkEndGameConditions();
    }

    public void draw(Graphics2D g)
    {
        bg.draw(g);
        if (player != null)
        {
            player.draw(g);
        }
        if (enemy != null)
        {
            enemy.draw(g);
        }
        playerHealthBar.draw(g);
        enemyHealthBar.draw(g);
        if (gameEnded)
        {
            if (playerWon)
            {
                g.setColor(Color.GREEN);
            }
            else
            {
                g.setColor(Color.RED);
            }
            g.setFont(new Font("Century Gothic", Font.PLAIN, 30));
            g.drawString(endGameMessage, 80, 100);
            g.setFont(new Font("Arial", Font.PLAIN, 12));
            
            for (int i = 0; i < options.length; i++)
            {
                if (i == currentChoice)
                {
                    g.setColor(Color.WHITE);
                }
                else
                {
                    g.setColor(Color.RED);
                }

                g.drawString(options[i], 145, 140 + i * 15);
            }
        }  
    }

    private void select()
    {
        if (currentChoice == 0)
        {
            gsm.setState(GameStateManager.MENUSTATE);
        }
        if (currentChoice == 1)
        {
            System.exit(0);
        }
    }
    
    public void keyPressed(int k)
    {
        if (player != null)
        {
            player.keyPressed(k);
        }
        if (gameEnded)
        {
            if (k == KeyEvent.VK_ENTER)
            {
                select();
            }
            if (k == KeyEvent.VK_UP)
            {
                currentChoice--;
                if (currentChoice == -1)
                {
                    currentChoice = options.length - 1;
                }
            }
            if (k == KeyEvent.VK_DOWN)
            {
                currentChoice++;
                if (currentChoice == options.length)
                {
                    currentChoice = 0;
                }
            }
        }
    }

    public void keyReleased(int k)
    {
        if (player != null)
        {
            player.keyReleased(k);
        }
    }
    
    public void checkEnemyCollision()
    {
        for (int i = 0; i < player.bullets.size(); i++)
        {
            PlayerBullet bullet = player.bullets.get(i);
            if (bullet.getBounds().intersects(enemy.getBounds()))
            {
                enemy.takeDamage();
                player.bullets.remove(i);
                i--;
            }
        }
    }
    
    public void checkPlayerCollision()
    {
        for (int i = 0; i < enemy.bullets.size(); i++)
        {
            EnemyBullet bullet = enemy.bullets.get(i);
            if (bullet.getBounds().intersects(player.getBounds()))
            {
                player.takeDamage();
                enemy.bullets.remove(i);
                i--;
            }
        }
    }

    private void checkEndGameConditions() 
    {
        if (!gameEnded && (player.getCurrentHealth() <= 0 || enemy.getCurrentHealth() <= 0))
        {
            gameEnded = true;

            if (enemy.getCurrentHealth() <= 0)
            {
                endGameMessage = "Wygrałeś";
                playerWon = true;
                enemy = null;
            }
            else
            {
                endGameMessage = "Przegrałeś";
                playerWon = false;
                player = null;
            }
            music.stop();
        }
    }
}
