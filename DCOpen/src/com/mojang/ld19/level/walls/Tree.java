package com.mojang.ld19.level.walls;

import java.util.*;

import com.mojang.ld19.Art;
import com.mojang.ld19.display.Sprite;

public class Tree extends Grass {
    private List<Sprite> leaves = new ArrayList<Sprite>();

    public Tree() {
        super(false);
        Random random = new Random(99);

        double offs = System.currentTimeMillis() % 1000 / 1000.0 * Math.PI * 2;

        for (int t = 0; t < 4; t++) {
            double xx = t % 2 * 0.5 + 0.25 + random.nextDouble() * 0.1 - 0.05;
            double zz = t / 2 * 0.5 + 0.25 + random.nextDouble() * 0.1 - 0.05;
            sprites.add(new Sprite(xx, 0.60, zz, Art.trunk));

            int branches = 4;
            for (int i = 0; i < branches; i++) {
                double rot = i * Math.PI * 2 / branches;
                double x = xx + Math.sin(rot) * 0.2;
                double z = zz + Math.cos(rot) * 0.2;
                double y = 0.15 + random.nextDouble() * 0.25;
                sprites.add(new Sprite(x, y, z, Art.branch));
            }
            int leafCount = 20;
            for (int i = 0; i < leafCount; i++) {
                double rot = i * Math.PI * 2 / leafCount;
                double y = 0.0 + random.nextDouble() * 0.3;
                double w = 0.2 * y * 1.4 + 0.2;
                double x = xx + Math.sin(rot) * w;
                double z = zz + Math.cos(rot) * w;

                Sprite sprite = new Sprite(x, y + 0.05 + Math.sin(offs + i*0.1) * 0.01, z, Art.leaves);
                sprites.add(sprite);
                leaves.add(sprite);
                sprite.yOrg = y;
            }
        }
    }

    public void update() 
    {
        double offs = System.currentTimeMillis() % 1200 / 1200.0 * Math.PI * 2;
        for (int i = 0; i < leaves.size(); i++) 
        {
            leaves.get(i).y = leaves.get(i).yOrg + Math.sin(offs + i * 4) * 0.005;
        }
    }
}
