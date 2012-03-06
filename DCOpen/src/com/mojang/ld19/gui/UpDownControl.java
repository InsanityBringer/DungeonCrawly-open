package com.mojang.ld19.gui;

import com.mojang.ld19.Art;
import com.mojang.ld19.display.ClickListener;
import com.mojang.ld19.display.Screen;
import com.mojang.ld19.display.Sprite;
import com.mojang.ld19.level.Level;

public class UpDownControl extends Menu implements ClickListener 
{
	public int count;
	public int limit = 99;
	public int minimum = 0;
	private int x, y;
	private MenuButton upButton, downButton;
	private Menu parent;
	public int id;
	
	public UpDownControl(int start, int x, int y, Menu parent, int id)
	{
		count = start;
		this.x = x;
		this.y = y;
		this.parent = parent;
		this.id = id;
		upButton = new MenuButton(0, "", this, x, y, Art.guiAmountInc); 
		downButton = new MenuButton(1, "", this, x, y+4, Art.guiAmountDec); 
	}
	
	public boolean click(Level level, Sprite sprite, int x, int y, int button) 
	{
        return true;
    }
	
	public void updateMaxStats(int max, int truemax)
	{
		if (max > truemax)
			limit = truemax;
		else limit = max;
	}
	
	public void buttonClicked(int ID)
	{		
		switch (ID)
		{
		case 0:
			count++;
			if (count > limit)
			{
				count = limit;
			}
			else parent.amountIncremented(id);
			break;
		case 1:
			count--;
			if (count < minimum)
			{
				count = minimum;
			}
			else parent.amountDecremented(id);
			break;
		}
	}
	
	public void render(Screen screen)
	{
		screen.blit(Art.guiUpDown, x, y, this);
		upButton.render(screen);
		downButton.render(screen);
		TextRenderer.drawString(String.format("%d", count), screen, x + 8, y + 1, this);
	}
}
