package com.mojang.ld19;

public enum PlayerClass 
{
	FIGHTER("Fighter", PlayerSkill.MELEE, PlayerSkill.ARCHERY),
	THIEF("Thief", PlayerSkill.MELEE, PlayerSkill.SNEAK),
	MAGE("Mage", PlayerSkill.MAGIC, PlayerSkill.MELEE);
	
	public String name;
	public PlayerSkill primarySkill;
	public PlayerSkill secondarySkill;
	
	private PlayerClass(String name, PlayerSkill primarySkill, PlayerSkill secondarySkill)
	{
		this.name = name;
		this.primarySkill = primarySkill;
		this.secondarySkill = secondarySkill;
	}
}
