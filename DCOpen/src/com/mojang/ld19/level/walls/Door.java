package com.mojang.ld19.level.walls;

import java.util.*;

import com.mojang.ld19.display.*;

public class Door extends DungeonFloor {
    public List<Sprite> doorSprites = new ArrayList<Sprite>();

    public Door(Bitmap img) {
        Random random = new Random(1000);
        int w = 6;
        int h = 6;
        double ws = 1.0 / (w);
        double hs = 1.0 / (h);
        for (int x = -1; x <= 1; x++) {
            for (int z = 0; z < w; z++) {
                for (int y = 0; y < h; y++) {
                    double xx = 0.5 + (random.nextFloat() - 0.5) * 0.03 + (x*0.75 - 0.5) * ws;
                    double yy = y * hs + (random.nextFloat() - 0.5) * 0.03;
                    double zz = z * ws + (random.nextFloat() - 0.5) * 0.03;

                    doorSprites.add(new Sprite(xx, yy, zz, img));
                    doorSprites.add(new Sprite(xx, yy, zz, img));
                }
            }
        }
        sprites.addAll(doorSprites);
    }
}
