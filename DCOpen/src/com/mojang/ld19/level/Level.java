package com.mojang.ld19.level;

import java.util.*;
import java.io.*;
import com.mojang.ld19.*;
import com.mojang.ld19.display.Bitmap;
import com.mojang.ld19.item.Item;
import com.mojang.ld19.level.noise.*;
import com.mojang.ld19.level.object.Switch;
import com.mojang.ld19.level.walls.Walls;
import com.mojang.ld19.mob.*;
import com.mojang.ld19.level.object.*;

public class Level
{
	private int w, h;
	private Tile[] tiles;
	private Tile waterTile = new BlockingTile(Walls.ocean);

	public Player player;
	public Level replaceWith;
	public int layer;

	public ArrayList<Switch> specialSwitches = new ArrayList<Switch>();

	// public boolean loading = false;

	public Level(Bitmap bitmap, Bitmap itemLayout, Player player, int layer,
			boolean loading)
	{
		w = bitmap.w;
		h = bitmap.h;

		int xw = 0, xh = 0;

		if (!loading)
		{
			xw = itemLayout.w;
			xh = itemLayout.h;
		}
		tiles = new Tile[w * h];

		this.layer = layer;

		int xSpawn = 0;
		int ySpawn = 0;

		// Random r = new Random();

		for (int x = 0; x < w; x++)
		{
			for (int y = 0; y < h; y++)
			{
				int col = bitmap.pixels[(x) + y * w] & 0xffffff;
				int id = 255 - ((bitmap.pixels[x + y * w] >> 24) & 0xff);

				if (col == 0xffffff)
				{
					xSpawn = x;
					ySpawn = y;
					col = bitmap.pixels[(x) + (y - 1) * w] & 0xffffff;
				}
				Tile tile = EmptyTile.instance;
				if (col == 0x00ff00)
					tile = new Tile(Walls.grass);
				if (col == 0x00aa00)
					tile = new BlockingTile(Walls.tree);
				if (col == 0x0000ff)
					tile = new BlockingTile(Walls.water);
				if (col == 0x0000B5)
					tile = new BlockingTile(Walls.ocean);
				if (col == 0x999999)
					tile = new WallTile(Walls.wall);
				if (col == 0x777777)
					tile = new Tile(Walls.dungeonFloor);
				if (col == 0xF0FF00)
					tile = new Tile(Walls.grassFlowers);
				if (col == 0xFE00FF)
					tile = new DirtStairsTile(-1);
				if (col == 0xFF00FF)
					tile = new StairsTile(-1);
				if (col == 0xFF00FE)
					tile = new StairsTile(1);
				if (col == 0xAF4F00)
					tile = new DoorTile(Walls.woodenDoor, id);
				if (col == 0x00FFFF)
					tile = new LockedDoorTile(Walls.ironDoor, id);
				if (col == 0xF06D00)
					tile = new Tile(Walls.dirtPath);
				if (col == 0x808080)
					tile = new Tile(Walls.stonePath);
				if (col == 0x222222)
					tile = new BlockingTile(Walls.lightpost);
				if (col == 0x404040)
					tile = new WallTile(Walls.village);
				if (col == 0x80ffFF)
					tile = new VillageTeleporter();
				if (col == 0xFF8000)
					tile = new WallTile(Walls.hellstone);
				if (col == 0xFF4000)
					tile = new Tile(Walls.hellfloor);
				if (col == 0xFF2000)
					tile = new VillageTeleporter();

				if ((col & 0xffff00) == 0xff0000 && tile == EmptyTile.instance)
				{
					tile = getSpecialTile(x, y, id);
				}

				tile.init(this, x, y);
				tiles[x + y * w] = tile;
			}
		}

		if (!loading)
		{
			for (int x = 0; x < xw; x++)
			{
				for (int y = 0; y < xh; y++)
				{
					int col = itemLayout.pixels[(x) + y * xw] & 0xffffff;
					int id = 255 - ((itemLayout.pixels[x + y * xw] >> 24) & 0xff);

					Entity entityToAdd = null;

					// System.out.printf("%d\n", id);

					if (col == 0x800080)
						entityToAdd = new SmallMob();
					if (col == 0x0000FF)
						entityToAdd = new ItemEntity(Item.potion);
					if (col == 0x804000)
						entityToAdd = new ItemEntity(Item.armorLeather);
					if (col == 0xffffff)
						entityToAdd = new ItemEntity(Item.arrow);
					if (col == 0x808080)
						entityToAdd = new ItemEntity(Item.bow);
					if (col == 0x808000)
						entityToAdd = new ItemEntity(Item.helmLeather);
					if (col == 0x807F00)
						entityToAdd = new ItemEntity(Item.legsLeather);
					if (col == 0xc0c0c0)
						entityToAdd = new Skeleton();
					if (col == 0x404040)
						entityToAdd = new ItemEntity(Item.superBow);
					if (col == 0x8000FF)
						entityToAdd = new ItemEntity(Item.superPotionl1);
					if (col == 0xFF0000)
						entityToAdd = new Switch(0, id);
					if (col == 0x00FF00)
						entityToAdd = new KeyHole(0, id, Item.blueKey);
					if (col == 0x000080)
						entityToAdd = new ItemEntity(Item.blueKey);
					if (col == 0xFF80FF)
						entityToAdd = new GoblinArcher();
					if (col == 0x008000)
						entityToAdd = new Goblin();
					if (col == 0xFFFF00)
						entityToAdd = new Zombie();
					if (col == 0xff8000)
						entityToAdd = new Ghoul();
					if (col == 0x80A000)
						entityToAdd = new ItemEntity(Item.spellbook);
					if (col == 0x008080)
						entityToAdd = new ItemEntity(Item.poisonCure);
					if (col == 0x400000)
						entityToAdd = new ItemEntity(Item.potionMP);
					if (col == 0x400040)
						entityToAdd = new Spider();
					if (col == 0xa08040)
						entityToAdd = new ItemEntity(Item.getScroll(id));
					if (col == 0xFFA000)
						entityToAdd = new FireDemon();
					if (col == 0xFF7C00)
						entityToAdd = new Imp();
					if (col == 0xFF2020)
						entityToAdd = new GemHolder(id, Item.cyanGem);
					if (col == 0xFF2030)
						entityToAdd = new GemHolder(id, Item.pinkGem);
					if (col == 0xFF2040)
						entityToAdd = new GemHolder(id, Item.blueGem);
					if (col == 0xFF2050)
						entityToAdd = new GemHolder(id, Item.redGem);
					if (col == 0xFF2060)
						entityToAdd = new GemHolder(id, Item.greenGem);
					if (col == 0xFF0020)
						entityToAdd = new ItemEntity(Item.cyanGem);
					if (col == 0xFF0030)
						entityToAdd = new ItemEntity(Item.pinkGem);
					if (col == 0xFF0040)
						entityToAdd = new ItemEntity(Item.blueGem);
					if (col == 0xFF0050)
						entityToAdd = new ItemEntity(Item.redGem);
					if (col == 0xFF0060)
						entityToAdd = new ItemEntity(Item.greenGem);

					if (entityToAdd != null && !(entityToAdd instanceof Switch))
					{
						int corner = 0;
						double posx = x / 2d;
						double posy = y / 2d;
						if (posx - (x / 2) > 0 && posy - (y / 2) > 0)
						{
							corner = 3;
						} else if (posx - (x / 2) > 0)
						{
							corner = 1;
						} else if (posy - (y / 2) > 0)
						{
							corner = 2;
						}
						if (entityToAdd.perfersToBeCentered == true)
						{
							corner = -1;
						}
						tiles[(x / 2) + (y / 2) * (xw / 2)].addEntity(corner,
								entityToAdd);
					} else if (entityToAdd instanceof Switch)
					{
						double switchposx = 0.0;
						double switchposz = 0.0;
						int corner = 0;
						double posx = x / 2d;
						double posy = y / 2d;
						if (posx - (x / 2) > 0 && posy - (y / 2) > 0)
						{
							corner = 3;
						} else if (posx - (x / 2) > 0)
						{
							corner = 1;
						} else if (posy - (y / 2) > 0)
						{
							corner = 2;
						}
						switch (corner)
						{
						case 0:
							switchposx = 0.5;
							switchposz = 0.1;
							break;
						case 2:
							switchposx = 0.1;
							switchposz = 0.5;
							break;
						case 1:
							switchposx = 0.5;
							switchposz = 0.9;
							break;
						case 3:
							switchposx = 0.9;
							switchposz = 0.5;
							break;
						}
						Switch switchToAdd = (Switch) entityToAdd;
						switchToAdd.setPosition(switchposx, 0.4, switchposz);
						tiles[(x / 2) + (y / 2) * (xw / 2)].addEntity(-1,
								switchToAdd);
					}
				}
			}

			if (player == null)
				player = new Player(this, xSpawn, ySpawn);
		}
		this.player = player;
	}

