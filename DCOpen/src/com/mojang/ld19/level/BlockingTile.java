package com.mojang.ld19.level;

import com.mojang.ld19.Entity;
import com.mojang.ld19.display.*;

public class BlockingTile extends Tile 
{
    public BlockingTile(SpriteBody sprites) 
    {
        super(sprites);
    }

    public boolean blocks(Entity e) 
    {
        return true;
    }
    
    public boolean canContainItems() 
    {
        return false;
    }    
}
