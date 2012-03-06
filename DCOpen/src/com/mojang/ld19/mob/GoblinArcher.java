package com.mojang.ld19.mob;

import java.io.*;

import com.mojang.ld19.Art;
import com.mojang.ld19.Player;
import com.mojang.ld19.item.Item;
import com.mojang.ld19.level.Level;
import com.mojang.ld19.level.Tile;
import com.mojang.ld19.level.object.ArrowEnemy;
import com.mojang.ld19.level.object.ItemEntity;

public class GoblinArcher extends SmallMob
{
	protected int arrows = 12;
	protected int arrowTime = 0; 
	protected int attackRot = -1;
	public GoblinArcher()
	{
		super();
		name = "Goblin Archer";
		armorClass = 4;
		health = 7;
		anims = Art.goblinArcher;
		exp = 8;
	}
	
	public void save(RandomAccessFile savefile)
		throws IOException
	{
		super.save(savefile);
		savefile.writeInt(arrows);
		savefile.writeInt(arrowTime);
		savefile.writeInt(attackRot);
	}
	
	public void load(RandomAccessFile loadfile) throws IOException
	{
		super.load(loadfile);
		arrows = loadfile.readInt();
		arrowTime = loadfile.readInt();
		attackRot = loadfile.readInt();
	}
	
    public void shootArrow(Player player, int xa, int ya) 
    {
    	location.addEntity(-1, new ArrowEnemy(xa, 10, 1, 3, 0));
    	arrows--;
    }
    
    public void tick(Level level) 
    {
    	if (arrows == 0)
    	{
    		anims = Art.goblin;
    		super.tick(level);
    		return;
    	}
    	target = level.player;
        if (blinkTime>0) blinkTime--;
        if (critTime > 0) critTime--;
        if (randomWalkTime > 0) randomWalkTime--;
        if (attackTime > 0) 
        {
            if (attackTime == ATTACK_DURATION / 2) 
            {
            	shootArrow(level.player, attackRot, 0);
        		arrowTime = 0;
            }
            attackTime--;
        }
        if (hurtTime == 0) {
            if (moveTime > 0) moveTime--;
        } else {
            attackTime = 0;
        }

        //        setFrame(random.nextInt(2));

        if (attackTime == 0 && hurtTime == 0 && moveTime == 0 && random.nextInt(10) == 0) {
            int rot = -1;
            int xt = xLast = location.x * 2 + corner % 2;
            int yt = yLast = location.y * 2 + corner / 2;

            if (randomWalkTime > 0) {
                rot = random.nextInt(4);
            } 
            else 
            {

                int xd = level.player.x * 2 - xt;
                int yd = level.player.z * 2 - yt;
                if (xd < 0) xd++;
                if (yd < 0) yd++;

                if (random.nextBoolean()) {
                    if (yd > 0) rot = 0;
                    if (yd < 0) rot = 2;
                    if (xd > 0) rot = 1;
                    if (xd < 0) rot = 3;
                } else {
                    if (xd > 0) rot = 1;
                    if (xd < 0) rot = 3;
                    if (yd > 0) rot = 0;
                    if (yd < 0) rot = 2;
                }

                /*if (xd * xd + yd * yd == 1) {
                    moveTime = ATTACK_DELAY;
                    rot = -1;
                }*/
                
                int distx2 = getDistX(level.player);
                int disty2 = getDistY(level.player);
                
            	//System.out.printf("dist to player: %d, %d\n", distx, disty);
                if (arrowTime >= 60 && arrows > 0 && attackTime == 0)
                {
	                /*if (Math.abs(distx2) < 5 && location.x == level.player.location.x)
	                {
	                    attackTime = ATTACK_DURATION;
	                }
	                else if (Math.abs(disty2) < 5 && location.y == level.player.location.y)
	                {
	                    attackTime = ATTACK_DURATION;
	                }*/
                    if (location.x == level.player.location.x)
                    {
                    	//System.out.printf("distx: %d\n", getDistX(level.player));
                    	if (Math.abs(disty2) < 5)
                    	{
                    		attackRot = 0;
                    		if (disty2 < 0)
                    		{
                    			attackRot = 2;
                    		}
                    		attackTime = ATTACK_DURATION;
                    	}
                    }
                    else if (location.y == level.player.location.y)
                    {
                		//System.out.printf("disty: %d\n", getDistY(level.player));
                    	if (Math.abs(distx2) < 5)
                    	{
                    		attackRot = 1;
                    		if (distx2 < 0)
                    		{
                    			attackRot = 3;
                    		}
                    		attackTime = ATTACK_DURATION;
                    	}
                    }
                }
            }

            if (rot >= 0) {
                if (rot == 0) yt++;
                if (rot == 1) xt++;
                if (rot == 2) yt--;
                if (rot == 3) xt--;
                Tile target = level.getTile(xt / 2, yt / 2);
                int targetCorner = xt % 2 + yt % 2 * 2;
                if (!target.blocks(this, targetCorner)) {
                    location.removeEntity(corner, this);
                    target.addEntity(targetCorner, this);
                } else {
                    if (randomWalkTime == 0) {
                        randomWalkTime = 60 * 2;
                    }
                }

                moveTime = 20;
            }
        }

        if (moveTime > 0) {
            double xd = ((location.x * 2 + corner % 2) - xLast) * 0.5;
            double yd = ((location.y * 2 + corner / 2) - yLast) * 0.5;

            this.x = xd * (-moveTime / 20.0);
            this.z = yd * (-moveTime / 20.0);
        } else {
            this.x = 0;
            this.z = 0;
        }

        if (health <= 0 && hurtTime == 0) {
        	level.player.rewardExp(exp);
        	level.player.addString(String.format("Killed a %s!", name));
        	onDeath();
            remove();
        }
        if (hurtTime > 0) {
            hurtTime--;
        }

        int frame = 0;
        if (attackTime > 0) frame = 2;
        if (attackTime > ATTACK_DURATION / 2) frame = 1;
        if (hurtTime > 0) frame = 3;
        tint = 0;
        if (attackTime > 0 && attackTime <= ATTACK_DURATION / 2) {
            if (attackTime / 4 % 2 == 1) {
                tint = 0xff0000;
            }
        }
        if (blinkTime / 4 % 2 == 1 && critTime == 0) 
        {
            tint = 0xffffff;
        }
        else if (critTime / 4 % 2 == 1)
        {
        	tint = 0x8080ff;
        }
        setFrame(frame);
        arrowTime++;
    }
    
    public void onDeath()
    {
    	for (int x = 0; x < arrows / 2; x++)
    	{
    		location.addEntity(corner, new ItemEntity(Item.arrow));
    	}
    }
    
    public int getID()
    {
    	return 5;
    }
}
