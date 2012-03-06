package com.mojang.ld19.mob;

import com.mojang.ld19.Art;
import com.mojang.ld19.Player;

public class Zombie extends BigMob
{
	public Zombie()
	{
		super();
		name = "Zombie";
		armorClass = 3;
		health = 21;
		anims = Art.zombie;
		exp = 9;
	}
	
    public void attack(Player player, int xa, int ya) 
    {
    	if (random.nextInt(20) > player.armorLevel)
    	{
	        int damage = random.nextInt(5) + 1;
	        player.hurt(damage);
	        player.setHurtAnim(xa, ya);
    	}
    }
    
    public int getID()
    {
    	return 4;
    }
}
