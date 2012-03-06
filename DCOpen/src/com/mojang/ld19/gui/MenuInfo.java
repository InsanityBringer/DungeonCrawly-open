package com.mojang.ld19.gui;

import com.mojang.ld19.Art;
import com.mojang.ld19.LD19;
import com.mojang.ld19.display.Screen;

public class MenuInfo extends Menu
{
	private MenuButton info = new MenuButton(0, "Ok", this, 17, 200, Art.guiMultiButton);
	
	public MenuInfo(LD19 game)
	{
		this.game = game;
	}
	
	public void render(Screen screen)
	{
		screen.fill(0, 0, 426, 240, 0x000000);
		TextRenderer.drawString("if you want, pretend there's an awesome story here", screen, 8, 8, this);
		info.render(screen);
	}
	
	public void buttonClicked(int ID)
	{
		if (ID == 0)
		{
			game.changeMenu(new MainMenu(game));
		}
	}
}
