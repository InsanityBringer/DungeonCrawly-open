package com.mojang.ld19.level.walls;

import com.mojang.ld19.Art;
import com.mojang.ld19.display.SpriteBody;

public class Walls {
    public static SpriteBody wall = new Wall();
    public static SpriteBody dirtTunnel = new DirtTunnel();
    public static SpriteBody grass = new Grass(false);
    public static SpriteBody dirtPath = new SimpleFloor(Art.dirt);
    public static SpriteBody stonePath = new SimpleFloor(Art.stonePath);
    public static SpriteBody sand = new SimpleFloor(Art.sand);
    public static SpriteBody grassFlowers = new Grass(true);
    public static SpriteBody woodenDoor = new Door(Art.wood);
    public static SpriteBody tree = new Tree();
    public static SpriteBody water = new Water(Art.water, 0.3);
    public static SpriteBody ocean = new Water(Art.ocean, 1.5);
    public static SpriteBody dungeonFloor = new DungeonFloor();
    public static SpriteBody ironDoor = new Door(Art.iron);
    public static SpriteBody lightpost = new LightPost();
    public static SpriteBody village = new VillageHouse();
    public static SpriteBody portal = new Portal();
    public static SpriteBody hellstone = new WallCustom(Art.hellStone);
    public static SpriteBody hellfloor = new SimpleFloor(Art.hellStone);

    public static void tick() 
    {
        ocean.update();
        water.update();
        tree.update();
        portal.update();
    }
}
