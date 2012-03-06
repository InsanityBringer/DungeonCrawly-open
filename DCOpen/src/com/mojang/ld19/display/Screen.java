package com.mojang.ld19.display;

import java.awt.*;
import java.awt.image.*;
import java.util.Arrays;

import com.mojang.ld19.level.Level;

public class Screen
{
	private int w, h, scale;
	private BufferedImage img;
	private int[] pixels;
	private int[] zBuffer;
	private int[] shadeBuffer;

	private Canvas canvas;

	private Sprite hr_sprite;
	private int hr_x, hr_y;
	private ClickListener hr_clickListener;

	private ClickListener clickListener;

	public int blacknessFactor = 20;

	public Screen(int w, int h, int scale)
	{
		this.w = w;
		this.h = h;
		this.scale = scale;

		canvas = new Canvas();

		img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		zBuffer = new int[w * h];
		shadeBuffer = new int[w * h];
		for (int x = 0; x < w; x++)
		{
			for (int y = 0; y < h; y++)
			{
				double xx = (x - (w * 0.5)) / (w / 4.0);
				double yy = (y - (h * 0.4)) / (w / 4.0);
				double dd = Math.sqrt(xx * xx + yy * yy + 2);
				shadeBuffer[x + y * w] = (int) (dd * 256);
				// shadeBuffer[x+y*w] = 0;
			}
		}
	}

	public Canvas getComponent()
	{
		return canvas;
	}

	public void clear()
	{
		Arrays.fill(pixels, 0);
		Arrays.fill(zBuffer, Integer.MAX_VALUE);
		hr_sprite = null;
		hr_clickListener = null;
	}

	public double xCam, yCam, zCam;
	public double camSin, camCos, camRot;

	public int xMouse, yMouse;

	public void setCamera(double xCam, double yCam, double zCam, double rot)
	{
		xCam -= 0.5 / 6.0;
		zCam -= 0.5 / 6.0;
		this.xCam = xCam;
		this.yCam = yCam;
		this.zCam = zCam;

		this.camRot = rot;
		camSin = Math.sin(rot);
		camCos = Math.cos(rot);

		this.xCam -= camSin * 0.4;
		this.zCam -= camCos * 0.4;
	}

	public void draw(SpriteBody body, double x, double y, double z,
			ClickListener clickListener)
	{
		double xd = x - xCam;
		double zd = z - zCam;
		double dot = camCos * zd + camSin * xd;
		this.clickListener = clickListener;
		if (dot > -0.5)
		{
			for (int i = 0; i < body.sprites.size(); i++)
			{
				Sprite sprite = body.sprites.get(i);
				draw(sprite, sprite.x + x, sprite.y + y, sprite.z + z);
			}
		}
	}

	public void drawSky(Bitmap sky)
	{
		int xo = (int) (-camRot / Math.PI * sky.w / 2);
		for (int y = 0; y < sky.h; y++)
		{
			for (int x = 0; x < w; x++)
			{
				int xs = (x + xo) % sky.w;
				if (xs < 0)
					xs += sky.w;
				pixels[x + y * w] = sky.pixels[xs + y * sky.w];
			}
		}
	}

	public void draw(Sprite sprite, double x, double y, double z)
	{
		Bitmap bitmap = sprite.bitmap;
		x -= xCam;
		y -= yCam;
		z -= zCam;

		double xt = -(camCos * x - camSin * z);
		double zt = camCos * z + camSin * x;

		x = xt;
		z = zt;

		z += (x * x + y * y) * 0.1;

		if (z < 0.1)
			return;
		if (z > 4.2)
			return;

		double scale = 1.0 / z;
		int zp = (int) (z * 65536);

		double spriteScale = 2;
		x *= scale * (w / 2);
		y *= scale * (w / 2);

		int guiSize = -58;
		x += (w + guiSize / 2) * 0.5;
		y += h * 0.40;

		x -= bitmap.xc * scale * spriteScale;
		y -= bitmap.yc * scale * spriteScale;
		int x0 = (int) (x);
		int x1 = (int) (x + bitmap.w * scale * spriteScale);
		int y0 = (int) (y);
		int y1 = (int) (y + bitmap.h * scale * spriteScale);

		int xp0 = x0;
		int xp1 = x1;
		int yp0 = y0;
		int yp1 = y1;

		if (xp1 <= xp0)
			return;
		if (yp1 <= yp0)
			return;

		if (xp0 < 0)
			xp0 = 0;
		if (yp0 < 0)
			yp0 = 0;
		if (xp1 > w)
			xp1 = w;
		if (yp1 > h)
			yp1 = h;

		int xpsa = (1) * bitmap.w * 65536 / (x1 - x0);
		int xps0 = (xp0 - x0) * xpsa;

		int ypsa = (1) * bitmap.h * 65536 / (y1 - y0);
		int yps0 = (yp0 - y0) * ypsa;

		if (xMouse >= xp0 && xMouse < xp1 && yMouse >= yp0 && yMouse < yp1)
		{
			int mpp = xMouse + yMouse * w;
			for (int yp = yp0; yp < yp1; yp++)
			{
				int pp = yp * w;
				int xps = xps0;
				int yps = (yps0 >> 16) * bitmap.w;

				for (int xp = xp0; xp < xp1; xp++)
				{
					if (zBuffer[pp + xp] > zp)
					{
						int sourceCol = bitmap.pixels[(xps >> 16) + yps];
						if (sourceCol < 0)
						{
							if (tint > 0)
							{
								sourceCol = ((sourceCol & 0xfefefe) + (tint & 0xfefefe)) / 2;
							}
							if (pp + xp == mpp)
							{
								hr_clickListener = clickListener;
								hr_sprite = sprite;
								hr_x = xps >> 16;
								hr_y = yps / bitmap.w;
							}
							zBuffer[pp + xp] = zp;
							pixels[pp + xp] = sourceCol;
						}
					}
					xps += xpsa;
				}

				yps0 += ypsa;
			}
		} else
		{
			for (int yp = yp0; yp < yp1; yp++)
			{
				int pp = yp * w + xp0;
				int xps = xps0;
				int yps = (yps0 >> 16) * bitmap.w;

				for (int xp = xp0; xp < xp1; xp++)
				{
					int sourceCol = bitmap.pixels[(xps >> 16) + yps];
					if (zBuffer[pp] > zp && sourceCol < 0)
					{
						if (tint > 0)
						{
							sourceCol = ((sourceCol & 0xfefefe) + (tint & 0xfefefe)) / 2;
						}
						zBuffer[pp] = zp;
						pixels[pp] = sourceCol;
					}
					xps += xpsa;
					pp += 1;
				}

				yps0 += ypsa;
			}
		}
	}
	
