package com.mojang.ld19.level.object;

import com.mojang.ld19.Entity;
import java.io.*;
import com.mojang.ld19.display.*;
import com.mojang.ld19.item.Item;
import com.mojang.ld19.level.*;

public class ThrownItem extends Entity {
    private Item item;
    private int delay = 0;
    private int power = 3;
    private int rot;

    public ThrownItem(Item item, int rot) {
        this.item = item;
        this.rot = rot;
        sprites.sprites.add(new Sprite(0, 0, 0, item.icon));


        double spread = 0.1;
        x = random.nextFloat() * spread - spread * 0.5;
        y = -0.5;
        z = random.nextFloat() * spread - spread * 0.5;
        
        name = "ThrownItem";
    }
    
    public ThrownItem()
    {
    	item = Item.potion;
    	rot = 0;
    	//sprites.sprites.add(new Sprite(0, 0, 0, item.icon));
    	
        double spread = 0.1;
        x = random.nextFloat() * spread - spread * 0.5;
        y = -0.5;
        z = random.nextFloat() * spread - spread * 0.5;
        
        name = "ThrownItem";
    }
    
    public void save(RandomAccessFile savefile)
    	throws IOException
	{
    	super.save(savefile);
    	savefile.writeInt(item.itemID);
    	savefile.writeInt(delay);
    	savefile.writeInt(power);
    	savefile.writeInt(rot);
	}
    
    public void load(RandomAccessFile loadfile) throws IOException
    {
    	super.load(loadfile);
    	item = Item.getItem(loadfile.readInt());
    	delay = loadfile.readInt();
    	power = loadfile.readInt();
    	rot = loadfile.readInt();
    	
    	sprites.sprites.add(new Sprite(0, 0, 0, item.icon));
    }
    
    public int getID()
    {
    	return 12;
    }

    public void tick(Level level) {
        if (++delay == 10) {
            delay = 0;

            power--;

            int xt = location.x * 2 + corner % 2;
            int yt = location.y * 2 + corner / 2;
            if (rot == 0) yt++;
            if (rot == 1) xt++;
            if (rot == 2) yt--;
            if (rot == 3) xt--;
            Tile target = level.getTile(xt / 2, yt / 2);
            if (target.canContainItems()) {
                location.removeEntity(corner, this);
                target.addEntity(xt % 2 + yt % 2 * 2, this);
            } else {
            }

            if (power == 0) {
                location.addEntity(corner, new ItemEntity(item));
                location.removeEntity(corner, this);
            }
            y = -(0.3+power/10.0);
        }
        super.tick(level);
    }

    public boolean click(Level level, Sprite sprite, int x, int y, int button) {
        return false;
    }
}
