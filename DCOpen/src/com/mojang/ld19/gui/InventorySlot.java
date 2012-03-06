package com.mojang.ld19.gui;

import com.mojang.ld19.Art;
import com.mojang.ld19.DamageType;
import com.mojang.ld19.Player;
import com.mojang.ld19.display.*;
import com.mojang.ld19.item.Item;
import com.mojang.ld19.level.Level;
import com.mojang.ld19.level.Tile;

public class InventorySlot implements ClickListener 
{
    protected static Sprite iconSprite = new Sprite(0, 0, 0, Art.slot);
    public Item item;

    public int x, y;
    public int itemIndex; 
    public int ticksLeft = 0;
    public int flashTime = 0;
    public int slotType;
    public boolean isUsableSlot;
    public int flashColor = 0xffffff;
    public int index;
    public Player player;

    public InventorySlot(int x, int y, boolean isUsable, int type, int index, Player player) 
    {
        this.x = x;
        this.y = y;
        isUsableSlot = isUsable;
        slotType = type;
        this.index = index;
        this.player = player;
    }

    public boolean click(Level level, Sprite sprite, int x, int y, int button) 
    {
    	//System.out.printf("pushed %d! \n", button);
    	if (ticksLeft > 0)
    	{
    		return false;
    	}
    	if (button == 1)
    	{
    		if (level.player.carried != null)
    		{
    			if (slotType != 0 && level.player.carried.type != slotType)
    			{
    				return true;
    			}
    		}
    		if (level.player.quickInv[index] != null && slotType != 0)
    			level.player.quickInv[index].onItemRemoved(level);
	        Item tmp = level.player.carried;
	        level.player.carried = level.player.quickInv[index];
	        if (level.player.quickInv[index] != null)
	        {
	        	level.player.addString(String.format("%s taken", level.player.quickInv[index].getName()));
	        }
	        level.player.quickInv[index] = tmp;
	        if (level.player.quickInv[index] != null && slotType != 0)
	        	level.player.quickInv[index].onItemEquipped(level);
	        return true;
    	}
    	else if (button == 2)
    	{
    		if (!isUsableSlot)
    		{
    			return false;
    		}
    		if (level.player.quickInv[index] != null)
    		{
	    		Item result = level.player.quickInv[index].onItemRightClick(level);
    	    	if (level.player.quickInv[index] != null)
    	    	{
    	    		flashColor = level.player.quickInv[index].useColor;
    	    	}
	    		if (result != null)
	    		{
	    			ticksLeft = level.player.quickInv[index].wait;
	    		}
	    		flashTime = 5;
	    		level.player.quickInv[index] = result;
	    		return true;
    		}
    		/*else if (slotType == 5)
    		{
    	    	int attackx = level.player.x;
    	    	int attackz = level.player.z;
    	        if (level.player.rot == 0) attackz++;
    	        if (level.player.rot == 1) attackx++;
    	        if (level.player.rot == 2) attackz--;
    	        if (level.player.rot == 3) attackx--;
    	    	Tile tile = level.getTile(attackx, attackz);
    	    	boolean hasHit = tile.attack(level.player, level.player.rot, 1, 0, DamageType.BLUNT);
    	    	if (item != null)
    	    	{
    	    		flashColor = item.useColor;
    	    	}
    	    	else
    	    	{
    	    		if (hasHit)
    	    		{
    	    			flashColor = 0xffffff;
    	    		}
    	    		else flashColor = 0xff0000;
    	    	}
    	    	flashTime = 5;
    	    	ticksLeft = 35;
    	    	return true;
    		}*/
    	}

        return false;
    }
    
    public void addItem(Item itemToAdd)
    {
    	player.quickInv[index] = itemToAdd;
    }

    public void render(Screen screen) {
        screen.blit(iconSprite, x, y, this);
        if (player.quickInv[index] != null) {
        	//848484
        	screen.fill(x+1, y+1, 14, 14, 0x848484);
            screen.blit(player.quickInv[index].icon, x, y, this);
            //Art.itemSheet[0 + 0 * 10]
        }
        if (ticksLeft > 0)
        {
        	screen.blit(Art.itemSheet[2], x, y, this);
        }
        if (flashTime > 0)
        {
        	screen.fill(x, y, 16, 16, flashColor);
        }
    }
    
    public void tick(Player player)
    {
    	if (ticksLeft != 0)
    	{
    		ticksLeft--;
    	}
    	if (flashTime > 0)
    	{
    		flashTime--;
    	}
    }
}
