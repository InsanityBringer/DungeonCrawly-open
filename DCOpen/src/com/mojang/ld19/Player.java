package com.mojang.ld19;

import java.io.*;

import com.mojang.ld19.display.Screen;
import com.mojang.ld19.gui.InventorySlot;
import com.mojang.ld19.item.Item;
import com.mojang.ld19.level.*;
import com.mojang.ld19.magic.MagicSkillsManager;
import com.mojang.ld19.magic.SpellListEntry;
import com.mojang.ld19.mob.Mob;

public class Player extends Entity {
    public static final int TURN_LEFT = 1;
    public static final int TURN_RIGHT = 2;
    public static final int MOVE_FORWARD = 3;
    public static final int MOVE_BACK = 4;
    public static final int MOVE_LEFT = 5;
    public static final int MOVE_RIGHT = 6;
    
    public static final int STATUS_POISON = 0;
    public static final int STATUS_HOLD = 1;
    public static final int STATUS_SLOW = 2;
    public static final int STATUS_MUTE = 3;
    public static final int STATUS_BLACKNESS = 4;
    public static final int STATUS_CHARM = 5;
    
    public static final int ENCHANT_FIRE = 0;
    public static final int ENCHANT_SHARP = 1;
    public static final int ENCHANT_SNIPER = 2;
    public static final int ENCHANT_DEFEND = 3;
    
    private int TICKS_PER_MOVE = 10;

    public int l_x, l_z;
    public int l_rot;

    public int x, z;
    public int rot;

    private int currentMove = -1;
    private int nextMove = -1;
    private int moveTime = 0;
    private int deathTime = 0;
    public Level level;
    private boolean collide = false;
    public Item carried;
    public int blackout;
    public int health, maxHealth;
    
    public int currentExp, expToNextLevel;
    public int totalExp;
    public int MP, MPMax;
    public int characterLevel;
    
    public int ticksAlive = 0;
    public int armorLevel = 0;

    private double hurtXa, hurtYa;
    private int hurtPow;
    public int arrows = 0;
    public int shieldAC = 0;
    
    public String characterName = "Player";
    public int statStr = 0;
    public int statInt = 0;
    public int statDex = 0;
    public int statCon = 0;
    
    public int skill1Points = 0;
    public int skill2Points = 0;
    public int skill3Points = 0;
    
    public PlayerClass currentClass = PlayerClass.FIGHTER;
    public PlayerSkill skill1 = PlayerSkill.MELEE;
    public PlayerSkill skill2 = PlayerSkill.ARCHERY;
    public PlayerSkill skill3 = PlayerSkill.MAGIC;
    
    boolean debug = false;
    
    public int currentSkillPoints = 0;
    
    public String[] currentStrings = new String[2];
    
    public int statusTime[] = new int[6];
    public int enchantmentTime[] = new int[6];
    
    public Mob holdInflictor = null; 
    
    public boolean isCasting = false;
    public boolean justOpenedSpellPanel = false;
    
    public SpellListEntry[][] playerSpells = new SpellListEntry[9][5];
    
    public Item[] quickInv = new Item[24];
    public Item[] backpack = new Item[24];

    public Player(Level level, int x, int z) 
    {
    	currentStrings[0] = ""; currentStrings[1] = "";
        this.level = level;
        l_x = this.x = x;
        l_z = this.z = z;
        l_rot = rot = 2;

        health = maxHealth = 15;
        currentExp = 0;
        expToNextLevel = 35;
        characterLevel = 1;
        
        MP = 15;
        MPMax = 15;
        
        name = "PlayerEntity";
        
        //registerSpell(SpellListEntry.magmaBall);
        
        //registerSpell(SpellListEntry.poisonCloud);
        //registerSpell(SpellListEntry.hurt);
        registerSpell(SpellListEntry.fireElement);
    }
    
