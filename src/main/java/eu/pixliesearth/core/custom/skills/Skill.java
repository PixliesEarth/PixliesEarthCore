package eu.pixliesearth.core.custom.skills;

import java.io.Serializable;

public abstract class Skill implements Serializable {
	
	private static final long serialVersionUID = 3944761533255069214L;

	public abstract String getSkillName();

	public abstract String getDescription();
	
	public abstract int getMaxSkillLevel();
	
	public abstract String getSkillUUID();
	
	public int getLevelFromExperience(int experience) {
		int level = 1;
		int xpForNextLevel = (level*20)+1000;
		int currentXP = experience;
		while (xpForNextLevel<=currentXP) {
			level++;
			currentXP -= (level*20)+1000;
		}
		return Math.min(level - 1, getMaxSkillLevel());
	}

	public int getExperienceFromLevel(int level) {
		int experience = 0;
		while (level >= 0) {
			experience += ((level*20)+1000);
			level--;
		}
		return experience;
	}
	
}
