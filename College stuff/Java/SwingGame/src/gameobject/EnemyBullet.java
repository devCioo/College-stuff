/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gameobject;

import java.awt.Color;

/**
 *
 * @author Dawid
 */
public class EnemyBullet extends Bullet
{
    private double angle;
    private double dx;
    private double dy;

    public EnemyBullet(int x, int y, Color color, double angle) 
    {
        super(x, y, color);
        this.angle = angle;
        speed = 8;
        calculateDirection();
    }
    
    private void calculateDirection()
    {
        dx = speed * Math.cos(angle);
        dy = speed * Math.sin(angle);
    }
    
    public void update() 
    {
        x += dx;
        y += dy;
    }  

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
