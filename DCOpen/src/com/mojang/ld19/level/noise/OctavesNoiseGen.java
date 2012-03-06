package com.mojang.ld19.level.noise;

import java.util.Random;

public class OctavesNoiseGen
{
	public SimplexNoise noisegen;
	private int octaves;
	
	public OctavesNoiseGen(Random rand, int octaves)
	{
		noisegen = new SimplexNoise(rand);
		this.octaves = octaves;
	}
	
	public double noise(double x, double y)
	{
		double sum = 0.0d;
		for (int lx = 1; lx < octaves; lx++)
		{
			double scalar = (double)1 / (double)lx;
			double noise = noisegen.noise(x * (double)lx, y * (double)lx) / (double)lx;
			sum += noise;
		}
		
		if (sum > 1.0d)
		{
			sum = 1.0d;
		}
		else if (sum < -1.0d)
		{
			sum = -1.0d;
		}
		
		return sum;
	}
}
