package com.mojang.ld19.level.walls;

import com.mojang.ld19.display.*;

public class Water extends SpriteBody {
    public double bob;
    
    public Water(Bitmap img, double bob) {
        this.bob = bob;
        int w = 6;
        int h = 8;
        double ws = 1.0 / (w);
        double hs = 1.0 / (h);
        for (int x = 0; x < w; x++) {
            for (int z = 0; z < w; z++) {
                int y = h - 1;
                double xx = x * ws;
                double yy = y * hs + 0.05;
                double zz = z * ws;
                sprites.add(new Sprite(xx, yy, zz, img));
            }
        }
    }

    public void update() 
    {
        double offs = System.currentTimeMillis() % 1000 / 1000.0 * Math.PI * 2;
        for (int i=0; i<sprites.size(); i++) 
        {
            sprites.get(i).y = 1 - 0.05 + Math.sin(offs + i*4*bob) * 0.015*bob;
        }
    }
}
