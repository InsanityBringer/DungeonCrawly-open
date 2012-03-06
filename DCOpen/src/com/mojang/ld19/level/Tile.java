package com.mojang.ld19.level;

import java.util.*;
import java.io.*;
import com.mojang.ld19.*;
import com.mojang.ld19.display.*;
import com.mojang.ld19.level.object.ItemEntity;
import com.mojang.ld19.level.object.Arrow;
import com.mojang.ld19.magic.PoisonCloud;

public class Tile implements ClickListener {
    protected static Random random = new Random();

    public SpriteBody sprites;
    @SuppressWarnings("unchecked")
    public List<Entity>[] corners = new List[4];
    public List<Entity> entities = new ArrayList<Entity>();
    public int x, y;
    public Level level;
    public int id = 0;

    public Tile(SpriteBody sprites) {
        this.sprites = sprites;
        for (int i = 0; i < 4; i++) {
            corners[i] = new ArrayList<Entity>();

        }

        /*if (this.canContainItems()) {
            while (random.nextInt(2) != 0) {
                addEntity(random.nextInt(4), new ItemEntity(Item.potion));
                addEntity(random.nextInt(4), new ItemEntity(Item.sword));
                addEntity(random.nextInt(4), new ItemEntity(Item.helmLeather));
                addEntity(random.nextInt(4), new ItemEntity(Item.armorLeather));
                addEntity(random.nextInt(4), new ItemEntity(Item.bootsLeather));
                addEntity(random.nextInt(4), new ItemEntity(Item.legsLeather));
                addEntity(random.nextInt(4), new ItemEntity(Item.bow));
                addEntity(random.nextInt(4), new ItemEntity(Item.cursedSword));
                addEntity(random.nextInt(4), new ItemEntity(Item.superSword));
            }
        }*/

        /*for (int i = 0; i < 4; i++) {
            if (random.nextInt(30) == 0) {
                Mob mob = new Mob();
                if (!blocks(mob, i)) {
                    addEntity(i, mob);
                }
            }
        }*/
    }

    public void init(Level level, int x, int y) 
    {
        this.level = level;
        this.x = x;
        this.y = y;
    }
    
    public void saveTile(RandomAccessFile savefile)
    	throws IOException
    {
    	for (int x = 0; x < 4; x++)
    	{
    		int len = corners[x].size();
    		savefile.writeInt(len);
    		if (len > 0)
    		{
        		//System.out.printf("entering corner saving function %d at %d, with len of %d!\n", x, savefile.getFilePointer(), len);
    		}
    		for (int i = 0; i < len; i++)
    		{
    			Entity entityToSave = corners[x].get(i);
    			entityToSave.save(savefile);
    		}
    	}
    	int centeredLen = entities.size();
    	savefile.writeInt(centeredLen);
		if (centeredLen > 0)
		{
			//System.out.printf("entering center loading function at %d, with len of %d!\n", savefile.getFilePointer(), centeredLen);
		}
    	for (int i = 0; i < centeredLen; i++)
    	{
    		Entity entityToSave = entities.get(i);
    		entityToSave.save(savefile);
    	}
    }
    
    public void loadTile(RandomAccessFile savefile)
    	throws IOException
	{
    	for (int x = 0; x < 4; x++)
    	{
    		int len = savefile.readInt();
    		if (len > 0)
    		{
    			//System.out.printf("entering corner loading function %d at %d, with len of %d!\n", x, savefile.getFilePointer(), len);
    		}
    		for (int i = 0; i < len; i++)
    		{
    			Entity entityToAdd = Entity.staticload(savefile);
    			if (entityToAdd != null)
    			{
    				addEntity(entityToAdd.corner, entityToAdd);
    			}
    		}
    	}
    	int centeredLen = savefile.readInt();
    	for (int i = 0; i < centeredLen; i++)
    	{
    		if (centeredLen > 0)
    		{
    			//System.out.printf("entering center loading function at %d, with len of %d!\n", savefile.getFilePointer(), centeredLen);
    		}
    		Entity entityToAdd = Entity.staticload(savefile);
			if (entityToAdd != null)
			{
				addEntity(-1, entityToAdd);
			}
    	}
	}