    /**
     * Saves the player to the save file
     * @param savefile The file to save the player to
     * @throws IOException
     */
    public void savePlayer(RandomAccessFile savefile) throws IOException
    {
    	savefile.writeInt(l_x);
    	savefile.writeInt(l_z);
    	savefile.writeInt(l_rot);
    	savefile.writeInt(x);
    	savefile.writeInt(z);
    	savefile.writeInt(rot);
    	savefile.writeInt(currentMove);
    	savefile.writeInt(nextMove);
    	savefile.writeInt(moveTime);
    	savefile.writeInt(deathTime);
    	savefile.writeBoolean(collide);
    	savefile.writeInt(blackout);
    	savefile.writeInt(health);
    	savefile.writeInt(maxHealth);
    	savefile.writeInt(currentExp);
    	savefile.writeInt(expToNextLevel);
    	savefile.writeInt(totalExp);
    	savefile.writeInt(MP);
    	savefile.writeInt(MPMax);
    	savefile.writeInt(characterLevel);
    	savefile.writeInt(ticksAlive);
    	savefile.writeInt(armorLevel);
    	savefile.writeDouble(hurtXa);
    	savefile.writeDouble(hurtYa);
    	savefile.writeInt(hurtPow);
    	savefile.writeInt(arrows);
    	savefile.writeInt(shieldAC);
    	savefile.writeInt(statStr);
    	savefile.writeInt(statDex);
    	savefile.writeInt(statInt);
    	savefile.writeInt(statCon);
    	savefile.writeInt(skill1Points);
    	savefile.writeInt(skill2Points);
    	savefile.writeInt(skill3Points);
    	for (int x = 0; x < 6; x++)
    	{
    		savefile.writeInt(statusTime[x]);
    	}
    	
    	for (int x = 0; x < 9; x++)
    	{
    		for (int y = 0; y < 5; y++)
    		{
    			if (playerSpells[x][y] != null)
    			{
    				savefile.writeInt(playerSpells[x][y].id);
    			}
    			else savefile.writeInt(-1);
    		}
    	}
    	saveInventory(savefile);
    }
    
    /**
     * Replicates a player from a file on disk
     * @param loadfile The file to load the disk from
     * @return The replicated player. 
     * @throws IOException
     */
    public static Player loadPlayer(RandomAccessFile loadfile) throws IOException
    {
    	Player newPlayer = new Player(null, 0, 0);
    	newPlayer.l_x = loadfile.readInt();
    	newPlayer.l_z = loadfile.readInt();
    	newPlayer.l_rot = loadfile.readInt();
    	newPlayer.x = loadfile.readInt();
    	newPlayer.z = loadfile.readInt();
    	newPlayer.rot = loadfile.readInt();
    	newPlayer.currentMove = loadfile.readInt();
    	newPlayer.nextMove = loadfile.readInt();
    	newPlayer.moveTime = loadfile.readInt();
    	newPlayer.deathTime = loadfile.readInt();
    	newPlayer.collide = loadfile.readBoolean();
    	newPlayer.blackout = loadfile.readInt();
    	newPlayer.health = loadfile.readInt();
    	newPlayer.maxHealth = loadfile.readInt();
    	newPlayer.currentExp = loadfile.readInt();
    	newPlayer.expToNextLevel = loadfile.readInt();
    	newPlayer.totalExp = loadfile.readInt();
    	newPlayer.MP = loadfile.readInt();
    	newPlayer.MPMax = loadfile.readInt();
    	newPlayer.characterLevel = loadfile.readInt();
    	newPlayer.ticksAlive = loadfile.readInt();
    	newPlayer.armorLevel = loadfile.readInt();
    	newPlayer.hurtXa = loadfile.readDouble();
    	newPlayer.hurtYa = loadfile.readDouble();
    	newPlayer.hurtPow = loadfile.readInt();
    	newPlayer.arrows = loadfile.readInt();
    	newPlayer.shieldAC = loadfile.readInt();
    	newPlayer.statStr = loadfile.readInt();
    	newPlayer.statDex = loadfile.readInt();
    	newPlayer.statInt = loadfile.readInt();
    	newPlayer.statCon = loadfile.readInt();
    	newPlayer.skill1Points = loadfile.readInt();
    	newPlayer.skill2Points = loadfile.readInt();
    	newPlayer.skill3Points = loadfile.readInt();
    	for (int x = 0; x < 6; x++)
    	{
    		newPlayer.statusTime[x] = loadfile.readInt();
    	}
    	for (int x = 0; x < 9; x++)
    	{
    		for (int y = 0; y < 5; y++)
    		{
    			SpellListEntry spell = SpellListEntry.getItem(loadfile.readInt());
    			if (spell != null)
    			{
    				newPlayer.playerSpells[x][y] = spell;
    			}
    		}
    	}
    	newPlayer.loadInventory(loadfile);
    	
    	return newPlayer;
    }
    
    /**
     * Saves the player's inventory to disk.
     * @param savefile The file to save the IDs to.
     * @throws IOException
     */
    public void saveInventory(RandomAccessFile savefile) throws IOException
    {
    	for (int x = 0; x < 24; x++)
    	{
    		Item item = quickInv[x];
    		if (item != null)
    		{
    			savefile.writeInt(item.itemID);
    		}
    		else savefile.writeInt(-1);
    	}
    }
    
    /**
     * Loads the player's inventory from disk. 
     * @param loadfile The file to load the IDs from.
     * @throws IOException
     */
    public void loadInventory(RandomAccessFile loadfile) throws IOException
    {
    	for (int x = 0; x < 24; x++)
    	{
    		quickInv[x] = Item.getItem(loadfile.readInt());
    	}
    }

