package com.mojang.ld19.magic;

import com.mojang.ld19.level.Level;

public class SpellClearStatus extends SpellListEntry
{
	public SpellClearStatus(String name, int id, int MP)
	{
		super(name, id, MP);
	}
	
	public void cast(Level level)
	{
		if (level.player.MP < this.MP)
		{
			level.player.addString("Not enough MP to cast spell!");
			return;
		}

		for (int x = 0; x < 6; x++)
		{
			level.player.statusTime[x] = 0;
		}
		
		level.player.MP -= this.MP;
	}
}
