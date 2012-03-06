package com.mojang.ld19.mob;

import com.mojang.ld19.Art;
import com.mojang.ld19.Player;

public class Imp extends BigMob
{
	public Imp()
	{
		super();
		name = "Imp";
		armorClass = 5;
		health = 24;
		anims = Art.imp;
		exp = 14;
		canCast = true;
		MP = 3;
		moveDuration = 10;
	}
	
    public void attack(Player player, int xa, int ya) 
    {
    	if (random.nextInt(20) > player.armorLevel)
    	{
	        int damage = random.nextInt(6) + 1;
	        player.hurt(damage);
	        player.setHurtAnim(xa, ya);
    	}
    }
    
    public int getID()
    {
    	return 21;
    }
    
    public void setOnFire()
    {
    }
}