    /**
     * Moves the player
     * @param move The move to perform. Can be TURN_LEFT, TURN_RIGHT, MOVE_FORWARD, MOVE_BACK, MOVE_LEFT, or MOVE_RIGHT
     */
    public void move(int move) {
        l_x = x;
        l_z = z;
        l_rot = rot;

        if (move == TURN_LEFT) {
            rot = (rot + 1) & 3;
        } else if (move == TURN_RIGHT) {
            rot = (rot - 1) & 3;
        } else if (move == MOVE_FORWARD) {
            if (rot == 0) z++;
            if (rot == 1) x++;
            if (rot == 2) z--;
            if (rot == 3) x--;
        } else if (move == MOVE_BACK) {
            if (rot == 0) z--;
            if (rot == 1) x--;
            if (rot == 2) z++;
            if (rot == 3) x++;
        } else if (move == MOVE_LEFT) {
            if (rot == 0) x++;
            if (rot == 1) z--;
            if (rot == 2) x--;
            if (rot == 3) z++;
        } else if (move == MOVE_RIGHT) {
            if (rot == 0) x--;
            if (rot == 1) z++;
            if (rot == 2) x++;
            if (rot == 3) z--;
        }

        collide = false;

        if (x != l_x || z != l_z) {
            Tile tile = level.getTile(x, z);
            if (tile.blocks(this, -1)) {
                int tmp1 = x;
                int tmp2 = z;
                x = l_x;
                z = l_z;
                l_x = tmp1;
                l_z = tmp2;
                collide = true;
            } else {
                level.getTile(l_x, l_z).removeEntity(-1, this);
                tile.addEntity(-1, this);
            }
        }
    }

    /**
     * Performs all player actions per tick
     */
    public void tick() 
    {
    	//debug = true;
    	//quickInv[6] = Item.blueGem;
    	//quickInv[7] = Item.pinkGem;
    	//quickInv[8] = Item.cyanGem;
        if (statusTime[STATUS_HOLD] > 0)
        {
        	nextMove = 0;
        	moveTime = 0;
        }
        if (holdInflictor == null)
        {
        	statusTime[STATUS_HOLD] = 0;
        }
        else if (holdInflictor.isDead)
        {
        	statusTime[STATUS_HOLD] = 0;
        }
    	
    	if (blackout > 0) blackout--;
        else 
        {
            if (hurtPow > 0) hurtPow--;
            if (moveTime > 0) 
            {
                moveTime--;  
                if (moveTime == TICKS_PER_MOVE / 2) 
                {
                    level.getTile(x, z).stepOn(this);
                }
            }
            if (health <= 0) 
            {
                if (deathTime < 60) deathTime++;
            } 
            else 
            {
                if (moveTime <= 0 && nextMove >= 0 && blackout == 0) 
                {
                    currentMove = nextMove;
                    nextMove = -1;
                    move(currentMove);
                    moveTime = TICKS_PER_MOVE;
                }
            }
        }
        ticksAlive++;
        if (ticksAlive % 200 == 0 && statusTime[STATUS_POISON] > 0)
        {
        	health--;
        }
        
        
        if (debug)
        {
	        healMP(1);
	        heal(1);
        }

    	for (int x = 0; x < 6; x++)
    	{
	        if (statusTime[x] > 0)
	        {
	        	statusTime[x]--;
	        }
	        if (enchantmentTime[x] > 0)
	        {
	        	enchantmentTime[x]--;
	        	if (enchantmentTime[x] == 0)
	        	{
	        		addString("The surge of power begins to vanish...");
	        	}
	        }
    	}
    }

    /**
     * Resets the camera angle
     * @param screen The current game screen
     */
    public void setCamera(Screen screen) {
        double yy = 0;

        double hp = hurtPow / 10.0;
        double dd = Math.sin(hp*hp*Math.PI)*0.1;
        double hx = hurtXa * dd;
        double hy = 0;
        double hz = hurtYa * dd;

        if (deathTime > 0) {
            yy = yy - deathTime * 0.01 + deathTime * deathTime * 0.001;
            if (yy > 0.3) yy = 0.3;
        }
        //        yy = +0.3;
        if (moveTime > 0) {
            double lerp = 1 - moveTime / (double) TICKS_PER_MOVE;
            if (collide) {
                lerp = 1 - (1 - lerp) * (1 - lerp);
                if (lerp < 0.5) lerp = 1 - lerp;
                lerp = lerp * 0.25 + 0.75;
            }

            lerp = ((-Math.cos(lerp * Math.PI) * 0.5 + 0.5) + lerp * lerp) / 2;
            double xx = l_x + (x - l_x) * lerp;
            double zz = l_z + (z - l_z) * lerp;

            yy -= Math.sin(lerp * Math.PI) * 0.01;

            double rotDiff = rot - l_rot;
            if (rotDiff > 2) rotDiff -= 4;
            if (rotDiff < -2) rotDiff += 4;
            double rrot = l_rot + (rotDiff) * lerp;
            screen.setCamera(xx + 0.5 + hx, yy + 0.35 + hy, zz + 0.5 + hz, rrot * Math.PI * 2 / 4);
        } else {
            screen.setCamera(x + 0.5 + hx, yy + 0.35 + hy, z + 0.5 + hz, rot * Math.PI * 2 / 4);
        }
    }

