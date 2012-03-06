package com.mojang.ld19.item;

import com.mojang.ld19.DamageType;
import com.mojang.ld19.PlayerSkill;
import com.mojang.ld19.display.Bitmap;
import com.mojang.ld19.level.Level;
import com.mojang.ld19.level.Tile;
import com.mojang.ld19.level.object.Arrow;
import com.mojang.ld19.level.object.KnifeEntity;

import java.util.Random;

public class KnifeThrowable extends Item
{
	int attackDice, attackSides;
	int attackBonus;
	public KnifeThrowable(Bitmap icon, int itemID, int dice, int sides, int bonus, String name)
	{
		super(icon, itemID, name);
		attackDice = dice;
		attackSides = sides;
		attackBonus = bonus;
		wait = 35;
	}
	
    public Item onItemRightClick(Level level)
    {
    	Random r = new Random();
    	int attackx = level.player.x;
    	int attackz = level.player.z;
    	
    	int hitChance = level.player.getSkill(PlayerSkill.MELEE);
    	boolean hit = r.nextInt(100) <= hitChance;
    	
        if (level.player.rot == 0) attackz++;
        if (level.player.rot == 1) attackx++;
        if (level.player.rot == 2) attackz--;
        if (level.player.rot == 3) attackx--;
    	Tile tile = level.getTile(attackx, attackz);
    	int damage = (r.nextInt(attackSides * attackDice) + 1) ;
    	int damageModifier = level.player.statStr / 4; 
    	damage += damageModifier; 
    	if (tile.containsMob() != -2)
    	{
	    	if (!hit)
	    	{
	    		useColor = 0xFF0000;
	    		String string = String.format("Missed Target!\n", damage);
	    		level.player.addString(string);
	    		return this;
	    	}
	    	if (tile.attack(level.player, level.player.rot, damage, attackBonus, DamageType.HACK) == false)
	    	{
	    		useColor = 0xFF0000;
	    		String string = String.format("Target has blocked!\n", damage);
	    		level.player.addString(string);
	    	}
	    	else 
	    	{
	    		useColor = 0xffffff;
	    		//String string = String.format("Hit for %d damage!\n", damage);
	    		//level.player.addString(string);
	    	}
    	}
    	else
    	{
        	wait = 35;
        	useColor = 0xFFFFFF;
        	int speed = (9 - (level.player.statDex / 2)) + 4;
        	level.getTile(level.player.x, level.player.z).addEntity(-1, new KnifeEntity(level.player.rot, speed, attackDice, attackSides, attackBonus));
        	return null;
    	}
    	return this;
    }
    
    public String getName()
    {
    	if (attackBonus > 0)
    	{
    		return String.format("%s +%d", itemName, attackBonus);
    	}
    	return itemName;
    }
}
