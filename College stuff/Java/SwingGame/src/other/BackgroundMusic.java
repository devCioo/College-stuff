/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package other;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JComponent;

/**
 *
 * @author Dawid
 */
public class BackgroundMusic extends JComponent
{
    private Clip clip;
    
    public BackgroundMusic(String musicFile)
    {
        try
        {
            URL soundURL = getClass().getResource(musicFile);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void play()
    {
        if (clip != null && !clip.isRunning())
        {
            clip.setFramePosition(0);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
    
    public void stop()
    {
        if (clip != null)
        {
            clip.stop();
        }
    }
}
