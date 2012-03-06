package com.mojang.ld19.level;

import com.mojang.ld19.display.SpriteBody;

public class EmptyTile extends Tile {
    public static final EmptyTile instance = new EmptyTile();

    private EmptyTile() {
        super(new SpriteBody());
    }
    
    public boolean canContainItems() {
        return false;
    }
}
