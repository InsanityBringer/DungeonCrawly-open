package com.mojang.ld19.magic;

public class MagicSkillsManager 
{
	public final int SKILL_SUPPORT = 0;
	public final int SKILL_MIND = 1;
	public final int SKILL_CHAOS = 2;
	public final int SKILL_CREATION = 3;
	public final int SKILL_STATE = 4;
	
	public final String SKILL_SUPPORT_DESC = "Supportive skills";
	public final String SKILL_MIND_DESC = "Mental abilities";
	public final String SKILL_CHAOS_DESC = "Chaotic generation";
	public final String SKILL_CREATION_DESC = "Element Creation";
	public final String SKILL_STATE_DESC = "State manipulation";
	
	public int[] skillsPool = new int[5];
	public int[] skillsExpPool = new int[5];
}
