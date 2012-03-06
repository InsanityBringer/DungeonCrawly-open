package com.mojang.ld19.level;

import com.mojang.ld19.Player;

public class HellTeleporter extends VillageTeleporter 
{
    public void stepOn(Player player) 
    {
    	//34, 9
    	//14, 47
        level.replaceWith = Level.get(-100, player);
        player.blackout = 15;
        player.l_x = player.x = 14;
        player.l_z = player.z = 47;
    }
}
