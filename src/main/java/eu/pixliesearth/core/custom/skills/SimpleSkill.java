package eu.pixliesearth.core.custom.skills;

public class SimpleSkill extends Skill {
	
	private static final long serialVersionUID = -4881018984700621605L;
	
	private final String name;
	private final String UUID;
	private final int maxLevel;
	
	public SimpleSkill(String name, String UUID, int maxLevel) {
		this.name = name;
		this.UUID = UUID;
		this.maxLevel = maxLevel;
	}
	
	public SimpleSkill(String name, int maxLevel) {
		this(name, "Pixlies:"+name, maxLevel);
	}
	
	public SimpleSkill(String name) {
		this(name, 100);
	}
	
	@Override
	public String getSkillName() {
		return this.name;
	}

	@Override
	public int getMaxSkillLevel() {
		return this.maxLevel;
	}

	@Override
	public String getSkillUUID() {
		return this.UUID;
	}
	
}
