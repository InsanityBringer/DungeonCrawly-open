package com.mojang.ld19.level.object;

import java.io.IOException;
import java.io.RandomAccessFile;

import com.mojang.ld19.Entity;
import com.mojang.ld19.display.*;
//import com.mojang.ld19.item.Item;
import com.mojang.ld19.level.*;
import com.mojang.ld19.magic.MagicParticle;
import com.mojang.ld19.Art;

public class BaseSpell extends Entity {
	
    protected int delay = 0;
    protected int power = 5;
    protected int delayMax;
    protected int sides;
    protected int dice;
    protected int bonus;
    protected int rot;

    public BaseSpell(int rot, int speed, int arrowDice, int arrowSides, int arrowBonus) {
        this.rot = rot;
        sprites.sprites.add(new Sprite(0, 0, 0, Art.spellGhoul));
        //ignoreCorners = true;
        
        x = 0.5;
        y = 0.5;
        z = 0.5;
        
        delayMax = speed;
        sides = arrowSides;
        dice = arrowDice;
        bonus = arrowBonus;
        
        name = "BaseSpell";
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
    	return 8;
    }
    
    public BaseSpell() 
    {
        this.rot = 0;
        sprites.sprites.add(new Sprite(0, 0, 0, Art.spellGhoul));
        //ignoreCorners = true;
        
        x = 0.5;
        y = 0.5;
        z = 0.5;
        
        delayMax = 0;
        sides = 0;
        dice = 0;
        bonus = 0;
        
        name = "BaseSpell";
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
            	explode();
            	location.removeEntity(corner, this);
            }
            
            if (target.containsPlayer(level))
            {
            	explode();
            	location.removeEntity(corner, this);
            	attackTile(level, xt, yt);
            }

            if (power == 0) 
            {
            	if (location != null)
            	{
            		explode();
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
    
    public void explode()
    {
    	for (int x = 0; x < 20; x++)
    	{
    		double velx = (random.nextDouble() * 2D) - 1D;
    		double vely = (random.nextDouble() * 2D) - 1D;
    		double velz = (random.nextDouble() * 2D) - 1D;
    		
    		MagicParticle particleFX = new MagicParticle(velx, vely, velz, 0x0026FF);
    		location.addEntity(-1, particleFX);
    	}
    }
    
    public void attackTile(Level level, int xt, int yt)
    {
        int xd = level.player.x - xt;
        int yd = level.player.z - yt;
    	level.player.health -= (random.nextInt(sides * dice) + 1);
		level.player.setHurtAnim(xd, yd);
    }

    public boolean click(Level level, Sprite sprite, int x, int y, int button) {
        return false;
    }    
    
}
