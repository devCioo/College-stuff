/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gameobject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author Dawid
 */
public abstract class Bullet 
{
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int speed;
    protected Color color;
    
    public Bullet(int x, int y, Color color)
    {
        this.x = x;
        this.y = y;
        this.color = color;
        width = 4;
        height = 2;
        speed = 8;
    }
    
    public abstract void update();

    
    public void draw(Graphics2D g)
    {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }
    
    public Rectangle getBounds()
    {
        return new Rectangle(x, y, width, height);
    }
}
