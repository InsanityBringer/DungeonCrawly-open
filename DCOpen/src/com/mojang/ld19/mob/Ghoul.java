package com.mojang.ld19.mob;

import com.mojang.ld19.Art;
import com.mojang.ld19.Player;
import com.mojang.ld19.level.object.GhoulSpell;

public class Ghoul extends BigMob
{
	public Ghoul()
	{
		name = "Ghoul";
		armorClass = 10;
		canCast = true;
		health = 20;
		anims = Art.ghoul;
		MP = 15;
	}
	
    public void attack(Player player, int xa, int ya) 
    {
    	if (random.nextInt(20) > player.armorLevel)
    	{
    		int damage = random.nextInt(6) + 1;
    		player.hurt(damage);
	        heal(damage / 2);
	        player.setHurtAnim(xa, ya);
    	}
    }
    
    public void heal(int amount)
    {
    	health += amount;
    	if (health > 20)
    	{
    		health = 20;
    	}
    }
    
    public int getID()
    {
    	return 2;
    }
    
    public void cast(Player player, int rot)
    {
    	MP--;
    	location.addEntity(-1, new GhoulSpell(rot, 10, 2, 4, 0));
    }
}
