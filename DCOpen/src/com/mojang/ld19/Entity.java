package com.mojang.ld19;

import java.util.Random;
import java.io.*;
import com.mojang.ld19.display.*;
import com.mojang.ld19.level.*;

public class Entity implements ClickListener {
    protected static Random random = new Random();

    public SpriteBody sprites = new SpriteBody();
    public Tile location;
    public int corner;
    public double x, y, z;
    public int hurtTime = 0;
    public boolean canTakeDamage = false;
    public boolean perfersToBeCentered = false;
    public String name = "Entity";
    public boolean isProjectile = false;
    
    //public HashMap<Integer, Entity> entityList = new HashMap();

    public int tint;
    
    public Entity()
    {
    	//System.out.printf("adding entity to %d!\n", getID());
    	//entityList.put(getID(), this);
    }

    /**
     * Default handler for mouse clicks on this Entity
     */
    public boolean click(Level level, Sprite sprite, int x, int y, int button) 
    {
        return false;
    }

    /**
     * Removes the entity from its current location
     */
    protected void remove() 
    {
        location.removeEntity(corner, this);
    }

    /**
     * Called each tick, where the entity should do most things
     * @param level The level this entity exists in
     */
    public void tick(Level level) {
        if (hurtTime > 0) {
            hurtTime--;
        }
    }

    /**
     * Checks if the entity should block other entities
     * @return If the entity is solid or not
     */
    public boolean isSolid() {
        return false;
    }

    /**
     * Basic function for harming the entity
     * @return whether the attack did damage or not
     */
    public boolean hurt() 
    {
    	return false;
    }
    
    /**
     * Harms an entity, based on how the entity should be harmed
     * @param damage How much damage should be done
     * @param bonus The bonus applied to the armor calculation
     * @param damagetype The damage type of the attack
     * @return Whether the attack worked or not
     */
    public boolean hurt(int damage, int bonus, DamageType damagetype)
    {
    	return false;
    }
    
    /**
     * Saves the entity to disk
     * @param savefile The file to save the entity to
     * @throws IOException
     */
    public void save(RandomAccessFile savefile)
    	throws IOException
    {
    	savefile.writeInt(getID());
    	//System.out.printf("Trying to save a %s at %d!\n", this.name, savefile.getFilePointer());
    	if (getID() == -1)
    	{
    		return;
    	}
    	savefile.writeInt(corner);
    	savefile.writeDouble(x);
    	savefile.writeDouble(y);
    	savefile.writeDouble(z);
    	savefile.writeInt(hurtTime);
    }
    
    /**
     * Creates a new instance of the entity
     * @param loadfile The file to load the entity's state from
     * @return The replicated entity
     * @throws IOException
     */
    public static Entity staticload(RandomAccessFile loadfile)
    	throws IOException
	{
    	int loadID = loadfile.readInt();
    	
    	if (loadID == -1)
    	{
    		return null;
    	}
    	
    	Entity finalEntity = null; 
    	
    	@SuppressWarnings("rawtypes")
		Class entClass = EntityList.getEntity(loadID);
    	try 
    	{
			finalEntity = (Entity)entClass.newInstance();
		} 
    	catch (InstantiationException e) 
		{
			e.printStackTrace();
		} 
    	catch (IllegalAccessException e) 
		{
			e.printStackTrace();
		}
    	
    	if (finalEntity != null)
    	{
    		//System.out.printf("Trying to save a %s at %d!\n", finalEntity.name, loadfile.getFilePointer());
        	finalEntity.corner = loadfile.readInt();
        	finalEntity.x = loadfile.readDouble();
        	finalEntity.y = loadfile.readDouble();
        	finalEntity.z = loadfile.readDouble();
        	finalEntity.hurtTime = loadfile.readInt();
    		finalEntity.load(loadfile);
    	}
    	return finalEntity;
	}
    
    /**
     * Non-static loading function, unused in practice
     * @param loadfile The file to load the entity from
     * @throws IOException
     */
    public void load(RandomAccessFile loadfile)
    	throws IOException
    {
    }
    
    /**
     * Generic function for inflicting status effects
     * @param status The status ID to apply
     */
    public void inflictStatus(int status)
    {
    }
    
    /**
     * Returns the ID of the entity. Critical for saving under all circumstances
     * @return The Entity's ID
     */
    public int getID()
    {
    	return -1;
    }
}
