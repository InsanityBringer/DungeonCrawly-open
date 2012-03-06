package com.mojang.ld19.item;

import com.mojang.ld19.display.Bitmap;
import com.mojang.ld19.level.Level;

public class Spellbook extends Item 
{
	protected Spellbook(Bitmap icon, int itemType, String name) 
	{
		super(icon, itemType, name);
	}
	
	public Item onItemRightClick(Level level)
	{
		level.player.isCasting = true;
		level.player.justOpenedSpellPanel = true;
		return this;
	}
}
