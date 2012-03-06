package com.mojang.ld19;

import java.util.HashMap;

import com.mojang.ld19.level.object.*;
import com.mojang.ld19.magic.*;
import com.mojang.ld19.mob.*;

public class EntityList 
{
	public static void init()
	{
		addEntity(0, SmallMob.class);
		addEntity(1, Skeleton.class);
		addEntity(2, Ghoul.class);
		addEntity(3, Goblin.class);
		addEntity(4, Zombie.class);
		addEntity(5, GoblinArcher.class);
		addEntity(6, Arrow.class);
		addEntity(7, ArrowEnemy.class);
		addEntity(8, BaseSpell.class);
		addEntity(9, ItemEntity.class);
		addEntity(10, KeyHole.class);
		addEntity(11, Switch.class);
		addEntity(12, ThrownItem.class);
		addEntity(13, Spider.class);
		addEntity(14, GhoulSpell.class);
		addEntity(15, PlayerSpell.class);
		addEntity(16, PoisonCloudProjectile.class);
		addEntity(17, PoisonCloud.class);
		addEntity(18, FireDemon.class);
		addEntity(19, FireDemonSpell.class);
		addEntity(20, KnifeEntity.class);
		addEntity(21, Imp.class);
		addEntity(22, ImpSpell.class);
		addEntity(23, MagmaBallProjectile.class);
		addEntity(24, MarkedBrick.class);
	}
	
	@SuppressWarnings("unchecked")
	public static void addEntity(int index, Object entity)
	{
		entityList.put(index, entity);
		inverseEntityList.put(entity, index);
	}
	
	public static Integer lookupID(Object entity)
	{
		return (Integer)inverseEntityList.get(entity);
	}
	
	@SuppressWarnings("rawtypes")
	public static Class getEntity(int lookup)
	{
		return (Class)entityList.get(lookup);
	}
	
	@SuppressWarnings("rawtypes")
	public static HashMap entityList = new HashMap();
	@SuppressWarnings("rawtypes")
	public static HashMap inverseEntityList = new HashMap();
}
