package com.mojang.ld19.magic;

import java.util.HashMap;

import com.mojang.ld19.item.Item;
import com.mojang.ld19.level.Level;
import com.mojang.ld19.level.object.Arrow;

public class SpellListEntry 
{
	public String name;
	public int id;
	public int MP;
	
	private static HashMap spellList = new HashMap();
	
	public static SpellListEntry poisonCloud = new SpellListEntry("Poison Cloud: 40", 0, 40);
	public static SpellHurtTarget hurt = new SpellHurtTarget("Hurt Target: 3", 1, 3);
	public static SpellListEntry magmaBall = new SpellMagmaBall("Magma Ball: 35", 2, 35);
	public static SpellListEntry fireElement = new SpellFireElement("Fire Storm: 5", 3, 5);
	public static SpellListEntry healStatus = new SpellClearStatus("Purge Statuses: 18", 4, 18);
	
	public SpellListEntry(String name, int id, int MP)
	{
		this.name = name;
		this.id = id;
		this.MP = MP;
		spellList.put(id, this);
	}
	
    public static SpellListEntry getItem(int ID)
    {
    	return (SpellListEntry)spellList.get(ID);
    }
    
    public static void init()
    {
    }
	
	public void cast(Level level)
	{
		if (level.player.MP < this.MP)
		{
			level.player.addString("Not enough MP to cast spell!");
			return;
		}
		level.getTile(level.player.x, level.player.z).addEntity(-1, new PoisonCloudProjectile(level.player.rot, 5, 1, 6, 0));
		level.player.MP -= this.MP;
	}
}
