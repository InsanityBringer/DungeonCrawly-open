package com.mojang.ld19.display;

import java.util.*;

public class SpriteBody {
    public List<Sprite> sprites = new ArrayList<Sprite>();

    public void update() {
    }

    public void rotate(double rot, boolean offs) {
        double cos = Math.cos(rot);
        double sin = Math.sin(rot);
        rotate(cos, sin, offs);
    }

    public void rotate(double cos, double sin, boolean offs) {
        for (int i = 0; i < sprites.size(); i++) {
            sprites.get(i).rotate(cos, sin, offs);
        }
    }
}
