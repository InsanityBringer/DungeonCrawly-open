package com.mojang.ld19.magic;

import com.mojang.ld19.level.Level;

public class SpellHurtTarget extends SpellListEntry
{
	public SpellHurtTarget(String name, int id, int MP)
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
		level.getTile(level.player.x, level.player.z).addEntity(-1, new PlayerSpell(level.player.rot, 5, 2, 6, 0));
		level.player.MP -= this.MP;
	}
}