    /**
     * Sets the next move to be performed when the current move finishes
     * @param move The move to perform. Can be TURN_LEFT, TURN_RIGHT, MOVE_FORWARD, MOVE_BACK, MOVE_LEFT, or MOVE_RIGHT
     */
    public void queueMove(int move) {
        nextMove = move;
    }

    public boolean isSolid() {
        return true;
    }

    /**
     * "Jiggles" the player when they get hit
     * @param xa the amount of force in the x axis
     * @param ya the amount of force in the y axis
     */
    public void setHurtAnim(int xa, int ya) {
        hurtPow = 10;
        hurtXa = xa;
        hurtYa = ya;
    }
    
    public boolean hurt(int damage)
    {
    	if (enchantmentTime[ENCHANT_DEFEND] > 0)
    	{
    		damage /= 2;
    	}
    	health -= damage;
    	return false;
    }
    
    /**
     * Rewards EXP for a kill, and handles leveling
     * @param amount the amount of EXP to give
     */
    public void rewardExp(int amount)
    {
    	currentExp += amount;
    	totalExp += amount;
    	if (currentExp >= expToNextLevel)
    	{
    		currentExp -= expToNextLevel;
    		characterLevel++;
    		expToNextLevel += 2 * (characterLevel * characterLevel);
    		maxHealth += random.nextInt(4) + 1;;
    		int newSkillPoints = random.nextInt(5) + 1;
    		currentSkillPoints += newSkillPoints;
    		int amountofMPUp = random.nextInt(10) + 1;
    		amountofMPUp -= (random.nextInt(100 - getSkill(PlayerSkill.MAGIC)) / 20);
    		if (amountofMPUp < 1)
    		{
    			amountofMPUp = 1;
    		}
    		MPMax += amountofMPUp;
    		String string = String.format("You have leveled to %d! XP for next: %d, gained %d SP", characterLevel, expToNextLevel, newSkillPoints);
    		addString(string);
    	}
    }
    
    /**
     * Gets the amount of skill points in a player skill
     * @param skill The skill to get the amount of points of
     * @return The amount of points in the specified skill
     */
    public int getSkill(PlayerSkill skill)
    {
    	if (skill == skill1)
    	{
    		return skill1Points;
    	}
    	else if (skill == skill2)
    	{
    		return skill2Points;
    	}
    	else if (skill == skill3)
    	{
    		return skill3Points;
    	}
    	return 1;
    }
    
    /**
     * Heals the player
     * @param amount The amount of HP to heal
     */
    public void heal(int amount)
    {
        if (health < maxHealth)
        {
        	health += amount;
        	if (health > maxHealth)
        	{
        		health = maxHealth;
        	}
        }
    }
    
    /**
     * Gives the player additional MP
     * @param amount The amount of MP to grant
     */
    public void healMP(int amount)
    {
        if (MP < MPMax)
        {
        	MP += amount;
        	if (MP > MPMax)
        	{
        		MP = MPMax;
        	}
        }
    }
    
    /**
     * Checks if the player has any statuses
     * @return true if the player does
     */
    public boolean hasAnyStatus()
    {
    	for (int x = 0; x < 6; x++)
    	{
	        if (statusTime[x] > 0)
	        {
	        	return true;
	        }
    	}
    	return false;
    }
    
    /**
     * Gives the player a new spell. Will not register if the spell is already registered
     * @param spell The spell to register.
     * @return Whether the spell was added or not
     */
    public boolean registerSpell(SpellListEntry spell)
    {
    	for (int x = 0; x < 5; x++)
    	{
    		for (int y = 0; y < 9; y++)
    		{
    			if (playerSpells[y][x] == null)
    			{
    				playerSpells[y][x] = spell;
    				System.out.printf("registered spell %s\n", spell.name);
    				return true;
    			}
    			if (playerSpells[y][x] == spell)
    			{
    				return false;
    			}
    		}
    	}
    	return false;
    }
    
    /*public void save(RandomAccessFile savefile)
    	throws IOException
	{
    	//needs special saving routines, do nothing
	}*/
    
    public void inflictStatus(int status)
    {
    	statusTime[status] = (random.nextInt(5)*100) + 1000;
    }
	
    /**
     * Adds a new message to the player's log
     * @param string The message to add
     */
    public void addString(String string)
	{
		currentStrings[1] = currentStrings[0];
		currentStrings[0] = string;
	}
}
