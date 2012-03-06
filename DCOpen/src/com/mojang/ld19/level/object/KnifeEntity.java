package com.mojang.ld19.level.object;

import com.mojang.ld19.Art;
import com.mojang.ld19.display.Sprite;
import com.mojang.ld19.item.Item;

public class KnifeEntity extends Arrow 
{
	public KnifeEntity(int rot, int speed, int arrowDice, int arrowSides, int arrowBonus) 
	{
		super(rot, speed, arrowDice, arrowSides, arrowBonus);
		
		sprites.sprites.clear();
		sprites.sprites.add(new Sprite(0, 0, 0, Art.knife));
		
		dropItem = Item.knife;
		chanceToDrop = 1;
	}
	
	public KnifeEntity()
	{
		super();
		sprites.sprites.clear();
		sprites.sprites.add(new Sprite(0, 0, 0, Art.knife));
		dropItem = Item.knife;
		chanceToDrop = 1;
	}
	
	public int getID()
	{
		return 20;
	}
}
