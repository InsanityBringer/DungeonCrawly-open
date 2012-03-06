package com.mojang.ld19.item;

import com.mojang.ld19.display.Bitmap;
import com.mojang.ld19.level.Level;
import com.mojang.ld19.level.object.Arrow;

public class Bow extends Item 
{
	private int dice, sides, bonus;
	private int speed;
	public Bow(Bitmap icon, int itemID, int arrowSpeed, int arrowDice, int arrowSides, int arrowBonus, String name)
	{
		super(icon, itemID, name);
		wait = 35;
		useColor = 0xffffff;
		speed = arrowSpeed;
		dice = arrowDice;
		sides = arrowSides;
		bonus = arrowBonus;
	}
	
    public Item onItemRightClick(Level level)
    {
    	if (level.player.arrows <= 0)
    	{
    		level.player.addString("Out of arrows!");
    		wait = 0;
    		useColor = 0xFF0000;
    		return this;
    	}
    	wait = 35;
    	useColor = 0xFFFFFF;
    	/*String string = String.format("launching an arrow at %d, %d\n", level.player.x, level.player.z);
    	level.player.addString(string);*/
    	level.getTile(level.player.x, level.player.z).addEntity(-1, new Arrow(level.player.rot, speed, dice, sides, bonus));
    	level.player.arrows--;
    	return this;
    }

}
