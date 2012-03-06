package com.mojang.ld19.item;

import java.util.Random;

import com.mojang.ld19.display.Bitmap;
import com.mojang.ld19.level.Level;

public class MPPotion extends Item 
{
	protected MPPotion(Bitmap icon, int itemType, String name) 
	{
		super(icon, itemType, name);
	}
	
    public Item onItemRightClick(Level level)
    {
    	Random r = new Random();
    	level.player.healMP((r.nextInt(7)) + 6);
    	return null;
    }
}
