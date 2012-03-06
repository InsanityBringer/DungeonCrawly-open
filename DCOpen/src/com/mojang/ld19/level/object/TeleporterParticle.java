package com.mojang.ld19.level.object;

import com.mojang.ld19.Art;
import com.mojang.ld19.Entity;
import com.mojang.ld19.display.Sprite;
import com.mojang.ld19.level.Level;

public class TeleporterParticle extends Entity 
{
	public double velx, vely, velz;
	public int lifetime = 0;
	public TeleporterParticle(double velx, double vely, double velz)
	{
		x = 0.5;
		y = 0.5;
		z = 0.5;
		
        sprites.sprites.clear();
        sprites.sprites.add(new Sprite(0, 0, 0, Art.sparkle));
        
        this.velx = velx;
        this.vely = vely;
        this.velz = velz;
        
        perfersToBeCentered = true;
	}
	
	public void tick(Level level)
	{
		lifetime++;
		if (lifetime > 50)
		{
			location.removeEntity(-1, this);
			return;
		}
		
		x += (velx / 25d);
		y += (vely / 25d);
		z += (velz / 25d);
	}
}
