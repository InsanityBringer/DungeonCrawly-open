package com.mojang.ld19.level.object;

import java.util.Random;
import java.io.*;

import com.mojang.ld19.Entity;
import com.mojang.ld19.display.*;
import com.mojang.ld19.item.Item;
import com.mojang.ld19.level.Level;

public class ItemEntity extends Entity {
    private static Random random = new Random();

    public Item item;

    public ItemEntity(Item item) 
    {
        this.item = item;
        sprites.sprites.add(new Sprite(0, 0, 0, item.icon));

        double spread = 0.1;
        x = random.nextFloat() * spread - spread * 0.5;
        z = random.nextFloat() * spread - spread * 0.5;
        
        name = "ItemEntity";
    }
    
    public ItemEntity()
    {
    	this.item = Item.potion;
    	
        sprites.sprites.add(new Sprite(0, 0, 0, item.icon));

        double spread = 0.1;
        x = random.nextFloat() * spread - spread * 0.5;
        z = random.nextFloat() * spread - spread * 0.5;
        
        name = "ItemEntity";
    }
    
    public void save(RandomAccessFile savefile)
    	throws IOException
    {
    	super.save(savefile);
    	savefile.writeInt(item.itemID);
    }
    
    public void load(RandomAccessFile loadfile) throws IOException
    {
    	super.load(loadfile);
    	item = Item.getItem(loadfile.readInt());
    	
    	sprites.sprites.clear();
    	sprites.sprites.add(new Sprite(0, 0, 0, item.icon));
    }

    public boolean click(Level level, Sprite sprite, int x, int y, int button) {
        if (level.player.carried != null) 
        {
            location.addEntity(corner, new ItemEntity(level.player.carried));
            level.player.carried = null;
        } 
        else 
        {
            level.player.carried = item;
            level.player.addString(String.format("%s taken", item.getName()));
            remove();
        }
        return true;
    }
    
    public int getID()
    {
    	return 9;
    }
}
