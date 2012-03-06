package com.mojang.ld19.item;

import com.mojang.ld19.Player;
import com.mojang.ld19.display.Bitmap;
import com.mojang.ld19.level.Level;

public class AntiPoisonPotion extends Item 
{
	protected AntiPoisonPotion(Bitmap icon, int itemType, String name) 
	{
		super(icon, itemType, name);
	}
	
    public Item onItemRightClick(Level level)
    {
    	if (level.player.statusTime[Player.STATUS_POISON] > 0)
    	{
    		level.player.statusTime[Player.STATUS_POISON] = 0;
    		level.player.addString("Cured Poison!");
    		return null;
    	}
    	return this;
    }
}
