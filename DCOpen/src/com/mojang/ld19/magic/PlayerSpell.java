package com.mojang.ld19.magic;

import java.io.IOException;
import java.io.RandomAccessFile;

import com.mojang.ld19.DamageType;
import com.mojang.ld19.Entity;
import com.mojang.ld19.display.*;
import com.mojang.ld19.item.Item;
import com.mojang.ld19.level.*;
import com.mojang.ld19.level.object.ItemEntity;
import com.mojang.ld19.Art;

public class PlayerSpell extends Entity {
	
    private int delay = 0;
    protected int power = 5;
    private int delayMax;
    private int sides;
    private int dice;
    private int bonus;
    private int rot;
    protected DamageType damageType = DamageType.MAGIC;

    public PlayerSpell(int rot, int speed, int arrowDice, int arrowSides, int arrowBonus) 
    {
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
        
        name = "PlayerSpell";
        
        isProjectile = true;
    }
    
    public int getID()
    {
    	return 15;
    }
    
    public PlayerSpell()
    {
    	rot = 0;
    	sprites.sprites.add(new Sprite(0, 0, 0, Art.spellGhoul));
    	x = 0.5;
    	y = 0.5;
    	z = 0.5;
    	
    	delayMax = 0;
    	sides = 0;
    	dice = 0;
    	bonus = 0;
    	
    	name = "PlayerSpell";
    	isProjectile = true;
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
            	onTimeOut();
            	explode();
            	location.removeEntity(corner, this);
            }

            if (power == 0) 
            {
            	if (location != null)
            	{
            		onTimeOut();
            		explode();
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
            	onImpact();
            	explode();
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
    
    public void onImpact()
    {
    	location.attack(this, corner, (random.nextInt(sides) + 1) * dice, bonus, damageType);
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
    
    public void onImpact_always()
    {
    }
    
    public void onTimeOut()
    {
    }
    
}
