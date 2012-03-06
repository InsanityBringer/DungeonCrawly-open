package com.mojang.ld19.level.object.particle;

import java.util.Random;

import com.mojang.ld19.Art;
import com.mojang.ld19.Entity;
import com.mojang.ld19.display.Sprite;
import com.mojang.ld19.level.Level;
import com.mojang.ld19.level.Tile;

public class FireParticle extends Entity 
{
	public double velx, vely, velz;
	public int lifetime = 0;
	public FireParticle(int targetCorner)
	{
		x = (random.nextDouble() * .6d) + .2d;
		y = 0.5;
		z = (random.nextDouble() * .6d) + .2d;
		
		corner = targetCorner;
		
        sprites.sprites.clear();
        sprites.sprites.add(new Sprite(0, 0, 0, Art.fire));
        
        this.velx = 0.0;
        this.vely = -0.1;
        this.velz = 0.0;
        
        if (corner != -1)
        {
        	//vely = 0.1;
        	y = -0.2;
        	x = (x - .5) / 2d;
        	z = (z - .5) / 2d;
        }
        
        perfersToBeCentered = true;
	}
	
	public static void causeFireExplosion(int x, int z, Tile tile)
	{
		Random r = new Random();
		for (int i = 0; i < 25; i++)
		{
			FireParticle fire = new FireParticle(-1);
			fire.velx = r.nextDouble() - .5;
			fire.vely = r.nextDouble() - .5;
			fire.velz = r.nextDouble() - .5;
			fire.x = 0.5; fire.y = 0.5; fire.z = 0.5;
			tile.addEntity(-1, fire);
		}
	}
	
	public void tick(Level level)
	{
		lifetime++;
		if (lifetime > 50)
		{
			location.removeEntity(corner, this);
			return;
		}
		
		x += (velx / 25d);
		y += (vely / 25d);
		z += (velz / 25d);
		
		vely *= 1.05;
	}
}
