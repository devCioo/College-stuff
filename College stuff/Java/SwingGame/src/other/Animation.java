/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package other;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.Timer;

/**
 *
 * @author Dawid
 */
public class Animation 
{
    private BufferedImage[] frames;
    private int currentFrame;
    private Timer timer;
    private int delay;
    
    public Animation()
    {
        timer = new Timer(0, new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                currentFrame++;
                if (currentFrame == frames.length)
                {
                    currentFrame = 0;
                }
            }
        });
    }
    
    public void setFrames(BufferedImage[] frames)
    {
        this.frames = frames;
        currentFrame = 0;
    }
    
    public void setDelay(int d)
    {
        delay = d;
        timer.setDelay(delay);
    }
    
    public void start()
    {
        currentFrame = 0;
        timer.start();
    }
    
    public void stop()
    {
        timer.stop();
    }
    
    public void setFrame(int i)
    {
        currentFrame = i;
    }
    
    public int getFrame()
    {
        return currentFrame;
    }
    
    public BufferedImage getImage()
    {
        return frames[currentFrame];
    }
}
