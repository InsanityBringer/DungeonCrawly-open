package com.mojang.ld19.gui;

import com.mojang.ld19.Player;
import com.mojang.ld19.display.Screen;
import com.mojang.ld19.display.Sprite;
import com.mojang.ld19.item.Item;
import com.mojang.ld19.level.Level;

public class QuiverSlot extends InventorySlot
{
	int arrows = 0;
	public QuiverSlot(int x, int y, Player player)
	{
		super(x, y, false, 0, 0, player);
	}
	
    public boolean click(Level level, Sprite sprite, int x, int y, int button) 
    {
    	if (button == 1)
    	{
    		if (level.player.carried != null)
    		{
    			if (level.player.carried == Item.arrow)
    			{
    				level.player.carried = null;
    				level.player.arrows++;
    			}
    		}
	        return true;
    	}
        return false;
    }
    
    public void render(Screen screen) 
    {
    	screen.blit(iconSprite, x, y, this);
        TextRenderer.drawString(String.format("%d", arrows), screen, 373, 33, this);
    }
    
    public void tick(Player player)
    {
    	arrows = player.arrows;
    }
}
