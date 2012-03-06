package com.mojang.ld19.level.object;

import com.mojang.ld19.Art;
import com.mojang.ld19.display.Bitmap;
import com.mojang.ld19.display.Sprite;
import com.mojang.ld19.level.Level;
import com.mojang.ld19.item.*;
import java.io.*;

public class KeyHole extends Switch
{
	private static Item key;
	
	private static Bitmap[] keyImageTable = new Bitmap[1];
	
	static
	{
		keyImageTable[0] = Art.keyhole;
	}
	
	public KeyHole(int imageID, int ID, Item keyItem)
	{
		super(0, ID);
		
        this.on = keyImageTable[imageID];
        this.off = keyImageTable[imageID];
        this.imageID = imageID;
		
		key = keyItem;
		
        sprites.sprites.clear();
        sprites.sprites.add(new Sprite(0, 0, 0, on));
        
        name = "KeyHole";
	}
	
	public void save(RandomAccessFile savefile)
		throws IOException
	{
		super.save(savefile);
		savefile.writeInt(key.itemID);
		savefile.writeInt(imageID);
	}
	
	public void load(RandomAccessFile loadfile) throws IOException
	{
		super.load(loadfile);
		key = Item.getItem(loadfile.readInt());
		imageID = loadfile.readInt();
		
        this.on = keyImageTable[imageID];
        this.off = keyImageTable[imageID];
		
        sprites.sprites.clear();
        sprites.sprites.add(new Sprite(0, 0, 0, on));
        
        name = "KeyHole";
	}
	
	public KeyHole()
	{
		super();
		key = Item.blueKey;
	}
	
	public int getID()
	{
		return 10;
	}
	
    public boolean click(Level level, Sprite sprite, int x, int y, int button) 
    {
    	if (level.player.carried == null)
    	{
    		level.player.addString("A key is required to use this lock");
    		return true;
    	}
    	if (!(level.player.carried instanceof ItemKey))
    	{
    		level.player.addString("A key is required to use this lock");
    		return true;
    	}
    	if (pressed)
    	{
    		level.player.addString("This lock has already been opened");
    		return true;
    	}
    	if (level.player.carried instanceof ItemKey)
    	{
    		if (level.player.carried != key)
    		{
	    		level.player.addString("The key doesn't turn...");
	    		return true;
    		}
    		else
    		{
    	        pressed = !pressed;
    	        level.trigger(switchID, pressed);
    	        level.player.carried = null;
    		}
    	}

        return true;
    }
}
