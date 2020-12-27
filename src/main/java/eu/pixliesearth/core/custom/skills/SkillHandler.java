package eu.pixliesearth.core.custom.skills;

import eu.pixliesearth.Main;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class SkillHandler implements Serializable {
	
	private static final long serialVersionUID = -7062481020279469116L;

	private static SkillHandler skillHandler = Main.getInstance().getSkillHandler();
	
	public static SkillHandler getSkillHandler() {
		return skillHandler;
	}
	
	public static void setSkills(SkillHandler handler) {
		skillHandler = handler;
	}

	/**
	 * A map containing a players skill levels
	 */
	private final Map<UUID, Map<String, Integer>> skillMap = new ConcurrentHashMap<>();
	/**
	 * A set of all {@link Skill}'s
	 */
	private final Set<Skill> skills = new HashSet<>();
	
	public void createSkillsFor(UUID uuid) {
		if (this.skillMap.containsKey(uuid)) return; // Don't override
		Map<String, Integer> map = new HashMap<>();
		for (Skill skill : skills) {
			map.put(skill.getSkillUUID(), 0);
		}
		this.skillMap.put(uuid, map);
	}
	
	public Set<Skill> getSkills() {
		return this.skills;
	}
	
	public void registerSkill(Skill skill) {
		skills.add(skill);
	}
	
	public void addXPTo(UUID uuid, String skillUUID, int amount) {
		skillMap.get(uuid).put(skillUUID, skillMap.get(uuid).get(skillUUID)+amount);
	}
	
	public void removeXPFrom(UUID uuid, String skillUUID, int amount) {
		addXPTo(uuid, skillUUID, -amount);
	}
	
	public Skill getSkillFromUUID(String uuid) {
		for (Skill skill : skills) {
			if (skill.getSkillUUID().equals(uuid)) {
				return skill;
			}
		}
		return null;
	}
	
	public int getXPOf(UUID uuid, String skillUUID) {
		for (Entry<String, Integer> entry : skillMap.get(uuid).entrySet()) {
			if (entry.getKey().equals(skillUUID)) {
				return entry.getValue();
			}
		}
		return -1;
	}
	
	public int getLevelOf(UUID uuid, String skillUUID) {
		for (Entry<String, Integer> entry : skillMap.get(uuid).entrySet()) {
			if (entry.getKey().equals(skillUUID)) {
				return getSkillFromUUID(skillUUID).getLevelFromExperience(entry.getValue());
			}
		}
		return -1;
	}
	
}