	public void blit(Sprite sprite, int x, int y, ClickListener clickListener)
	{
		Bitmap bitmap = sprite.bitmap;
		blit(bitmap, x, y, clickListener);
	}

	public void fill(int x, int y, int w, int h, int col)
	{
		for (int yy = 0; yy < h; yy++)
		{
			int pp = x + (y + yy) * this.w;
			for (int xx = 0; xx < w; xx++)
			{
				pixels[pp + xx] = col;
			}
		}
	}

	public void blit(Bitmap bitmap, int x, int y, ClickListener clickListener)
	{
		this.clickListener = clickListener;
		int x0 = (x);
		int x1 = (x + bitmap.w);
		int y0 = (y);
		int y1 = (y + bitmap.h);

		int xp0 = x0;
		int xp1 = x1;
		int yp0 = y0;
		int yp1 = y1;

		if (xp0 < 0)
			xp0 = 0;
		if (yp0 < 0)
			yp0 = 0;
		if (xp1 > w)
			xp1 = w;
		if (yp1 > h)
			yp1 = h;

		int xpsa = (1) * bitmap.w * 65536 / (x1 - x0);
		int xps0 = (xp0 - x0) * xpsa;

		int ypsa = (1) * bitmap.h * 65536 / (y1 - y0);
		int yps0 = (yp0 - y0) * ypsa;

		if (xMouse >= xp0 && xMouse < xp1 && yMouse >= yp0 && yMouse < yp1)
		{
			int mpp = xMouse + yMouse * w;
			for (int yp = yp0; yp < yp1; yp++)
			{
				int pp = yp * w;
				int xps = xps0;
				int yps = (yps0 >> 16) * bitmap.w;

				for (int xp = xp0; xp < xp1; xp++)
				{
					int sourceCol = bitmap.pixels[(xps >> 16) + yps];
					if (sourceCol < 0)
					{
						if (tint > 0)
						{
							sourceCol = ((sourceCol & 0xfefefe) + (tint & 0xfefefe)) / 2;
						}
						if (pp + xp == mpp)
						{
							hr_clickListener = clickListener;
							hr_x = xps >> 16;
							hr_y = yps / bitmap.w;
						}
						zBuffer[pp + xp] = 0;
						if (sourceCol != 0xffff00ff)
							pixels[pp + xp] = sourceCol;
					}
					xps += xpsa;
				}

				yps0 += ypsa;
			}
		} else
		{
			for (int yp = yp0; yp < yp1; yp++)
			{
				int pp = yp * w;
				int xps = xps0;
				int yps = (yps0 >> 16) * bitmap.w;

				for (int xp = xp0; xp < xp1; xp++)
				{
					int sourceCol = bitmap.pixels[(xps >> 16) + yps];
					if (sourceCol < 0)
					{
						if (tint > 0)
						{
							sourceCol = ((sourceCol & 0xfefefe) + (tint & 0xfefefe)) / 2;
						}
						zBuffer[pp + xp] = 0;
						if (sourceCol != 0xffff00ff)
							pixels[pp + xp] = sourceCol;
					}
					xps += xpsa;
				}

				yps0 += ypsa;
			}
		}
	}

	int lcr = 0;
	public int tint;

	public void show()
	{
		lcr = 0;
		for (int i = 0; i < w * h; i++)
		{
			int col = pixels[i];
			int dist = (zBuffer[i] * shadeBuffer[i]) / 300000;
			if (dist < 1)
				dist = 1;
			if (dist > 1000)
				dist = 1;

			int br = 23000 / dist + blacknessFactor - dist / 5;
			if (br < 0)
				br = 0;
			if (br > 256)
				br = 256;

			// lcr = lcr * lcr * 797311 + lcr + 2;
			br = br * (((lcr >> 16) & 3) * 3 + 91) / 100;

			int r = (((col >> 16) & 0xff) * br);
			int g = (((col >> 8) & 0xff) * br);
			int b = (((col) & 0xff) * br);

			r = (r) / 255;
			g = (g) / 255;
			b = (b) / 255;

			if (r > 255)
			{
				r = 255;
			}
			if (g > 255)
			{
				g = 255;
			}
			if (b > 255)
			{
				b = 255;
			}

			pixels[i] = r << 16 | g << 8 | b;
		}

		BufferStrategy bs = canvas.getBufferStrategy();
		if (bs == null)
		{
			canvas.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, w * scale, h * scale, null);
		g.dispose();
		bs.show();
	}

	public boolean handleClick(Level level, int button)
	{
		if (hr_clickListener != null)
		{
			return hr_clickListener.click(level, hr_sprite, hr_x, hr_y, button);
		}
		return false;
	}
}
