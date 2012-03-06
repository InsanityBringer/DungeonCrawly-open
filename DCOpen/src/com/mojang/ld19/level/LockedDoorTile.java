package com.mojang.ld19.level;

import com.mojang.ld19.display.Sprite;
import com.mojang.ld19.display.SpriteBody;

public class LockedDoorTile extends DoorTile
{
	public LockedDoorTile(SpriteBody spriteBody, int blockTag) 
	{
        super(spriteBody, blockTag);
	}
	
    public boolean click(Level level, Sprite sprite, int x, int y, int button)
    {
    	level.player.addString("You cannot open this door");
    	return false;
    }
    
    public void trigger(boolean on) 
    {
    	if (containsMob() != -2)
    	{
    		return;
    	}
        open = on;
    }
}
