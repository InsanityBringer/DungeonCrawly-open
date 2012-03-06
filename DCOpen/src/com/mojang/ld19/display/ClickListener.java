package com.mojang.ld19.display;

import com.mojang.ld19.level.Level;

public interface ClickListener 
{
    public boolean click(Level level, Sprite sprite, int x, int y, int button);
}
