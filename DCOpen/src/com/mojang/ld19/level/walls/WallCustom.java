package com.mojang.ld19.level.walls;

import java.util.Random;

import com.mojang.ld19.Art;
import com.mojang.ld19.display.*;

public class WallCustom extends SpriteBody {
    public WallCustom(Bitmap image) 
    {
        Random random = new Random(1000);
        int w = 6;
        int h = 9;
        double ws = 1.0 / (w);
        double hs = 1.0 / (h);
        int z = 0;
        for (int x = 0; x < w; x++) 
        {
            for (int y = 0; y < h; y++) 
            {
                double xx = x * ws + (random.nextFloat() - 0.5) * 0.03;
                double yy = y * hs + (random.nextFloat() - 0.5) * 0.03;
                double zz = z * ws + (random.nextFloat() - 0.5) * 0.03;
                sprites.add(new Sprite(xx, yy, zz, image));
            }
        }
    }
}
