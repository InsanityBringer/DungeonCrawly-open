package com.mojang.ld19.level.object;

import java.io.IOException;
import java.io.RandomAccessFile;

import com.mojang.ld19.DamageType;
import com.mojang.ld19.Entity;
import com.mojang.ld19.display.*;
import com.mojang.ld19.item.Item;
import com.mojang.ld19.level.*;
import com.mojang.ld19.Art;

public class Arrow extends Entity {
	
    private int delay = 0;
    private int power = 5;
    private int delayMax;
    private int sides;
    private int dice;
    private int bonus;
    private int rot;
    
    protected Item dropItem = Item.arrow;
    protected int chanceToDrop;

    public Arrow(int rot, int speed, int arrowDice, int arrowSides, int arrowBonus) {
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
        
        name = "Arrow";
        isProjectile = true;
        chanceToDrop = 2;
    }
    
    public int getID()
    {
    	return 6;
    }
    
    public Arrow()
    {
    	rot = 0;
    	sprites.sprites.add(new Sprite(0, 0, 0, Art.arrow));
    	x = 0.5;
    	y = 0.5;
    	z = 0.5;
    	
    	delayMax = 0;
    	sides = 0;
    	dice = 0;
    	bonus = 0;
    	
    	name = "Arrow";
    	isProjectile = true;
    	chanceToDrop = 2;
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
            	if (location.canContainItems())
            	{
            		location.addEntity(random.nextInt(4), new ItemEntity(dropItem));
            	}
            	location.removeEntity(corner, this);
            }
            
            /*int cornerAttacked = target.containsMob();
            if (cornerAttacked != -2)
            {
            	if (chanceToDrop == 1 || random.nextInt(chanceToDrop) == 0)
            	{
            		System.out.printf("dropping an item at %d\n", cornerAttacked);
            		if (cornerAttacked == -1)
            		{
            			cornerAttacked = random.nextInt(4);
            		}
            		location.addEntity(cornerAttacked, new ItemEntity(dropItem));
            	}
            	location.removeEntity(corner, this);
            	target.attack(this, corner, (random.nextInt(sides) + 1) * dice, bonus, DamageType.ARROW);
            }*/

            if (power == 0) 
            {
            	if (location != null)
            	{
	            	if (location.canContainItems())
	            	{
	            		location.addEntity(random.nextInt(4), new ItemEntity(dropItem));
	            	}
	            	location.removeEntity(corner, this);
            	}
            }
            //System.out.printf("arrow flying at %d, %d\n", location.x, location.y);
        }
        super.tick(level);
        
        if (location != null)
        {
            int cornerAttacked = location.containsMob();
            if (cornerAttacked != -2)
            {
            	if (chanceToDrop == 1 || random.nextInt(chanceToDrop) == 0)
            	{
            		System.out.printf("dropping an item at %d\n", cornerAttacked);
            		if (cornerAttacked == -1)
            		{
            			cornerAttacked = random.nextInt(4);
            		}
            		location.addEntity(cornerAttacked, new ItemEntity(dropItem));
            	}
            	location.attack(this, corner, (random.nextInt(sides) + 1) * dice, bonus, DamageType.ARROW);
            	location.removeEntity(corner, this);
            }
        }
        
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
