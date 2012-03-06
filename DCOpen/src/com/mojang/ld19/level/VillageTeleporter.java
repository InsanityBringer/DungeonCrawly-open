package com.mojang.ld19.level;

import java.util.Random;

import com.mojang.ld19.*;
import com.mojang.ld19.display.*;
import com.mojang.ld19.level.object.TeleporterParticle;
import com.mojang.ld19.level.walls.Walls;
import com.mojang.ld19.mob.Mob;

public class VillageTeleporter extends Tile 
{
	Random random = new Random();
    public VillageTeleporter() 
    {
        super(Walls.dungeonFloor);
    }

    public boolean canContainItems() {
        return false;
    }
    
    public void tick()
    {
    	//if (random.nextInt(5) == 0)
    	{
			double velx = ((random.nextDouble() * 2D) - 1D) / 2d;
			double vely = ((random.nextDouble() * 2D) - 1D) / 2d;
			double velz = ((random.nextDouble() * 2D) - 1D) / 2d;
			
			TeleporterParticle particleFX = new TeleporterParticle(velx, vely, velz);
			addEntity(-1, particleFX);
    	}
    	
    	if (random.nextInt(5) == 0)
    	{
    		double velx = ((random.nextDouble() * 2D) - 1D) / 8d;
			double vely = ((random.nextDouble() * 2D) - 1D) / 8d;
			double velz = ((random.nextDouble() * 2D) - 1D) / 8d;
			
			double startx = (random.nextDouble() + .5d) / 2d;
			double starty = (random.nextDouble() + .5d) / 2d;
			double startz = (random.nextDouble() + .5d) / 2d;
			
			TeleporterParticle particleFX = new TeleporterParticle(velx, vely, velz);
			particleFX.x = startx;
			particleFX.y = starty;
			particleFX.z = startz;
			addEntity(-1, particleFX);
    	}
    }

    public void stepOn(Player player) 
    {
    	//34, 9
    	//14, 47
        level.replaceWith = Level.get(1, player);
        player.blackout = 15;
        player.l_x = player.x = 34;
        player.l_z = player.z = 9;
    }
    
    public boolean blocks(Entity e)
    {
    	if (e instanceof Mob)
    	{
    		return true;
    	}
    	return super.blocks(e);
    }
}
