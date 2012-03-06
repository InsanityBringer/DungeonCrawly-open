package com.mojang.ld19.mob;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import com.mojang.ld19.DamageType;
import com.mojang.ld19.level.Level;
import com.mojang.ld19.level.Tile;
import com.mojang.ld19.level.pathfinder.Node;
import com.mojang.ld19.level.pathfinder.Pathfinder;
import com.mojang.ld19.magic.PoisonCloud;

public class AdvancedMob extends Mob
{
	protected boolean pathNeedsUpdate = true;
	protected int restTime = 0;
	protected int currentNode = 0;
	protected ArrayList<Node> path = null;
	int currentPathDestX = 0; int currentPathDestY = 0;
	protected boolean canTeleport = false;
	protected int teleportTime = 0;
	public AdvancedMob()
	{
		super();
		name = "AdvancedMob";
		perfersToBeCentered = true;
		this.x = 0.5;
		this.y = 0.5; 
		this.z = 0.5;
	}
	
    public void tick(Level level) 
    {
    	//health--;
    	target = level.player;
    	target = level.player;
    	if (pathNeedsUpdate)
    	{
    		path = (ArrayList<Node>) Pathfinder.getPath(target.location.x, target.location.y, level, this);
    		currentNode = 1;
    		currentPathDestX = 0;
    		currentPathDestY = 0;
    		pathNeedsUpdate = false;
    	}
        if (blinkTime>0) blinkTime--;
        if (critTime>0) critTime--;
        if (randomWalkTime > 0) randomWalkTime--;
        if (attackTime > 0) 
        {
            if (attackTime == ATTACK_DURATION / 2) 
            {
                int xt = xLast = location.x;
                int yt = yLast = location.y;

                int xd = level.player.x - xt;
                int yd = level.player.z - yt;

                if (xd * xd + yd * yd == 1) 
                {
                    attack(level.player, xd, yd);
                }
            }
            attackTime--;
        }
        if (castTime > 0)
        {
        	if (castTime == ATTACK_DURATION / 2) 
            {
        		cast(level.player, castRot);
        		timeToNextCast = 0;
            }
            castTime--;
        }
        if (hurtTime == 0) 
        {
            if (moveTime > 0) moveTime--;
        } 
        else 
        {
            attackTime = 0;
        }

        if (attackTime == 0 && hurtTime == 0 && moveTime == 0 && restTime <= 0) 
        {
        	if (path != null)
        	{
        		int xt = xLast = location.x;
                int yt = yLast = location.y;

                int xd = level.player.x - xt;
                int yd = level.player.z - yt;

                if (xd * xd + yd * yd == 1) 
                {
                	attackTime = ATTACK_DURATION;
                    //moveTime = ATTACK_DELAY;
                    restTime = 10 + ATTACK_DURATION;
                }
                
                else if (canCast && castTime <= 0)
                {
                    int distx2 = getDistX(level.player);
                    int disty2 = getDistY(level.player);
                    
                    if (timeToNextCast >= 100 && attackTime == 0 && MP > 0)
                    {
                        if (location.x == level.player.location.x)
                        {
                        	if (Math.abs(disty2) < 5)
                        	{
                        		castRot = 0;
                        		boolean castCheck = getLineUnobstructedX(level, level.player, 1);
                        		if (disty2 < 0)
                        		{
                        			castRot = 2;
                        			castCheck = getLineUnobstructedX(level, level.player, -1);
                        		}
                        		if (castCheck)
                        		{
                        			castTime = ATTACK_DURATION;
                        		}
                        	}
                        }
                        else if (location.y == level.player.location.y)
                        {
                        	if (Math.abs(distx2) < 5)
                        	{
                        		castRot = 1;
                        		boolean castCheck = getLineUnobstructedX(level, level.player, 1);
                        		if (distx2 < 0)
                        		{
                        			castCheck = getLineUnobstructedX(level, level.player, -1);
                        			castRot = 3;
                        		}
                        		if (castCheck)
                        		{
                        			castTime = ATTACK_DURATION;
                        		}
                        	}
                        }
                    }
                }
                
                if (teleportTime > 200 && (xd * xd + yd * yd > 4) && canTeleport && moveTime == 0)
                {
                	int randDestX = target.location.x + (random.nextInt(4) - 2);
                	int randDestY = target.location.y + (random.nextInt(4) - 2);
                	Tile target = level.getTile(randDestX, randDestY);
                	
                	if (!target.blocks(this, -1)) 
                	{
	                	if ((randDestX >= 0 && randDestX < 64) && (randDestY >= 0 && randDestY < 64))
	                	{
	                		ArrayList<Node> tppath = (ArrayList<Node>) Pathfinder.getPath(randDestX, randDestY, level, this);
	                		if (tppath != null)
	                		{
	                			Node destNode = tppath.get(tppath.size()-1);
	                			
	                			if (!target.blocks(this, -1)) 
	        		            {
	                				critFX();
	        		                xLast = destNode.x;
	        		                yLast = destNode.y;
	        		                moveTime = 0;
	        		                location.removeEntity(corner, this);
	        		                target.addEntity(-1, this);
	        		                critFX();
	        		                restTime = 60;
	        		                pathNeedsUpdate = true;
	        		            } 
	                			teleportTime = 0;
	                		}
	                	}
	                	else
	                	{
	                		teleportTime = 0;
	                	}
                	}
                }
                else if (attackTime == 0 && castTime == 0)
                {
		        	Node nextNode = path.get(currentNode);
		        	Tile target = level.getTile(nextNode.x, nextNode.y);
		            if (!target.blocks(this, -1) && !checkForPoison(target)) 
		            {
		                location.removeEntity(corner, this);
		                target.addEntity(-1, this);
		                currentNode++;
		            } 
		            else
		            {
		            	pathNeedsUpdate = true;
		            }
		            restTime = 50;
		            moveTime = 40;
                }
        	}
        	else
        	{
        		restTime = 80;
        	}
        }

        if (moveTime > 0) 
        {
            double xd = (location.x - xLast);
            double yd = (location.y - yLast);

            this.x = xd * (-moveTime / 40.0) + .5;
            this.z = yd * (-moveTime / 40.0) + .5;
        } 
        else 
        {
            this.x = 0.5;
            this.z = 0.5;
        }

        if (health <= 0 && hurtTime == 0) 
        {
        	level.player.rewardExp(exp);
        	level.player.addString(String.format("Killed a %s!", name));
        	onDeath();
            remove();
            return;
        }
        
        if (ticksAlive % 40 == 0 && poisonTime > 0)
        {
        	hurt(1, 0, DamageType.POISON);
        }
        if (poisonTime > 0)
        {
        	poisonTime--;
        }
        
        super.tick(level);

        int frame = 0;
        if (attackTime > 0) frame = 2;
        if (attackTime > ATTACK_DURATION / 2) frame = 1;
        if (castTime > 0) frame = 2;
        if (castTime > ATTACK_DURATION / 2) frame = 1;
        if (hurtTime > 0) frame = 3;
        tint = 0;
        if (attackTime > 0 && attackTime <= ATTACK_DURATION / 2) 
        {
            if (attackTime / 4 % 2 == 1) 
            {
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
        
        if (ticksAlive % 140 == 0)
        {
        	pathNeedsUpdate = true;
        }
        
        if (path != null)
        {
	        if (currentNode == path.size())
	        {
	        	pathNeedsUpdate = true;
	        }
        }
        
        if (location != null)
        {
	        if (location.x == currentPathDestX && location.y == currentPathDestY)
	        {
	        	pathNeedsUpdate = true;
	        }
        }
        
        timeToNextCast++;
        ticksAlive++;
        restTime--;
        teleportTime++;
        //System.out.printf("my corner: %d\n", corner);
    }
    
    public boolean checkForPoison(Tile tile)
    {
		for (int i = 0; i < tile.entities.size(); i++)
		{
			if (tile.entities.get(i) instanceof PoisonCloud)
			{
				return true;
			}
		}
    	return false;
    }
    
    public void save(RandomAccessFile savefile)
        	throws IOException
        {
        	super.save(savefile);
        	savefile.writeInt(restTime);
        	savefile.writeInt(teleportTime);
        }

    public void load(RandomAccessFile loadfile) throws IOException
    {
    	super.load(loadfile);
    	restTime = loadfile.readInt();
    	teleportTime = loadfile.readInt();

    }
}
