package com.mojang.ld19.level.object;

import java.io.IOException;
import java.io.RandomAccessFile;

import com.mojang.ld19.Entity;
import com.mojang.ld19.SpellStatusEffect;
import com.mojang.ld19.display.*;
//import com.mojang.ld19.item.Item;
import com.mojang.ld19.level.*;
import com.mojang.ld19.magic.MagicParticle;
import com.mojang.ld19.Art;

public class FireDemonSpell extends BaseSpell 
{
	

    public FireDemonSpell(int rot, int speed, int arrowDice, int arrowSides, int arrowBonus) {
        this.rot = rot;
        sprites.sprites.clear();
        sprites.sprites.add(new Sprite(0, 0, 0, Art.fireball));
        //ignoreCorners = true;
        
        x = 0.5;
        y = 0.5;
        z = 0.5;
        
        delayMax = speed;
        sides = arrowSides;
        dice = arrowDice;
        bonus = arrowBonus;
        
        name = "FireDemonSpell";
    }
    
    public int getID()
    {
    	return 19;
    }
    
    public void explode()
    {
    	for (int x = 0; x < 20; x++)
    	{
    		double velx = (random.nextDouble() * 2D) - 1D;
    		double vely = (random.nextDouble() * 2D) - 1D;
    		double velz = (random.nextDouble() * 2D) - 1D;
    		
    		MagicParticle particleFX = new MagicParticle(velx, vely, velz, 0xFF6A00);
    		location.addEntity(-1, particleFX);
    	}
    }
    
    public FireDemonSpell() 
    {
        this.rot = 0;
        sprites.sprites.clear();
        sprites.sprites.add(new Sprite(0, 0, 0, Art.fireball));
        //ignoreCorners = true;
        
        x = 0.5;
        y = 0.5;
        z = 0.5;
        
        delayMax = 0;
        sides = 0;
        dice = 0;
        bonus = 0;
        
        name = "FireDemonSpell";
    } 
}
