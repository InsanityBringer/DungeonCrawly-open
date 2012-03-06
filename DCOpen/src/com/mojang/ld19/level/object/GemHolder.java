package com.mojang.ld19.level.object;

import com.mojang.ld19.Art;
import com.mojang.ld19.display.Sprite;
import com.mojang.ld19.item.Item;
import com.mojang.ld19.item.ItemGem;
import com.mojang.ld19.item.ItemKey;
import com.mojang.ld19.level.Level;

public class GemHolder extends Switch 
{
	private static Item gem;
	public Item placedItem = null;
	public Sprite displaySprite = null;
	public GemHolder(int id, Item requiredItem)
	{
		super(0, id);
		name = "GemHolder";
		gem = requiredItem;
		sprites.sprites.clear();
		sprites.sprites.add(new Sprite(0, 0, 0, Art.gemSlot));
	}
	
    public boolean click(Level level, Sprite sprite, int x, int y, int button) 
    {
    	//sprites.sprites.clear();
		//sprites.sprites.add(new Sprite(0, 0, 0, Art.gemSlot));
    	if (placedItem != null)
    	{
    		if (level.player.carried == null)
    		{
    			System.out.println("taking item");
    			level.player.carried = placedItem;
    			placedItem = null;
    			pressed = false;
    			sprites.sprites.remove(displaySprite);
        		return true;
    		}
    		else if (level.player.carried instanceof ItemGem)
    		{
    			System.out.println("replacing item");
    			Item temp = level.player.carried;
    			if (temp == gem)
        		{
        	        pressed = true;
        	        level.checkIfShouldTrigger(switchID);
        		}
    			level.player.carried = placedItem;
    			placedItem = temp;
    			sprites.sprites.remove(displaySprite);
    			displaySprite = new Sprite(0,0,0.01,placedItem.icon);
    	        sprites.sprites.add(displaySprite);
        		return true;
    		}
    	}
    	if (level.player.carried == null)
    	{
    		return true;
    	}
    	if (!(level.player.carried instanceof ItemGem))
    	{
    		level.player.addString("The item doesn't fit...");
    		return true;
    	}
    	if (level.player.carried instanceof ItemGem)
    	{
    		System.out.println("placing new item");
    		if (level.player.carried == gem)
    		{
    	        pressed = true;
    	        level.checkIfShouldTrigger(switchID);
    		}
    		placedItem = level.player.carried;
	        level.player.carried = null;
	        displaySprite = new Sprite(0,0,0.01,placedItem.icon);
	        sprites.sprites.add(displaySprite);
    	}

        return true;
    }
}
