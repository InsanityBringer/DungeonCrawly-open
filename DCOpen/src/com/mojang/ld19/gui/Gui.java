package com.mojang.ld19.gui;

import java.util.*;
import java.io.*;
import com.mojang.ld19.*;
import com.mojang.ld19.display.*;
import com.mojang.ld19.item.Item;
import com.mojang.ld19.level.Level;

public class Gui implements ClickListener {
    private List<InventorySlot> slots = new ArrayList<InventorySlot>();
    private int xp = LD19.WIDTH - 58;
    private int yp = 0;
    private Player player;
    private QuiverSlot quiver;
    private GuiButton moreButton; 
    private InfoPanel infoPanel;
    private SpellPanel spellPanel;

    public Gui(Player player) 
    {
        this.player = player;

        int xo = 4;
        int yo = 32;
        int handslot = 0;
        int firstSlot = 0;
        
        int currentSlot = 0;
        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 4; y++) 
            {
                if (y == 1 || x == 1) 
                {
                	if (y == 1 && x != 1)
                	{
                		slots.add(new InventorySlot(xp + x * 17 + xo, yp + y * 17 + yo, true, 5, currentSlot, player));
                		handslot = slots.size();
                	}
                	else slots.add(new InventorySlot(xp + x * 17 + xo, yp + y * 17 + yo, false, y + 1, currentSlot, player));
                	currentSlot++;
                }
            }

