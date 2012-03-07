package com.mojang.ld19.gui;

import com.mojang.ld19.Art;
import com.mojang.ld19.Player;
import com.mojang.ld19.level.Level;
import com.mojang.ld19.display.*;

public class InfoPanel extends Menu implements ClickListener
{
	public MenuButton incSkill1 = new MenuButton(0, "", this, 276 + 16, 138, Art.guiSkillInc);
	public MenuButton incSkill2 = new MenuButton(1, "", this, 276 + 16, 148, Art.guiSkillInc);
	public MenuButton incSkill3 = new MenuButton(2, "", this, 276 + 16, 158, Art.guiSkillInc);
	Player player;
	public InfoPanel(Player player)
	{
		this.player = player;
	}
	
	public boolean click(Level level, Sprite sprite, int x, int y, int button) 
	{
        return true;
    }
	
	public void render(Screen screen, Player player)
	{
		screen.blit(Art.guiinfoPanel, 276, 0, this);
		//292, 8
		
		TextRenderer.drawString(player.characterName, screen, 292, 8, this);
		TextRenderer.drawString(player.currentClass.name, screen, 292, 8+7, this);
		TextRenderer.drawString(String.format("Level %d", player.characterLevel), screen, 292, 8+14, this);
		TextRenderer.drawString(String.format("X: %d", player.x), screen, 292, 8+21, this); //exp
		TextRenderer.drawString(String.format("Z: %d", player.z), screen, 292, 14+27, this); //ac
		
		TextRenderer.drawString(String.format("STR: %d", player.statStr), screen, 292, 54, this);
		TextRenderer.drawString(String.format("INT: %d", player.statInt), screen, 292, 54+7, this);
		TextRenderer.drawString(String.format("CON: %d", player.statCon), screen, 292, 54+14, this);
		TextRenderer.drawString(String.format("DEX: %d", player.statDex), screen, 292, 54+21, this);
		
		TextRenderer.drawString("Skills:", screen, 293, 118, this);
		TextRenderer.drawString("Available", screen, 293, 128, this);
		TextRenderer.drawString(String.format("%d", player.currentSkillPoints), screen, 349, 128, this);
		TextRenderer.drawString(player.skill1.name, screen, 300, 138, this);
		TextRenderer.drawString(String.format("%d", player.skill1Points), screen, 349, 138, this);
		TextRenderer.drawString(player.skill2.name, screen, 300, 148, this);
		TextRenderer.drawString(String.format("%d", player.skill2Points), screen, 349, 148, this);
		TextRenderer.drawString(player.skill3.name, screen, 300, 158, this);
		TextRenderer.drawString(String.format("%d", player.skill3Points), screen, 349, 158, this);
		
		incSkill1.render(screen);
		incSkill2.render(screen);
		incSkill3.render(screen);
	}
	
	@Override
	public void buttonClicked(int ID)
	{
		if (player.currentSkillPoints == 0)
		{
			return;
		}
		player.currentSkillPoints--;
		switch (ID)
		{
		case 0:
			player.skill1Points++;
			if (player.skill1Points > 99)
			{
				player.skill1Points--;
				player.currentSkillPoints++;
			}
			break;
		case 1:
			player.skill2Points++;
			if (player.skill2Points > 99)
			{
				player.skill2Points--;
				player.currentSkillPoints++;
			}
			break;
		case 2:
			player.skill3Points++;
			if (player.skill3Points > 99)
			{
				player.skill3Points--;
				player.currentSkillPoints++;
			}
			break;
		}
	}
}
