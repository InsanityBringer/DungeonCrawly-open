package com.mojang.ld19.level.walls;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mojang.ld19.Art;
import com.mojang.ld19.display.Sprite;

public class Portal extends DungeonFloor 
{
	private List<Sprite> sparklelist = new ArrayList<Sprite>();
	public int angle = 0;
	public Portal()
	{
		super();
		Random random = new Random();
		
		int sparkles = 50;
		for (int i = 0; i < sparkles; i++)
		{
			double posx = random.nextDouble();
			double posy = random.nextDouble();
			double posz = random.nextDouble();
			
			Sprite sparkle = new Sprite(posx, posy, posz, Art.sparkle);
			sprites.add(sparkle);
			sparklelist.add(sparkle);
		}
	}
	
	public void update()
	{
		double offs = System.currentTimeMillis() % 1200 / 1200.0 * Math.PI * 2;
		
		for (int x = 0; x < sparklelist.size(); x++)
		{
			sparklelist.get(x).rotate(Math.cos(offs), Math.sin(offs), true);
		}
	}
}
