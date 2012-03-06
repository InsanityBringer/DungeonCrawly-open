package com.mojang.ld19.item;

import com.mojang.ld19.DamageType;
import com.mojang.ld19.Player;
import com.mojang.ld19.PlayerSkill;
import com.mojang.ld19.display.Bitmap;
import com.mojang.ld19.level.Level;
import com.mojang.ld19.level.Tile;

import java.util.Random;

public class Sword extends Item
{
	int attackDice, attackSides;
	int attackBonus;
	public Sword(Bitmap icon, int itemID, int dice, int sides, int bonus, String name)
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
    	if (level.player.enchantmentTime[Player.ENCHANT_SHARP] > 0)
    	{
    		damageModifier += r.nextInt(6) + 1;
    	}
    	if (level.player.enchantmentTime[Player.ENCHANT_SNIPER] > 0)
    	{
    		hit = true;
    	}
    	damage += damageModifier; 
    	DamageType type = DamageType.HACK;
    	if (level.player.enchantmentTime[Player.ENCHANT_FIRE] > 0)
    		type = DamageType.FIRE;
    	if (!hit)
    	{
    		useColor = 0xFF0000;
    		String string = String.format("Missed Target!\n", damage);
    		level.player.addString(string);
    		return this;
    	}
    	if (tile.attack(level.player, level.player.rot, damage, attackBonus, type) == false)
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
