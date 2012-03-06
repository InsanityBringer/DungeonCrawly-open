package com.mojang.ld19.item;

import java.util.Random;

import com.mojang.ld19.display.Bitmap;
import com.mojang.ld19.level.Level;

public class SuperPotion extends Item
{
	private int potionLevel;
	public SuperPotion(Bitmap icon, int itemType, int potionAmount, String name)
	{
		super(icon, itemType, name);
		potionLevel = potionAmount;
	}
	
    public Item onItemRightClick(Level level)
    {
    	Random r = new Random();
    	level.player.heal(r.nextInt(8) + 1);
    	return getNextPotionLevel();
    }
    
    private Item getNextPotionLevel()
    {
    	switch (potionLevel)
    	{
    	case 1:
    		return Item.superPotionl2;
    	case 2:
    		return Item.superPotionl3;
    	case 3:
    		return Item.superPotionl4;
    	}
    	return null;
    }
}
