package com.mojang.ld19.mob;

import com.mojang.ld19.Art;
import com.mojang.ld19.Player;

public class Goblin extends SmallMob
{
	public Goblin()
	{
		super();
		name = "Goblin";
		armorClass = 4;
		health = 12;
		anims = Art.goblin;
		exp = 4;
	}
	
    public void attack(Player player, int xa, int ya) 
    {
    	if (random.nextInt(20) > player.armorLevel)
    	{
	        int damage = random.nextInt(3) + 1;
	        player.hurt(damage);
	        player.setHurtAnim(xa, ya);
	        /*player.statusTime[Player.STATUS_HOLD] = 1500;
	        player.holdInflictor = this;
	        player.addString("You've been bound in place!");*/
    	}
    }
    
    public int getID()
    {
    	return 3;
    }
}
