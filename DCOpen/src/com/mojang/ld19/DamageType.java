package com.mojang.ld19;

public enum DamageType 
{
	HACK("Physical", true, true),
	BLUNT("Blunt-Force", true, true),
	ARROW("Arrowed", true, true),
	MAGIC("Magic", true, false),
	POISON("Poison", false, false),
	POISON_DIRECT("Direct Poison", false, false),
	FIRE("Fire", true, false);
	
	public String name;
	public boolean normal;
	public boolean physical;
	
	private DamageType(String name, boolean normal, boolean physical)
	{
		this.name = name;
		this.normal = normal;
		this.physical = physical;
	}
}