	public Level(Player player, int layer, boolean loading)
	{
		Random r = new Random();
		
		OctavesNoiseGen noise = new OctavesNoiseGen(r, 8);

		w = 512;
		h = 512;

		tiles = new Tile[w * h];

		this.layer = layer;

		int xSpawn = 0;
		int ySpawn = 0;
		
		int tilecount = 0;
		double average = 0.0;

		for (int x = 0; x < w; x++)
		{
			for (int y = 0; y < h; y++)
			{
				Tile tile;
				double noiseval = noise.noise(x / 32d, y / 32d);
				if (noiseval >= 0.0)
				{
					tile = new Tile(Walls.grass);
				}
				else
				{
					tile = new BlockingTile(Walls.water);
				}
				
				average += noiseval;

				tile.init(this, x, y);
				tiles[x + y * w] = tile;
				tilecount++;
			}
		}
		
		System.out.printf("average: %f\n", average / (double)tilecount);
		
		if (player == null)
			player = new Player(this, 0, 0);
		
		int rx = r.nextInt(512); int ry = r.nextInt(512);
		
		while (getTile(rx, ry).blocks(player))
		{
			rx = r.nextInt(512); ry = r.nextInt(512);
		}
		
		player.x = player.l_x = rx; player.z = player.l_z = ry;

		this.player = player;
	}

