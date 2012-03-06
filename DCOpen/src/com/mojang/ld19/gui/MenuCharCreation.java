package com.mojang.ld19.gui;

import java.util.Random;

import com.mojang.ld19.Art;
import com.mojang.ld19.LD19;
import com.mojang.ld19.PlayerClass;
import com.mojang.ld19.PlayerSkill;
import com.mojang.ld19.display.ClickListener;
import com.mojang.ld19.display.Screen;

public class MenuCharCreation extends Menu implements ClickListener
{
	public MultiButton characterClass = new MultiButton(this, 50, 52, 59);
	public MultiButton characterGender = new MultiButton(this, 51, 52, 69);
	public MultiButton characterExtraSkill = new MultiButton(this, 52, 34, 195); 
	
	public MenuButton startGame = new MenuButton(100, "Begin the game!", this, 228, 227, Art.guiMenuBUp);
	public MenuButton mainMenu = new MenuButton(101, "Return to main menu!", this, 228, 216, Art.guiMenuBUp);
	public MenuButton reroll = new MenuButton(102, "Reroll all stats!", this, 228, 205, Art.guiMenuBUp);
	
	public UpDownControl characterStr = new UpDownControl(0,11, 111, this, 0);
	public UpDownControl characterInt = new UpDownControl(0,11, 120, this, 1);
	public UpDownControl characterDex = new UpDownControl(0,11, 129, this, 2);
	public UpDownControl characterCon = new UpDownControl(0,11, 138, this, 3);
	
	public UpDownControl skill1control = new UpDownControl(0, 11, 177, this, 4);
	public UpDownControl skill2control = new UpDownControl(0, 11, 186, this, 5);
	public UpDownControl skill3control = new UpDownControl(0, 11, 186+9, this, 5);
	
	public String characterName = "Player";
	
	public PlayerClass charClass = PlayerClass.FIGHTER;
	public PlayerSkill skill0, skill1, skill2;
	
	public int charStr = 0;
	public int charDex = 0;
	public int charCon = 0;
	public int charInt = 0;
	
	private int bonusStats = 0;
	private int bonusSkills = 0;
	
	private int skillPoints0 = 0, skillPoints1 = 0, skillPoints2 = 0;
	
	boolean onceTicked = false;
	
	public MenuCharCreation(LD19 game)
	{
		characterClass.addString("Fighter");
		characterClass.addString("Thief");
		characterClass.addString("Mage");
		
		characterGender.addString("Male");
		characterGender.addString("Female");
		
		characterExtraSkill.addString("Melee");
		characterExtraSkill.addString("Magic");
		characterExtraSkill.addString("Archery");
		characterExtraSkill.addString("Sneak");
		
		characterStr.limit = 18;
		characterInt.limit = 18;
		characterDex.limit = 18;
		characterCon.limit = 18;
		
		skill0 = charClass.primarySkill;
		skill1 = charClass.secondarySkill;
		skill2 = PlayerSkill.MELEE;
		
		this.game = game;
	}
	
	public void render(Screen screen)
	{
		screen.blit(Art.guiCharCreation, 0, 0, this);
		TextRenderer.drawString(characterName, screen, 53, 50, this);
		TextRenderer.drawString(String.format("%d", bonusStats), screen, 12, 100, this);
		TextRenderer.drawString("bonus points remaining", screen, 28 , 100, this);
		characterClass.render(screen);
		characterGender.render(screen);
		
		characterStr.render(screen);
		TextRenderer.drawString("STR", screen, 35, 112, this);
		characterInt.render(screen);
		TextRenderer.drawString("INT", screen, 35, 121, this);
		characterDex.render(screen);
		TextRenderer.drawString("DEX", screen, 35, 130, this);
		characterCon.render(screen);
		TextRenderer.drawString("CON", screen, 35, 139, this);
		
		//35, 178
		skill1control.render(screen);
		TextRenderer.drawString(skill0.name, screen, 35, 178, this);
		skill2control.render(screen);
		TextRenderer.drawString(skill1.name, screen, 35, 187, this);
		skill3control.render(screen);
		characterExtraSkill.render(screen);
		
		//11, 166
		TextRenderer.drawString(String.format("%d", bonusSkills), screen, 12, 166, this);
		TextRenderer.drawString("extra skill points remaining", screen, 28, 166, this);
		
		startGame.render(screen);
		mainMenu.render(screen);
		reroll.render(screen);
	}
	
