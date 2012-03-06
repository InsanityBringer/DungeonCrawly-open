package com.mojang.ld19.mob;

import com.mojang.ld19.*;
import com.mojang.ld19.display.*;
import com.mojang.ld19.level.*;
import com.mojang.ld19.level.object.BaseSpell;
import com.mojang.ld19.level.object.particle.FireParticle;
import com.mojang.ld19.magic.MagicParticle;

import java.io.*;

public class Mob extends Entity {
	protected static final int ATTACK_DURATION = 40;
    protected static final int ATTACK_DELAY = 60;
    protected int xLast, yLast;
    protected int moveTime = 0;
    protected int blinkTime = 0;
    protected int critTime = 0;
    protected int health = 6;
    protected Bitmap[] anims;
    protected int attackTime = 0;
    protected int randomWalkTime = 0;
    protected int armorClass = 0;
    //protected String name = "Slime";
    protected int exp = 3;
    protected Player target = null;
    protected int MP = 0;
    protected boolean canCast = false;
    protected int castTime = 0;
    protected int timeToNextCast = 100;
    protected int castRot = 0;
    public boolean isDead = false;
    public int ticksAlive = 0;
    
    public int poisonTime = 0;
    public int fireTime = 0;

    public Mob() 
    {
    	super();
    	canTakeDamage = true;
        anims = Art.slimes;
        this.x = 0.0;
        this.y = -0.2;
        this.z = 0.0;
        setFrame(0);
        name = "Slime";
        armorClass = 3;
    }
    
    public void save(RandomAccessFile savefile)
    	throws IOException
    {
    	super.save(savefile);
    	savefile.writeInt(xLast);
    	savefile.writeInt(yLast);
    	savefile.writeInt(moveTime);
    	savefile.writeInt(blinkTime);
    	savefile.writeInt(critTime);
    	savefile.writeInt(health);
    	savefile.writeInt(attackTime);
    	savefile.writeInt(randomWalkTime);
    	savefile.writeInt(MP);
    	savefile.writeInt(castTime);
    	savefile.writeInt(timeToNextCast);
    	savefile.writeInt(castRot);
    	savefile.writeInt(ticksAlive);
    }
    
    public void load(RandomAccessFile loadfile) throws IOException
    {
    	super.load(loadfile);
    	xLast = loadfile.readInt();
    	yLast = loadfile.readInt();
    	moveTime = loadfile.readInt();
    	blinkTime = loadfile.readInt();
    	critTime = loadfile.readInt();
    	health = loadfile.readInt();
    	attackTime = loadfile.readInt();
    	randomWalkTime = loadfile.readInt();
    	MP = loadfile.readInt();
    	castTime = loadfile.readInt();
    	timeToNextCast = loadfile.readInt();
    	castRot = loadfile.readInt();
    	ticksAlive = loadfile.readInt();
    }

    public void tick(Level level) 
    {
    	super.tick(level);
    	if (ticksAlive % 40 == 0 && fireTime > 0)
        {
        	hurt(1, 0, DamageType.POISON);
        }
        if (fireTime > 0)
        {
        	fireTime--;
        	if (location != null)
        	{
        		location.addEntity(corner, new FireParticle(corner));
        	}
        }
    }

    public void attack(Player player, int xa, int ya) 
    {
    	if (random.nextInt(20) > player.armorLevel)
    	{
	        player.health -= 1;
	        player.setHurtAnim(xa, ya);
    	}
    }
    
    public void cast(Player player, int rot)
    {
    	MP--;
    	location.addEntity(-1, new BaseSpell(rot, 10, 2, 6, 0));
    }

    protected void setFrame(int frame) {
        sprites.sprites.clear();
        sprites.sprites.add(new Sprite(0, 0, 0.20, anims[frame]));
    }

    public boolean isSolid() {
        return true;
    }

    public boolean hurt() 
    {
    	if (random.nextInt(20) < armorClass)
    	{
    		return false;
    	}
        if (attackTime > 0) {
            blinkTime = 10;
            return false;
        }

        if (hurtTime == 0) {
            hurtTime = 20;
            blinkTime = 20;
            health--;
            return true;
        }
        return false;
    }
    
    public boolean hurt(int damage, int bonus, DamageType damagetype) 
    {
    	int attackRoll = random.nextInt(20) + 1;
    	if (damagetype == DamageType.MAGIC)
    	{
    		attackRoll = 20; 
    	}
    	if (damagetype == DamageType.FIRE)
    	{
    		setOnFire();
    	}
    	if (damagetype == DamageType.POISON_DIRECT)
    	{
    		poisonTime = 1000 + (random.nextInt(10) * 50);
    	}
    	if (attackRoll == 20 && damagetype.physical)
    	{
    		damage *= 2;
    		bonus = 20;
    		if (target != null)
    		{
    			target.addString("Critical hit!");
    			critTime = 20;
    			critFX();
    		}
    	}
    	if (attackRoll < (armorClass - bonus) && damagetype.physical)
    	{
    		//System.out.printf("attack failed due to armor roll\n");
    		return false;
    	}
        if (attackTime > 0 && critTime <= 0) 
        {
            blinkTime = 10;
            //System.out.printf("attack failed due to attackTime\n");
            return false;
        }
        else if (critTime > 0 && attackTime > 0)
        {
        	attackTime = 0;
        }

        if (hurtTime == 0)
        {
            hurtTime = 20;
            blinkTime = 20;
            health -= damage;
    		if (target != null && damagetype.normal)
    		{
    			target.addString(String.format("Hit %s for %d damage!", name, damage));
    		}
            return true;
        }
        //System.out.printf("attack failed due to other circumstances\n");
        return false;
    }
    
    protected void onDeath()
    {
    	isDead = true;
    }
    
    protected int getDistX(Player player)
    {
    	int curx = location.x;
    	int playerx = player.location.x;
    	
    	return playerx - curx;
    }
    
    protected boolean getLineUnobstructedX(Level level, Player player, int dir)
    {
    	int curx = location.x;
    	for (int x = 0; x < 5; x++)
    	{
    		int checkPointx = curx + (x * dir);
    		if (!level.getTile(checkPointx, location.y).canProjectilePass())
    		{
    			return false;
    		}
    	}
    	return true;
    }
    
    protected boolean getLineUnobstructedY(Level level, Player player, int dir)
    {
    	int cury = location.y;
    	for (int x = 0; x < 5; x++)
    	{
    		int checkPointy = cury + (x * dir);
    		if (!level.getTile(location.x, checkPointy).canProjectilePass())
    		{
    			return false;
    		}
    	}
    	return true;
    }
    
    public void critFX()
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
    
    protected int getDistY(Player player)
    {
    	int curx = location.y;
    	int playerx = player.location.y;
    	
    	return playerx - curx;
    }
    
    public void inflictStatus(int status)
    {
    	poisonTime = (random.nextInt(5)*100) + 1000;
    }
    
    public void setOnFire()
    {
    	fireTime = (random.nextInt(5)*50) + 500;
    }
    
    public int getID()
    {
    	return 0;
    }
}
