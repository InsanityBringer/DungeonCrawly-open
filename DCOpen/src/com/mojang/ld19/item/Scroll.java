package com.mojang.ld19.item;

import java.util.Random;

import com.mojang.ld19.display.Bitmap;
import com.mojang.ld19.level.Level;
import com.mojang.ld19.magic.SpellListEntry;

public class Scroll extends Item 
{
	private SpellListEntry spellToGrant;
	protected Scroll(Bitmap icon, int itemType, String name, SpellListEntry spellToGrant) 
	{
		super(icon, itemType, name);
		this.spellToGrant = spellToGrant;
	}
	
    public Item onItemRightClick(Level level)
    {
    	boolean result = level.player.registerSpell(spellToGrant);
    	if (result)
    	{
    		level.player.addString(String.format("Learned %s!\n", spellToGrant.name));
    		return null;
    	}
    	level.player.addString(String.format("Failed to learn %s!\n", spellToGrant.name));
    	return this;
    }
}
