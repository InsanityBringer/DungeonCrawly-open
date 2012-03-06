package com.mojang.ld19.mob;

import com.mojang.ld19.Art;
import com.mojang.ld19.Player;
import com.mojang.ld19.item.*;
import com.mojang.ld19.level.object.ItemEntity;

public class Spider extends BigMob
{
	public Spider()
	{
		super();
		name = "Spider";
		armorClass = 2;
		health = 20;
		anims = Art.spider;
		exp = 12;
	}
	
    public void attack(Player player, int xa, int ya) 
    {
    	if (random.nextInt(20) > player.armorLevel)
    	{
	        int damage = random.nextInt(6) + 1;
	        player.hurt(damage);
	        player.setHurtAnim(xa, ya);
	        if (random.nextInt(100) < 80)
	        {
	        	player.statusTime[Player.STATUS_POISON] = ((random.nextInt(5) + 5) * 100) + 2000;
	        	player.addString(String.format("PoisonTime: %d", player.statusTime[Player.STATUS_POISON]));
	        }
    	}
    }
    
    public int getID()
    {
    	return 13;
    }
}
