package com.mojang.ld19.level.object;

import com.mojang.ld19.Art;
import com.mojang.ld19.Entity;
import com.mojang.ld19.display.Sprite;
import com.mojang.ld19.level.Level;

public class MarkedBrick extends Switch
{
	private String[] messages = {"The color of the sky during day", "A feminine color, bright in nature", "The color of the ocean surrounding here", 
			"The color of fire, deep and powerful", "The color of natural life", "Five gems control access through this door"};
	
	int messageID = 0;
	
	public MarkedBrick(int ID)
	{
		super();
		
		sprites.sprites.clear();
        sprites.sprites.add(new Sprite(0, 0, 0, Art.markedBrick));
        
        messageID = ID;
        
        name = "MarkedBrick";
	}
	
	public MarkedBrick()
	{
		super();
		
		sprites.sprites.clear();
        sprites.sprites.add(new Sprite(0, 0, 0, Art.markedBrick));
        
        messageID = 0;
        
        name = "MarkedBrick";
	}
	
	public int getID()
	{
		return 24; 
	}
	
	public boolean click(Level level, Sprite sprite, int x, int y, int button) 
    {
		level.player.addString(messages[messageID]);
		return true;
    }
}
