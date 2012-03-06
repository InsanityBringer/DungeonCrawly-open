package com.mojang.ld19.magic;

import com.mojang.ld19.item.Item;
import com.mojang.ld19.level.object.ItemEntity;

public class PoisonCloudProjectile extends PlayerSpell 
{
	public PoisonCloudProjectile(int rot, int speed, int arrowDice, int arrowSides, int arrowBonus)
	{
		super(rot, speed, arrowDice, arrowSides, arrowBonus);
		name = "PoisonCloudProjectile";
		power = 2;
	}
	
	public PoisonCloudProjectile()
	{
		super();
		name = "PoisonCloudProjectile";
		power = 2;
	}
	
	public void onTimeOut()
	{
    	if (location.canProjectilePass())
    	{
    		location.addEntity(-1, new PoisonCloud());
    	}
	}
	
	public void onImpact()
	{
		super.onImpact();
    	if (location.canProjectilePass())
    	{
    		location.addEntity(-1, new PoisonCloud());
    	}
	}
	
	public int getID()
	{
		return 16;
	}
}