    public void removeEntity(int corner, Entity entity) {
        if (corner == -1) {
            if (entities.remove(entity)) {
                entity.location = null;
            }
        } else if (corners[corner].remove(entity)) {
            entity.location = null;
        }
    }

    public void addEntity(int corner, Entity entity) {
        if (corner == -1) {
            entities.add(entity);
            entity.location = this;
            entity.corner = corner;
        } else {
            corners[corner].add(entity);
            entity.location = this;
            entity.corner = corner;
        }
    }

    public boolean blocks(Entity e) {
        return false;
    }
    
    public void damageEverythingEver(DamageType damagetype, int damage, int bonus)
    {
    	for (int x = 0; x < 4; x++)
    	{
    		int len = corners[x].size();
    		for (int i = 0; i < len; i++)
    		{
    			corners[x].get(i).hurt(damage, bonus, damagetype);
    		}
    	}
    	int len = entities.size();
		for (int i = 0; i < len; i++)
		{
			entities.get(i).hurt(damage, bonus, damagetype);
		}
    }
    
    public void inflictStatusOnAll(int status)
    {
    	for (int x = 0; x < 4; x++)
    	{
    		int len = corners[x].size();
    		for (int i = 0; i < len; i++)
    		{
    			corners[x].get(i).inflictStatus(status);
    		}
    	}
    	int len = entities.size();
		for (int i = 0; i < len; i++)
		{
			entities.get(i).inflictStatus(status);
		}
    }
    
    public int containsMob()
    {
    	for (int x = 0; x < entities.size(); x++)
    	{
    		if (entities.get(x).canTakeDamage)
    		{
    			return -1;
    		}
    	}
    	for (int c = 0; c < 4; c++)
    	{
    		for (int x = 0; x < corners[c].size(); x++)
    		{
        		if (corners[c].get(x).canTakeDamage)
        		{
        			return c;
        		}
    		}
    	}
    	return -2;
    }
    
    public boolean containsPlayer(Level level)
    {
    	for (int x = 0; x < entities.size(); x++)
    	{
    		if (entities.get(x) == level.player)
    		{
    			return true;
    		}
    	}
    	return false;
    }
    
    public boolean blocks(Entity e, int corner)
    {
    	return blocks(e, corner, true);
    }

    public boolean blocks(Entity e, int corner, boolean countPlayer) 
    {
    	if (countPlayer == false) return blocksPath(e, corner);
        if (blocks(e)) return true;
        if (e.isSolid()) 
        {
            for (int i = 0; i < entities.size(); i++) 
            {
                if (entities.get(i).isSolid() && !(entities.get(i) instanceof Player && !countPlayer)) 
                {
                    return true;
                }
            }
            if (corner == -1) 
            {
                for (int c = 0; c < 4; c++) 
                {
                    for (int i = 0; i < corners[c].size(); i++) {
                    	
                        if (corners[c].get(i).isSolid()) 
                        {
                            return true;
                        }
                    }
                }
            } 
            else
            {
                for (int i = 0; i < corners[corner].size(); i++) 
                {
                    if (corners[corner].get(i).isSolid()) return true;
                }
            }
        }
        return false;
    }
    
