package com.mojang.ld19.gui;

public enum SlotType {
	all("Backpack", 0),
	helmet("Helmet", 1),
	chestplate("Chestplate", 2),
	leggings("Leggings", 3),
	boots("Boots", 4),
	hands("Hands", 5);
	
	public final String slotName;
	public final int slotID;
	
	private SlotType(String name, int id)
	{
		slotName = name;
		slotID = id;
	}
}