	public Level(Bitmap bitmap, Bitmap objects, Player player, int layer)
	{
		this(bitmap, objects, player, layer, false);
	}

	private Tile getSpecialTile(int x, int y, int val)
	{
		Tile tile = EmptyTile.instance;
		if (layer == 0)
		{
			tile = new Tile(Walls.grass);
			Switch sw = new Switch(0, val);
			sw.setPosition(0.2, 0.4, 0.9);
			tile.addEntity(-1, sw);
		}
		if (layer == -1)
		{
			tile = new Tile(Walls.dungeonFloor);
			Switch sw = new Switch(0, val);
			sw.setPosition(0.5, 0.4, 0.9);
			tile.addEntity(-1, sw);
		}
		return tile;
	}

	public Tile getTile(int x, int y)
	{
		if (x < 0 || y < 0 || x >= w || y >= h)
		{
			waterTile.x = x;
			waterTile.y = y;
			return waterTile;
		}

		return tiles[x + y * w];
	}

	public void tick()
	{
		player.tick();
		List<Entity> toTick = new ArrayList<Entity>();
		int r = 7;
		for (int x = -r; x < r; x++)
		{
			for (int y = -r; y < r; y++)
			{
				Tile tile = getTile(player.x + x, player.z + y);
				tile.addToTick(toTick);
				tile.tick();
			}
		}

		for (int i = 0; i < toTick.size(); i++)
		{
			Entity e = toTick.get(i);
			e.tick(this);
		}
	}

	/**
	 * Triggers all tiles with the specified ID
	 * 
	 * @param id
	 *            The tile ID to trigger
	 * @param pressed
	 *            True when the button is down
	 */
	public void trigger(int id, boolean pressed)
	{
		for (int y = 0; y < h; y++)
		{
			for (int x = 0; x < w; x++)
			{
				Tile b = tiles[x + y * w];
				if (b.id == id)
				{
					b.trigger(pressed);
				}
			}
		}
	}

