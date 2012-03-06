package com.mojang.ld19.level.walls;

import java.util.Random;

import com.mojang.ld19.Art;
import com.mojang.ld19.display.*;

public class DirtTunnel extends SpriteBody {
    public DirtTunnel() {
        Random random = new Random(1000);
        int w = 6;
        int h = 8;
        double ws = 1.0 / (w);
        double hs = 1.0 / (h);
        for (int x = 0; x < w; x++) {
            for (int z = 0; z < w; z++) {
                for (int y = 0; y < h; y++) {
                    double xx = x * ws + (random.nextFloat() - 0.5) * 0.04;
                    double yy = y * hs + (random.nextFloat() - 0.5) * 0.04;
                    double zz = z * ws + (random.nextFloat() - 0.5) * 0.04;
                    if (y == h - 1) {
                        sprites.add(new Sprite(xx, yy, zz, Art.dirt));
                    } else if (y == 0) {
                        sprites.add(new Sprite(xx, yy, zz, Art.dirt));
                    }
                }
            }
        }
    }
}