    public boolean blocksPath(Entity e, int corner) 
    {
        if (blocks(e)) return true;
        if (e.isSolid()) 
        {
            for (int i = 0; i < entities.size(); i++) 
            {
                if (entities.get(i).isSolid() && !(entities.get(i) instanceof Player))
                {
                    return true;
                }
                if (entities.get(i) instanceof PoisonCloud)
                {
                	return true;
                }
            }
            for (int c = 0; c < 4; c++) 
            {
                for (int i = 0; i < corners[c].size(); i++) {
                	
                    if (corners[c].get(i).isSolid()) 
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void render(Screen screen) 
    {
        screen.draw(sprites, x, 0, y, this);
        renderSprites(screen);
    }

    protected void renderSprites(Screen screen) {
        double ss = 1 / 12.0;
        for (int i = 0; i < 4; i++) {
            double xx = x + (i % 2 * 2 - 1) * 0.25 + 0.5 - ss;
            double zz = y + (i / 2 * 2 - 1) * 0.25 + 0.5 - ss;
            for (int j = 0; j < corners[i].size(); j++) {
                Entity e = corners[i].get(j);
                SpriteBody sb = e.sprites;
                if (sb != null) {
                    sb.rotate(-screen.camCos, screen.camSin, false);
                    screen.tint = e.tint;
                    screen.draw(sb, xx + e.x, 0.72 + e.y, zz + e.z, e);
                }
            }
        }

        for (int j = 0; j < entities.size(); j++) {
            Entity e = entities.get(j);
            SpriteBody sb = e.sprites;
            if (sb != null) {
                sb.rotate(-screen.camCos, screen.camSin, false);
                screen.tint = e.tint;
                screen.draw(sb, x + e.x - ss, e.y, y + e.z - ss, e);
            }
        }
        screen.tint = 0;
    }

    public boolean isWall() {
        return false;
    }

    public boolean canContainItems() {
        return !isWall();
    }

    public boolean click(Level level, Sprite sprite, int x, int y, int button) {
        if (canContainItems()) {
            if (sprite.y > 0.8 && level.player.carried != null) {
                int xs = (int) ((sprite.x) * 2);
                int ys = (int) ((sprite.z) * 2);
                int corner = -1;
                if (xs == 0 && ys == 0) corner = 0;
                if (xs == 1 && ys == 0) corner = 1;
                if (xs == 0 && ys == 1) corner = 2;
                if (xs == 1 && ys == 1) corner = 3;

                if (corner >= 0) {
                    addEntity(corner, new ItemEntity(level.player.carried));
                    level.player.carried = null;
                    return true;
                }
            }
        }
        return false;
    }

    public void addToTick(List<Entity> toTick) {
        toTick.addAll(entities);
        toTick.addAll(corners[0]);
        toTick.addAll(corners[1]);
        toTick.addAll(corners[2]);
        toTick.addAll(corners[3]);
    }

    public void stepOn(Player player) {
    }

    public void trigger(boolean on) {
    }

    public void tick() {
    }
    
    public boolean canProjectilePass()
    {
    	return true;
    }

    public boolean attack(Entity player, int rot, int damage, int bonus, DamageType damagetype) {
        if (attackCorner(player, -1, damage, bonus, damagetype)) return true;

        int start;
        int right;
        int backstart;
        int backright;
        if (rot == 2) {
            start = 2;
            right = 3;
            backstart = 0;
            backright = 1;
        } else if (rot == 1) {
            start = 0;
            right = 2;
            backright = 3;
            backstart = 1;
        } else if (rot == 0) {
            start = 1;
            right = 0;
            backstart = 2;
            backright = 3;
        } else {
            start = 3;
            right = 1;
            backstart = 2;
            backright = 0;
        }

        if (random.nextBoolean()) {
            int tmp = start;
            start = right;
            right = tmp;
        }
        if (attackCorner(player, start, damage, bonus, damagetype)) return true;
        if (attackCorner(player, right, damage, bonus, damagetype)) return true;
        if (player.isProjectile)
        {
            if (attackCorner(player, backstart, damage, bonus, damagetype)) return true;
            if (attackCorner(player, backright, damage, bonus, damagetype)) return true;
        }
        //        if (attackCorner(player, (start + next) & 3)) return;
        //        if (attackCorner(player, (start + next + right) & 3)) return;
        return false;
    }
    
 

    public boolean attackCorner(Entity player, int corner, int damage, int bonus, DamageType damagetype) {
        if (corner == -1) {
            for (int i = 0; i < entities.size(); i++) {
                Entity e = entities.get(i);
                if (e.isSolid()) 
                {
                    if (e.hurt(damage, bonus, damagetype) == false)
                    {
                    	return false;
                    }
                    return true;
                }
            }
        } else {
            for (int i = 0; i < corners[corner].size(); i++) {
                Entity e = corners[corner].get(i);
                if (e.isSolid()) 
                {
                    if (e.hurt(damage, bonus, damagetype) == false)
                    {
                    	return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
