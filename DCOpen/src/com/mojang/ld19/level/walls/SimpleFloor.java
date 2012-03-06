package com.mojang.ld19.level.walls;

import java.util.Random;

import com.mojang.ld19.display.*;

public class SimpleFloor extends SpriteBody {
    public SimpleFloor(Bitmap img) {
        Random random = new Random(43287234);
        int w = 6;
        int h = 8;
        double ws = 1.0 / (w);
        double hs = 1.0 / (h);
        for (int x = 0; x < w; x++) {
            for (int z = 0; z < w; z++) {
                int y = h - 1;
                double xx = x * ws + (random.nextFloat() - 0.5) * 0.04;
                double yy = y * hs + (random.nextFloat() - 0.5) * 0.04;
                double zz = z * ws + (random.nextFloat() - 0.5) * 0.04;
                sprites.add(new Sprite(xx, yy, zz, img));
            }
        }
    }
}
