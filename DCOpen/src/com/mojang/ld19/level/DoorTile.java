package com.mojang.ld19.level;

import java.util.*;
import java.io.IOException;
import java.io.RandomAccessFile;
import com.mojang.ld19.Entity;
import com.mojang.ld19.display.*;
import com.mojang.ld19.level.walls.Door;

public class DoorTile extends Tile {
    protected boolean open = false;
    private double openness = 0;

    public DoorTile(SpriteBody spriteBody, int blockTag) {
        super(spriteBody);
        id = blockTag;
    }
    
    public void saveTile(RandomAccessFile savefile)
    	throws IOException
    {
    	super.saveTile(savefile);
    	savefile.writeBoolean(open);
    	savefile.writeDouble(openness);
    }
    
    public void loadTile(RandomAccessFile loadfile) throws IOException
    {
    	super.loadTile(loadfile);
    	open = loadfile.readBoolean();
    	openness = loadfile.readDouble();
    }

    public void render(Screen screen) {
        //        double open = Math.sin(System.currentTimeMillis() % 1000 / 1000.0*Math.PI*2)*0.5+0.5;
        double open = openness * 0.47;
        List<Sprite> doorSprites = ((Door) sprites).doorSprites;
        if (level.getTile(x, y - 1).isWall() && level.getTile(x, y + 1).isWall()) {
            sprites.rotate(0, true);
            for (int i = 0; i < doorSprites.size(); i++) {
                Sprite sprite = doorSprites.get(i);
                if (sprite.z < 0.45) {
                    sprite.z -= open;
                } else {
                    sprite.z += open;
                }
            }
        } else {
            sprites.rotate(Math.PI * 2 * 0.25, true);
            for (int i = 0; i < doorSprites.size(); i++) {
                Sprite sprite = doorSprites.get(i);
                if (sprite.x < 0.45) {
                    sprite.x -= open;
                } else {
                    sprite.x += open;
                }
            }
        }
        super.render(screen);
    }

    public boolean canContainItems() 
    {
        return open;
    }

    public void tick() 
    {
        double speed = 0.1;

        if (open) openness += speed;
        else openness -= speed;

        if (openness > 1) openness = 1;
        if (openness < 0) openness = 0;

        if (level.player.x == x && level.player.z == y) 
        {
            openness = 1;
            open = true;
        }
    }

    /*public void trigger(boolean on) {
        open = on;
    }*/
    
    public boolean click(Level level, Sprite sprite, int x, int y, int button)
    {
    	if (containsMob() != -2)
    	{
    		return true;
    	}
    	open = !open;
    	return true;
    }

    public boolean blocks(Entity e) {
        return openness < 1;
    }
    
    public boolean canProjectilePass()
    {
    	return open;
    }
}
