package com.mojang.ld19.item;

import java.util.Random;

import com.mojang.ld19.display.Bitmap;
import com.mojang.ld19.level.Level;

public class EnchantmentScroll extends Item
{
	public int enchantment;
	protected EnchantmentScroll(Bitmap icon, int itemType, String name, int enchantment)
	{
		super(icon, itemType, name);
		this.enchantment = enchantment;
	}

	public Item onItemRightClick(Level level)
    {
    	Random r = new Random();
    	level.player.enchantmentTime[enchantment] = 30 * 60;
    	level.player.addString("A surge of power runs through you...");
    	return null;
    }
}
