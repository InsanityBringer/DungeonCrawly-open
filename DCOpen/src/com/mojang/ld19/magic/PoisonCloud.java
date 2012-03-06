package com.mojang.ld19.magic;

import com.mojang.ld19.Art;
import com.mojang.ld19.DamageType;
import com.mojang.ld19.Entity;
import com.mojang.ld19.Player;
import com.mojang.ld19.display.Sprite;
import com.mojang.ld19.item.Item;
import com.mojang.ld19.level.Level;
import com.mojang.ld19.level.object.ItemEntity;

import java.io.*;

public class PoisonCloud extends Entity 
{
	int duration;
	int lifetime = 0;
	
	public PoisonCloud()
	{
		duration = random.nextInt(1000) + 500;
		
		x = 0.5;
		y = 0.5;
		z = 0.5;
		
		name = "PoisonCloud";
		
		sprites.sprites.add(new Sprite(0, 0, 0, Art.poisonCloud));
		perfersToBeCentered = true;
	}
	
	public void save(RandomAccessFile savefile) throws IOException
	{
		super.save(savefile);
		savefile.writeInt(duration);
		savefile.writeInt(lifetime);
	}
	
	public void load(RandomAccessFile loadfile) throws IOException
	{
		super.load(loadfile);
		duration = loadfile.readInt();
		lifetime = loadfile.readInt();
	}
	
	public void tick(Level level)
	{
		super.tick(level);
		if (location == null)
		{
			return;
		}
		if (lifetime % 40 == 0)
		{
			if (location != null)
			{
				location.damageEverythingEver(DamageType.POISON_DIRECT, random.nextInt(1) + 1, 0);
				if (location.containsPlayer(level))
				{
					level.player.health -= random.nextInt(1) + 1;
		        	
				}
			}
		}
		if (location != null)
		{
			if (level.player.statusTime[Player.STATUS_POISON] == 0 && location.containsPlayer(level))
			{
				level.player.addString("You've been poisoned!");
			}
			location.inflictStatusOnAll(0);
		}
		lifetime++;
		if (lifetime > duration)
		{
			location.removeEntity(corner, this);
		}
	}
	
	public int getID()
	{
		return 17;
	}
}
