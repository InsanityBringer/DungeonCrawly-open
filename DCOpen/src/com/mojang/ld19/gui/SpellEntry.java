package com.mojang.ld19.gui;

import com.mojang.ld19.Art;
import com.mojang.ld19.display.Bitmap;
import com.mojang.ld19.display.ClickListener;
import com.mojang.ld19.display.Screen;
import com.mojang.ld19.display.Sprite;
import com.mojang.ld19.level.Level;
import com.mojang.ld19.magic.SpellListEntry;

public class SpellEntry implements ClickListener 
{
	public String name = "Science!";
	public boolean clicked = false;
	public SpellPanel spellPanel;
	public SpellListEntry currentSpell = null;
	int id;
	
	public SpellEntry(SpellPanel spellPanel, int id)
	{
		this.spellPanel = spellPanel;
		this.id = id;
	}
	
	@Override
	public boolean click(Level level, Sprite sprite, int x, int y, int button) 
	{
		clicked = !clicked;
		spellPanel.uncheckAllButtons(id);
		return true;
	}
	
	public void drawAt(Screen screen, int x, int y)
	{
		if (currentSpell == null)
		{
			return;
		}
		Bitmap theButton = clicked ? Art.guiCastBtndn : Art.guiCastBtnup;
		int adjust = clicked ? 1 : 0;
		screen.blit(theButton, x, y, this);
		TextRenderer.drawString(currentSpell.name, screen, x+2+adjust, y+2+adjust, this);
	}
	
}
