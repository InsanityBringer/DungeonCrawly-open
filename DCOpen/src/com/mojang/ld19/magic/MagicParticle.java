package com.mojang.ld19.magic;

import com.mojang.ld19.Art;
import com.mojang.ld19.Entity;
import com.mojang.ld19.display.Sprite;
import com.mojang.ld19.level.Level;

public class MagicParticle extends Entity 
{
	public double velx, vely, velz;
	public int lifetime = 0;
	public MagicParticle(double velx, double vely, double velz, int tint)
	{
		x = 0.5;
		y = 0.5;
		z = 0.5;
		
        sprites.sprites.clear();
        sprites.sprites.add(new Sprite(0, 0, 0, Art.magicsparkle));
        
        this.velx = velx;
        this.vely = vely;
        this.velz = velz;
        
        this.tint = tint;
        
        perfersToBeCentered = true;
	}
	
	public void tick(Level level)
	{
		lifetime++;
		if (lifetime > 50 && location != null)
		{
			location.removeEntity(-1, this);
			return;
		}
		
		x += (velx / 25d);
		y += (vely / 25d);
		z += (velz / 25d);
	}
}
