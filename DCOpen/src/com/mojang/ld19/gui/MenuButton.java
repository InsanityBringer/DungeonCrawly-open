package com.mojang.ld19.gui;

import com.mojang.ld19.display.Bitmap;
import com.mojang.ld19.display.ClickListener;
import com.mojang.ld19.display.Screen;
import com.mojang.ld19.display.Sprite;
import com.mojang.ld19.level.Level;

public class MenuButton implements ClickListener 
{
	int id;
	String label;
	Menu parentMenu;
	int x, y;
	Bitmap image;
	
	public MenuButton(int id, String label, Menu parent, int x, int y, Bitmap image)
	{
		this.id = id;
		this.label = label;
		parentMenu = parent;
		this.x = x;
		this.y = y;
		this.image = image;
	}
	
	public boolean click(Level level, Sprite sprite, int x, int y, int button) 
	{
		parentMenu.buttonClicked(id);
        return true;
    }
	
	public void render(Screen screen)
	{
		screen.blit(image, x, y, this);
		TextRenderer.drawString(label, screen, x + 1, y + 1, this);
	}
}
