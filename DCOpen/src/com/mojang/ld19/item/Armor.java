package com.mojang.ld19.item;

import com.mojang.ld19.display.Bitmap;
import com.mojang.ld19.level.Level;

public class Armor extends Item
{
	public int AC;
	public Armor(Bitmap icon, int itemID, int ArmorAmount, String name)
	{
		super(icon, itemID, name);
		AC = ArmorAmount;
	}

	public void onItemEquipped(Level level)
	{
		level.player.armorLevel += AC;
	}
	
	public void onItemRemoved(Level level)
	{
		level.player.armorLevel -= AC;
	}
}