	public void tick()
	{
		if (onceTicked == false)
		{
			Random random = new Random();
			charStr = roll(random, 3);
			charInt = roll(random, 3);
			charDex = roll(random, 3);
			charCon = roll(random, 3);
			
			bonusStats = roll(random, 2) + 3;
			
			characterStr.count = charStr;
			characterStr.minimum = charStr;
			characterStr.updateMaxStats(characterStr.count + bonusStats, 18);
			characterInt.count = charInt;
			characterInt.minimum = charInt;
			characterInt.updateMaxStats(characterInt.count + bonusStats, 18);
			characterDex.count = charDex;
			characterDex.minimum = charDex;
			characterDex.updateMaxStats(characterDex.count + bonusStats, 18);
			characterCon.count = charCon;
			characterCon.minimum = charCon;
			characterCon.updateMaxStats(characterCon.count + bonusStats, 18);
			
			bonusSkills = roll(random, 4);
			skillPoints0 = roll(random, 3) + 45;
			skillPoints1 = roll(random, 3) + 25; 
			skillPoints2 = roll(random, 4);
			
			skill1control.count = skill1control.minimum = skillPoints0;
			skill2control.count = skill2control.minimum = skillPoints1;
			skill3control.count = skill3control.minimum = skillPoints2;
			
			onceTicked = true;
		}
	}
	
	public void amountIncremented(int ID)
	{
		switch (ID)
		{
		case 0:
		case 1:
		case 2:
		case 3:
			bonusStats--;
			characterStr.updateMaxStats(characterStr.count + bonusStats, 18);
			characterInt.updateMaxStats(characterInt.count + bonusStats, 18);
			characterDex.updateMaxStats(characterDex.count + bonusStats, 18);
			characterCon.updateMaxStats(characterCon.count + bonusStats, 18);
			break; 
		case 4:
		case 5:
			bonusSkills--;
			skill1control.updateMaxStats(skill1control.count + bonusSkills, 99);
			skill2control.updateMaxStats(skill2control.count + bonusSkills, 99);
			skill3control.updateMaxStats(skill3control.count + bonusSkills, 99);
			break;
		}
	}
	
	public void buttonClicked(int ID)
	{
		switch (ID)
		{
		case 50:
			updateCharClass();
			break;
		case 52:
			updateThirdSkill();
			break;
		case 100:
			game.beginGame(charClass, skill2, characterStr.count, characterDex.count, characterCon.count, characterInt.count, skill1control.count, skill2control.count, skill3control.count);
			break;
		case 101:
			game.changeMenu(new MainMenu(game));
			break;
		case 102:
			onceTicked = false;
			break;
		}
	}
	
	public void amountDecremented(int ID)
	{
		switch (ID)
		{
		case 0:
		case 1:
		case 2:
		case 3:
			bonusStats++;
			characterStr.updateMaxStats(characterStr.count + bonusStats, 18);
			characterInt.updateMaxStats(characterInt.count + bonusStats, 18);
			characterDex.updateMaxStats(characterDex.count + bonusStats, 18);
			characterCon.updateMaxStats(characterCon.count + bonusStats, 18);
			break;
		case 4:
		case 5:
			bonusSkills++;
			skill1control.updateMaxStats(skill1control.count + bonusSkills, 99);
			skill2control.updateMaxStats(skill2control.count + bonusSkills, 99);
			skill3control.updateMaxStats(skill2control.count + bonusSkills, 99);
			break;
		}
	}
	
	public int roll(Random random, int dice)
	{
		int result = 0;
		for (int x = 0; x < dice; x++)
		{
			result += (random.nextInt(6) + 1);
		}
		return result;
	}
	
	public void updateCharClass()
	{
		int currentClass = characterClass.id;
		
		switch (currentClass)
		{
		case 0:
			charClass = PlayerClass.FIGHTER;
			break;
		case 1:
			charClass = PlayerClass.THIEF;
			break;
		case 2: 
			charClass = PlayerClass.MAGE;
			break;
		}
		skill0 = charClass.primarySkill;
		skill1 = charClass.secondarySkill;
	}
	
	public void updateThirdSkill()
	{
		int currentClass = characterExtraSkill.id;
		
		switch (currentClass)
		{
		case 0:
			skill2 = PlayerSkill.MELEE;
			break;
		case 1:
			skill2 = PlayerSkill.MAGIC;
			break;
		case 2: 
			skill2 = PlayerSkill.ARCHERY;
			break;
		case 3:
			skill2 = PlayerSkill.SNEAK;
			break;
		}	
	}
}
