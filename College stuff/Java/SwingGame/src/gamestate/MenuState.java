/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import other.Background;
import other.BackgroundMusic;

/**
 *
 * @author Dawid
 */
public class MenuState extends GameState
{
    private Background bg;
    private BackgroundMusic music;

    private String[] options = {"Start", "Quit"};
    private int currentChoice;
    private Color titleColor;
    private Font titleFont;
    private Font font;

    public MenuState(GameStateManager gsm)
    {
        this.gsm = gsm;

        try
        {
            bg = new Background("/Resources/menubg.jpg", 1);
            bg.setVector(-0.1, 0);

            titleColor = new Color(128, 0, 0);
            titleFont = new Font("Century Gothic", Font.PLAIN, 36);
            font = new Font("Arial", Font.PLAIN, 12);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        music = new BackgroundMusic("/Resources/menust.wav");
    }

    public void init()
    {
        music.play();
    }

    public void update()
    {
        bg.update();
        music.play();
    }

    public void draw(Graphics2D g)
    {
        bg.draw(g);
        g.setColor(titleColor);
        g.setFont(titleFont);
        g.drawString("Arcane Forest", 40, 70);
        g.setFont(font);
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

    private void select()
    {
        if (currentChoice == 0)
        {
            music.stop();
            gsm.setState(GameStateManager.LEVELSTATE);
        }
        if (currentChoice == 1)
        {
            System.exit(0);
        }
    }

    public void keyPressed(int k)
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

    public void keyReleased(int k)
    {

    }
}