package com.mojang.ld19.level;

import com.mojang.ld19.Entity;
import com.mojang.ld19.display.*;

public class WallTile extends Tile {
    public WallTile(SpriteBody sprites) {
        super(sprites);
    }

    public boolean blocks(Entity e) {
        return true;
    }

    public boolean isWall() {
        return true;
    }
    
    public boolean canProjectilePass()
    {
    	return false;
    }

    public void render(Screen screen) {
        if (!level.getTile(x, y - 1).isWall()) {
            sprites.rotate(Math.PI / 2 * 0, true);
            screen.draw(sprites, x, 0, y, this);
        }
        if (!level.getTile(x + 1, y).isWall()) {
            sprites.rotate(Math.PI / 2 * 1, true);
            screen.draw(sprites, x, 0, y, this);
        }
        if (!level.getTile(x, y + 1).isWall()) {
            sprites.rotate(Math.PI / 2 * 2, true);
            screen.draw(sprites, x, 0, y, this);
        }
        if (!level.getTile(x - 1, y).isWall()) {
            sprites.rotate(Math.PI / 2 * 3, true);
            screen.draw(sprites, x, 0, y, this);
        }
        renderSprites(screen);
    }
}
