package com.mojang.ld19.item;

import com.mojang.ld19.display.Bitmap;
import com.mojang.ld19.level.Level;

public class Shield extends Armor
{
	public Shield(Bitmap icon, int itemID, int ArmorAmount, String name)
	{
		super(icon, itemID, ArmorAmount, name);
	}
	
	public void onItemEquipped(Level level)
	{
		level.player.armorLevel += AC;
		level.player.shieldAC += AC;
	}
	
	public void onItemRemoved(Level level)
	{
		level.player.armorLevel -= AC;
		level.player.shieldAC -= AC;
	}
	
	public Item onItemRightClick(Level level)
	{
		level.player.addString("This item is used automatically when worn");
		return this;
	}
}
