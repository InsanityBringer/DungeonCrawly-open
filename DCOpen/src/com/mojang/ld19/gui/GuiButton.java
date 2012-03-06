package com.mojang.ld19.gui;

import com.mojang.ld19.Art;
import com.mojang.ld19.level.Level;
import com.mojang.ld19.display.*;

public class GuiButton implements ClickListener
{
	private int drawx, drawy;
	public String label;
	public boolean pressed = false;
	
	public GuiButton(int x, int y, String name)
	{
		drawx = x; drawy = y;
		label = name;
	}
	
	public boolean click(Level level, Sprite sprite, int x, int y, int button) 
	{
		pressed = !pressed;
        return true;
    }
	
	public void render(Screen screen)
	{
		Bitmap frame = Art.guiButtonup;
		int adjustment = 1;
		if (pressed)
		{
			frame = Art.guiButtondown;
			adjustment++;
		}
		screen.blit(frame, drawx, drawy, this);
		TextRenderer.drawString(label, screen, drawx+adjustment, drawy+adjustment, this);
	}
}