	/**
	 * Checks if all special switches are triggered on the world, triggers all
	 * tiles with specified id if true
	 * 
	 * @param id
	 *            The id of the switches to check
	 */
	public void checkIfShouldTrigger(int id)
	{
		for (int x = 0; x < specialSwitches.size(); x++)
		{
			Switch theSwitch = specialSwitches.get(x);
			if (theSwitch.switchID == id)
			{
				if (theSwitch.pressed == false)
				{
					return;
				}
			}
		}
		trigger(id, true);
	}

	private static Map<Integer, Level> loadedMaps = new HashMap<Integer, Level>();

	public static Level get(int layer, Player player)
	{
		Level level = loadedMaps.get(layer);
		if (level == null)
		{
			level = new Level(Art.load("/res/map/layer_" + layer + ".png"),
					Art.load("/res/map/items_" + layer + ".png"), player, layer);
			loadedMaps.put(layer, level);
		}
		return level;
	}

	public void init()
	{
		if (layer >= 0)
		{
			Art.brick.replaceWith(Art.normalBrick);
		} else if (layer < 0)
		{
			Art.brick.replaceWith(Art.mossBrick);
		}
		if (player.location != null)
		{
			player.location.removeEntity(-1, player);
		}
		getTile(player.x, player.z).addEntity(-1, player);
	}

	public static void clearLevels()
	{
		for (int x = -2; x <= 1; x++)
		{
			if (loadedMaps.get(x) != null)
			{
				loadedMaps.put(x, null);
			}
		}
	}

	public void saveAllOpenLevels(RandomAccessFile savefile) throws IOException
	{
		System.out.printf("Entering map saver at %d!\n",
				savefile.getFilePointer());
		int mapsToSave = loadedMaps.size();
		savefile.writeInt(mapsToSave);

		saveMap(1, savefile);
		saveMap(0, savefile);
		saveMap(-1, savefile);
		saveMap(-2, savefile);
		saveMap(-100, savefile);
	}

	public void saveMap(int layer, RandomAccessFile savefile)
			throws IOException
	{
		System.out.printf("Saving map %d at %d!\n", layer,
				savefile.getFilePointer());
		Level level = loadedMaps.get(layer);
		if (level == null)
		{
			return;
		}
		savefile.writeInt(layer);
		for (int x = 0; x < level.w; x++)
		{
			for (int y = 0; y < level.h; y++)
			{
				if (x == 0 && y == 0)
				{
					System.out.printf("Entering first tile at %d!\n",
							savefile.getFilePointer());
				}
				Tile tileToSave = level.getTile(x, y);
				tileToSave.saveTile(savefile);
			}
		}
	}

	public static void loadMap(int layer, Player player,
			RandomAccessFile loadfile) throws IOException
	{
		Level level = new Level(Art.load("/res/map/layer_" + layer + ".png"),
				null, player, layer, true);
		for (int x = 0; x < level.w; x++)
		{
			for (int y = 0; y < level.h; y++)
			{
				if (x == 0 && y == 0)
				{
					System.out.printf("Entering first tile at %d!\n",
							loadfile.getFilePointer());
				}
				Tile tileToSave = level.getTile(x, y);
				tileToSave.loadTile(loadfile);
			}
		}
		level.init();
		loadedMaps.put(layer, level);
	}

	public static void loadAllOpenLevels(Player player,
			RandomAccessFile loadfile) throws IOException
	{
		System.out.printf("Entering map loader at %d!\n",
				loadfile.getFilePointer());
		int mapsToLoad = loadfile.readInt();

		for (int x = 0; x < mapsToLoad; x++)
		{
			System.out.printf("Loading a map at %d!\n",
					loadfile.getFilePointer());
			int layer = loadfile.readInt();
			System.out.printf("Looks like it's map %d!\n", layer);
			loadMap(layer, player, loadfile);
		}
	}
}
