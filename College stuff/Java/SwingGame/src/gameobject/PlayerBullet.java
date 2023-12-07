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
public class PlayerBullet extends Bullet
{

    public PlayerBullet(int x, int y, Color color) 
    {
        super(x, y, color);
    }
    
    public void update() 
    {
        x += speed;
    }  
    
    public int getX()
    {
        return x;
    }
}
