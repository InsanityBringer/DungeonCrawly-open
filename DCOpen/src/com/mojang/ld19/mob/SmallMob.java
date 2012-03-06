package com.mojang.ld19.mob;

import com.mojang.ld19.DamageType;
import com.mojang.ld19.level.Level;
import com.mojang.ld19.level.Tile;
import com.mojang.ld19.level.pathfinder.Pathfinder;

public class SmallMob extends Mob 
{
	protected int moveDuration = 20;
	public void tick (Level level)
	{
    	ticksAlive++;
    	
    	target = level.player;
    	
        if (blinkTime>0) blinkTime--;
        if (critTime>0) critTime--;
        if (randomWalkTime > 0) randomWalkTime--;
        if (attackTime > 0) 
        {
            if (attackTime == ATTACK_DURATION / 2) 
            {
                int xt = xLast = location.x * 2 + corner % 2;
                int yt = yLast = location.y * 2 + corner / 2;

                int xd = level.player.x * 2 - xt;
                int yd = level.player.z * 2 - yt;
                if (xd < 0) xd++;
                if (yd < 0) yd++;

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
            } else {

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

                if (xd * xd + yd * yd == 1) {
                    attackTime = ATTACK_DURATION;
                    moveTime = ATTACK_DELAY;
                    rot = -1;
                }
                
                if (attackTime == 0 && canCast)
                {
                    int distx2 = getDistX(level.player);
                    int disty2 = getDistY(level.player);
                    
                    if (timeToNextCast >= 100 && attackTime == 0)
                    {
                        if (location.x == level.player.location.x)
                        {
                        	//System.out.printf("distx: %d\n", getDistX(level.player));
                        	if (Math.abs(disty2) < 5)
                        	{
                        		castRot = 0;
                        		if (disty2 < 0)
                        		{
                        			castRot = 2;
                        		}
                        		castTime = ATTACK_DURATION;
                        	}
                        }
                        else if (location.y == level.player.location.y)
                        {
                    		//System.out.printf("disty: %d\n", getDistY(level.player));
                        	if (Math.abs(distx2) < 5)
                        	{
                        		castRot = 1;
                        		if (distx2 < 0)
                        		{
                        			castRot = 3;
                        		}
                        		castTime = ATTACK_DURATION;
                        	}
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
                if (!target.blocks(this, targetCorner)) 
                {
                    location.removeEntity(corner, this);
                    target.addEntity(targetCorner, this);
                } 
                else 
                {
                    if (randomWalkTime == 0) 
                    {
                        randomWalkTime = 60 * 2;
                    }
                }

                moveTime = moveDuration;
            }
        }

        if (moveTime > 0) {
            double xd = ((location.x * 2 + corner % 2) - xLast) * 0.5;
            double yd = ((location.y * 2 + corner / 2) - yLast) * 0.5;

            this.x = xd * (-moveTime / (double)moveDuration);
            this.z = yd * (-moveTime / (double)moveDuration);
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
        
        if (ticksAlive % 100 == 0 && poisonTime > 0)
        {
        	hurt(random.nextInt(3) + 1, 0, DamageType.POISON);
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
        timeToNextCast++;
	}
}
