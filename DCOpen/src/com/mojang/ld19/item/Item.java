package com.mojang.ld19.item;

import com.mojang.ld19.Art;
import com.mojang.ld19.display.Bitmap;
import com.mojang.ld19.level.Level;
import com.mojang.ld19.magic.SpellListEntry;

import java.util.HashMap;
import java.util.Random;

//@SuppressWarnings()

public class Item 
{
    @SuppressWarnings("rawtypes")
	public static HashMap itemList = new HashMap();
    public static HashMap scrolls = new HashMap();
	
	public static Item potion = new Item(Art.itemSheet[0 + 0 * 10], 5, "Potion").setID(1);
    public static Item sword = new Sword(Art.itemSheet[1 + 0 * 10], 5, 1, 6, 0, "Short Sword").setID(2);
    public static Item cursedSword = new Sword(Art.itemSheet[4 + 0 * 10], 5, 1, 1, -6, "Cursed Sword").setID(3);
    public static Item superSword = new Sword(Art.itemSheet[5 + 0 * 10], 5, 2, 6, 3, "SuperSword").setID(4);
    public static Item helmLeather = new Armor(Art.itemSheet[0 + 2 * 10], 1, 2, "Leather Helm").setID(5);
    public static Item armorLeather = new Armor(Art.itemSheet[1 + 2 * 10], 2, 3, "Leather Armor").setID(6);
    public static Item legsLeather = new Armor(Art.itemSheet[2 + 2 * 10], 3, 2, "Leather Pants").setID(7);
    public static Item bootsLeather = new Armor(Art.itemSheet[3 + 2 * 10], 4, 1, "Leather Boots").setID(8);
    public static Item bow = new Bow(Art.itemSheet[3 + 0 * 10], 5, 10, 1, 6, 0, "Hunter's Bow").setID(9);
    public static Item arrow = new Item(Art.itemSheet[4 + 1 * 10], 0, "Arrow").setID(10);
    public static Item superBow = new Bow(Art.itemSheet[3 + 0 * 10], 5, 4, 2, 6, 3, "Elven Bow").setID(11);
    public static Item superPotionl1 = new SuperPotion(Art.itemSheet[0 + 1 * 10], 5, 1, "Mega Potion").setID(12);
    public static Item superPotionl2 = new SuperPotion(Art.itemSheet[3 + 1 * 10], 5, 2, "Mega Potion").setID(13);
    public static Item superPotionl3 = new SuperPotion(Art.itemSheet[1 + 1 * 10], 5, 3, "Mega Potion").setID(14);
    public static Item superPotionl4 = new SuperPotion(Art.itemSheet[2 + 1 * 10], 5, 4, "Mega Potion").setID(15);
    public static Item blueKey = new ItemKey(Art.itemSheet[4 + 2 * 10], 0, "Blue Key").setID(16);
    public static Item leatherShield = new Shield(Art.itemSheet[5 + 1 * 10], 5, 2, "Leather Buckler").setID(17);
    public static Item poisonCure = new AntiPoisonPotion(Art.itemSheet[5 + 2 * 10], 5, "Potion of Cure Poison").setID(18);
    public static Item spellbook = new Spellbook(Art.itemSheet[0 + 3 * 10], 5, "Spell Book").setID(19);
    public static Item potionMP = new MPPotion(Art.itemSheet[1 + 3 * 10], 5, "MP Potion").setID(20);
    public static Item scrollPoison = new Scroll(Art.itemSheet[2 + 3 * 10], 5, "Scroll of Poison Cloud", SpellListEntry.poisonCloud).setID(21).registerScroll(0);
    public static Item scrollHurt = new Scroll(Art.itemSheet[2 + 3 * 10], 5, "Scroll of Hurt Target", SpellListEntry.hurt).setID(22).registerScroll(1);
    public static Item knife = new KnifeThrowable(Art.itemSheet[3 + 3 * 10], 5, 1, 4, 0, "Knife").setID(23);
    public static Item cyanGem = new ItemGem(Art.itemSheet[0 + 9 * 10], 5, "Cyan Gem").setID(24);
    public static Item pinkGem = new ItemGem(Art.itemSheet[1 + 9 * 10], 5, "Pink Gem").setID(25);
    public static Item redGem = new ItemGem(Art.itemSheet[2 + 9 * 10], 5, "Red Gem").setID(26);
    public static Item blueGem = new ItemGem(Art.itemSheet[3 + 9 * 10], 5, "Blue Gem").setID(27);
    public static Item greenGem = new ItemGem(Art.itemSheet[4 + 9 * 10], 5, "Green Gem").setID(28);
    public static Item eScrollFire = new EnchantmentScroll(Art.itemSheet[2 + 3 * 10], 5, "Scroll of Fire Enchantment", 0).setID(29);
    public static Item eScrollSharpness = new EnchantmentScroll(Art.itemSheet[2 + 3 * 10], 5, "Scroll of Sharpness Enchantment", 1).setID(30);
    public static Item eScrollSniper = new EnchantmentScroll(Art.itemSheet[2 + 3 * 10], 5, "Scroll of Sniper Enchantment", 2).setID(31);
    public static Item eScrollDefend = new EnchantmentScroll(Art.itemSheet[2 + 3 * 10], 5, "Scroll of Defense Enchantment", 3).setID(32);
    
    public Bitmap icon;
    public int type = 0;
    public int wait = 0;
    public int useColor = 0x00ff00;
    public String itemName;
    public int itemID;

    protected Item(Bitmap icon, int itemType, String name) 
    {
        this.icon = icon;
        this.type = itemType;
        itemName = name;
    }
    
    /**
     * Ensures that all items are loaded
     */
    public static void init()
    {
    	System.out.printf("%d items added\n", itemList.size());
    }
    
    @SuppressWarnings("unchecked")
    /**
     * Registers the Item ID for saving
     * @param ID The unique ID of the item
     * @return The changed item
     */
	public Item setID(int ID)
    {
    	itemID = ID; 
    	itemList.put(ID, this);
    	return this;
    }
    
    /**
     * Gets an item from the specified
     * @param ID The id of the wanted item.
     * @return The item if found, null if the ID is unregistered
     */
    public static Item getItem(int ID)
    {
    	return (Item)itemList.get(ID);
    }
    
    /**
     * Registers an item into the special scrolls list
     * @param ID The id of the current scroll
     * @return The registered item
     */
    public Item registerScroll(int ID)
    {
    	scrolls.put(ID, this);
    	return this;
    }
    
    /**
     * Called when the item is right-clicked in the inventory
     * @param level The level that the player is in
     * @return The resultant item.
     */
    public Item onItemRightClick(Level level)
    {
    	Random r = new Random();
    	level.player.heal((r.nextInt(3)) + 4);
    	return null;
    }
    
    /**
     * Called when the item is equipped
     * @param level The level that the player is on
     */
    public void onItemEquipped(Level level)
    {
    }
    
    /**
     * Called when the item is unequiped
     * @param level The level the player is on
     */
    public void onItemRemoved(Level level)
    {
    }
    
    /**
     * Gets a scroll from the scrolls list
     * @param ID The ID of the scroll
     * @return The requested scroll
     */
    public static Item getScroll(int ID)
    {
    	return (Item)scrolls.get(ID);
    }
    
    /**
     * Gets the name of the item
     * @return The item name
     */
    public String getName()
    {
    	return itemName;
    }
}
