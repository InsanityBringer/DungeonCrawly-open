package com.mojang.ld19.level.object;

import com.mojang.ld19.Art;
import com.mojang.ld19.Entity;
import com.mojang.ld19.display.Bitmap;
import com.mojang.ld19.display.Sprite;

public class DecorationEntity extends Entity
{
	public int spriteID;
	
	public Bitmap[] spriteList = new Bitmap[1];
	
	public DecorationEntity(int id)
	{
		super();
		spriteList[0] = Art.window;
		spriteID = id;
		
		sprites.sprites.add(new Sprite(0, 0, 0, Art.window));
    	x = 0.5;
    	y = 0.5;
    	z = 0.5;
    	
    	perfersToBeCentered = true;
	}
}
