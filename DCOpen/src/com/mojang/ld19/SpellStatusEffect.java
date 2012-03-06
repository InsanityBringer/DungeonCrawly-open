package com.mojang.ld19;

public enum SpellStatusEffect 
{
	DAMAGE("Damage"),
	SHOCK("Shock"),
	STUN("Stun"),
	CHARM("Charm");
	
	public String name;
	private SpellStatusEffect(String name)
	{
		this.name = name;
	}
}
