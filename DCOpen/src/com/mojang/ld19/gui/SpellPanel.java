package com.mojang.ld19.gui;

import com.mojang.ld19.Art;
import com.mojang.ld19.Player;
import com.mojang.ld19.display.ClickListener;
import com.mojang.ld19.display.Screen;
import com.mojang.ld19.display.Sprite;
import com.mojang.ld19.level.Level;
import com.mojang.ld19.magic.SpellListEntry;

public class SpellPanel extends Menu implements ClickListener {

	Player thePlayer;
	public int x = 241;
	public int y = 112;
	public int page = 0;
	
	//23, 111
	public MenuButton castButton = new MenuButton(0, "Cast!",this, x + 23, y + 111, Art.guiCastBtn); 
	public MenuButton abortButton = new MenuButton(1, "Abort",this, x + 68, y + 111, Art.guiCastAbort); 
	
	public SpellEntry[] spells = new SpellEntry[9];
	
	public SpellPanel(Player player)
	{
		thePlayer = player;
		for (int x = 0; x < 9; x++)
		{
			spells[x] = new SpellEntry(this, x);
		}
	}
	
	@Override
	public boolean click(Level level, Sprite sprite, int x, int y, int button) 
	{
		return false;
	}
	
	public void render (Screen screen)
	{
		screen.blit(Art.guiCastPanel, x, y, this);
		castButton.render(screen);
		abortButton.render(screen);
		for (int i = 0; i < 9; i++)
		{
			spells[i].drawAt(screen, x+8, y +(i * 11) + 8);
		}
	}
	
	public void buttonClicked(int id)
	{
		switch(id)
		{
		case 0:
			SpellEntry spell = getClickedSpell();
			if (spell != null)
			{
				spell.currentSpell.cast(thePlayer.level);
				thePlayer.isCasting = false;
				uncheckAllButtons(-1);
			}
			break;
		case 1:
			thePlayer.isCasting = false;
			break;
		}
		
	}
	
	public void uncheckAllButtons(int exception)
	{
		for (int x = 0; x < 9; x++)
		{
			if (x != exception)
			{
				spells[x].clicked = false;
			}
		}
	}
	
	public void populateSpellPage(int page)
	{
		uncheckAllButtons(-1);
		if (page >= 5)
		{
			return;
		}
		for (int x = 0; x < 9; x++)
		{
			spells[x].currentSpell = thePlayer.playerSpells[x][page];
		}
	}
	
	public SpellEntry getClickedSpell()
	{
		for (int x = 0; x < 9; x++)
		{
			if(spells[x].clicked)
			{
				return spells[x];
			}
		}
		return null;
	}
}
