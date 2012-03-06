package com.mojang.ld19;

public enum PlayerSkill 
{
	MELEE("Melee"),
	MAGIC("Magic"),
	SNEAK("Sneaking"),
	ARCHERY("Archery");
	public String name;
	
	private PlayerSkill(String name)
	{
		this.name = name;
	}
}
