package com.mojang.ld19.gui;

import com.mojang.ld19.Art;
import com.mojang.ld19.LD19;
import com.mojang.ld19.display.Screen;

public class MainMenu extends Menu
{
	private MenuButton newGame = new MenuButton(0, "Start a new game", this, 17, 75, Art.guiMenuBUp);
	private MenuButton loadGame = new MenuButton(1, "Load a saved game", this, 17, 94, Art.guiMenuBUp);
	private MenuButton info = new MenuButton(3, "The story so far...", this, 17, 114, Art.guiMenuBUp);
	
	public MainMenu(LD19 game)
	{
		this.game = game;
	}
	
	public void buttonClicked(int ID)
	{
		switch (ID)
		{
		case 0:
			game.changeMenu(new MenuCharCreation(game));
			break;
		case 1:
			game.loadFile();
			break;
		case 3:
			game.changeMenu(new MenuInfo(game));
			break;
		}
	}
	
	public void render(Screen screen)
	{
		screen.blit(Art.guiMainMenu, 0, 0, this);
		newGame.render(screen);
		loadGame.render(screen);
		info.render(screen);
		TextRenderer.drawString("DungeonCrawly (public testing release #2)\nBy InsanityBringer. Some art and code is based off the works of Notch", screen, 0, 240-14, this);
		if (game.isRunningExternal)
		{
			String version = "Ext. Client 0.1";
			int xcoord = 426 - (6 * version.length());
			TextRenderer.drawString(version, screen, xcoord, 0, this);
		}
	}
}
