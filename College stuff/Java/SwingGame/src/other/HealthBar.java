/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package other;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 *
 * @author Dawid
 */
public class HealthBar 
{
    private int x;
    private int y;
    private int width;
    private int height;
    private int currentHealth;
    private int maxHealth;
    private Color color;
    
    public HealthBar(int x, int y, int width, int height, int maxHealth, Color color)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.color = color;
    }
    
    public void setCurrentHealth(int currentHealth)
    {
        this.currentHealth = currentHealth;
    }
    
    public void draw(Graphics2D g)
    {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
        
        int currentHealthWidth = (int) ((double) currentHealth / maxHealth * width);
        g.setColor(color);
        g.fillRect(x, y, currentHealthWidth, height);
        
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 10));
        g.drawString("Health: " + currentHealth + " / " + maxHealth, x, y - 5);
    }
}
