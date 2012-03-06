package com.mojang.ld19.level;

import java.util.Random;

import com.mojang.ld19.*;
import com.mojang.ld19.display.*;
import com.mojang.ld19.mob.Mob;

public class DirtStairsTile extends Tile {
    private int dir;

    public DirtStairsTile(int dir) {
        super(new SpriteBody());
        this.dir = dir;

        Random random = new Random(1000);
        int w = 6;
        int h = 8;
        double ws = 1.0 / (w);
        double hs = 1.0 / (h);
        for (int x = -1; x < w + 1; x++) {
            for (int z = 0; z < w * 3; z++) {
                for (int y = h - 1 - z; y < h + 1; y++) {
                    double xx = x * ws + (random.nextFloat() - 0.5) * 0.05;
                    double yy = y * hs + (random.nextFloat() - 0.5) * 0.05;
                    double zz = z * ws + (random.nextFloat() - 0.5) * 0.05;
                    yy += (z + 1) * hs * 0.6 * -dir;
                    sprites.sprites.add(new Sprite(xx, yy, zz, Art.brick));
                    if (x > -1 && x < w && y == 0) y = h - 2;
                }
            }
        }
    }

    public void render(Screen screen) {
        sprites.sprites.clear();
        Random random = new Random(1000);
        int w = 6;
        int h = 8;
        double ws = 1.0 / (w);
        double hs = 1.0 / (h);
        for (int x = -1; x < w + 1; x++) {
            for (int z = 0; z < w * 3; z++) {
                for (int y = h - 2 - z/2; y < h + 1; y++) {
                    if (x > -1 && x < w && y < h-1 && z<w*2) y = h - 1;
                    double xx = x * ws + (random.nextFloat() - 0.5) * 0.05;
                    double yy = y * hs + (random.nextFloat() - 0.5) * 0.05;
                    double zz = z * ws + (random.nextFloat() - 0.5) * 0.05;
                    yy += (z + 1) * hs * 0.6 * -dir;
                    sprites.sprites.add(new Sprite(xx, yy, zz, Art.mossBrick));
                    if (x > -1 && x < w && y < h-1) y = h - 2;
                }
            }
        }

        if (level.getTile(x, y - 1) == EmptyTile.instance) {
            sprites.rotate(Math.PI * 2 * 0.5, true);
        } else if (level.getTile(x, y + 1) == EmptyTile.instance) {
            sprites.rotate(Math.PI * 2 * 0.0, true);
        } else if (level.getTile(x - 1, y) == EmptyTile.instance) {
            sprites.rotate(Math.PI * 2 * 0.25, true);
        } else if (level.getTile(x + 1, y) == EmptyTile.instance) {
            sprites.rotate(Math.PI * 2 * 0.75, true);
        }
        super.render(screen);
    }

    public boolean canContainItems() {
        return false;
    }

    public void stepOn(Player player) {
        level.replaceWith = Level.get(level.layer + dir, player);
        player.blackout = 15;
    }
    
    public boolean blocks(Entity e)
    {
    	if (e instanceof Mob)
    	{
    		return true;
    	}
    	return super.blocks(e);
    }
}
