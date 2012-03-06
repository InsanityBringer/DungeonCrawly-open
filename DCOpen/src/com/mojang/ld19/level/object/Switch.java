package com.mojang.ld19.level.object;

import java.util.Random;
import java.io.*;
import com.mojang.ld19.Art;
import com.mojang.ld19.Entity;
import com.mojang.ld19.display.*;
import com.mojang.ld19.level.Level;

public class Switch extends Entity {
    protected static Random random = new Random();

    protected Bitmap on, off;
    protected int imageID;
    private int tx, ty;
    public int switchID;
    public boolean pressed;
    
    private static Bitmap[][] imageTable = new Bitmap[1][2];
    
    static
    {
    	imageTable[0][0] = Art.button_1;
    	imageTable[0][1] = Art.button_2;
    }

    public Switch(int imageID, int ID) 
    {
        this.on = imageTable[imageID][0];
        this.off = imageTable[imageID][1];
        this.imageID = imageID;
        switchID = ID;

        sprites.sprites.clear();
        sprites.sprites.add(new Sprite(0, 0, 0, on));
        
        name = "Switch";
    }
    
    public void save(RandomAccessFile savefile)
    	throws IOException
    {
    	super.save(savefile);
    	savefile.writeInt(imageID);
    	savefile.writeInt(switchID);
    	savefile.writeInt(tx);
    	savefile.writeInt(ty);
    	savefile.writeBoolean(pressed);
    }
    
    public void load(RandomAccessFile loadfile) throws IOException
    {
    	super.load(loadfile);
    	imageID = loadfile.readInt();
    	switchID = loadfile.readInt();
    	tx = loadfile.readInt();
    	ty = loadfile.readInt();
    	pressed = loadfile.readBoolean();
    	
        this.on = imageTable[imageID][0];
        this.off =imageTable[imageID][1];
        
        Bitmap imageToAdd = on;
        
        if (pressed)
        {
        	imageToAdd = off;
        }
		
        sprites.sprites.clear();
        sprites.sprites.add(new Sprite(0, 0, 0, imageToAdd));
        
    }
    
    public Switch()
    {
    	switchID = 0;
        name = "Switch";
    }
    
    public int getID()
    {
    	return 11;
    }

    public boolean click(Level level, Sprite sprite, int x, int y, int button) 
    {
        pressed = !pressed;
        sprites.sprites.clear();
        sprites.sprites.add(new Sprite(0, 0, 0, pressed ? off : on));
        level.trigger(switchID, pressed);

        return true;
    }

    /**
     * Sets the position of the switch
     * @param x The x pos of the switch
     * @param y The y pos of the switch
     * @param z The z pos of the switch
     */
    public void setPosition(double x, double y, double z) 
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
