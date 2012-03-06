package com.mojang.ld19.mob;

import com.mojang.ld19.Art;
import com.mojang.ld19.Player;
import com.mojang.ld19.item.*;
import com.mojang.ld19.level.object.ItemEntity;

public class Skeleton extends SmallMob
{
	public Skeleton()
	{
		super();
		name = "Skeleton";
		armorClass = 0;
		health = 18;
		anims = Art.skeleton;
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
    
    public void onDeath()
    {
    	super.onDeath();
    	if (random.nextInt(2) == 0)
    	{
    		location.addEntity(corner, new ItemEntity(Item.sword));
    	}
    }
    
    public int getID()
    {
    	return 1;
    }
}
