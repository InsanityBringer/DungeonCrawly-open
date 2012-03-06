package com.mojang.ld19.level.object;

import com.mojang.ld19.Player;
import com.mojang.ld19.level.Level;

public class GhoulSpell extends BaseSpell 
{
	public GhoulSpell(int rot, int speed, int arrowDice, int arrowSides, int arrowBonus)
	{
		super(rot, speed, arrowDice, arrowSides, arrowBonus);
	}
	
	public GhoulSpell()
	{
		super();
	}
	
	public int getID()
	{
		return 14;
	}
	
    public void attackTile(Level level, int xt, int yt)
    {
        int xd = level.player.x - xt;
        int yd = level.player.z - yt;
    	level.player.health -= (random.nextInt(sides * dice) + 1);
		level.player.setHurtAnim(xd, yd);
		if (random.nextInt(100) < 80)
		{
			level.player.statusTime[Player.STATUS_BLACKNESS] = 1500;
			level.player.addString("You've been cast into darkness!");
		}
    }
}
