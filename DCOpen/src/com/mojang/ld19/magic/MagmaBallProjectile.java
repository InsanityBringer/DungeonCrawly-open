package com.mojang.ld19.magic;

import com.mojang.ld19.Art;
import com.mojang.ld19.DamageType;
import com.mojang.ld19.display.Sprite;
import com.mojang.ld19.item.Item;
import com.mojang.ld19.level.object.ItemEntity;

public class MagmaBallProjectile extends PlayerSpell 
{
	public MagmaBallProjectile(int rot, int speed, int arrowDice, int arrowSides, int arrowBonus)
	{
		super(rot, speed, arrowDice, arrowSides, arrowBonus);
		name = "MagmaBallProjectile";
		damageType = DamageType.FIRE;
		sprites.sprites.clear();
		sprites.sprites.add(new Sprite(0, 0, 0, Art.magmaBall));
	}
	
	public MagmaBallProjectile()
	{
		super();
		name = "MagmaBallProjectile";
		damageType = DamageType.FIRE;
		sprites.sprites.clear();
		sprites.sprites.add(new Sprite(0, 0, 0, Art.magmaBall));
	}
	
	public int getID()
	{
		return 23;
	}
}
