package com.mojang.ld19.level.walls;

import com.mojang.ld19.Art;
import com.mojang.ld19.display.Sprite;

public class LightPost extends Grass 
{
    public LightPost() 
    {
        super(false);

        sprites.add(new Sprite(0.5, 0.45, 0.5, Art.lightPost));
    }
}