        yo = 135;
        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 6; y++) 
            {
            	if (x == 0 && y == 0)
            	{
            		firstSlot = slots.size();
            	}
                slots.add(new InventorySlot(xp + x * 17 + xo, yp + y * 17 + yo, false, 0, currentSlot, player));
                currentSlot++;
            }
        
        Item itemToAdd;
        if (player.currentClass == PlayerClass.MAGE)
        {
        	itemToAdd = Item.knife;
        	for (int x = 0; x < 2; x++)
        	{
        		InventorySlot slot = slots.get(firstSlot + x - 1);
        		slot.addItem(itemToAdd);
        	}
        }
        else itemToAdd = Item.sword;
        
        InventorySlot slot = slots.get(handslot - 1);
        slot.addItem(itemToAdd);
        //372, 32;
        quiver = new QuiverSlot(372, 32, player);
        moreButton = new GuiButton(372, 22, "More...");
        infoPanel = new InfoPanel(player);
        spellPanel = new SpellPanel(player);
        spellPanel.populateSpellPage(0);
    }

    public void render(Screen screen) 
    {
        screen.blit(Art.inventory, 0, 0, this);
        for (int i = 0; i < slots.size(); i++) 
        {
            InventorySlot slot = slots.get(i);
            slot.render(screen);
        }
        quiver.render(screen);

        int max = 48;
        int filled = ((((player.maxHealth - player.health) * (max) + player.maxHealth / 2) / (player.maxHealth)));
        if (filled < 1) filled = 1;
        if (filled > max - 1) filled = max - 1;
        if (player.health <= 0) filled = max;
        if (player.health >= player.maxHealth) filled = 0;

        String healthString = String.format("%d/%d", player.health, player.maxHealth);
        if (player.statusTime[Player.STATUS_POISON] > 0)
        {
        	screen.blit(Art.guiPoisonBar, 374, 101, this);
        }
        screen.fill(xp + 6 + max - filled, 101, filled, 7, 0x202020);
        //374, 101
        TextRenderer.drawString(healthString, screen, 374, 102, this);
        
        filled = ((((player.expToNextLevel - player.currentExp) * (max) + player.expToNextLevel / 2) / (player.expToNextLevel)));
        if (filled < 1) filled = 1;
        if (filled > max - 1) filled = max - 1;
        if (player.currentExp <= 0) filled = max;
        if (player.currentExp >= player.expToNextLevel) filled = 0;

        screen.fill(xp + 6 + max - filled, 121, filled, 7, 0x202020);
        
        double percentage = ((double)player.currentExp / (double)player.expToNextLevel) * 100d;
        String XPString = String.format("%.0f%%", percentage);
        TextRenderer.drawString(XPString, screen, 374, 122, this);
        
        filled = ((((player.MPMax - player.MP) * (max) + player.MPMax / 2) / (player.MPMax)));
        if (filled < 1) filled = 1;
        if (filled > max - 1) filled = max - 1;
        if (player.MP <= 0) filled = max;
        if (player.MP >= player.MPMax) filled = 0;

        screen.fill(xp + 6 + max - filled, 111, filled, 7, 0x202020);
        
        String MPString = String.format("%d/%d", player.MP, player.MPMax);
        TextRenderer.drawString(MPString, screen, 374, 112, this);
        
        TextRenderer.drawString(player.currentStrings[1], screen, 6, 221, this);
        TextRenderer.drawString(player.currentStrings[0], screen, 6, 221+8, this);
        
        TextRenderer.drawString(String.format("LVL:%d", player.characterLevel), screen, 373, 3, this);
        if (!player.hasAnyStatus())
        {
        	TextRenderer.drawString(String.format("SP:%d", player.currentSkillPoints), screen, 373, 3+7, this);
        }
        else if (player.statusTime[Player.STATUS_POISON] > 0)
        {
        	TextRenderer.drawString(String.format("Poison!", player.currentSkillPoints), screen, 373, 3+7, this);
        }
        else if (player.statusTime[Player.STATUS_HOLD] > 0)
        {
        	TextRenderer.drawString(String.format("Hold!", player.currentSkillPoints), screen, 373, 3+7, this);
        }  
        else if (player.statusTime[Player.STATUS_CHARM] > 0)
        {
        	TextRenderer.drawString(String.format("Charm!", player.currentSkillPoints), screen, 373, 3+7, this);
        }      
        else if (player.statusTime[Player.STATUS_BLACKNESS] > 0)
        {
        	TextRenderer.drawString(String.format("Dark!", player.currentSkillPoints), screen, 373, 3+7, this);
        }  
        else if (player.statusTime[Player.STATUS_SLOW] > 0)
        {
        	TextRenderer.drawString(String.format("Slow!", player.currentSkillPoints), screen, 373, 3+7, this);
        }  
        else if (player.statusTime[Player.STATUS_MUTE] > 0)
        {
        	TextRenderer.drawString(String.format("Mute!", player.currentSkillPoints), screen, 373, 3+7, this);
        }  
        
        //374, 111
        
        moreButton.render(screen);
        if (moreButton.pressed)
        {
        	infoPanel.render(screen, player);
        }
        if (player.isCasting)
        {
        	spellPanel.render(screen);
        }
        if (player.justOpenedSpellPanel)
        {
        	spellPanel.populateSpellPage(spellPanel.page);
        	player.justOpenedSpellPanel = false;
        }
    }

    public boolean click(Level level, Sprite sprite, int x, int y, int button) {
        return true;
    }
    
    public void saveInventory(RandomAccessFile savefile) throws IOException
    {
    	for (int x = 0; x < slots.size(); x++)
    	{
    		InventorySlot slot = slots.get(x);
    		if (slot.item != null)
    		{
    			savefile.writeInt(slot.item.itemID);
    		}
    		else savefile.writeInt(-1);
    	}
    }
    
    public void loadInventory(RandomAccessFile loadfile) throws IOException
    {
    	for (int x = 0; x < slots.size(); x++)
    	{
    		InventorySlot slot = slots.get(x);
    		slot.item = Item.getItem(loadfile.readInt());
    	}
    }
    
    public void tick()
    {
    	for (int i = 0; i < slots.size(); i++) 
    	{
    		InventorySlot slot = slots.get(i);
    		slot.tick(player);
    	}
    	quiver.tick(player);
    }
    
}
