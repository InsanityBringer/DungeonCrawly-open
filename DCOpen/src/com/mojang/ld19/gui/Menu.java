package com.mojang.ld19.gui;

import com.mojang.ld19.LD19;
import com.mojang.ld19.display.ClickListener;
import com.mojang.ld19.display.Screen;
import com.mojang.ld19.display.Sprite;
import com.mojang.ld19.level.Level;

public class Menu implements ClickListener
{
	public LD19 game;
	public boolean click(Level level, Sprite sprite, int x, int y, int button) 
	{
        return true;
    }
	
	public void render(Screen screen)
	{
	}
	
	public void buttonClicked(int ID)
	{		
	}
	
	public void amountDecremented(int ID)
	{
	}
	
	public void amountIncremented(int ID)
	{
	}
	
	public void tick()
	{
	}
}
