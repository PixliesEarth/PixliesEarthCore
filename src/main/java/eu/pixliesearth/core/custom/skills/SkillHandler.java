package eu.pixliesearth.core.custom.skills;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class SkillHandler implements Serializable {
	
	private static final long serialVersionUID = -7062481020279469116L;

	private static SkillHandler skillHandler;
	
	public static SkillHandler getSkillHandler() {
		if (skillHandler==null) {
			skillHandler = new SkillHandler();
		}
		return skillHandler;
	}
	
	public static void setSkills(SkillHandler handler) {
		skillHandler = handler;
	}

	/**
	 * A map containing a players skill levels
	 */
	public static final Map<UUID, Map<String, Integer>> skillMap = new ConcurrentHashMap<>();
	/**
	 * A set of all {@link Skill}'s
	 */
	public static final Set<Skill> skills = new HashSet<>();
	
	public void createSkillsFor(UUID uuid) {
		if (skillMap.containsKey(uuid)) return; // Don't override
		Map<String, Integer> map = this.skillMap.getOrDefault(uuid, new HashMap<>());
		for (Skill skill : skills) {
			if (!map.containsKey(skill.getSkillUUID())) {
				map.put(skill.getSkillUUID(), 0);
			}
		}
		skillMap.put(uuid, map);
	}
	
	public Set<Skill> getSkills() {
		return skills;
	}
	
	public void registerSkill(Skill skill) {
		skills.removeIf((s) -> s.getSkillUUID().equals(skill.getSkillUUID()));
		skills.add(skill);
	}
	
	public void addXPTo(UUID uuid, String skillUUID, int amount) {
		skillMap.get(uuid).put(skillUUID, skillMap.get(uuid).getOrDefault(skillUUID, 0)+amount);
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
	
	public static Map<String, List<UUID>> leaderboardMap = new HashMap<>();
	
	public List<UUID> getLeaderboardOf(String skillUUID) {
		return leaderboardMap.get(skillUUID);
	}

}
