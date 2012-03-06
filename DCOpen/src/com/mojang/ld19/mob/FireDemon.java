package com.mojang.ld19.mob;

import com.mojang.ld19.Art;
import com.mojang.ld19.Player;
import com.mojang.ld19.level.object.FireDemonSpell;

public class FireDemon extends AdvancedMob
{
	public FireDemon()
	{
		name = "Fire Demon";
		armorClass = 5;
		canCast = true;
		health = 30;
		anims = Art.fireDemon;
		MP = 400;
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
    	return 18;
    }
    
    public void cast(Player player, int rot)
    {
    	MP--;
    	location.addEntity(-1, new FireDemonSpell(rot, 10, 2, 6, 0));
    }
    
    public void setOnFire()
    {
    }
}
