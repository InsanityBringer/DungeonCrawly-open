package com.mojang.ld19.magic;

import com.mojang.ld19.DamageType;
import com.mojang.ld19.level.Level;
import com.mojang.ld19.level.Tile;
import com.mojang.ld19.level.object.particle.FireParticle;

public class SpellFireElement extends SpellListEntry
{
	public SpellFireElement(String name, int id, int MP)
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
		int attackz = level.player.z;
		int attackx = level.player.x;
		if (level.player.rot == 0) attackz++;
        if (level.player.rot == 1) attackx++;
        if (level.player.rot == 2) attackz--;
        if (level.player.rot == 3) attackx--;
    	Tile tile = level.getTile(attackx, attackz);
		tile.damageEverythingEver(DamageType.FIRE, 2, 0);
		
		FireParticle.causeFireExplosion(attackx, attackz, tile);
		
		level.player.MP -= this.MP;
	}
}
