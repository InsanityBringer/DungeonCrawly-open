package com.mojang.ld19.gui;

import java.util.ArrayList;
import com.mojang.ld19.Art;
import com.mojang.ld19.display.ClickListener;
import com.mojang.ld19.display.Screen;
import com.mojang.ld19.display.Sprite;
import com.mojang.ld19.level.Level;

public class MultiButton implements ClickListener 
{
	public int id;
	public int clickID;
	@SuppressWarnings("rawtypes")
	ArrayList stringList = new ArrayList();
	Menu parentMenu;
	int x, y;
	String label;
	
	public MultiButton(Menu parent, int id, int x, int y)
	{
		this.clickID = id;
		parentMenu = parent;
		this.x = x;
		this.y = y;
	}
	
	@SuppressWarnings("unchecked")
	public void addString(String name)
	{
		stringList.add(name);
		label = (String)stringList.get(0);
	}
	
	public boolean click(Level level, Sprite sprite, int x, int y, int button) 
	{
		id++;
		if (id >= stringList.size())
		{
			id = 0;
		}
		label = (String)stringList.get(id);
		parentMenu.buttonClicked(clickID);
        return true;
    }
	
	public void render(Screen screen)
	{
		screen.blit(Art.guiMultiButton, x, y, this);
		TextRenderer.drawString(label, screen, x + 1, y + 1, this);
	}
}