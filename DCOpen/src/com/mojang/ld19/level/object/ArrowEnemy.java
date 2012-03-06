package com.mojang.ld19.level.object;

import com.mojang.ld19.Art;
import java.io.*;

import com.mojang.ld19.display.Sprite;
import com.mojang.ld19.level.Level;
import com.mojang.ld19.level.Tile;
import com.mojang.ld19.Entity;

public class ArrowEnemy extends Entity
{
    private int delay = 0;
    private int power = 5;
    private int delayMax;
    private int sides;
    private int dice;
    private int bonus;
    private int rot;

    public ArrowEnemy(int rot, int speed, int arrowDice, int arrowSides, int arrowBonus) {
        this.rot = rot;
        sprites.sprites.add(new Sprite(0, 0, 0, Art.arrow));
        //ignoreCorners = true;
        
        x = 0.5;
        y = 0.5;
        z = 0.5;
        
        delayMax = speed;
        sides = arrowSides;
        dice = arrowDice;
        bonus = arrowBonus;
        
        name = "ArrowEnemy";
    }
    
    public void save(RandomAccessFile savefile)
    	throws IOException
    {
    	super.save(savefile);
    	savefile.writeInt(delay);
    	savefile.writeInt(power);
    	savefile.writeInt(delayMax);
    	savefile.writeInt(sides);
    	savefile.writeInt(dice);
    	savefile.writeInt(bonus);
    	savefile.writeInt(rot);
    }
    
    public void load(RandomAccessFile loadfile)
        	throws IOException
        {
        	super.load(loadfile);
        	delay = loadfile.readInt();
        	power = loadfile.readInt();
        	delayMax = loadfile.readInt();
        	sides = loadfile.readInt();
        	dice = loadfile.readInt();
        	bonus = loadfile.readInt();
        	rot = loadfile.readInt();
        }
    
    public int getID()
    {
    	return 7;
    }
    
    public ArrowEnemy() 
    {
        this.rot = 0;
        sprites.sprites.add(new Sprite(0, 0, 0, Art.arrow));
        //ignoreCorners = true;
        
        x = 0.5;
        y = 0.5;
        z = 0.5;
        
        delayMax = 0;
        sides = 0;
        dice = 0;
        bonus = 0;
        
        name = "ArrowEnemy";
    }
    
    public void tick(Level level) 
    {
        if (location == null)
        {
        	return;
        }
       
        if (++delay == delayMax) 
        {
        	//System.out.printf("arrow flying at %d, %d\n", location.x, location.y);
            delay = 0;

            power--;
            
            if (location == null)
            {
            	return;
            }

            int xt = location.x;
            int yt = location.y;
            if (rot == 0) yt++;
            if (rot == 1) xt++;
            if (rot == 2) yt--;
            if (rot == 3) xt--;
            Tile target = level.getTile(xt, yt);
            if (target.canProjectilePass()) 
            {
                location.removeEntity(corner, this);
                target.addEntity(corner, this);
            } 
            else 
            {
            	location.removeEntity(corner, this);
            }
            
            if (target.containsPlayer(level))
            {
            	location.removeEntity(corner, this);
            	if (random.nextInt(20) > (level.player.armorLevel + level.player.shieldAC) - bonus)
            	{
                    int xd = level.player.x - xt;
                    int yd = level.player.z - yt;
            		level.player.health -= (random.nextInt(sides) + 1) * dice;
            		level.player.setHurtAnim(xd, yd);
            	}
            }

            if (power == 0) 
            {
            	if (location != null)
            	{
	            	location.removeEntity(corner, this);
            	}
            }
            //System.out.printf("arrow flying at %d, %d\n", location.x, location.y);
        }
        super.tick(level);
        
        switch (rot)
        {
        case 0:
        	z = .5 + ((double) (delay) / (double) (delayMax));
        	break;
        case 1:
        	x = .5 + ((double) (delay) / (double) (delayMax));
        	break;
        case 2:
        	z = .5 - ((double) (delay) / (double) (delayMax));
        	break;
        case 3:
        	x = .5 - ((double) (delay) / (double) (delayMax));
        	break;
        }
    }

    public boolean click(Level level, Sprite sprite, int x, int y, int button) {
        return false;
    }   
}
