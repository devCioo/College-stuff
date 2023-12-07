/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import gamestate.GameStateManager;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author Dawid
 */
public class GamePanel extends JPanel implements Runnable, KeyListener
{
    // Podstawowe dane
    public static final int WIDTH = 320;
    public static final int HEIGHT = 240;
    public static final int SCALE = 2;
    private static final int FPS = 60;
    private static final long TARGETTIME = 1000 / FPS;
    
    // Wątek i referencje
    private Thread thread;
    private boolean running;
    private BufferedImage image;
    private Graphics2D g;
    private GameStateManager gsm;
    
    public GamePanel() 
    {
        super();
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setFocusable(true);
        requestFocus();
    }

    public void addNotify()
    {
        super.addNotify();
        if (thread == null)
        {
            thread = new Thread(this);
            addKeyListener(this);
            thread.start();
        }
    }

    private void init()
    {
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        running = true;
        gsm = new GameStateManager();
    }

    public void run()
    {
        init();
        long start, elapsed, wait;

        while (running)
        {
            start = System.nanoTime();

            update();
            draw();

            elapsed = System.nanoTime() - start;
            wait = TARGETTIME - elapsed / 1000000;
            if(wait < 0) 
            {
                wait = 5;
            }

            try
            {
                Thread.sleep(wait);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void update()
    {
        gsm.update();
    }

    private void draw()
    {
        gsm.draw(g);
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        g2.dispose();
    }

    public void keyTyped(KeyEvent key) 
    {
        
    }
    
    public void keyPressed(KeyEvent key) 
    {
        gsm.keyPressed(key.getKeyCode());
    }
    
    public void keyReleased(KeyEvent key) 
    {
        gsm.keyReleased(key.getKeyCode());
    }
}